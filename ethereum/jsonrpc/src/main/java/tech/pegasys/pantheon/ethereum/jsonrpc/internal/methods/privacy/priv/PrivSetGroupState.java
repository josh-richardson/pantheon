package tech.pegasys.pantheon.ethereum.jsonrpc.internal.methods.privacy.priv;

import org.apache.logging.log4j.Logger;
import tech.pegasys.pantheon.ethereum.core.Hash;
import tech.pegasys.pantheon.ethereum.core.PrivacyParameters;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.JsonRpcRequest;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.methods.JsonRpcMethod;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.parameters.JsonRpcParameter;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcError;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcResponse;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcSuccessResponse;
import tech.pegasys.pantheon.util.bytes.BytesValues;

import static org.apache.logging.log4j.LogManager.getLogger;

public class PrivSetGroupState implements JsonRpcMethod {

    private static final Logger LOG = getLogger();
    private PrivacyParameters privacyParameters;
    private final JsonRpcParameter parameters;

    public PrivSetGroupState(
            final PrivacyParameters privacyParameters,
            final JsonRpcParameter parameters) {
        this.privacyParameters = privacyParameters;
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public JsonRpcResponse response(JsonRpcRequest request) {
        final String privacyGroupId = parameters.required(request.getParams(), 0, String.class);
        final String privateState = parameters.required(request.getParams(), 1, String.class);
        privacyParameters.getPrivateStateStorage().updater().putPrivateAccountState(BytesValues.fromBase64(privacyGroupId), Hash.fromHexString(privateState)).commit();
        privacyParameters.getPrivateTransactionStorage().updater();
//        privacyParameters.getPrivateWorldStateArchive().

        return new JsonRpcSuccessResponse(request.getId(), true);
    }
}