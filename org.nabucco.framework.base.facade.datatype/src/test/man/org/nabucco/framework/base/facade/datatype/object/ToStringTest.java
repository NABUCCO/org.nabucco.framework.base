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
package org.nabucco.framework.base.facade.datatype.object;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * ToStringTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ToStringTest {

    @Test
    public void testToStringBasetype() {
        Name n1 = new Name();
        Name n2 = new Name();

        String n1String = n1.toString();
        System.out.println(n1String);

        String n2string = n2.toString();
        System.out.println(n2string);

        Assert.assertNotNull("String must not be null.", n1String);
        Assert.assertEquals("Strings must be equal.", n1String, n2string);
    }

    @Test
    public void testToStringDatatype() {
        User user = new User();

        String userString = user.toString();
        System.out.println(userString);

        Assert.assertNotNull("String must not be null.", userString);
    }

}
