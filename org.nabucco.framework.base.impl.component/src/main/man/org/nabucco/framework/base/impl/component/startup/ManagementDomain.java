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
package org.nabucco.framework.base.impl.component.startup;

import java.util.Hashtable;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.jmx.MBean;
import org.nabucco.framework.base.facade.service.jmx.MBeanSupport;
import org.nabucco.framework.base.facade.service.jmx.dynamic.MBeanWrapper;

/**
 * ManagementDomain
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ManagementDomain implements StartupDomain {

    private static final String NAME = "ManagementDomain";

    private static final String SUBDOMAIN = "management";

    private static final String TYPE = "NabuccoManagement";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ManagementDomain.class);

    private boolean started;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void start() throws Exception {
        ExtensionMap managementExtensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                ExtensionPointType.ORG_NABUCCO_MANAGEMENT);

        String[] extensionNames = managementExtensions.getExtensionNames();
        for (String extensionName : extensionNames) {

            NabuccoExtension extension = managementExtensions.getExtension(extensionName);

            if (extension instanceof ManagementExtension) {
                MBeanWrapper mbean = new MBeanWrapper((ManagementExtension) extension);

                Hashtable<String, String> keys = new Hashtable<String, String>();
                keys.put(MBean.NAME, extension.getIdentifier().getValue());
                keys.put(MBean.TYPE, TYPE);

                MBeanSupport.register(SUBDOMAIN, mbean, keys);

                logger.info("Registered Managment Extension '", extension.getIdentifier(), "' in MBean Server.");
            }
        }
    }

}
