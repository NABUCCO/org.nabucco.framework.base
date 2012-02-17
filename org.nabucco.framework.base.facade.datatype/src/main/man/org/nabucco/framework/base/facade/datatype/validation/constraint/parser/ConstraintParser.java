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
package org.nabucco.framework.base.facade.datatype.validation.constraint.parser;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintFactory;

/**
 * ConstraintParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintParser {

    /** Constraint Separator */
    static final String CONSTRAINT_SEPARATOR = ";";

    /** Cached Constraints */
    private Map<String, SoftReference<ConstraintContainer>> containerMap;

    /**
     * Singleton instance.
     */
    private static ConstraintParser instance;

    /**
     * Private constructor.
     */
    private ConstraintParser() {
        this.containerMap = new HashMap<String, SoftReference<ConstraintContainer>>();
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
     * Parses the constraint string of a property and adds them to the constraint container.
     * 
     * @param constraintString
     *            the string to parse
     * 
     * @return the constraint container
     */
    public synchronized ConstraintContainer parseConstraints(String constraintString) {
        if (constraintString == null || constraintString.isEmpty()) {
            return new ConstraintContainer();
        }

        ConstraintContainer container = null;

        if (this.containerMap.get(constraintString) == null || this.containerMap.get(constraintString).get() == null) {
            container = this.parseConstraintString(constraintString);
            this.containerMap.put(constraintString, new SoftReference<ConstraintContainer>(container));
        }

        container = this.containerMap.get(constraintString).get();
        container.clear();

        return container;
    }

    /**
     * Parses the constraint string with indexes and returns all constraints per property index.
     * 
     * @param constraints
     *            the constraints string including indexes to parse
     * 
     * @return the constraint containers per property index
     */
    public synchronized Map<Integer, ConstraintContainer> parseConstraintsWithIndex(String constraints) {

        if (constraints == null || constraints.isEmpty()) {
            return Collections.emptyMap();
        }

        String[] properties = constraints.split("\\s");

        Map<Integer, ConstraintContainer> constraintMap = new TreeMap<Integer, ConstraintContainer>();

        for (String propertyConstraints : properties) {
            if (propertyConstraints == null || propertyConstraints.isEmpty()) {
                continue;
            }

            char first = propertyConstraints.charAt(0);
            if (!Character.isDigit(first) && first != '-') {
                continue;
            }

            StringBuilder indexBuffer = new StringBuilder();
            indexBuffer.append(first);

            for (int i = 1; i < propertyConstraints.length(); i++) {
                char character = propertyConstraints.charAt(i);
                if (!Character.isDigit(character)) {
                    break;
                }
                indexBuffer.append(character);
            }

            int index = Integer.parseInt(indexBuffer.toString());

            ConstraintContainer constraintContainer = this.parseConstraints(propertyConstraints.substring(indexBuffer
                    .length()));

            ConstraintContainer container = new ConstraintContainer();
            container.addAll(constraintContainer);

            constraintMap.put(index, container);
        }

        return constraintMap;
    }

    /**
     * Parses the constraints string and creates a constraint container.
     * 
     * @param constraintString
     *            the constraint string of one index
     * 
     * @return the parsed constraints
     */
    private ConstraintContainer parseConstraintString(String constraintString) {
        Set<Constraint> constraintList = new HashSet<Constraint>();
        String[] tokens = constraintString.split(CONSTRAINT_SEPARATOR);

        for (String token : tokens) {
            Constraint constraint = ConstraintFactory.getInstance().getConstraint(token);
            constraintList.add(constraint);
        }

        return new ConstraintContainer(constraintList);
    }

}
