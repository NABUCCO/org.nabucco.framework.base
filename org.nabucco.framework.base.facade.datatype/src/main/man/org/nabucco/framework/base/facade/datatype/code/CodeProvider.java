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
package org.nabucco.framework.base.facade.datatype.code;

/**
 * Interface for providing codes for the {@link CodeFacade}.
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public interface CodeProvider {

    /**
     * Provides the defined root element of Codes.
     * 
     * @return root element of the {@link CodeGroup} tree.
     */
    CodeGroup getCodeRoot() throws CodeProviderException;

    /**
     * Provides a eager fetch reload for a single group in case of eviction.
     * 
     * @param codePath
     *            the groups code path
     * 
     * @return the reloaded code group
     * 
     * @throws CodeProviderException
     *             when the code path cannot be reloaded
     */
    CodeGroup reloadPath(String codePath) throws CodeProviderException;

}
