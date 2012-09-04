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
package org.nabucco.framework.base.ui.web.model.editor.util.parser;

import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * NabuccoBooleanParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BooleanControlParser implements ControlParser<NBoolean> {

    private Class<NBoolean> classname;

    /**
     * Parses boolean values
     * 
     * Creates a new {@link BooleanControlParser} instance.
     * 
     * @param classname
     *            classname to be instanciated
     */
    public BooleanControlParser(Class<NBoolean> classname) {
        if (classname == null) {
            throw new IllegalArgumentException("Cannot create boolean parser. classname is null");
        }
        this.classname = classname;
    }

    /**
     * Parses boolean values
     * 
     * Creates a new {@link BooleanControlParser} instance.
     * 
     * @param classname
     *            classname to be instanciated
     */
    public BooleanControlParser() {
    }

    @Override
    public NBoolean parseString(String value) throws ControlParserException {
        return this.parseString(value, classname);
    }

    @Override
    @SuppressWarnings("unchecked")
    public NBoolean parseString(NabuccoProperty property, String value) throws ControlParserException {
        if (property == null) {
            return null;
        }
        if (property.getInstance() != null) {
            Class<NBoolean> classname = (Class<NBoolean>) property.getInstance().getClass();
            return this.parseString(value, classname);
        } else {
            Class<?> boundType = property.getType();

            if (NBoolean.class.isAssignableFrom(boundType)) {
                Class<NBoolean> className = (Class<NBoolean>) boundType;
                return this.parseString(value, className);
            } else {
                throw new IllegalStateException(
                        "Cannot parse boolean value. The property is bound to a non boolean basetype.");
            }

        }
    }

    /**
     * Parses boolean
     * 
     * @param value
     *            value to be parsed
     * @param classname
     *            the classname to be instanciated
     * @return parsed boolean
     * @throws ControlParserException
     *             if problems by parsing or instanciation
     */
    private NBoolean parseString(String value, Class<NBoolean> classname) throws ControlParserException {
        if (classname == null) {
            throw new IllegalArgumentException("Cannot instanciate boolean class 'null'");
        }

        try {
            Boolean parsedBool = Boolean.parseBoolean(value);
            NBoolean retVal = classname.newInstance();

            retVal.setValue(parsedBool);

            return retVal;
        } catch (InstantiationException e) {
            throw new ControlParserException("Cannot instanciate class " + classname.getCanonicalName(), e);
        } catch (IllegalAccessException e) {
            throw new ControlParserException("Cannot access class " + classname.getCanonicalName(), e);
        }
    }
}
