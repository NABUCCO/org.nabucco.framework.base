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
package org.nabucco.framework.base.ui.web.model.control.property.image;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.control.ControlType;
import org.nabucco.framework.base.ui.web.model.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.control.util.validator.NabuccoValidator;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * SingleImageControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SingleImageControlModel extends PropertyControlModel<NType> {

    private static final String NO_IMAGE_URI = "./images/noimage.png";

    private static final String IMAGE_PREFIX = "./resource/id/";

    private String dialogId;

    private String urlPath;

    /**
     * Creates a new {@link SingleImageControlModel} instance.
     * 
     * @param id
     *            the id of the element
     * @param propertyPath
     *            the path to the image property
     * @param validator
     *            the validator to use
     * @param dependencyController
     *            the dependency controller to use
     * @param editable
     *            true if the user can edit image and chose another one
     * @param dialogId
     *            the dialog to use for editing of the image
     */
    public SingleImageControlModel(String id, String propertyPath, NabuccoValidator<NType> validator,
            DependencyController dependencyController, boolean editable, String dialogId, String urlPath) {
        super(id, propertyPath, ControlType.IMAGE, validator, dependencyController, editable);

        if (dialogId == null) {
            throw new IllegalArgumentException(
                    "Cannot create Single Image control model because the referenced dialog id is 'null'");
        }
        this.dialogId = dialogId;

        if (urlPath == null) {
            throw new IllegalArgumentException(
                    "Cannot create Single Image control model because the referenced url path is 'null'");
        }
        this.urlPath = urlPath;

    }

    @Override
    protected NType parse(String stringValue) throws ControlParserException {
        // TODO Think about it!!!
        return null;
    }

    /**
     * Resolves the image url that should be opened
     * 
     * @return image url string
     */
    private String getImageURL() {
        ComponentRelationProperty contentRelation = (ComponentRelationProperty) this.getProperty();
        if (contentRelation == null) {
            throw new IllegalStateException(
                    "The component relation is null. Cannot render single image element");
        }
        
        NabuccoList<ComponentRelation<?>> instance = contentRelation.getInstance();

        if (instance == null) {
            throw new IllegalStateException(
                    "The instance of component relation is null. Cannot render single image element");
        }

        if (instance.size() == 0) {
            return NO_IMAGE_URI;
        }

        if (instance.size() == 1) {
            ComponentRelation<?> componentRelation = instance.get(0);
            ContentEntry target = (ContentEntry) componentRelation.getTarget();

            Long resourceId = target.getId();
            ContentEntryType type = target.getType();

            // Creates a new reference key to the image
            String imageReferenceKey = NabuccoServletUtil.getResourceController().addResource(resourceId, type);

            return IMAGE_PREFIX + imageReferenceKey;
        }

        return NO_IMAGE_URI;
    }

    /**
     * Return resolved configured url path where is image should/ is stored
     * 
     * @return the path like org/nabucco/images/lagranovskiy/pic.png
     */
    public String getResolvedUploadPath() {
        Datatype datatype = super.getEditorModel().getDatatype();
        // resolve the properties if any in the upload path
        PropertyLabelProvider<Datatype> provider = new PropertyLabelProvider<Datatype>(datatype);
        String resolvedUploadPath = provider.getLabelFromMessage(this.urlPath, new DefaultWebRenderer());

        return resolvedUploadPath;
    }

    /**
     * Getter for the extensionFilter.
     * 
     * @return Returns the extensionFilter.
     */
    public String getDialogId() {
        return this.dialogId;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        String imageURLToLoad = this.getImageURL();
        json.add(JSON_VALUE, imageURLToLoad);

        return json;
    }

}
