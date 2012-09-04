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
package org.nabucco.framework.base.facade.datatype.extension.ui;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.DialogButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.DialogExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.GridDialogExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DateDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownSelectionItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.FileDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.LinkDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.TextDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * DialogExtensionParser
 * 
 * Parses the dialog extenstion
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_NAME = "name";

    private static final String ELEMENT_SELECTION = "selection";

    private static final String CONSTANT_DATE = "DATE";

    private static final String CONSTANT_DROPDOWN = "DROPDOWN";

    private static final String ATTR_EXTENSION_FILTER = "extensionFilter";

    private static final String CONTROL_TYPE_TEXT_LINK = "TEXTLINK";

    private static final String CONTROL_TYPE_TEXTAREA = "TEXTAREA";

    private static final String CONTROL_TYPE_TEXT = "TEXT";

    private static final String CONTROL_TYPE_FILE = "FILE";

    private static final String ELEMENT_BUTTON = "button";

    private static final String ELEMENT_CONTROLS = "controls";

    private static final String DIALOG_TYPE_GRID = "gridDialog";

    private static final String DIALOG_TYPE_MESSAGE_DIALOG = "messageDialog";

    private static final String ATTR_MANDATORY = "mandatory";

    private static final String ATTR_ID = "id";

    private static final String ATTR_ACTION_ID = "actionId";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TITLE = "title";

    private static final String ATTR_MESSAGE = "message";

    private static final String ATTR_ICON = "icon";

    private static final String ATTR_GRID = "grid";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_POSITION = "position";

    private static final String ATTR_VALIDATE = "validate";

    private static final String ATTR_URL = "url";

    private static final String ATTR_PARAMETER = "parameter";

    private static final String ATTR_EDITABLE = "editable";

    private static final String ELEMENT_DEFAULT_VALUE = "defaultValue";

    private static final String ATTR_DEFAULT_ID = "defaultId";

    private static final String ATTR_LOCALIZATION_PATH = "localizationPath";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DialogExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface editor configuration.");
            DialogExtension extension = null;

            Element dialogElement = super.getChildren(element).get(0);

            String dialogType = dialogElement.getTagName();

            String id = element.getAttribute(ATTR_ID);

            if (dialogType.equalsIgnoreCase(DIALOG_TYPE_GRID)) {
                extension = this.parseGridDialogExtension(dialogElement);
            } else if (dialogType.equalsIgnoreCase(DIALOG_TYPE_MESSAGE_DIALOG)) {
                extension = new DialogExtension();
            } else {
                throw new ExtensionParserException("The given dialog type " + dialogType + " is not supported yet");
            }

            EnumerationProperty dialogTypeProperty = new EnumerationProperty();
            dialogTypeProperty.setValue(dialogType);
            extension.setType(dialogTypeProperty);
            extension.setIdentifier(id);
            extension.setTitle(super.getStringProperty(dialogElement, ATTR_TITLE));
            extension.setMessage(super.getStringProperty(dialogElement, ATTR_MESSAGE));
            extension.setIcon(super.getStringProperty(dialogElement, ATTR_ICON));

            for (Element buttonElement : super.getElementsByTagName(dialogElement, ELEMENT_BUTTON)) {
                DialogButtonExtension buttonExtension = new DialogButtonExtension();
                buttonExtension.setIdentifier(new ExtensionId(buttonElement.getAttribute(ATTR_ID)));
                buttonExtension.setActionId(super.getStringProperty(buttonElement, ATTR_ACTION_ID));
                buttonExtension.setTooltip(super.getStringProperty(buttonElement, ATTR_TOOLTIP));
                buttonExtension.setLabel(super.getStringProperty(buttonElement, ATTR_LABEL));
                buttonExtension.setIcon(super.getStringProperty(buttonElement, ATTR_ICON));

                buttonExtension.setParameter(super.getStringProperty(buttonElement, ATTR_PARAMETER));
                buttonExtension.setValidate(super.getBooleanProperty(buttonElement, ATTR_VALIDATE, false));
                extension.getButtons().add(buttonExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Parses the Grid dialog extension
     * 
     * @param grid
     *            the grid element
     * @return parsed extension
     * @throws ExtensionParserException
     *             if cannot parse
     * @throws ExtensionException
     *             if cannot find element
     */
    private GridDialogExtension parseGridDialogExtension(Element grid) throws ExtensionParserException,
            ExtensionException {
        GridDialogExtension ext = new GridDialogExtension();

        Element controlsElement = super.getElementsByTagName(grid, ELEMENT_CONTROLS).get(0);
        ext.setGrid(super.getStringProperty(controlsElement, ATTR_GRID));

        for (Element controlElement : super.getChildren(controlsElement)) {

            DialogControlExtension controlExtension = this.parseControlExtension(controlElement);
            ext.getControls().add(controlExtension);
        }
        return ext;
    }

    /**
     * Parses the control to it extension
     * 
     * @param controlElement
     *            the element to parse
     * @return
     * @throws ExtensionException
     */
    private DialogControlExtension parseControlExtension(Element controlElement) throws ExtensionParserException,
            ExtensionException {

        DialogControlExtension controlExtension = null;
        String tagName = controlElement.getTagName();

        if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXT)) {
            controlExtension = this.parseTextExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXTAREA)) {
            controlExtension = this.parseTextExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_FILE)) {
            FileDialogControlExtension ext = new FileDialogControlExtension();
            ext.setExtensionFilter(super.getStringProperty(controlElement, ATTR_EXTENSION_FILTER));
            controlExtension = ext;
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXT_LINK)) {
            LinkDialogControlExtension ext = new LinkDialogControlExtension();
            ext.setUrl(super.getStringProperty(controlElement, ATTR_URL));
            controlExtension = ext;
        } else if (tagName.equalsIgnoreCase(CONSTANT_DATE)) {
            controlExtension = this.parseDateExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONSTANT_DROPDOWN)) {
            controlExtension = this.parseDropDownExtension(controlElement);
        } else {
            throw new ExtensionParserException("The tag name " + tagName + " is not supported yet");
        }

        this.parseDefaultDialogControlParameters(controlElement, controlExtension);

        return controlExtension;
    }

    /**
     * Parses the default attributes of the control extension
     * 
     * @param controlElement
     *            control element to be parsed
     * @param controlExtension
     *            control extension to fill with parsed attributes
     * @throws ExtensionParserException
     *             if canot parse
     */
    private void parseDefaultDialogControlParameters(Element controlElement, DialogControlExtension controlExtension)
            throws ExtensionParserException {
        // Set Type
        String tagName = controlElement.getTagName();
        EnumerationProperty controlType = new EnumerationProperty();
        controlType.setValue(tagName);
        controlExtension.setType(controlType);

        controlExtension.setIdentifier(controlElement.getAttribute(ATTR_ID));
        controlExtension.setLabel(super.getStringProperty(controlElement, ATTR_LABEL));
        controlExtension.setTooltip(super.getStringProperty(controlElement, ATTR_TOOLTIP));
        controlExtension.setPosition(super.getStringProperty(controlElement, ATTR_POSITION));
        controlExtension.setMandatory(super.getBooleanProperty(controlElement, ATTR_MANDATORY, false));
    }

    /**
     * Parses text specifical parameters in text dialog extension
     * 
     * @param controlElement
     *            control element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if cannot parse
     * @throws ExtensionException
     */
    private TextDialogControlExtension parseTextExtension(Element controlElement) throws ExtensionParserException,
            ExtensionException {
        TextDialogControlExtension controlExtension = new TextDialogControlExtension();

        controlExtension.setEditable(super.getBooleanProperty(controlElement, ATTR_EDITABLE, true));
        StringProperty defaultValueStringProperty = new StringProperty();
        List<Element> childElements = super.getElementsByTagName(controlElement, ELEMENT_DEFAULT_VALUE);
        if (childElements != null && !childElements.isEmpty()) {
            Element defaultValueElement = childElements.get(0);
            String defaultValue = defaultValueElement.getTextContent();
            defaultValueStringProperty.setValue(defaultValue);
        }
        controlExtension.setDefaultValue(defaultValueStringProperty);
        return controlExtension;
    }

    /**
     * Parses text specifical parameters in text dialog extension
     * 
     * @param controlElement
     *            control element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if cannot parse
     * @throws ExtensionException
     */
    private DateDialogControlExtension parseDateExtension(Element controlElement) throws ExtensionParserException,
            ExtensionException {
        DateDialogControlExtension controlExtension = new DateDialogControlExtension();

        controlExtension.setEditable(super.getBooleanProperty(controlElement, ATTR_EDITABLE, true));
        StringProperty defaultValueStringProperty = new StringProperty();
        List<Element> childElements = super.getElementsByTagName(controlElement, ELEMENT_DEFAULT_VALUE);
        if (childElements != null && !childElements.isEmpty()) {
            Element defaultValueElement = childElements.get(0);
            String defaultValue = defaultValueElement.getTextContent();
            defaultValueStringProperty.setValue(defaultValue);
        }
        controlExtension.setDefaultValue(defaultValueStringProperty);
        return controlExtension;
    }

    /**
     * Parses text specifical parameters in text dialog extension
     * 
     * @param controlElement
     *            control element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if cannot parse
     * @throws ExtensionException
     */
    private DropDownDialogControlExtension parseDropDownExtension(Element controlElement)
            throws ExtensionParserException, ExtensionException {
        DropDownDialogControlExtension controlExtension = new DropDownDialogControlExtension();

        controlExtension.setEditable(super.getBooleanProperty(controlElement, ATTR_EDITABLE, true));
        super.getElementsByTagName(controlElement, ATTR_DEFAULT_ID);
        controlExtension.setDefaultId(super.getStringProperty(controlElement, ATTR_DEFAULT_ID));

        // parse selections
        List<Element> selectionElements = super.getElementsByTagName(controlElement, ELEMENT_SELECTION);
        for (Element selectionElement : selectionElements) {
            DropDownSelectionItemExtension item = new DropDownSelectionItemExtension();
            item.setId(super.getStringProperty(selectionElement, ATTR_ID));
            item.setName(super.getStringProperty(selectionElement, ATTR_NAME));
            item.setLocalizationPath(super.getStringProperty(selectionElement, ATTR_LOCALIZATION_PATH));
            controlExtension.getSelectionList().add(item);
        }
        return controlExtension;
    }
}
