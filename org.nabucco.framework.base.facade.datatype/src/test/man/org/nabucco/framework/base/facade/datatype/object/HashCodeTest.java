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
 * HashCodeTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class HashCodeTest {

    @Test
    public void testHashCodeBasetype() {
        Name n1 = new Name();
        Name n2 = new Name();

        Assert.assertEquals("Hashcode of same object must be equal.", n1.hashCode(), n1.hashCode());
        Assert.assertEquals("Hashcode of objects must be equal.", n1.hashCode(), n2.hashCode());

        Name n3 = new Name("Test");
        Assert.assertFalse("Hashcode of different objects must be different.", n1.hashCode() == n3.hashCode());
    }

    @Test
    public void testHashCodeDatatype() {
        User u1 = new User();
        User u2 = new User();

        Assert.assertEquals("Hashcode of same object must be equal.", u1.hashCode(), u1.hashCode());
        Assert.assertEquals("Hashcode of objects must be equal.", u1.hashCode(), u2.hashCode());

        User u3 = new User();
        u3.setUsername("Test");
        Assert.assertFalse("Hashcode of different objects must be different.", u1.hashCode() == u3.hashCode());
    }

}
