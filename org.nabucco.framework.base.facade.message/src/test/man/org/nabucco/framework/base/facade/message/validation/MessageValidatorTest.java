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
package org.nabucco.framework.base.facade.message.validation;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;

/**
 * MessageValidatorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MessageValidatorTest {

    @Test
    public void testDatatypeValidation() throws ValidationException {

        Name name = new Name();
        name.setValue("TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---TOOOOOOOOOLONG---");

        User user = new User();
        user.setUsername(name);

        UserMsg msg = new UserMsg();

        ValidationResult result = new ValidationResult();
        msg.validate(result);

        Assert.assertEquals(1, result.getErrorList().size());

        result = new ValidationResult();
        msg.setUser(user);
        msg.validate(result);

        Assert.assertEquals(4, result.getErrorList().size());
    }

    /**
     * Test Class
     */
    private class UserMsg extends ServiceMessageSupport implements ServiceMessage {

        private static final long serialVersionUID = 1L;

        private User User;

        @Override
        public String[] getConstraints() {
            return new String[] { "m1,1;" };
        }

        @Override
        public Object[] getProperties() {
            return VisitorUtility.merge(super.getProperties(), new Object[] { this.getUser() });
        }

        public User getUser() {
            return this.User;
        }

        public void setUser(User User) {
            this.User = User;
        }
    }

}
