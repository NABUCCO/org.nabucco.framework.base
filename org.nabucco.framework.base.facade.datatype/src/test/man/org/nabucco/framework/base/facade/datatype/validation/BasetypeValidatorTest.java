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

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.business.WorkTime;
import org.nabucco.framework.base.facade.datatype.business.address.Email;

/**
 * BasetypeValidatorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BasetypeValidatorTest {

    @Test
    public void testBasetypeLengthValidation() throws Exception {

        Name name = new Name();
        name.setValue("Dummy");

        ValidationResult result = new ValidationResult();
        name.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertNotNull(result.getErrorList());
        Assert.assertEquals(0, result.getErrorList().size());

        System.out.println();

        Owner owner = new Owner("");
        owner.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(1, result.getErrorList().size());
    }

    @Test
    public void testBasetypeValueValidation() throws Exception {

        ValidationResult result = new ValidationResult();

        DateTime time = new DateTime(0l);
        time.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(0, result.getErrorList().size());
    }

    @Test
    public void testBasetypeDecimalValueValidation() throws Exception {

        ValidationResult result = new ValidationResult();

        WorkTime time = new WorkTime(new BigDecimal(-0.5));
        time.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(1, result.getErrorList().size());
    }

    @Test
    public void testBasetypePatternValidation() throws Exception {

        ValidationResult result = new ValidationResult();

        Email email = new Email("[asf@de.prodyna.com");
        email.validate(result, ValidationType.SHALLOW);

        System.out.println(result);
        Assert.assertEquals(0, result.getErrorList().size());
    }

}
