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
package org.nabucco.framework.base.facade.message.ping;

import java.io.Serializable;

/**
 * PingRequest
 * <p/>
 * The request for third party system pings.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jndiName;

    /**
     * Getter for the jndiName.
     * 
     * @return Returns the jndiName.
     */
    public String getJndiName() {
        return this.jndiName;
    }

    /**
     * Setter for the jndiName.
     * 
     * @param jndiName
     *            The jndiName to set.
     */
    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

}
