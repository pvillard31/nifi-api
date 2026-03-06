/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.flow;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VersionedComponentState {

    private Map<String, String> clusterState;
    private List<VersionedNodeState> localNodeStates;

    @Schema(description = "The cluster-scoped state of the component, or null if not exported")
    public Map<String, String> getClusterState() {
        return clusterState;
    }

    public void setClusterState(final Map<String, String> clusterState) {
        this.clusterState = clusterState;
    }

    @Schema(description = "The local-scoped state of the component ordered by node ordinal index, or null if not exported")
    public List<VersionedNodeState> getLocalNodeStates() {
        return localNodeStates;
    }

    public void setLocalNodeStates(final List<VersionedNodeState> localNodeStates) {
        this.localNodeStates = localNodeStates;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VersionedComponentState that = (VersionedComponentState) o;
        return Objects.equals(clusterState, that.clusterState) && Objects.equals(localNodeStates, that.localNodeStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterState, localNodeStates);
    }

    @Override
    public String toString() {
        return "VersionedComponentState[clusterState=%s, localNodeStates=%s]".formatted(
                clusterState != null ? clusterState.size() + " entries" : "null",
                localNodeStates != null ? localNodeStates.size() + " nodes" : "null");
    }
}
