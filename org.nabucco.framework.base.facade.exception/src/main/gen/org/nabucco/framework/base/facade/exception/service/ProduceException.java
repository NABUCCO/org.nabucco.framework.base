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
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;

/**
 * ProduceException<p/>Exception for produce services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Stefanie Feld, PRODYNA AG, 2010-03-29
 */
public class ProduceException extends ServiceException {

    private Map<String, String> parameterMap = new HashMap<String, String>();

    private static final long serialVersionUID = 1L;

    /** Constructs a new ProduceException instance. */
    public ProduceException() {
        super();
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param message the String.
     */
    public ProduceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param throwable the Throwable.
     */
    public ProduceException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ProduceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Getter for the Parameters.
     *
     * @return the Map<String, String>.
     */
    public Map<String, String> getParameters() {
        return new HashMap<String, String>(this.parameterMap);
    }
}
