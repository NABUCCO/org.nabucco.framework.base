/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.component.application.connector;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;

/**
 * DatatypeConnector
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface DatatypeConnector extends Connector {

    /**
     * Setter for the source datatype instance.
     * 
     * @param datatype
     *            the source datatype
     */
    <S extends NabuccoDatatype> void setSourceDatatype(S datatype);

    /**
     * Getter for the name of the source datatype.
     * 
     * @return the source datatype name
     */
    Class<?> getSourceDatatypeClass();

    /**
     * Handles the component relation maintenance service.
     * 
     * @throws ConnectorException
     */
    void maintain() throws ConnectorException;

    /**
     * Handles the component relation resolve service.
     * 
     * @throws ConnectorException
     */
    void resolve() throws ConnectorException;

}
