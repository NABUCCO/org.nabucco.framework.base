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
package org.nabucco.framework.base.facade.message;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;

/**
 * ServiceMessage
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ServiceMessage extends Validatable, NType, PropertyOwner {

    /**
     * Clones this service message.
     * 
     * @return the service message clone.
     */
    @Override
    ServiceMessage cloneObject();

    /**
     * Generic setter for a property instance.
     * 
     * @param property
     *            the property to set
     * 
     * @return <b>true</b> if the property has successfully been set, <b>false</b> if not
     */
    @Override
    boolean setProperty(NabuccoProperty property);

    /**
     * Initialize the message with default values.
     */
    void init();
}
