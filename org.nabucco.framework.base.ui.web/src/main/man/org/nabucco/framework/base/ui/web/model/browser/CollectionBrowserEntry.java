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
package org.nabucco.framework.base.ui.web.model.browser;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.common.grouping.UIGroupDevider;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebRenderer;
import org.nabucco.framework.base.ui.web.model.control.util.PropertyStringParser;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * CollectionBrowserEntry
 * 
 * Browser Entry that can be bound on a collection of datatypes
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CollectionBrowserEntry<T extends Datatype> extends BrowserEntry {

    private static final String JSON_COUNT = "count";

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TableModel.class);

    /** Binding path to the label */
    private String propertyLabel;

    /** Binding path to the tooltip */
    private String propertyTooltip;

    /** Property Grouping type */
    private String propertyGrouping;

    /** Action to be fired by the children elements s */
    private String elementAction;

    /** The instance to be shown */
    private List<T> instance;

    /** Group devider to devide elements into configured groups */
    private UIGroupDevider<BrowserEntry> groupDevider;

    private int childrenCount;

    /**
     * Creates a new {@link CollectionBrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param propertyLabel
     *            path to the label of the property
     * @param action
     *            action fired by click
     */
    public CollectionBrowserEntry(String id, String propertyLabel, String action) {
        super(id, false);

        if (propertyLabel == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry with propertylabel [null].");
        }

        if (action == null) {
            throw new IllegalArgumentException(
                    "Cannot create Browser Entry with  [null] action for referenced elements.");
        }

        this.groupDevider = new BrowserGroupDivider(10); // Per Default grouping = 10

        this.setPropertyLabel(propertyLabel);
        this.setElementAction(action);
    }

    public void setContent(List<T> content) {
        this.instance = content;
    }

    /**
     * Rebuild the tree of bound elements
     * 
     * @throws ExtensionException
     *             if property type not supported
     */
    private void rebuildBindingTree() throws ExtensionException {

        this.getEntryMap().clear();

        if (this.instance == null) {
            return;
        }

        List<BrowserEntry> elements = this.createCollectionChildElementGroup(this.instance);

        this.setChildrenCount(elements.size());
        List<BrowserEntry> dividedList = this.groupDevider.devideElements(this.getId(), elements);

        for (BrowserEntry entry : dividedList) {
            this.getEntryMap().put(entry.getId(), entry);
        }
    }

    /**
     * Add a new bound child element
     * 
     * @param boundProperty
     *            property to be evaluated
     */
    private BrowserEntry createChildElement(Datatype instance) {

        if (instance == null) {
            return null;
        }

        String id;

        if (instance instanceof NabuccoDatatype) {
            id = String.valueOf(((NabuccoDatatype) instance).getId());
        } else {
            id = this.getId();
        }

        BrowserEntry retVal = new BrowserEntry(id, this.getPropertyLabel(), this.getPropertyTooltip(), this.getImage(),
                true, null, null);
        retVal.setAction(this.getElementAction());

        PropertyStringParser propertyParser = new PropertyStringParser(this.propertyLabel, this.propertyTooltip,
                this.getImage());
        Set<String> parsedProperties = propertyParser.parseProperties();
        WebRenderer renderer = new DefaultWebRenderer();

        for (String propName : parsedProperties) {

            WebLabelProvider<Datatype> labelProvider = new PropertyLabelProvider<Datatype>(instance);
            String resolvedPropertyValue = labelProvider.getLabel(propName, renderer);

            retVal.getPropertyMap().put(propName, resolvedPropertyValue);
        }
        return retVal;
    }

    /**
     * Add a collection of bound child elements
     * 
     * @param collection
     *            property to be evaluated
     * @return list of bound elements
     */
    private List<BrowserEntry> createCollectionChildElementGroup(List<T> collection) {
        List<BrowserEntry> collectionElements = new ArrayList<BrowserEntry>();

        for (Datatype child : collection) {
            BrowserEntry childElement = this.createChildElement(child);
            if (childElement != null) {
                collectionElements.add(childElement);
            }
        }

        return collectionElements;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        if (event.getNewValue() instanceof Collection) {
            List<T> newValue = (List<T>) event.getNewValue();

            this.instance = newValue;

            try {
                this.rebuildBindingTree();
            } catch (ExtensionException e) {
                logger.error("Cannot rebuild browser tree:", e.getMessage());
            }
        }
    }

    /**
     * Setter for the propertyLabel.
     * 
     * @param propertyLabel
     *            The propertyLabel to set.
     */
    public void setPropertyLabel(String propertyLabel) {
        this.propertyLabel = propertyLabel;
    }

    /**
     * Getter for the propertyLabel.
     * 
     * @return Returns the propertyLabel.
     */
    public String getPropertyLabel() {
        return this.propertyLabel;
    }

    /**
     * Setter for the propertyTooltip.
     * 
     * @param propertyTooltip
     *            The propertyTooltip to set.
     */
    public void setPropertyTooltip(String propertyTooltip) {
        this.propertyTooltip = propertyTooltip;
    }

    /**
     * Getter for the propertyTooltip.
     * 
     * @return Returns the propertyTooltip.
     */
    public String getPropertyTooltip() {
        return this.propertyTooltip;
    }

    /**
     * Setter for the propertyGrouping.
     * 
     * @param propertyGrouping
     *            The propertyGrouping to set.
     */
    public void setPropertyGrouping(String propertyGrouping) {
        this.propertyGrouping = propertyGrouping;

        int grouping = 10;

        if (this.propertyGrouping != null && !this.propertyGrouping.isEmpty()) {
            grouping = Integer.parseInt(this.propertyGrouping);
        }
        this.groupDevider = new BrowserGroupDivider(grouping);
    }

    /**
     * Getter for the propertyGrouping.
     * 
     * @return Returns the propertyGrouping.
     */
    public String getPropertyGrouping() {
        return this.propertyGrouping;
    }

    /**
     * Setter for the elementAction.
     * 
     * @param elementAction
     *            The elementAction to set.
     */
    public void setElementAction(String elementAction) {
        this.elementAction = elementAction;
    }

    /**
     * Getter for the elementAction.
     * 
     * @return Returns the elementAction.
     */
    public String getElementAction() {
        return this.elementAction;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_COUNT, this.getChildrenCount());
        return json;
    }

    /**
     * Setter for the childrenCount.
     * 
     * @param childrenCount
     *            The childrenCount to set.
     */
    private void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    /**
     * Getter for the childrenCount.
     * 
     * @return Returns the childrenCount.
     */
    public int getChildrenCount() {
        return this.childrenCount;
    }

}
