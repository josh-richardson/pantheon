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
package tech.pegasys.pantheon.ethereum.jsonrpc.internal.privacy.parameters;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SetPrivacyGroupStateParameter implements Serializable {
  private final ArrayList<CommitmentPair> payload;
  private final String privacyGroupId;

  @JsonCreator
  public SetPrivacyGroupStateParameter(
      @JsonProperty("privacyGroupId") String privacyGroupId,
      @JsonProperty("payload") ArrayList<CommitmentPair> payload) {
    this.payload = payload;
    this.privacyGroupId = privacyGroupId;
  }

  @JsonProperty("payload")
  public ArrayList<CommitmentPair> payload() {
    return payload;
  }

  @JsonProperty("privacyGroupId")
  public String privacyGroupId() {
    return privacyGroupId;
  }
}
