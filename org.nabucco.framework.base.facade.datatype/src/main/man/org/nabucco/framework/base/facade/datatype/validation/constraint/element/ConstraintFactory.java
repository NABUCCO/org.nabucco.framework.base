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
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

/**
 * ConstraintFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintFactory {

    /**
     * Singleton instance.
     */
    private static ConstraintFactory instance = new ConstraintFactory();

    /**
     * Private constructor.
     */
    private ConstraintFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the ConstraintFactory instance.
     */
    public static ConstraintFactory getInstance() {
        return instance;
    }

    /**
     * Retrieves the constraint for the given constraint string.
     * 
     * @param constraint
     *            the constraint string
     * 
     * @return the appropriate constraint
     */
    public Constraint getConstraint(String constraint) {
        if (constraint == null || constraint.length() == 0) {
            throw new IllegalArgumentException("Constraint ["
                    + String.valueOf(constraint) + "] is not valid.");
        }

        char type = constraint.charAt(0);

        switch (type) {
        case LengthConstraint.TYPE:
            return new LengthConstraint(constraint);
        case MultiplicityConstraint.TYPE:
            return new MultiplicityConstraint(constraint);
        }

        throw new IllegalArgumentException("Constraint ["
                + String.valueOf(constraint) + "] is not supported.");
    }

}
