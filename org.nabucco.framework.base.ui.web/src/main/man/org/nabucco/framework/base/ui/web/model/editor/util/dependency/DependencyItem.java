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
package org.nabucco.framework.base.ui.web.model.editor.util.dependency;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencyExtension;

/**
 * Dependency
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DependencyItem {

    private static final String EMPTY_STRING = "";

    private DependencyExtension dependencyExtension;

    private boolean valid = false;

    /**
     * 
     * Creates a new {@link DependencyItem} instance.
     * 
     * @param dependencyExtension
     */
    DependencyItem(DependencyExtension dependencyExtension) {
        if (dependencyExtension == null) {
            throw new IllegalArgumentException("Cannot create dependency element with extension 'null'");
        }
        this.dependencyExtension = dependencyExtension;
    }

    /**
     * Getter for the dependent property
     * 
     * @return the dependent property name
     */
    public String getTargetProperty() {
        return PropertyLoader.loadProperty(this.dependencyExtension.getTargetProperty());
    }

    /**
     * Getter for the condition type of the dependency
     * 
     * @return the type of condition
     */
    private DependecyConditionType getDependencyConditionType() {
        return PropertyLoader.loadProperty(DependecyConditionType.class, this.dependencyExtension.getCondition());
    }

    /**
     * Process dependency check according to the given value
     * 
     * @param value
     *            the value to be checked
     * @return if the evaluation result has changed. Can be used to avoid unnessesary traffic.
     */
    public boolean checkDependency(Object value) {
        boolean checkResult = false;

        DependecyConditionType dependencyConditionType = this.getDependencyConditionType();

        switch (dependencyConditionType) {
        case NULL: {

            // Check if value is null or empty
            if (value instanceof NabuccoList<?>) {
                NabuccoList<?> list = (NabuccoList<?>) value;
                if (list.size() == 0) {
                    checkResult = true;
                    break;
                }

                Object firstValue = list.get(0);
                if (firstValue instanceof ComponentRelation<?>) {
                    ComponentRelation<?> relation = (ComponentRelation<?>) firstValue;
                    value = relation.getTarget();
                } else {
                    value = firstValue;
                }
            }

            if (value == null || value.equals(EMPTY_STRING) || value.equals(0)) {
                checkResult = true;
                break;
            }

            break;

        }
        case VALUE: {
            // Check if value is not null and not empty
            if (value instanceof NabuccoList<?>) {
                NabuccoList<?> list = (NabuccoList<?>) value;
                if (list.size() > 1) {
                    checkResult = true;
                    break;
                }

                if (list.size() == 0) {
                    break;
                }
                Object firstValue = list.get(0);
                if (firstValue instanceof ComponentRelation<?>) {
                    ComponentRelation<?> relation = (ComponentRelation<?>) firstValue;
                    value = relation.getTarget();
                } else {
                    value = firstValue;
                }
            }

            if (value != null && !value.equals(EMPTY_STRING) && !value.equals(0)) {
                checkResult = true;
                break;
            }

            break;
        }
        default: {
            throw new IllegalArgumentException("The set connection type is not supported yet");
        }
        }

        boolean retVal = (this.valid != checkResult);

        this.valid = checkResult;

        return retVal;
    }

    /**
     * Returns if the dependency is valid according to the current set value
     * 
     * @return true if valid
     */
    public boolean isValid() {
        return this.valid;
    }

}
