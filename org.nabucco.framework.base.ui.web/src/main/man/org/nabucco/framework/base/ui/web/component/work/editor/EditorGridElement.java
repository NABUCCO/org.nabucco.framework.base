/*
 * Copyright 2012 PRODYNA AG
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
 */
package org.nabucco.framework.base.ui.web.component.work.editor;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;

/**
 * EditorGridElement
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class EditorGridElement extends WebComponent {

    private static final long serialVersionUID = 1L;

    private static final String JSON_COLUMN = "col";

    private static final String JSON_ROW = "row";

    private static final String JSON_HEIGHT = "height";

    private static final String JSON_WIDTH = "width";

    private static final String JSON_HINT = "hint";

    EditorControlExtension extension;

    /** The Control start position */
    private String startPosition;

    /** The Control end position */
    private String endPosition;

    /**
     * Creates a new {@link EditorGridElement} instance.
     * 
     * @param type
     *            the type of the grid element
     * @param extension
     *            the ui extension
     */
    public EditorGridElement(WebElementType type, EditorControlExtension extension) {
        super(type, extension);
        this.extension = extension;
    }

    /**
     * Get the grid element model of this element
     * 
     * @return the configured model
     */
    public abstract EditorGridElementModel getGridElementModel();

    /**
     * Indicates if the control may be visible on the ui
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * Getter for the editor control start position.
     * 
     * @return the editor control start position
     */
    private String getStartPosition() {
        if (startPosition == null) {
            String position = PropertyLoader.loadProperty(extension.getPosition());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[0].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve position of control [" + this.getId() + "].");
            }

            startPosition = positions[0];
        }

        return startPosition;
    }

    /**
     * Getter for the editor control end position.
     * 
     * @return the editor control end position
     */
    private String getEndPosition() {
        if (endPosition == null) {
            String position = PropertyLoader.loadProperty(extension.getPosition());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[1].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve position of control [" + this.getId() + "].");
            }

            endPosition = positions[1];
        }

        return endPosition;
    }

    /**
     * Getter for the starting row.
     * 
     * @return the start row
     */
    public Integer getStartRow() {
        String start = this.getStartPosition();
        int startHeight = Integer.parseInt(start.substring(1));

        return startHeight;
    }

    /**
     * Getter for the starting row.
     * 
     * @return the start row
     */
    public Integer getStartColumn() {
        String start = this.getStartPosition();
        Character startCharacter = start.charAt(0);

        return Character.getNumericValue(startCharacter) - 10;
    }

    /**
     * Getter for the control height.
     * 
     * @return the height of the control
     */
    public Integer getHeight() {
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
    public Integer getWidth() {
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
        return PropertyLoader.loadProperty(extension.getHint());
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     *            visitor to be accepted
     * @param context
     *            context of the visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_ID, this.getId());
        json.add(JSON_ROW, this.getStartRow());
        json.add(JSON_COLUMN, this.getStartColumn());
        json.add(JSON_HEIGHT, this.getHeight());
        json.add(JSON_WIDTH, this.getWidth());
        json.add(JSON_HINT, this.getHint());

        return json;
    }

}
