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
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * EqualsTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EqualsTest {

    @Test
    public void testEqualsBasetype() {
        Name n1 = new Name();
        Name n2 = new Name();

        Assert.assertEquals("Object must be equal with itself.", n1, n2);
        Assert.assertEquals("Objects must be equal.", n1, n2);

        Name n3 = new Name("Test");
        Assert.assertFalse("Objects must not be equal.", n1.equals(n3));
    }

    @Test
    public void testEqualsDatatype() {
        User u1 = new User();
        User u2 = new User();

        Assert.assertEquals("Object must be equal with itself.", u1, u2);
        Assert.assertEquals("Objects must be equal.", u1, u2);

        User u3 = new User();
        u3.setUsername("Test");
        Assert.assertFalse("Objects must not be equal.", u1.equals(u3));
    }

    @Test
    public void testEqualSafety() throws Exception {
        Code c1 = new Code();
        Code c2 = new Code();

        Assert.assertTrue(c1.equals(c2));

        c1.getName();
        Assert.assertTrue("getName", c1.equals(c2));

        c1.setName((String) null);
        Assert.assertTrue("setName", c1.equals(c2));

        Assert.assertTrue("getId", c1.equals(c2));

        c1.getId();
        Assert.assertTrue("getId", c1.equals(c2));
    }

}
