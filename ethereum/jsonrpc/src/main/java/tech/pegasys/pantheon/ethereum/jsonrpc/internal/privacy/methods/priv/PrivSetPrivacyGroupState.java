/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.ethereum.jsonrpc.internal.privacy.methods.priv;

import static org.apache.logging.log4j.LogManager.getLogger;
import static tech.pegasys.pantheon.crypto.Hash.keccak256;

import tech.pegasys.pantheon.enclave.Enclave;
import tech.pegasys.pantheon.enclave.types.ReceiveRequest;
import tech.pegasys.pantheon.enclave.types.ReceiveResponse;
import tech.pegasys.pantheon.ethereum.core.BlockHeader;
import tech.pegasys.pantheon.ethereum.core.Hash;
import tech.pegasys.pantheon.ethereum.core.LogSeries;
import tech.pegasys.pantheon.ethereum.core.MutableWorldState;
import tech.pegasys.pantheon.ethereum.core.PrivacyParameters;
import tech.pegasys.pantheon.ethereum.core.WorldUpdater;
import tech.pegasys.pantheon.ethereum.debug.TraceOptions;
import tech.pegasys.pantheon.ethereum.jsonrpc.RpcMethod;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.JsonRpcRequest;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.methods.JsonRpcMethod;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.parameters.JsonRpcParameter;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.privacy.parameters.CommitmentPair;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.privacy.parameters.SetPrivacyGroupStateParameter;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.queries.BlockchainQueries;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.queries.TransactionWithMetadata;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcResponse;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcSuccessResponse;
import tech.pegasys.pantheon.ethereum.mainnet.MainnetTransactionProcessor;
import tech.pegasys.pantheon.ethereum.privacy.PrivateStateStorage;
import tech.pegasys.pantheon.ethereum.privacy.PrivateTransaction;
import tech.pegasys.pantheon.ethereum.privacy.PrivateTransactionProcessor;
import tech.pegasys.pantheon.ethereum.privacy.PrivateTransactionStorage;
import tech.pegasys.pantheon.ethereum.rlp.BytesValueRLPInput;
import tech.pegasys.pantheon.ethereum.rlp.RLP;
import tech.pegasys.pantheon.ethereum.vm.BlockHashLookup;
import tech.pegasys.pantheon.ethereum.vm.DebugOperationTracer;
import tech.pegasys.pantheon.ethereum.vm.OperationTracer;
import tech.pegasys.pantheon.ethereum.worldstate.WorldStateArchive;
import tech.pegasys.pantheon.util.bytes.Bytes32;
import tech.pegasys.pantheon.util.bytes.BytesValue;
import tech.pegasys.pantheon.util.bytes.BytesValues;

import org.apache.logging.log4j.Logger;

public class PrivSetPrivacyGroupState implements JsonRpcMethod {

  private static final Logger LOG = getLogger();
  private final Enclave enclave;
  private final String enclavePublicKey;
  private final PrivateStateStorage privateStateStorage;
  private final PrivateTransactionStorage privateTransactionStorage;
  private final WorldStateArchive privateWorldStateArchive;
  private final BlockchainQueries blockchainQueries;
  private final JsonRpcParameter parameters;
  private final MainnetTransactionProcessor mainnetTransactionProcessor;
  private final PrivateTransactionProcessor privateTransactionProcessor;
  private static final Hash EMPTY_ROOT_HASH = Hash.wrap(keccak256(RLP.NULL));

  public PrivSetPrivacyGroupState(
      final Enclave enclave,
      final PrivacyParameters privacyParameters,
      final JsonRpcParameter parameters,
      final BlockchainQueries blockchainQueries,
      final MainnetTransactionProcessor mainnetTransactionProcessor,
      final PrivateTransactionProcessor privateTransactionProcessor) {
    this.enclave = enclave;
    this.parameters = parameters;
    this.enclavePublicKey = privacyParameters.getEnclavePublicKey();
    this.privateStateStorage = privacyParameters.getPrivateStateStorage();
    this.privateTransactionStorage = privacyParameters.getPrivateTransactionStorage();
    this.privateWorldStateArchive = privacyParameters.getPrivateWorldStateArchive();
    this.blockchainQueries = blockchainQueries;
    this.mainnetTransactionProcessor = mainnetTransactionProcessor;
    this.privateTransactionProcessor = privateTransactionProcessor;
  }

