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
package org.nabucco.framework.base.facade.datatype.visitor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * DatatypeVisitorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeVisitorTest {

    @Before
    public void setUp() throws Exception {
        // Put your set-up code here!
    }

    @After
    public void tearDown() throws Exception {
        // Put your tear-down code here!
    }

    @Test
    public void testVisitor() throws Exception {

        TestVisitor visitor = new TestVisitor();

        User user = new User();
        user.setUsername("Test");

        user.accept(visitor);

        Assert.assertEquals(1, visitor.basetypeCount);
        Assert.assertEquals(1, visitor.datatypeCount);
        Assert.assertEquals(2, visitor.visitableCount);
        
        visitor = new TestVisitor();

        user.setId(1l);
        user.setVersion(0l);
        user.setUsername("Test");
        user.setOwner("NBC");
        user.setDescription("Test");

        user.accept(visitor);

        Assert.assertEquals(5, visitor.basetypeCount);
        Assert.assertEquals(1, visitor.datatypeCount);
        Assert.assertEquals(6, visitor.visitableCount);
    }

    class TestVisitor extends DatatypeVisitor {

        private int basetypeCount = 0;

        private int datatypeCount = 0;

        private int visitableCount = 0;

        @Override
        public void visit(Basetype basetype) throws VisitorException {
            basetypeCount++;
            super.visit(basetype);
        }

        @Override
        public void visit(Datatype datatype) throws VisitorException {
            datatypeCount++;
            super.visit(datatype);
        }

        @Override
        public void visit(Visitable visitable) throws VisitorException {
            visitableCount++;
            super.visit(visitable);
        }

    }

}
