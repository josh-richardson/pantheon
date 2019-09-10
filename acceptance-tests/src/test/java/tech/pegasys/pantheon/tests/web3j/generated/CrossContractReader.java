package tech.pegasys.pantheon.tests.web3j.generated;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class CrossContractReader extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610391806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80635374ded214610046578063775c300c1461006e578063a087a87e14610076575b600080fd5b61006c6004803603602081101561005c57600080fd5b50356001600160a01b03166100ae565b005b61006c61010a565b61009c6004803603602081101561008c57600080fd5b50356001600160a01b0316610178565b60408051918252519081900360200190f35b6000819050806001600160a01b031663775c300c6040518163ffffffff1660e01b8152600401600060405180830381600087803b1580156100ee57600080fd5b505af1158015610102573d6000803e3d6000fd5b505050505050565b6000604051610118906101ea565b604051809103906000f080158015610134573d6000803e3d6000fd5b50604080516001600160a01b038316815290519192507f9ac6876e0aa40667ffeaa9b359b5ed924f4cdd0e029eb6e9c369e78c68f711fb919081900360200190a150565b600080829050806001600160a01b0316633fa4f2456040518163ffffffff1660e01b815260040160206040518083038186803b1580156101b757600080fd5b505afa1580156101cb573d6000803e3d6000fd5b505050506040513d60208110156101e157600080fd5b50519392505050565b610165806101f88339019056fe608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610133806100326000396000f3fe6080604052348015600f57600080fd5b5060043610603c5760003560e01c80633fa4f2451460415780636057361d14605957806367e404ce146075575b600080fd5b60476097565b60408051918252519081900360200190f35b607360048036036020811015606d57600080fd5b5035609d565b005b607b60ef565b604080516001600160a01b039092168252519081900360200190f35b60025490565b604080513381526020810183905281517fc9db20adedc6cf2b5d25252b101ab03e124902a73fcb12b753f3d1aaa2d8f9f5929181900390910190a1600255600180546001600160a01b03191633179055565b6001546001600160a01b03169056fea265627a7a72305820964d6b1168cda84ea2a5bea18eef49bd496a1ff53f7be8178e00cca5dbec045764736f6c634300050a0032a265627a7a723058206bdfc638d14f0f26a6507f178968016fb2a0330e74accb27be8d6e302989fc3264736f6c634300050a0032";

    public static final String FUNC_DEPLOYREMOTE = "deployRemote";

    public static final String FUNC_READ = "read";

    public static final Event NEWEVENTEMITTER_EVENT = new Event("NewEventEmitter", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected CrossContractReader(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CrossContractReader(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CrossContractReader(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CrossContractReader(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> deployRemote(String crossAddress) {
        final Function function = new Function(
                FUNC_DEPLOYREMOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(crossAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deploy() {
        final Function function = new Function(
                FUNC_DEPLOY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> read(String emitter_address) {
        final Function function = new Function(FUNC_READ, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(emitter_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<NewEventEmitterEventResponse> getNewEventEmitterEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWEVENTEMITTER_EVENT, transactionReceipt);
        ArrayList<NewEventEmitterEventResponse> responses = new ArrayList<NewEventEmitterEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEventEmitterEventResponse typedResponse = new NewEventEmitterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewEventEmitterEventResponse> newEventEmitterEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewEventEmitterEventResponse>() {
            @Override
            public NewEventEmitterEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWEVENTEMITTER_EVENT, log);
                NewEventEmitterEventResponse typedResponse = new NewEventEmitterEventResponse();
                typedResponse.log = log;
                typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewEventEmitterEventResponse> newEventEmitterEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWEVENTEMITTER_EVENT));
        return newEventEmitterEventFlowable(filter);
    }

    @Deprecated
    public static CrossContractReader load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrossContractReader(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CrossContractReader load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrossContractReader(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CrossContractReader load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CrossContractReader(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CrossContractReader load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CrossContractReader(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CrossContractReader> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrossContractReader.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrossContractReader> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrossContractReader.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CrossContractReader> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrossContractReader.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrossContractReader> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrossContractReader.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewEventEmitterEventResponse {
        public Log log;

        public String contractAddress;
    }
}
