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

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.loader.XMLFileFilter;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.registry.ExtensionMapping;
import org.nabucco.common.extension.registry.ExtensionRegistry;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.extension.bootstrap.BootstrapExtensionParser;
import org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap.ExtensionConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap.NabuccoBootstrapExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Bootstrap logic for NABUCCO extensions.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExtensionBootstraping {

    /** Constant for the path to the NABUCCO extension configuration. */
    public static final String NBC_EXTENSION_PATH = "NABUCCO_EXTENSION_PATH";

    /** The bootrapping singleton map. */
    private static Map<String, ExtensionBootstraping> instanceMap;

    /** The extension registry. */
    private ExtensionRegistry registry;

    /** The logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ExtensionBootstraping.class);

    /** The default tanent. */
    private static final String DEFAULT_TENANT = new Tenant().getValue();

    /** The tenant. */
    private final String tenant;

    /**
     * Creates a new {@link ExtensionBootstraping} instance.
     * 
     * @param tenant
     *            the extension tenant
     * 
     * @throws ExtensionException
     *             when the extension bootstrapping fails
     */
    private ExtensionBootstraping(String tenant) throws ExtensionException {
        this.tenant = tenant;

        try {
            this.bootstrapExtensionConfiguration();
        } catch (ExtensionException ee) {
            logger.fatal(ee, "Cannot create ExtensionRegistry.");
            throw ee;
        } catch (Exception e) {
            logger.fatal(e, "Cannot create ExtensionRegistry.");
            throw new ExtensionException("Cannot create ExtensionRegistry.", e);
        }
    }

    /**
     * Singleton access.
     * 
     * @param tenant
     *            the configured tenant
     * 
     * @return the singleton instance
     * 
     * @throws ExtensionException
     *             when the extension bootstrapping fails
     */
    public static synchronized ExtensionBootstraping getInstance(Tenant tenant) throws ExtensionException {
        if (tenant == null || tenant.getValue() == null) {
            tenant = new Tenant();
        }

        if (instanceMap == null) {
            instanceMap = Collections.synchronizedMap(new HashMap<String, ExtensionBootstraping>());
        }

        ExtensionBootstraping bootstraping = instanceMap.get(tenant.getValue());
        if (bootstraping == null) {
            bootstraping = new ExtensionBootstraping(tenant.getValue());
            instanceMap.put(tenant.getValue(), bootstraping);
        }

        return bootstraping;
    }

    /**
     * Getter for the tenant.
     * 
     * @return Returns the tenant.
     */
    public String getTenant() {
        return this.tenant;
    }

    /**
     * Getter for the extension registry.
     * 
     * @return the extension registry
     */
    public ExtensionRegistry getExtensionRegistry() {
        return this.registry;
    }

    /**
     * Bootstraps the extension configuration.
     * 
     * @throws ExtensionException
     *             when the bootstrapping fails
     */
    private void bootstrapExtensionConfiguration() throws ExtensionException {

        logger.info("Bootstraping NABUCCO Extensions.");

        String path = System.getProperty(NBC_EXTENSION_PATH, "conf/extension");
        logger.info("Using Extensions from path [" + new File(path).getAbsolutePath() + "].");

        String tenantPath = path + File.separator + tenant;
        String defaultTenantPath = path + File.separator + DEFAULT_TENANT;

        File bootstrapFile = this.loadBootstrapFile(tenantPath, defaultTenantPath);
        Element element = this.loadElement(bootstrapFile);

        try {
            BootstrapExtensionParser parser = new BootstrapExtensionParser();
            NabucoExtensionContainer container = parser.parseExtension(element);

            NabuccoBootstrapExtension bootstrapExtension = (NabuccoBootstrapExtension) container.getExtension();
            ExtensionMapping mapping = this.buildExtensionMapping(bootstrapExtension);

            this.registry = ExtensionRegistry.newInstance(mapping, tenantPath, defaultTenantPath);

        } catch (Exception e) {
            throw new ExtensionException("Error bootstrapping NABUCCO Extensions.", e);
        }
    }

    /**
     * Load the bootstrap xml file.
     * 
     * @param path
     *            the file path
     * 
     * @return the bootstrap file cannot be f
     * 
     * @throws ExtensionException
     *             when the bootstrap file cannot be found
     */
    private File loadBootstrapFile(String tenantPath, String defaultPath) throws ExtensionException {

        File bootstrapDir = new File(tenantPath, ExtensionPointType.ORG_NABUCCO_BOOTSTRAP.getId());
        File[] bootstrapFiles = bootstrapDir.listFiles(new XMLFileFilter());

        if (bootstrapFiles == null || bootstrapFiles.length == 0) {

            logger.warning("No Bootstrap extension file found in path [", bootstrapDir.getAbsolutePath(),
                    "]. Using default the tenant [", DEFAULT_TENANT, "].");

            if (!this.tenant.equals(DEFAULT_TENANT)) {
                bootstrapDir = new File(defaultPath, ExtensionPointType.ORG_NABUCCO_BOOTSTRAP.getId());
                bootstrapFiles = bootstrapDir.listFiles(new XMLFileFilter());
            }

            if (bootstrapFiles == null || bootstrapFiles.length == 0) {
                logger.fatal("No Bootstrap extension file found in path [", bootstrapDir.getAbsolutePath(), "].");
                throw new ExtensionException("No Bootstrap extension file found in path ["
                        + bootstrapDir.getAbsolutePath() + "].");
            }
        }

        if (bootstrapFiles.length > 1) {
            logger.warning("More than one Bootstrap extension file found in path=[", bootstrapDir.getAbsolutePath(),
                    "], using the first as default.");
        }

        return bootstrapFiles[0];
    }

    /**
     * Load an xml element from a file.
     * 
     * @param file
     *            the file to parse
     * 
     * @return the xml element
     * 
     * @throws ExtensionException
     *             when the xml element cannot be parsed
     */
    private Element loadElement(File file) throws ExtensionException {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(file);
            return doc.getDocumentElement();
        } catch (Exception e) {
            throw new ExtensionException(e);
        }
    }

    /**
     * Create the extension mapping.
     * 
     * @param bootstrapExtension
     *            the bootstrap extension
     * 
     * @return the extension mapping
     * 
     * @throws ExtensionException
     *             when the mapping fails
     */
    private ExtensionMapping buildExtensionMapping(NabuccoBootstrapExtension bootstrapExtension)
            throws ExtensionException {

        ExtensionMapping mapping = new ExtensionMapping(ExtensionPointType.ORG_NABUCCO_BOOTSTRAP.getId());

        List<ExtensionConfigurationExtension> configList = bootstrapExtension.getExtensionList();
        for (ExtensionConfigurationExtension extension : configList) {

            String clazz = extension.getExtensionClass().getImplClass().getValue().getValue();

            try {
                @SuppressWarnings("unchecked")
                Class<? extends ExtensionParser> pClass = (Class<? extends ExtensionParser>) Class.forName(clazz);
                mapping.put(extension.getExtensionPoint().getValue().getValue(), pClass);
            } catch (Exception e) {
                throw new ExtensionException(e);
            }
        }

        return mapping;
    }

}
