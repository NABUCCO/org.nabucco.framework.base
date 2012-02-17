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
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;

/**
 * PatternConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PatternConstraint extends Constraint {

    /** The pattern constraint type. */
    static final char TYPE = 'p';

    private String pattern;

    private static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

    private static final String MESSAGE = "Error at {0} with property {1} where value is \"{2}\" and pattern constraint is {3}.";

    /**
     * Creates a new {@link PatternConstraint} instance.
     * 
     * @param patternString
     *            the pattern to compile
     */
    PatternConstraint(String patternString) {
        super(ConstraintType.PATTERN);

        // Remove preceding constraint character!
        if (patternString.charAt(0) == TYPE) {
            patternString = patternString.substring(1);
        }

        this.pattern = patternString;

        if (!patternMap.containsKey(patternString)) {
            patternMap.put(this.pattern, Pattern.compile(this.pattern));
        }
    }

    @Override
    public void check(Validatable owner, NabuccoProperty property, ValidationResult result) {

        if (property == null || property.getInstance() == null) {
            return;
        }

        if (property.getPropertyType() == NabuccoPropertyType.BASETYPE) {
            BasetypeProperty basetypeProperty = (BasetypeProperty) property;
            Object value = basetypeProperty.getInstance().getValue();

            if (value instanceof String) {
                this.checkString((String) value, property.getName(), owner, result);
            }

        } else if (property.getPropertyType() == NabuccoPropertyType.SIMPLE) {
            SimpleProperty simpleProperty = (SimpleProperty) property;
            Object value = simpleProperty.getInstance();

            if (value instanceof String) {
                this.checkString((String) value, property.getName(), owner, result);
            }

        } else {
            this.raiseException(owner, property);
        }

    }

    /**
     * Checks a string against the length constraints.
     * 
     * @param propertyValue
     *            the concrete property value
     * @param propertyName
     *            name of the property to validate
     * @param parent
     *            the parent object
     * @param result
     *            the validation result
     */
    private void checkString(String propertyValue, String propertyName, Validatable parent, ValidationResult result) {

        String parentName = parent.getClass().getSimpleName();

        Pattern pattern = patternMap.get(this.pattern);
        Matcher matcher = pattern.matcher(propertyValue);

        if (!matcher.matches()) {
            Object[] arguments = new Object[] { parentName, propertyName, propertyValue, this.pattern };

            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(parent, propertyName, message));
        }
    }

    /**
     * Raise an {@link IllegalArgumentException}.
     * 
     * @param owner
     *            the validation owner
     * @param property
     *            the property
     */
    private void raiseException(Validatable owner, NabuccoProperty property) {
        StringBuilder message = new StringBuilder();
        message.append("Can only validate a PatternConstraint against a Basetype. Given property ");
        message.append(property.getName());
        message.append(" in type ");
        message.append(owner.getClass().getSimpleName());
        message.append(" was of type ");
        message.append(property.getPropertyType().getName());
        message.append(".");
        throw new IllegalArgumentException(message.toString());
    }

    /**
     * Getter for the pattern.
     * 
     * @return Returns the pattern or null if it has not been compiled yet.
     */
    public Pattern getPattern() {
        return patternMap.get(this.pattern);
    }

    /**
     * Getter for the pattern string.
     * 
     * @return Returns the pattern string.
     */
    public String getPatternAsString() {
        return this.pattern;
    }

    @Override
    public String format() {
        StringBuilder result = new StringBuilder();
        result.append(TYPE);
        result.append(this.pattern);
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Pattern (");
        result.append(this.pattern);
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean isValidRestriction(Constraint c) {
        return false;
    }

}
