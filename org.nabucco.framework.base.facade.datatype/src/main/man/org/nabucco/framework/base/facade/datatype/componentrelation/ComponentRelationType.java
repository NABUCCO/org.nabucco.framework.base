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
package org.nabucco.framework.base.facade.datatype.componentrelation;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;

/**
 * ComponentRelationType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ComponentRelationType extends Enumeration {

    /**
     * Getter for the source class.
     * 
     * @return Returns the source type.
     */
    Class<? extends NabuccoDatatype> getSource();

    /**
     * Getter for the target class.
     * 
     * @return Returns the target type.
     */
    Class<? extends NabuccoDatatype> getTarget();

    /**
     * Getter for the relation class.
     * 
     * @return Returns the relation type.
     */
    Class<? extends ComponentRelation<?>> getRelation();

}
