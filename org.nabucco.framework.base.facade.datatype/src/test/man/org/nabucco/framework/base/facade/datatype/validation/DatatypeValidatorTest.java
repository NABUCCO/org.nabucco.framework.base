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
import org.nabucco.framework.base.facade.datatype.security.User;


/**
 * DatatypeValidatorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeValidatorTest {

    @Test
    public void testDatatypeValidation() throws ValidationException {

        Name name = new Name();
        name.setValue("TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---");

        User user = new User();
        user.setUsername(name);

        ValidationResult result = new ValidationResult();
        user.validate(result);

        Assert.assertEquals(4, result.getErrorList().size());
    }
    
}
