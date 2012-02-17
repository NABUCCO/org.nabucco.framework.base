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
package org.nabucco.framework.base.facade.component;

import java.util.Properties;

import org.nabucco.framework.base.facade.component.application.Application;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.service.injection.Injectable;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;

/**
 * NabuccoInstance
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoInstance {

    private static final String PROPERTY_OWNER = "owner";

    /** The current application. */
    private Application application;

    /** Singleton instance. */
    private static NabuccoInstance instance;

    /* Constants */

    /** ID for NABUCCO Instance. */
    private static final String INSTANCE_ID = "instance";

    /**
     * Private constructor.
     */
    private NabuccoInstance() {
        InjectionProvider injectionProvider = InjectionProvider.getInstance(INSTANCE_ID);
        Injectable injectable = injectionProvider.inject(Application.APPLICATION_ID);

        if (injectable instanceof Application) {
            this.application = (Application) injectable;
        }
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoInstance instance.
     */
    public static synchronized NabuccoInstance getInstance() {
        if (instance == null) {
            instance = new NabuccoInstance();
        }
        return instance;
    }

    /**
     * Getter for the application.
     * 
     * @return Returns the application.
     */
    public Application getApplication() {
        return this.application;
    }

    /**
     * Getter for the configured owner.
     * 
     * @return the owner
     */
    public Owner getOwner() {
        InjectionProvider injectionProvider = InjectionProvider.getInstance(INSTANCE_ID);
        Properties properties = injectionProvider.getProperties();
        return new Owner(properties.getProperty(PROPERTY_OWNER, NabuccoConstants.OWNER));
    }

}
