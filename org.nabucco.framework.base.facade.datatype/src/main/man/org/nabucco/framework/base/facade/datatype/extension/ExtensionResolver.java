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
package org.nabucco.framework.base.facade.datatype.extension;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.common.extension.registry.ExtensionRegistry;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Utility class for resolving NABUCCO Extensions.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExtensionResolver {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ExtensionResolver.class);

    public static final String DEFAULT_EXTENSION = "Default";

    /**
     * Resolves a NABUCCO Extension Point and returns a map of all Extensions for this Extension
     * Point configured for the default tenant.
     * 
     * @param point
     *            The Extension Point to search for.
     * @return A filled Map with NABUCCO Extensions.
     * @throws ExtensionException
     *             Thrown if Extension Point does not exists or an exception occures while
     *             processing the extension.
     */
    public ExtensionMap resolveExtensions(ExtensionPointType point) throws ExtensionException {
        return this.resolveExtensions(point, new Tenant());
    }

    /**
     * Resolves a NABUCCO Extension Point and returns a map of all Extensions for this Extension
     * Point configured for the given tenant.
     * 
     * @param point
     *            the Extension Point to search for.
     * @param tenant
     *            the tenant the extension point must be configured for
     * 
     * @return A filled Map with NABUCCO Extensions.
     * 
     * @throws ExtensionException
     *             Thrown if Extension Point does not exists or an exception occures while
     *             processing the extension.
     */
    public ExtensionMap resolveExtensions(ExtensionPointType point, Tenant tenant) throws ExtensionException {

        String extensionPoint = point.getId();
        logger.debug("Trying to resolve the extensions for extension point [" + extensionPoint + "].");
        logger.debug("Trying to resolve the extensions for tenant [", tenant, "].");

        try {
            ExtensionMap map = new ExtensionMap(point);
            ExtensionBootstraping bootstraping = ExtensionBootstraping.getInstance(tenant);
            if (bootstraping == null || bootstraping.getExtensionRegistry() == null) {
                throw new ExtensionException("Cannot resolve extention path for tenant [" + tenant + "].");
            }

            ExtensionRegistry registry = bootstraping.getExtensionRegistry();
            List<NabuccoExtension> extensionList = registry.getExtension(extensionPoint);

            for (NabuccoExtension nabuccoExtension : extensionList) {

                NabucoExtensionContainer container = (NabucoExtensionContainer) nabuccoExtension;
                NabuccoExtensionComposite extension = container.getExtension();

                if (extension.getIdentifier() != null) {
                    map.addExtension(extension.getIdentifier().getValue(), extension);
                } else {
                    logger.warning("Extension for point [" + point + "] has no id.");
                    map.addExtension("NO_ID", extension);
                }
            }

            return map;

        } catch (Exception e) {
            throw new ExtensionException("Cannot resolve extension point [" + extensionPoint + "].", e);
        }
    }

    /**
     * Resolves a NABUCCO Extension Point and returns a map of all Extensions for this Extension
     * Point configured for the default tenant.
     * 
     * @param point
     *            The Extension Point to search for.
     * @param id
     *            The Extension Point id to search for.
     * 
     * @return A filled Map with NABUCCO Extensions.
     * 
     * @throws ExtensionException
     *             Thrown if Extension Point does not exists or an exception occures while
     *             processing the extension.
     */
    public NabuccoExtensionComposite resolveExtension(ExtensionPointType point, String id) throws ExtensionException {
        return this.resolveExtension(point, id, new Tenant());
    }

    /**
     * Resolves a NABUCCO Extension Point and returns a map of all Extensions for this Extension
     * Point configured for the given tenant.
     * 
     * @param point
     *            the Extension Point to search for.
     * @param id
     *            The Extension Point id to search for.
     * @param tenant
     *            the tenant the extension point must be configured for
     * 
     * @return A filled Map with NABUCCO Extensions.
     * 
     * @throws ExtensionException
     *             Thrown if Extension Point does not exists or an exception occures while
     *             processing the extension.
     */
    public NabuccoExtensionComposite resolveExtension(ExtensionPointType point, String id, Tenant tenant)
            throws ExtensionException {

        String extensionPoint = point.getId();
        logger.debug("Trying to resolve the extensions for extension point [" + extensionPoint + "].");
        logger.debug("Trying to resolve the extensions for tenant [", tenant, "].");

        try {
            ExtensionBootstraping bootstraping = ExtensionBootstraping.getInstance(tenant);
            if (bootstraping == null || bootstraping.getExtensionRegistry() == null) {
                throw new ExtensionException("Cannot resolve extention path for tenant [" + tenant + "].");
            }

            ExtensionRegistry registry = bootstraping.getExtensionRegistry();
            List<NabuccoExtension> extensionList = registry.getExtension(extensionPoint);

            for (NabuccoExtension ne : extensionList) {
                NabucoExtensionContainer container = (NabucoExtensionContainer) ne;
                NabuccoExtensionComposite extension = container.getExtension();
                if (extension.getIdentifier().getValue().equals(id)) {
                    return extension;
                }
            }

            logger.error("No such extension availavle with id [", id, "], point [", point.toString(), "].");
            throw new ExtensionException("No such extension availavle with id ["
                    + id + "], point [" + point.toString() + "].");

        } catch (ExtensionParserException epe) {
            throw new ExtensionException("Cannot resolve extension point [" + extensionPoint + "].", epe);
        } catch (Exception e) {
            throw new ExtensionException("Cannot resolve extension point [" + extensionPoint + "].", e);
        }
    }

    /**
     * Reset the extension registry.
     * 
     * @throws ExtensionException
     *             when the registry cannot be reseted
     */
    public void reset(String tenant) throws ExtensionException {
        try {
            ExtensionBootstraping.getInstance(new Tenant(tenant)).getExtensionRegistry().reset();
        } catch (Exception e) {
            logger.error(e, "Error resetting extension registry.");
        }
    }
}
