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
package org.nabucco.framework.base.facade.datatype.validation;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * DatatypeValidatorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeValidatorTest {

    @Test
    public void testMinLengthValidation() throws Exception {

        User user = new User();
        ValidationResult result = new ValidationResult();

        user.setUsername("Dummy");
        user.setOwner("");
        user.setDescription("This is a dummy Description!");
        user.setUserType(new Code());

        user.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(1, result.getErrorList().size());

        // Valid
        result = new ValidationResult();

        user.setOwner("NBC");
        user.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(0, result.getErrorList().size());
        System.out.println();
    }

    @Test
    public void testMaxLengthValidation() throws Exception {

        User user = new User();
        ValidationResult result = new ValidationResult();

        user.setUsername("TO LOOOOOOOOOOOOOOOOOOOOOOOOONG");
        user.setOwner("NBC");
        user.setDescription("This is a dummy Description!");
        user.setUserType(new Code());

        user.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(1, result.getErrorList().size());

        // Valid
        result = new ValidationResult();

        user.setUsername("NOT TO LOOOOOOOOOOOOOOOOOOOONG");
        user.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(0, result.getErrorList().size());
        System.out.println();
    }

    @Test
    public void testMultiplicityValidation() throws Exception {
        User user = new User();
        ValidationResult result = new ValidationResult();

        user.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(4, result.getErrorList().size());
        System.out.println();
    }

}
