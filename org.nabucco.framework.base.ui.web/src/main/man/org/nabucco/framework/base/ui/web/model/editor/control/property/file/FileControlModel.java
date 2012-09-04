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
package org.nabucco.framework.base.ui.web.model.editor.control.property.file;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignment;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * FileControlModel
 * 
 * the model of the file upload control.
 * 
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class FileControlModel extends PropertyControlModel<ContentEntry> {

    private static final String JSON_URL = "url";

    public static final String UPLOADFILEPATH = "uploadFilePath";

    private static final String RESOURCE_PREFIX = "./resource/id/";

    private String urlPath;

    /**
     * 
     * Creates a new {@link FileControlModel} instance.
     * 
     * @param id
     *            the id of the file control
     * @param propertyPath
     *            the bound property path
     * @param validator
     *            the validator to use to validate input
     * @param dependencyController
     *            dependency controller to use
     * @param editable
     *            is the control editable or not
     * @param urlPath
     *            the uplaod url path
     */
    public FileControlModel(String id, String propertyPath, NabuccoValidator<ContentEntry> validator,
            DependencyController dependencyController, boolean editable, String urlPath) {

        super(id, propertyPath, ControlType.FILE, validator, dependencyController, editable);

        if (urlPath == null) {
            throw new IllegalArgumentException(
                    "Cannot create Single Image control model because the referenced url path is 'null'");
        }
        this.urlPath = urlPath;
    }

    @Override
    protected ContentEntry parse(String url) throws ControlParserException {

        String instanceid = url.substring(url.indexOf("/") + 1, url.lastIndexOf("/"));
        ContentEntry uploadedResource = NabuccoServletUtil.getResourceController().getUploadedResource(instanceid);

        return uploadedResource;
    }

    @Override
    public void setProperty(NabuccoProperty property) {
        super.setProperty(property);

        // If possible set upload path
        NabuccoProperty uploadFilePathProperty = property.getParent().getProperty(UPLOADFILEPATH);
        if (uploadFilePathProperty != null) {
            Path instance = (Path) uploadFilePathProperty.getInstance();
            if (instance == null) {
                instance = new Path();
            }

            instance.setValue(this.getResolvedUploadPath());
            NabuccoProperty newPathproperty = uploadFilePathProperty.createProperty(instance);
            property.getParent().setProperty(newPathproperty);
        }
    }

    /**
     * Creates and gives back an download url for the bound file
     * 
     * @return url for download or null if no file uploaded
     */
    private String getDownloadUrl() {
        ContentEntry datatype = this.getValue();
        Long resourceId = datatype.getId();
        ContentEntryType type = datatype.getType();

        if (resourceId == null) {
            return "";
        }

        // Creates a new reference key to the image
        String imageReferenceKey = NabuccoServletUtil.getResourceController().addDownloadReferenceResource(resourceId,
                type);

        return RESOURCE_PREFIX + imageReferenceKey + "/" + datatype.getName().getValue();
    }

    /**
     * Return resolved configured url path where is image should/ is stored
     * 
     * @return the path like org/nabucco/images/lagranovskiy/pic.png
     */
    public String getResolvedUploadPath() {
        ContentEntryAssignment datatype = (ContentEntryAssignment) super.getEditorModel().getDatatype();
        // resolve the properties if any in the upload path
        PropertyLabelProvider<Datatype> provider = new PropertyLabelProvider<Datatype>(datatype);
        String resolvedUploadPath = provider.getLabelFromMessage(urlPath, new DefaultWebRenderer());

        return resolvedUploadPath;
    }

    /**
     * Returns the name of the corrently
     * 
     * @return filename or empty string
     */
    @Override
    public String getStringValue() {
        ContentEntry actualValue = super.getValue();
        if (actualValue == null) {
            return "";
        } else {
            Name name = actualValue.getName();
            if (name == null) {
                return "";
            }
            return name.getValue();
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();
        json.add(JSON_URL, this.getDownloadUrl());
        json.add(JSON_VALUE, this.getStringValue());
        return json;
    }

}
