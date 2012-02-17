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
package org.nabucco.framework.base.ui.web.component.work.editor.control;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;

/**
 * EditorField
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class EditorControl extends WebComponent {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_COLUMN = "col";

    private static final String JSON_ROW = "row";

    private static final String JSON_HEIGHT = "height";

    private static final String JSON_WIDTH = "width";

    private static final String JSON_HINT = "hint";

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorControl.class);

    /** The Editor Field Extension */
    private EditorControlExtension extension;

    /** The Control start position */
    private String startPosition;

    /** The Control end position */
    private String endPosition;

    /** The Control Model */
    private ControlModel<?> model;

    /** The control dependency Set */
    private DependencyController dependencySet;

    /**
     * Creates a new {@link EditorControl} instance.
     * 
     * @param extension
     *            the editor control extension
     */
    public EditorControl(EditorControlExtension extension) {
        super(WebElementType.EDITOR_CONTROL, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor control for extension 'null'.");
        }
        this.extension = extension;
    }

    /**
     * Creates a new {@link EditorControl} instance
     * 
     * @param extension
     *            the editor control Extension
     * @param type
     *            type of the control
     */
    public EditorControl(EditorControlExtension extension, WebElementType type) {
        super(type, extension);

        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor control for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        String propertyPath = this.getProperty();

        String id = this.getId();

        if (id == null) {
            throw new IllegalArgumentException(
                    "Error by instanciation of the control. The id is not set and is 'null'.");
        }

        if (propertyPath == null || propertyPath.isEmpty()) {
            throw new IllegalArgumentException("Cannot add Control for property [null] to the model.");
        }

        DependencySetExtension dependencySetExtension = this.extension.getDependencySet();
        this.dependencySet = new DependencyController(dependencySetExtension);
        this.dependencySet.init();
        this.model = this.instantiateModel(id, propertyPath);
        this.model.init();
    }

    /**
     * Instanciate a Model of the Control
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            the property path
     * @return
     */
    public abstract ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException;

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public ControlModel<?> getModel() {
        return this.model;
    }

    /**
     * Indicates if the control may be visible on the ui
     * 
     * @return true if visible
     */
    public boolean isVisible(){
        return this.model.isVisible();
    }

    /**
     * Getter for the dependency set
     * 
     * @return dependency set
     */
    protected DependencyController getDependencySet() {
        return this.dependencySet;
    }

    /**
     * Getter for the editor control id.
     * 
     * @return the editor control id or null if no setted
     */
    public String getId() {
        if (this.extension.getIdentifier() == null) {
            return null;
        }

        return this.extension.getIdentifier().getValue();
    }

    /**
     * Getter for the editor control property.
     * 
     * @return the editor control property
     */
    private String getProperty() {
        return PropertyLoader.loadProperty(this.extension.getProperty());
    }

    /**
     * Getter for the editor control label.
     * 
     * @return the editor control label
     */
    private String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the editor control tooltip.
     * 
     * @return the editor control tooltip
     */
    private String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }


    /**
     * Return if the Control Editable Flag.
     * 
     * @return <b>true</b> if the control is editable, <b>false</b> if not
     */
    protected boolean getEditable() {
        return PropertyLoader.loadProperty(this.extension.getEditable());
    }

    /**
     * Getter for the editor control start position.
     * 
     * @return the editor control start position
     */
    private String getStartPosition() {
        if (this.startPosition == null) {
            String position = PropertyLoader.loadProperty(this.extension.getPosition());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[0].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve position of control [" + this.getId() + "].");
            }

            this.startPosition = positions[0];
        }

        return this.startPosition;
    }

    /**
     * Getter for the editor control end position.
     * 
     * @return the editor control end position
     */
    private String getEndPosition() {
        if (this.endPosition == null) {
            String position = PropertyLoader.loadProperty(this.extension.getPosition());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[1].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve position of control [" + this.getId() + "].");
            }

            this.endPosition = positions[1];
        }

        return this.endPosition;
    }

    /**
     * Getter for the starting row.
     * 
     * @return the start row
     */
    private Integer getStartRow() {
        String start = this.getStartPosition();
        int startHeight = Integer.parseInt(start.substring(1));

        return startHeight;
    }

    /**
     * Getter for the starting row.
     * 
     * @return the start row
     */
    private Integer getStartColumn() {
        String start = this.getStartPosition();
        Character startCharacter = start.charAt(0);

        return Character.getNumericValue(startCharacter) - 10;
    }

    /**
     * Getter for the control height.
     * 
     * @return the height of the control
     */
    private Integer getHeight() {
        String start = this.getStartPosition();
        String end = this.getEndPosition();

        int startHeight = Integer.parseInt(start.substring(1));
        int endHeight = Integer.parseInt(end.substring(1));

        return endHeight - startHeight + 1;
    }

    /**
     * Getter for the control width.
     * 
     * @return the width of the control
     */
    private Integer getWidth() {
        String start = this.getStartPosition();
        String end = this.getEndPosition();

        Character startCharacter = start.charAt(0);
        Character endCharacter = end.charAt(0);

        return endCharacter.compareTo(startCharacter) + 1;
    }

    /**
     * Getter for the editor control position hint.
     * 
     * @return the editor control position hint
     */
    private String getHint() {
        return PropertyLoader.loadProperty(this.extension.getHint());
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    protected EditorControlExtension getExtension() {
        return this.extension;
    }


    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_ROW, this.getStartRow());
        json.add(JSON_COLUMN, this.getStartColumn());
        json.add(JSON_HEIGHT, this.getHeight());
        json.add(JSON_WIDTH, this.getWidth());
        json.add(JSON_HINT, this.getHint());

        if (this.model != null) {
            json.add(JSON_MODEL, this.model.toJson());
        }

        return json;
    }


}
