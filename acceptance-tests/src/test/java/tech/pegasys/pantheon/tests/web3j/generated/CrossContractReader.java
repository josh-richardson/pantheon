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
package tech.pegasys.pantheon.tests.web3j.generated;

import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
@SuppressWarnings("rawtypes")
public class CrossContractReader extends Contract {
  private static final String BINARY =
      "608060405234801561001057600080fd5b50610106806100206000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c8063a087a87e14602d575b600080fd5b605060048036036020811015604157600080fd5b50356001600160a01b03166062565b60408051918252519081900360200190f35b600080829050806001600160a01b0316633fa4f2456040518163ffffffff1660e01b815260040160206040518083038186803b15801560a057600080fd5b505afa15801560b3573d6000803e3d6000fd5b505050506040513d602081101560c857600080fd5b5051939250505056fea265627a7a723058207f9edd1fef554b145dd0c7153470dca7d8aac411e17dcdeeb2b6ea17b9f394a964736f6c634300050a0032";

  public static final String FUNC_READ = "read";

  @Deprecated
  protected CrossContractReader(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected CrossContractReader(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected CrossContractReader(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected CrossContractReader(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<BigInteger> read(String emitter_address) {
    final Function function =
        new Function(
            FUNC_READ,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(emitter_address)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  @Deprecated
  public static CrossContractReader load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new CrossContractReader(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static CrossContractReader load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new CrossContractReader(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static CrossContractReader load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new CrossContractReader(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static CrossContractReader load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new CrossContractReader(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<CrossContractReader> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        CrossContractReader.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<CrossContractReader> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(
        CrossContractReader.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<CrossContractReader> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        CrossContractReader.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<CrossContractReader> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        CrossContractReader.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }
}
