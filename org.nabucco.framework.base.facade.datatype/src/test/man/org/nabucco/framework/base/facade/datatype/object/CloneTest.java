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
 * CloneTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class CloneTest {

    @Test
    public void testCloneBasetype() {
        Name n1 = new Name();
        Name n2 = n1.cloneObject();

        Assert.assertEquals("Object must be equal with itself.", n1, n2);
        Assert.assertEquals("Cloned objects must be equal.", n1, n2);
    }

    @Test
    public void testCloneDatatype() {
        User u1 = new User();
        User u2 = u1.cloneObject();

        Assert.assertEquals("Object must be equal with itself.", u1, u2);
        Assert.assertEquals("Cloned objects must be equal.", u1, u2);
    }

}
