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
package org.nabucco.framework.base.facade.datatype.validation;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;


/**
 * BasetypeValidatorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BasetypeValidatorTest {

    @Test
    public void testBasetypeValidation() throws Exception {

        Name name = new Name();
        name.setValue("Dummy");
        
        ValidationResult result = new ValidationResult();
        name.validate(result, ValidationType.DEEP);
        
        System.out.println(result);
        Assert.assertNotNull(result.getErrorList());
        Assert.assertEquals(0, result.getErrorList().size());

        System.out.println();
        
        Owner owner = new Owner("");
        owner.validate(result, ValidationType.DEEP);
     
        System.out.println(result);
        Assert.assertEquals(1, result.getErrorList().size());
    }

}