  @Override
  public String getName() {
    return RpcMethod.PRIV_SET_PRIVACY_GROUP_STATE.getMethodName();
  }

  @Override
  public JsonRpcResponse response(final JsonRpcRequest request) {
    LOG.trace("Executing {}", RpcMethod.PRIV_SET_PRIVACY_GROUP_STATE.getMethodName());

    final SetPrivacyGroupStateParameter groupState =
        parameters.required(request.getParams(), 0, SetPrivacyGroupStateParameter.class);

    for (final CommitmentPair commitmentPair : groupState.payload()) {

      final ReceiveRequest enclaveRequest =
          new ReceiveRequest(commitmentPair.enclaveKey(), enclavePublicKey);
      ReceiveResponse receiveResponse = enclave.receive(enclaveRequest);

      final BytesValueRLPInput bytesValueRLPInput =
          new BytesValueRLPInput(BytesValues.fromBase64(receiveResponse.getPayload()), false);

      final PrivateTransaction privateTransaction = PrivateTransaction.readFrom(bytesValueRLPInput);

      var markerTransaction =
          blockchainQueries
              .transactionByHash(Hash.wrap(Bytes32.fromHexString(commitmentPair.markerTxHash())))
              .get();

      final WorldUpdater publicWorldState =
          blockchainQueries.getWorldState(markerTransaction.getBlockNumber()).get().updater();

      final BlockHeader normalBlockHeader =
          blockchainQueries.getBlockHeaderByNumber(markerTransaction.getBlockNumber()).get();
      final BlockHashLookup blockHashLookup =
          new BlockHashLookup(normalBlockHeader, blockchainQueries.getBlockchain());

      for (int i = 0; i < markerTransaction.getTransactionIndex(); i++) {
        TransactionWithMetadata transaction =
            blockchainQueries
                .transactionByBlockNumberAndIndex(markerTransaction.getBlockNumber(), i)
                .get();
        mainnetTransactionProcessor.processTransaction(
            blockchainQueries.getBlockchain(),
            publicWorldState.updater(),
            normalBlockHeader,
            transaction.getTransaction(),
            normalBlockHeader.getCoinbase(),
            OperationTracer.NO_TRACING,
            blockHashLookup,
            false);
        publicWorldState.commit();
      }

      final BytesValue privacyGroupId = BytesValues.fromBase64(receiveResponse.getPrivacyGroupId());

      // get the last world state root hash - or create a new one
      final Hash lastRootHash =
          privateStateStorage.getPrivateAccountState(privacyGroupId).orElse(EMPTY_ROOT_HASH);

      final MutableWorldState disposablePrivateState =
          privateWorldStateArchive.getMutable(lastRootHash).get();

      final WorldUpdater privateWorldStateUpdater = disposablePrivateState.updater();
      final PrivateTransactionProcessor.Result result =
          privateTransactionProcessor.processTransaction(
              blockchainQueries.getBlockchain(),
              publicWorldState.updater(),
              privateWorldStateUpdater,
              normalBlockHeader,
              privateTransaction,
              normalBlockHeader.getCoinbase(),
              new DebugOperationTracer(TraceOptions.DEFAULT),
              blockHashLookup,
              privacyGroupId);

      if (result.isInvalid() || !result.isSuccessful()) {
        LOG.error(
            "Failed to process the private transaction: {}",
            result.getValidationResult().getErrorMessage());

        /*todo: error here*/
      }

      LOG.trace(
          "Persisting private state {} for privacyGroup {}",
          disposablePrivateState.rootHash(),
          privacyGroupId);
      privateWorldStateUpdater.commit();
      disposablePrivateState.persist();

      final PrivateStateStorage.Updater privateStateUpdater = privateStateStorage.updater();
      privateStateUpdater.putPrivateAccountState(privacyGroupId, disposablePrivateState.rootHash());
      privateStateUpdater.commit();

      final Bytes32 txHash = keccak256(RLP.encode(privateTransaction::writeTo));
      final PrivateTransactionStorage.Updater privateUpdater = privateTransactionStorage.updater();
      final LogSeries logs = result.getLogs();
      if (!logs.isEmpty()) {
        privateUpdater.putTransactionLogs(txHash, result.getLogs());
      }
      privateUpdater.putTransactionResult(txHash, result.getOutput());
      privateUpdater.commit();
    }

    return new JsonRpcSuccessResponse(request.getId(), "maybe it worked?");
  }
}
