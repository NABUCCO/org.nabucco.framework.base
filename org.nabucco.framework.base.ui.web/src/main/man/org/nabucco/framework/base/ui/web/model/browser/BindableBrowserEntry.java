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
import java.util.List;
import java.util.Set;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.common.grouping.UIGroupDevider;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebRenderer;
import org.nabucco.framework.base.ui.web.model.editor.util.PropertyStringParser;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * BindableBrowserEntry
 * 
 * That is the kind of browser entry that can be bound and synchronize its children with the bound
 * element
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BindableBrowserEntry extends BrowserEntry {

    private static final String JSON_COUNT = "count";

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TableModel.class);

    /** Bound property */
    private NabuccoProperty property;

    /** Binding path to the label */
    private String propertyLabel;

    /** Binding path to the tooltip */
    private String propertyTooltip;

    /** Property Grouping type */
    private String propertyGrouping;

    /** Action to be fired by the children elements s */
    private String elementAction;

    /** The instance to be shown */
    private NType instance;

    private String propertyPath;

    private int childrenCount;

    /** Group devider to devide elements into configured groups */
    private UIGroupDevider<BrowserEntry> groupDevider;

    /**
     * Creates a new {@link BindableBrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param propertyLabel
     *            path to the label of the property
     * @param propertyPath
     *            path to the bound property
     * @param action
     *            action fired by click
     */
    public BindableBrowserEntry(String id, String propertyLabel, String propertyPath, String action) {
        super(id, false);

        if (propertyLabel == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry with propertylabel [null].");
        }

        if (action == null) {
            throw new IllegalArgumentException(
                    "Cannot create Browser Entry with  [null] action for referenced elements.");
        }

        if (propertyPath == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry, the propertyPath is 'null'.");
        }

        this.groupDevider = new BrowserGroupDivider(0); // Per Default no grouping active

        this.setPropertyLabel(propertyLabel);
        this.setElementAction(action);
        this.setPropertyPath(propertyPath);
    }

    /**
     * Creates a new {@link BindableBrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param label
     *            the browser label
     * @param tooltip
     *            the browser element tooltip
     * @param image
     *            the browser element icon
     * @param propertyLabel
     *            path to the label of the property
     * @param propertyTooltip
     *            path to the tooltip of the bound property
     * @param propertyPath
     *            path to the bound property
     * @param action
     *            action fired by click
     */
    public BindableBrowserEntry(String id, String label, String tooltip, String image, String propertyLabel,
            String propertyTooltip, String propertyPath, String action) {
        super(id, label, tooltip, image, false, null, null);

        if (label == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry with label [null].");
        }

        if (propertyLabel == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry with propertylabel [null].");
        }

        if (action == null) {
            throw new IllegalArgumentException(
                    "Cannot create Browser Entry with [null] action for referenced elements.");
        }

        if (propertyPath == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry, the propertyPath is 'null'.");
        }

        this.groupDevider = new BrowserGroupDivider(0); // Per Default no grouping active

        this.setPropertyLabel(propertyLabel);
        this.setPropertyTooltip(propertyTooltip);
        this.setElementAction(action);
        this.setPropertyPath(propertyPath);
    }

    /**
     * Set property to bind
     * 
     * @param property
     *            property to be set
     */
    public void setProperty(NabuccoProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot bind a browser entry to a [null].");
        }
        this.property = property;
        this.instance = (NType) property.getInstance();

        try {
            this.rebuildBindingTree();
        } catch (ExtensionException e) {
            throw new IllegalArgumentException("Cannot initialize the browser tree", e);
        }
    }

    /**
     * Rebuild the tree of bound elements
     * 
     * @throws ExtensionException
     *             if property type not supported
     */
    private void rebuildBindingTree() throws ExtensionException {

        this.getEntryMap().clear();

        if (this.property == null) {
            return;
        }

        switch (this.property.getPropertyType()) {

        case COLLECTION: {
            @SuppressWarnings("unchecked")
            NabuccoCollection<Datatype> collection = (NabuccoCollection<Datatype>) this.instance;

            if (collection != null && collection.isTraversable()) {
                List<BrowserEntry> elements = this.createCollectionChildElementGroup(collection);

                this.childrenCount = elements.size();
                String idPrefix = super.generateUniqueId(this.getId(), this.property.getName());
                List<BrowserEntry> dividedList = this.groupDevider.devideElements(idPrefix, elements);

                for (BrowserEntry entry : dividedList) {
                    this.getEntryMap().put(entry.getId(), entry);
                }
            }
            break;
        }

        case COMPONENT_RELATION: {
            @SuppressWarnings("unchecked")
            NabuccoList<ComponentRelation<?>> componentRelations = (NabuccoList<ComponentRelation<?>>) this.instance;
            List<BrowserEntry> elements = this.createComponentRelationChildElementGroup(componentRelations);

            this.childrenCount = elements.size();
            String idPrefix = super.generateUniqueId(this.getId(), this.property.getName());
            List<BrowserEntry> dividedList = this.groupDevider.devideElements(idPrefix, elements);

            for (BrowserEntry entry : dividedList) {
                this.getEntryMap().put(entry.getId(), entry);
            }
            break;
        }

        case DATATYPE: {
            Datatype datatype = (Datatype) this.instance;
            BrowserEntry childElement = this.createChildElement(datatype);
            if (childElement != null) {
                this.getEntryMap().put(childElement.getId(), childElement);
                this.childrenCount = 1;
            }
            break;
        }

        default: {
            throw new ExtensionException("The type of the bound property is not yet supported by the browser");
        }
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
    private List<BrowserEntry> createCollectionChildElementGroup(NabuccoCollection<Datatype> collection) {
        List<BrowserEntry> collectionElements = new ArrayList<BrowserEntry>();

        for (Datatype child : collection) {
            BrowserEntry childElement = this.createChildElement(child);
            if (childElement != null) {
                collectionElements.add(childElement);
            }
        }

        return collectionElements;
    }

    /**
     * Add a component relation bound target elements
     * 
     * @param boundProperty
     *            property to be evaluated
     * 
     * @return list of elements
     */
    private List<BrowserEntry> createComponentRelationChildElementGroup(
            NabuccoCollection<ComponentRelation<?>> relations) {
        List<BrowserEntry> collectionElements = new ArrayList<BrowserEntry>();

        for (ComponentRelation<?> entryRelation : relations) {
            BrowserEntry childElement = this.createChildElement(entryRelation.getTarget());
            if (childElement != null) {
                collectionElements.add(childElement);
            }
        }
        return collectionElements;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        Object newValue = event.getNewValue();
        if (newValue instanceof NType) {
            this.instance = (NType) event.getNewValue();

            try {
                this.rebuildBindingTree();
            } catch (ExtensionException e) {
                logger.error("Cannot rebuild browser tree:", e.getMessage());
            }

            for (BrowserEntry entry : this.getEntryMap().values()) {
                entry.propertyChange(event);
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

        int grouping = 0;
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

    /**
     * Setter for the propertyPath.
     * 
     * @param propertyPath
     *            The propertyPath to set.
     */
    private void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    /**
     * Getter for the propertyPath.
     * 
     * @return Returns the propertyPath.
     */
    public String getPropertyPath() {
        return this.propertyPath;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_COUNT, this.childrenCount);
        return json;
    }
}
