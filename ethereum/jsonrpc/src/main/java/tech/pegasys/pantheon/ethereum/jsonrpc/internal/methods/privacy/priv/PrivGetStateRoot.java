package tech.pegasys.pantheon.ethereum.jsonrpc.internal.methods.privacy.priv;

import org.apache.logging.log4j.Logger;
import tech.pegasys.pantheon.enclave.Enclave;
import tech.pegasys.pantheon.ethereum.core.PrivacyParameters;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.JsonRpcRequest;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.methods.JsonRpcMethod;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.parameters.JsonRpcParameter;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcError;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcResponse;
import tech.pegasys.pantheon.ethereum.jsonrpc.internal.response.JsonRpcSuccessResponse;
import tech.pegasys.pantheon.util.bytes.BytesValue;
import tech.pegasys.pantheon.util.bytes.BytesValues;

import static org.apache.logging.log4j.LogManager.getLogger;

public class PrivGetStateRoot implements JsonRpcMethod {

    private static final Logger LOG = getLogger();
    private PrivacyParameters privacyParameters;
    private final JsonRpcParameter parameters;

    public PrivGetStateRoot(
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
        var result = privacyParameters.getPrivateStateStorage().getPrivateAccountState(BytesValues.fromBase64(privacyGroupId));
        return result.map(hash -> new JsonRpcSuccessResponse(request.getId(), hash)).orElseGet(() -> new JsonRpcSuccessResponse(request.getId(), JsonRpcError.CANNOT_GET_STATE_ROOT));
    }
}
