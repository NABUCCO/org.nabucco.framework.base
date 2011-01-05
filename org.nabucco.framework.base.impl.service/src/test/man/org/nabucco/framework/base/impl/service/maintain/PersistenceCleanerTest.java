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
package org.nabucco.framework.base.impl.service.maintain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * PersistenceCleanerTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PersistenceCleanerTest {

    @Before
    public void setUp() throws Exception {
        // Put your set-up code here!
    }

    @After
    public void tearDown() throws Exception {
        // Put your tear-down code here!
    }

    @Test
    public void testPersistenceCleaner() throws Exception {

        PersistenceCleaner cleaner = new PersistenceCleaner();

        Parent parent = new Parent();
        parent.setDatatypeState(DatatypeState.INITIALIZED);

        NabuccoList<Child> children = parent.getChildren();
        
        Assert.assertEquals(NabuccoCollectionState.EAGER, children.getState());
        Assert.assertSame(LinkedList.class, children.getDelegate().getClass());

        parent.accept(cleaner);

        Assert.assertEquals(NabuccoCollectionState.EAGER, children.getState());
        Assert.assertSame(ArrayList.class, children.getDelegate().getClass());
        Assert.assertSame(1, children.getDelegate().size());
        
        // Simulate persistence operation!
        children.setState(NabuccoCollectionState.LAZY);

        cleaner.reset();
        parent.accept(cleaner);

        Assert.assertEquals(NabuccoCollectionState.LAZY, children.getState());
        Assert.assertSame(ArrayList.class, children.getDelegate().getClass());
        Assert.assertSame(0, children.getDelegate().size());
    }

    class Parent extends User {

        private static final long serialVersionUID = 1L;

        private NabuccoList<Child> children;

        public Parent() {
            children = new NabuccoList<PersistenceCleanerTest.Child>();
            children.setDelegate(new LinkedList<PersistenceCleanerTest.Child>());
            children.add(new Child());
        }

        @Override
        public List<NabuccoProperty<?>> getProperties() {
            List<NabuccoProperty<?>> properties = new ArrayList<NabuccoProperty<?>>();

            properties.add(new ListProperty<Child>("child", Child.class, "", children));
            return properties;
        }

        /**
         * Getter for the children.
         * 
         * @return Returns the children.
         */
        public NabuccoList<Child> getChildren() {
            return this.children;
        }
    }

    class Child extends User {

        private static final long serialVersionUID = 1L;

        @Override
        public List<NabuccoProperty<?>> getProperties() {
            return Collections.<NabuccoProperty<?>> emptyList();
        }

    }

}
