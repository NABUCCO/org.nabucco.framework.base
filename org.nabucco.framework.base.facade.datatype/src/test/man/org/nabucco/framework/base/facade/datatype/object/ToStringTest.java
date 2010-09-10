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

        Assert.assertNotNull("String must not be null.", n1.toString());
        Assert.assertEquals("Strings must be equal.", n1.toString(), n2.toString());
    }

    @Test
    public void testToStringDatatype() {
        User u1 = new User();
        User u2 = new User();

        Assert.assertNotNull("String must not be null.", u1.toString());
        Assert.assertEquals("Strings must be equal.", u1.toString(), u2.toString());
    }

}
