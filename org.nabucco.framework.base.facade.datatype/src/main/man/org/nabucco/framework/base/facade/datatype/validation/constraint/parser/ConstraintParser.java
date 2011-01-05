/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.facade.datatype.validation.constraint.parser;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintFactory;

/**
 * ConstraintParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintParser {

    private Map<Class<? extends Validatable>, SoftReference<ConstraintContainer>> containerMap;

    private static final String SEPARATOR = ";";

    /**
     * Singleton instance.
     */
    private static ConstraintParser instance;

    /**
     * Private constructor.
     */
    private ConstraintParser() {
        this.containerMap = new HashMap<Class<? extends Validatable>, SoftReference<ConstraintContainer>>();
    }

    /**
     * Singleton access.
     * 
     * @return the ConstraintParser instance.
     */
    public static synchronized ConstraintParser getInstance() {
        if (instance == null) {
            instance = new ConstraintParser();
        }
        return instance;
    }

    /**
     * Parses a datatype for static constraints and puts them into an appropriate constraint
     * container.
     * 
     * @param validatable
     *            the validatable datatype to parse
     * 
     * @return the extracted constraint container
     */
    public synchronized ConstraintContainer parseConstraint(Validatable validatable) {

        ConstraintContainer container = null;

        if (validatable == null) {
            throw new IllegalArgumentException("Validatable is not valid.");
        }

        if (this.containerMap.get(validatable.getClass()) == null
                || this.containerMap.get(validatable.getClass()).get() == null) {
            container = this.parseAllConstraints(validatable);
            this.containerMap.put(validatable.getClass(), new SoftReference<ConstraintContainer>(
                    container));

        } else {
            container = this.containerMap.get(validatable.getClass()).get();
        }

        return container;
    }

    /**
     * Parses the constraint string of a validatable and maps them to the constraint container
     * containing the appropriate constraints.
     * 
     * @param validatable
     *            the validatable to
     * 
     * @return the parsed constraint container
     */
    private ConstraintContainer parseAllConstraints(Validatable validatable) {

        List<NabuccoProperty<?>> properties = validatable.getProperties();
        ConstraintContainer container = new ConstraintContainer();
        for (int i = 0; i < properties.size(); i++) {
            NabuccoProperty<?> property = properties.get(i);
            container.put(i, this.parseConstraintsForIndex(property.getConstraints()));
        }
        return container;
    }

    /**
     * Parses the constraints for a single index.
     * 
     * @param constraintString
     *            the constraint string of one index
     * 
     * @return the parsed constraints
     */
    private List<Constraint> parseConstraintsForIndex(String constraintString) {
        List<Constraint> constraintList = new ArrayList<Constraint>();
        String[] tokens = constraintString.split(SEPARATOR);

        for (String token : tokens) {
            constraintList.add(ConstraintFactory.getInstance().getConstraint(token));
        }

        return constraintList;
    }

}
