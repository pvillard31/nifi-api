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
package org.apache.nifi.registry.extension;

import org.apache.nifi.components.AbstractConfigurableComponent;
import org.apache.nifi.components.ConfigVerificationResult;
import org.apache.nifi.components.ConfigVerificationResult.Outcome;
import org.apache.nifi.logging.ComponentLog;

import javax.net.ssl.SSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractExtensionRegistryClient extends AbstractConfigurableComponent implements ExtensionRegistryClient, VerifiableExtensionRegistryClient {

    private volatile String identifier;
    private volatile Optional<SSLContext> systemSslContext;
    private volatile ComponentLog logger;

    @Override
    public void initialize(final ExtensionRegistryClientInitializationContext context) {
        this.identifier = context.getIdentifier();
        this.logger = context.getLogger();
        this.systemSslContext = context.getSystemSslContext();
    }

    @Override
    public final String getIdentifier() {
        return identifier;
    }

    protected final ComponentLog getLogger() {
        return logger;
    }

    protected final Optional<SSLContext> getSystemSslContext() {
        return systemSslContext;
    }

    @Override
    public List<ConfigVerificationResult> verify(final ExtensionRegistryClientConfigurationContext context, final ComponentLog verificationLogger, final Map<String, String> variables) {
        final List<ConfigVerificationResult> results = new ArrayList<>();

        try {
            final int bundleCount = listBundles(context).size();
            results.add(new ConfigVerificationResult.Builder()
                    .verificationStepName("List Bundles")
                    .outcome(Outcome.SUCCESSFUL)
                    .explanation("Successfully listed " + bundleCount + " bundles from the Extension Registry Client")
                    .build());
        } catch (final Exception e) {
            verificationLogger.error("Failed to list bundles during verification", e);

            results.add(new ConfigVerificationResult.Builder()
                    .verificationStepName("List Bundles")
                    .outcome(Outcome.FAILED)
                    .explanation("Failed to list bundles: " + e.getMessage())
                    .build());
        }

        return results;
    }
}
