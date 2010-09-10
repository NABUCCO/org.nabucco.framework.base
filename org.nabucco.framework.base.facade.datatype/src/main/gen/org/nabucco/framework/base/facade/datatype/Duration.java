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
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NLong;

/**
 * Duration<p/>A time duration in milliseconds<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-08
 */
public class Duration extends NLong implements Basetype {

    private static final String CONSTRAINTS = "l0,n;";

    private static final long serialVersionUID = 1L;

    /** Constructs a new Duration instance. */
    public Duration() {
        super();
    }

    /**
     * Constructs a new Duration instance.
     *
     * @param value the Long.
     */
    public Duration(Long value) {
        super(value);
    }

    @Override
    public Duration cloneObject() {
        Duration clone = new Duration();
        super.cloneObject(clone);
        return clone;
    }

    @Override
    public String[] getConstraints() {
        return new String[] { CONSTRAINTS };
    }
}