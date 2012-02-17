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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * MessageSearchComposite
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public abstract class MessageSearchComposite implements MessageSearchComponent {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            MessageSearchComposite.class);

    private String id;

    private MessageSearchComponent parent;

    private Map<String, MessageSearchComponent> map;

    /**
     * Creates a new {@link MessageSearchComposite} instance.
     * 
     * @param parent
     *            the parent component
     * @param id
     *            the component id
     */
    public MessageSearchComposite(MessageSearchComponent parent, String id) {
        this.id = id;
        this.parent = parent;
        this.map = new HashMap<String, MessageSearchComponent>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public MessageSearchComponent getParent() {
        return parent;
    }

    @Override
    public void add(MessageSearchComponent component) {
        MessageSearchComponent overwritten = this.map.put(component.getId(), component);

        if (overwritten != null) {
            String path = "";
            MessageSearchComponent parent = overwritten;
            while (parent != null) {
                path = String.valueOf(parent.getId()) + "/" + path;
                parent = parent.getParent();
            }

            logger.warning("Overwriting existing error message, component id [", overwritten.getId(),
                    "] parent path [", path, "].");
        }
    }

    @Override
    public MessageSearchComponent[] getComponents() {
        return this.map.values().toArray(new MessageSearchComponent[0]);
    }

    @Override
    public MessageSearchComponent get(String id) {
        return this.map.get(id);
    }

    @Override
    public String toString() {
        return this.getId();
    }

}
