/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AdviceType;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AspectConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AspectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutListExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Registry for NABUCCO aspects.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectRegistry {

    private static final String SERVICE_SEPERATOR = ".";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AspectRegistry.class);

    private static AspectRegistry instance;

    private Map<AspectRegistryKey, List<AspectRegistryEntry>> registryMap;

    private Map<String, AspectExtension> aspectMap;

    /**
     * Creates a new {@link AspectRegistry} instance.
     */
    private AspectRegistry() {
        this.registryMap = new HashMap<AspectRegistryKey, List<AspectRegistryEntry>>();
        this.aspectMap = new HashMap<String, AspectExtension>();
        configure();
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the instance
     */
    public static synchronized AspectRegistry getInstance() {
        if (instance == null) {
            instance = new AspectRegistry();
        }
        return instance;
    }

    /**
     * getter for the aspect extension
     * 
     * @param joinpoint
     *            the appropriate joinpoint.
     * 
     * @return the extension for the given joinpoint
     */
    public AspectExtension getAspectExtension(String joinpoint) {
        return this.aspectMap.get(joinpoint);
    }

    /**
     * Getter for the pointcuts of the given service operation.
     * 
     * @param key
     *            the service key
     * @param joinpoint
     *            the joinpoint
     * @param advice
     *            the invocation advice (before, after, around)
     * 
     * @return the pointcuts
     */
    public List<AspectRegistryEntry> getPointcuts(String key, String joinpoint, AdviceType advice) {
        AspectRegistryKey aspectKey = new AspectRegistryKey(key, joinpoint, advice.getId());
        List<AspectRegistryEntry> entryList = this.registryMap.get(aspectKey);
        if (entryList == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No Pointcuts availabe for JoinPoint=[", joinpoint, "] on service=[", key,
                        "] with advice=[", advice, "].");
            }
            entryList = new ArrayList<AspectRegistryEntry>();
        }
        return entryList;
    }

    /**
     * Configure the aspect registry with pointcut extensions.
     */
    private void configure() {

        try {
            AspectConfigExtension aspectConfig = (AspectConfigExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_ASPECT, ExtensionResolver.DEFAULT_EXTENSION);

            for (AspectExtension aspect : aspectConfig.getAspectList()) {

                String aspectExtension = aspect.getPoint().getValue().getValue();
                this.aspectMap.put(aspectExtension, aspect);

                ExtensionPointType type = ExtensionPointType.valueOfId(aspectExtension);

                ExtensionMap aspectExtensionMap = resolveExtension(type);

                String[] extensionNames = aspectExtensionMap.getExtensionNames();
                for (String name : extensionNames) {

                    PointcutListExtension pointcutList = (PointcutListExtension) aspectExtensionMap.getExtension(name);

                    for (PointcutExtension pointcut : pointcutList.getPointcutExtensionList()) {

                        String serviceName = pointcut.getService().getValue().getValue();
                        String operationName = pointcut.getOperation().getValue().getValue();
                        String key = serviceName + SERVICE_SEPERATOR + operationName;
                        String advice = pointcut.getAdvice().getValue().getValue();

                        if (logger.isDebugEnabled()) {
                            logger.debug("Registering Pointcut [", type.getId(), "] with Advice [", advice,
                                    "] on Service-Operation '", serviceName, ".", operationName, "'.");
                        }

                        AspectRegistryKey aspectKey = new AspectRegistryKey(key, type.getId(), advice);
                        AspectRegistryEntry entry = new AspectRegistryEntry(pointcut);

                        List<AspectRegistryEntry> entryList = this.registryMap.get(aspectKey);
                        if (entryList == null) {
                            entryList = new ArrayList<AspectRegistryEntry>();
                            this.registryMap.put(aspectKey, entryList);
                        }

                        entryList.add(entry);
                    }
                }
            }

        } catch (ExtensionException e) {
            logger.error(e, "Cannot configure AspectRegistry.");
        }
    }

    /**
     * Resolve the extensions for the given type.
     * 
     * @param type
     *            the extension type to resolve
     * 
     * @return the map of extensions
     */
    private ExtensionMap resolveExtension(ExtensionPointType type) {
        try {
            return NabuccoSystem.getExtensionResolver().resolveExtensions(type);
        } catch (Exception e) {
            return new ExtensionMap(type);
        }
    }

}
