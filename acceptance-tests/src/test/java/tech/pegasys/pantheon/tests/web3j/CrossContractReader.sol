/*
 * Copyright 2018 ConsenSys AG.
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
pragma solidity >=0.4.0 <0.6.0;

import "./EventEmitter.sol";

// compile with:
// solc CrossContractReader.sol --bin --abi --optimize --overwrite -o .
// then create web3j wrappers with:
// web3j solidity generate -b ./generated/CrossContractReader.bin -a ./generated/CrossContractReader.abi -o ../../../../../ -p tech.pegasys.pantheon.tests.web3j.generated
contract CrossContractReader {

    event NewEventEmitter(
        address contractAddress
    );

    function read(address emitter_address) view public returns (uint) {
        EventEmitter em = EventEmitter(emitter_address);
        return em.value();
    }

    function deploy() public {
        EventEmitter em = new EventEmitter();
        emit NewEventEmitter(address(em));
    }

    function deployRemote(address crossAddress) public {
        CrossContractReader cross = CrossContractReader(crossAddress);
        cross.deploy();
    }
}
