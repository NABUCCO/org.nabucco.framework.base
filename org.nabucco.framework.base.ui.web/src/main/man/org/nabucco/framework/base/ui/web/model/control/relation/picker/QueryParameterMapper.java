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
package org.nabucco.framework.base.ui.web.model.control.relation.picker;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterMappingExtension;

/**
 * QueryParameterMapper
 * 
 * Parameter mapper is used by pickers to controll mapping parameters
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class QueryParameterMapper {

    private QueryParameterMappingExtension extension;

    private Map<String, String> parameterMap;

    /**
     * 
     * Creates a new {@link QueryParameterMapper} instance.
     * 
     * @param extension
     */
    public QueryParameterMapper(QueryParameterMappingExtension extension) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create Query Parameter Mapper instance. The extension is 'null'");
        }

        this.extension = extension;
    }

    /**
     * Initialize extension
     */
    private void init() {
        this.parameterMap = new HashMap<String, String>();

        for (QueryParameterExtension parameter : this.extension.getParameters()) {
            String name = PropertyLoader.loadProperty(parameter.getName());
            String propertyTarget = PropertyLoader.loadProperty(parameter.getTargetProperty());
            if (this.parameterMap.containsKey(name)) {
                throw new IllegalArgumentException("The query paramter " + name + " is double defined. Conflict. ");
            }
            this.parameterMap.put(name, propertyTarget);
        }
    }

    /**
     * Getter for the target property to parameter
     * 
     * @param parameter
     *            the parameter to search for
     * @return target property path ("employee.master.owner") or null if not found
     */
    public String getParameterTargetProperty(String parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Cannot get property path to parameter 'null'.");
        }

        if (this.parameterMap == null) {
            this.init();
        }

        if (!this.parameterMap.containsKey(parameter)) {
            return null;
        }

        return this.parameterMap.get(parameter);

    }
}
