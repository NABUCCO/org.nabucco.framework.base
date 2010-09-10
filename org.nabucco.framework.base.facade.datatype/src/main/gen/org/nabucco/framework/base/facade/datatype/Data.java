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
import org.nabucco.framework.base.facade.datatype.NByteArray;

/**
 * Data<p/>NABUCCO representation of a ByteArray. Mapped to database as BLOB.<p/>
 *
 * @author Silas Schwarz, PRODYNA AG, 2010-05-27
 */
public class Data extends NByteArray implements Basetype {

    private static final String CONSTRAINTS = "l0,n;";

    private static final long serialVersionUID = 1L;

    /** Constructs a new Data instance. */
    public Data() {
        super();
    }

    /**
     * Constructs a new Data instance.
     *
     * @param value the byte[].
     */
    public Data(byte[] value) {
        super(value);
    }

    @Override
    public Data cloneObject() {
        Data clone = new Data();
        super.cloneObject(clone);
        return clone;
    }

    @Override
    public String[] getConstraints() {
        return new String[] { CONSTRAINTS };
    }
}
