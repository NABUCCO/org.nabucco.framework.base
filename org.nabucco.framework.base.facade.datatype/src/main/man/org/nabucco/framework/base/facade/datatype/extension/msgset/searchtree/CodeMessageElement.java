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
package org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessageCodeType;

/**
 * CodeMessageElement
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class CodeMessageElement extends MessageSearchElement {

    private Map<FieldMessageCodeType, String> codeMap;

    /**
     * Creates a new {@link MessageElement} instance.
     * 
     * @param parent
     *            the parent component
     * @param id
     *            the id
     */
    public CodeMessageElement(MessageSearchComponent parent, String id) {
        super(parent, id);
    }

    /**
     * Getter for the codeMap.
     * 
     * @return Returns the codeMap.
     */
    public Map<FieldMessageCodeType, String> getCodeMap() {
        if (this.codeMap == null) {
            this.codeMap = new HashMap<FieldMessageCodeType, String>();
        }

        return this.codeMap;
    }

}
