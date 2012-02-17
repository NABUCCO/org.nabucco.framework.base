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
package org.nabucco.framework.base.ui.web.component.messagequeue;

import java.util.Deque;
import java.util.LinkedList;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messagequeue.MessageQueueExtension;
import org.nabucco.framework.base.ui.web.action.result.WebActionResultItem;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * MessageQueue
 * 
 * Controls messages that comes as push to the UI
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MessageQueue extends WebComponent {

    private static final long serialVersionUID = 1L;

    private static final String JSON_PERIOD = "period";

    private static final String JSON_ITEMS = "items";

    private static final String JSON_ACTIVE = "active";

    private MessageQueueExtension extension;

    private Deque<WebActionResultItem> queue = new LinkedList<WebActionResultItem>();

    private int period;

    private boolean active = true;

    /**
     * Creates a new {@link MessageQueue} instance.
     * 
     * @param extension
     *            the messagequeue extension
     */
    public MessageQueue(MessageQueueExtension extension) {
        super(WebElementType.MESSAGEQUEUE, extension);
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.period = PropertyLoader.loadProperty(this.extension.getPeriod());
    }

    /**
     * Push result item
     * 
     * @param item
     *            item to be pushed
     */
    public void pushItem(WebActionResultItem item) {
        if (item == null) {
            return;
        }

        this.queue.add(item);
    }

    /**
     * Getter for the active.
     * 
     * @return Returns the active.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Starts the message queue, if it is not already started.
     */
    public void start() {
        this.active = true;
    }

    /**
     * Stop the message queue if it is not already stopped.
     */
    public void stop() {
        this.active = false;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ACTIVE, this.active);
        json.add(JSON_PERIOD, this.period);

        WorkArea workArea = NabuccoServletUtil.getWorkArea();
        if (this.active && workArea != null) {
            boolean dirty = workArea.isDirty();
            json.add(JSON_DIRTY, dirty);
        } else {
            json.add(JSON_DIRTY, false);
        }

        JsonList items = new JsonList();
        while (!this.queue.isEmpty()) {
            WebActionResultItem item = this.queue.poll();
            items.add(item.toJson());
        }
        json.add(JSON_ITEMS, items);

        return json;
    }

}
