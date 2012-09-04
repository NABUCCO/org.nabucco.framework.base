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
package org.nabucco.framework.base.ui.web.model.editor.control.property.input;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.converter.BasetypeConverter;
import org.nabucco.framework.base.facade.datatype.converter.ConverterException;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.formatter.ControlFormatter;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * ControlModel for input area fields.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TextAreaInputControlModel extends PropertyControlModel<Basetype> {

    public static final String UPLOADFILEPATH = "uploadFilePath";

    private String uploadPath;

    /**
     * Creates a new {@link TextAreaInputControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            property path
     * @param validator
     *            validator
     * @param formatter
     *            the data formatter
     * @param dependencySet
     *            the dependency set
     * @param editable
     *            editable
     * @param uploadPath
     *            the path where the content can be uploaded
     */
    public TextAreaInputControlModel(String id, String propertyPath, NabuccoValidator<Basetype> validator,
            ControlFormatter<Basetype> formatter, DependencyController dependencySet, boolean editable,
            String uploadPath) {
        super(id, propertyPath, ControlType.TEXTAREA, validator, dependencySet, editable);

        super.setFormatter(formatter);
        this.uploadPath = uploadPath;
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
     * Return resolved configured url path where is image should/ is stored
     * 
     * @return the path like org/nabucco/images/lagranovskiy/pic.png
     */
    public String getResolvedUploadPath() {
        Datatype datatype = super.getEditorModel().getDatatype();
        // resolve the properties if any in the upload path
        PropertyLabelProvider<Datatype> provider = new PropertyLabelProvider<Datatype>(datatype);
        String resolvedUploadPath = provider.getLabelFromMessage(uploadPath, new DefaultWebRenderer());

        return resolvedUploadPath;
    }

    /**
     * Getter for the basetype value object.
     * 
     * @return the input value
     */
    public Object getInputValue() {
        return super.getValue().getValue();
    }

    /**
     * Getter for the value as string.
     * 
     * @return the string value
     */
    public String getStringValue() {
        Basetype value = super.getValue();

        String retVal = this.getFormatter().format(value);

        return retVal;
    }

    @Override
    public Basetype parse(String value) throws ControlParserException {
        try {
            Basetype newValue = this.instantiate();

            String clearedString = this.getFormatter().removeFormat(value);

            BasetypeConverter.setValueAsString(newValue, clearedString);

            return newValue;

        } catch (ConverterException ce) {
            throw new ControlParserException("Cannot convert String '" + value + "' to Basetype.'", ce);
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_VALUE, this.getStringValue());

        return json;
    }

}
