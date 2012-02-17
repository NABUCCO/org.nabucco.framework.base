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
package org.nabucco.framework.base.ui.web.component.messageset;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractMessageSetNode
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class AbstractMessageSetNode implements MessageSetNode {

    protected Map<String, MessageSetNode> childrenMap = new HashMap<String, MessageSetNode>();

    @Override
    public final MessageSetNode getChild(String name) {

        if (name == null) {
            return this.childrenMap.get(STAR_SYMBOL);
        }

        if (this.childrenMap.containsKey(name)) {
            return this.childrenMap.get(name);
        }
        return this.childrenMap.get(STAR_SYMBOL);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (String val : this.childrenMap.keySet()) {
            builder.append("\n\r------------------------");
            builder.append(val);
            builder.append(this.childrenMap.get(val).toString());
            builder.append("\n\r");
        }
        return builder.toString();
    }
}
