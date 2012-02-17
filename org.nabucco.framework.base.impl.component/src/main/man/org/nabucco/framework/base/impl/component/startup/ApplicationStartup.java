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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.component.adapter.registry.AdapterRegistryDomain;

/**
 * ApplicationStartup
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ApplicationStartup implements ApplicationStartupMBean, StartupDomain {

    private boolean isStarted;

    private List<StartupDomain> domains = new ArrayList<StartupDomain>();

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ApplicationStartup.class);

    private static final String APPLICATION = "Application";

    @Override
    public void start() throws Exception {
        if (this.isStarted) {
            logger.warning("NABUCCO ApplicationStartup MBean is already running.");
            return;
        }

        logger.info("Starting NABUCCO Application.");

        this.domains.add(new ManagementDomain());
        this.domains.add(new AdapterRegistryDomain());

        for (StartupDomain domain : this.domains) {
            try {
                logger.info("Starting Domain '", domain.getName(), "'.");
                domain.start();
                logger.info("Domain '", domain.getName(), "' successfully started.");
            } catch (Exception e) {
                logger.error(e, "Error starting domain '", domain.getName(), "'.");
            }
        }

        this.isStarted = true;

        logger.info("NABUCCO ApplicationStartup MBean started.");
    }

    @Override
    public void stop() {
        if (!this.isStarted) {
            logger.warning("NABUCCO ApplicationStartup MBean is already stopped.");
            return;
        }

        this.isStarted = false;

        logger.info("NABUCCO ApplicationStartup MBean stopped.");
    }

    @Override
    public String getName() {
        return APPLICATION;
    }

    @Override
    public boolean isStarted() {
        return this.isStarted;
    }

}
