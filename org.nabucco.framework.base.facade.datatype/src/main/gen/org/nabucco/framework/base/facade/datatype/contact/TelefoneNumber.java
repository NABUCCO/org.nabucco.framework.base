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
package org.nabucco.framework.base.facade.datatype.contact;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;

/**
 * TelefoneNumber<p/>An email address<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class TelefoneNumber extends NString implements Basetype {

    private static final String CONSTRAINTS = "l0,255;";

    private static final long serialVersionUID = 1L;

    /** Constructs a new TelefoneNumber instance. */
    public TelefoneNumber() {
        super();
    }

    /**
     * Constructs a new TelefoneNumber instance.
     *
     * @param value the String.
     */
    public TelefoneNumber(String value) {
        super(value);
    }

    @Override
    public TelefoneNumber cloneObject() {
        TelefoneNumber clone = new TelefoneNumber();
        super.cloneObject(clone);
        return clone;
    }

    @Override
    public String[] getConstraints() {
        return new String[] { CONSTRAINTS };
    }
}
