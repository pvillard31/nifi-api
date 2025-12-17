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
package org.apache.nifi.parameter;

import java.util.Objects;

/**
 * Represents a tag associated with a parameter, consisting of a key-value pair.
 * Tags can be used to store metadata about parameters, such as information
 * retrieved from external parameter providers that support tagging.
 */
public class ParameterTag {
    private final String key;
    private final String value;

    public ParameterTag(final String key, final String value) {
        this.key = Objects.requireNonNull(key, "Tag key is required");
        this.value = value;
    }

    /**
     * @return The key of the tag
     */
    public String getKey() {
        return key;
    }

    /**
     * @return The value of the tag, may be null
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParameterTag that = (ParameterTag) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "ParameterTag[key=" + key + ", value=" + value + "]";
    }
}

