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

import org.apache.nifi.components.ConfigurableComponent;
import org.apache.nifi.flow.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * <p>
 * Represents an external source from where extensions might retrieved. The
 * interface provides the possibility to have multiple different types of
 * registries backing NiFi.
 * </p>
 *
 * <p>
 * <code>ExtensionRegistryClient</code>s are discovered using Java's
 * <code>ServiceLoader</code> mechanism. As a result, all implementations must
 * follow these rules:
 *
 * <ul>
 * <li>The implementation must implement this interface.</li>
 * <li>The implementation must have a file named
 * org.apache.nifi.registry.extension.ExtensionRegistryClient located within the
 * jar's <code>META-INF/services</code> directory. This file contains a list of
 * fully-qualified class names of all <code>ExtensionRegistryClient</code>s in
 * the jar, one-per-line.
 * <li>The implementation must support a default constructor.</li>
 * </ul>
 *
 * <p>
 * <code>ExtensionRegistryClient</code> instances are always considered "active"
 * and approachable when the validation status is considered as valid. Therefore
 * after initialize, implementations must expect incoming requests any time.
 * </p>
 *
 * <p>
 * The argument list of the request methods contain instances
 * <code>ExtensionRegistryClientConfigurationContext</code>, which always
 * contains the current state of the properties. Caching properties between
 * method calls is not recommended.
 * </p>
 *
 */
public interface ExtensionRegistryClient extends ConfigurableComponent {

    /**
     * Initializes the extension registry client with the given context.
     *
     * @param context the context
     */
    void initialize(ExtensionRegistryClientInitializationContext context);

    /**
     * Returns the NAR file for the given bundle.
     *
     * @param context the context
     * @param bundle  the bundle
     * @return the NAR file
     * @throws ExtensionRegistryException if an error occurs
     * @throws IOException                if an error occurs
     */
    InputStream getNARFile(ExtensionRegistryClientConfigurationContext context, Bundle bundle) throws ExtensionRegistryException, IOException;

    /**
     * Returns the manifest file for the given bundle.
     *
     * @param context the context
     * @param bundle  the bundle
     * @return the manifest file
     * @throws ExtensionRegistryException if an error occurs
     * @throws IOException                if an error occurs
     */
    InputStream getManifestFile(ExtensionRegistryClientConfigurationContext context, Bundle bundle) throws ExtensionRegistryException, IOException;

    /**
     * Returns the list of bundles.
     *
     * @param context the context
     * @return the list of bundles
     * @throws ExtensionRegistryException if an error occurs
     * @throws IOException                if an error occurs
     */
    Set<Bundle> listBundles(ExtensionRegistryClientConfigurationContext context) throws ExtensionRegistryException, IOException;

    /**
     * Returns metadata for a bundle, including its dependency coordinate and extension manifest stream. This allows callers to
     * build dependency graphs without downloading the entire NAR separately.
     *
     * @param context the context
     * @param bundle  the bundle
     * @return metadata for the bundle
     * @throws ExtensionRegistryException if an error occurs
     * @throws IOException                if an error occurs
     */
    ExtensionBundleMetadata getBundleMetadata(ExtensionRegistryClientConfigurationContext context, Bundle bundle) throws ExtensionRegistryException, IOException;

}
