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
package org.nabucco.framework.base.facade.datatype.collection;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * NabuccoListTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoListTest {

    private NabuccoListImpl<User> userList;

    @Before
    public void setUp() throws Exception {
        this.userList = new NabuccoListImpl<User>();
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#add(org.nabucco.framework.base.facade.datatype.NType)}
     * .
     */
    @Test(expected = LazyInitializationException.class)
    public void testAdd() {

        try {
            this.userList.add(new User());
            Assert.assertEquals(1, this.userList.size());

            List<User> assignedList = this.userList.getAssignedList();
            Assert.assertEquals(1, assignedList.size());
        } catch (LazyInitializationException e) {
            fail("No exception expected.");
        }

        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.add(new User());

    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#addAll(java.util.Collection)}
     * .
     */
    @Test(expected = LazyInitializationException.class)
    public void testAddAll() {
        List<User> other = new ArrayList<User>();
        other.add(new User());

        try {
            this.userList.addAll(other);
        } catch (LazyInitializationException e) {
            fail("No exception expected.");
        }

        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.addAll(other);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#clear()}.
     */
    @Test
    public void testClear() {
        this.userList.add(new User());

        Assert.assertEquals(1, this.userList.size());

        List<User> assignedList = this.userList.getAssignedList();
        Assert.assertEquals(1, assignedList.size());

        this.userList.clear();

        Assert.assertEquals(0, this.userList.size());
        Assert.assertEquals(0, assignedList.size());

        List<User> unassignedList = this.userList.getUnassignedList();
        Assert.assertEquals(0, unassignedList.size());

        this.userList.addWithoutAssignment(new User());

        this.userList.remove(0);
        unassignedList = this.userList.getUnassignedList();
        Assert.assertEquals(1, unassignedList.size());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        Assert.assertTrue(this.userList.isEmpty());

        this.userList.add(new User());
        Assert.assertFalse(this.userList.isEmpty());

        this.userList.remove(new User());
        Assert.assertTrue(this.userList.isEmpty());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#remove(java.lang.Object)}
     * .
     */
    @Test(expected = LazyInitializationException.class)
    public void testRemove() {

        try {
            this.userList.addWithoutAssignment(new User());
            Assert.assertEquals(1, this.userList.size());

            this.userList.remove(new User());
            Assert.assertEquals(0, this.userList.size());

        } catch (LazyInitializationException e) {
            fail("No exception expected.");
        }

        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.remove(new User());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#removeAll(java.util.Collection)}
     * .
     */
    @Test(expected = LazyInitializationException.class)
    public void testRemoveAll() {
        List<User> other = new ArrayList<User>();
        other.add(new User());

        try {
            this.userList.addAll(other);
        } catch (LazyInitializationException e) {
            fail("No exception expected.");
        }

        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.addAll(other);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#retainAll(java.util.Collection)}
     * .
     */
    @Test(expected = LazyInitializationException.class)
    public void testRetainAll() {

        try {
            this.userList.addWithoutAssignment(new User());
            Assert.assertEquals(1, this.userList.size());

            List<User> other = new ArrayList<User>();
            other.add(new User());

            this.userList.retainAll(other);
            Assert.assertEquals(1, this.userList.size());

            Assert.assertTrue(this.userList.getUnassignedList().isEmpty());

            this.userList.retainAll(new ArrayList<User>());
            Assert.assertEquals(0, this.userList.size());

            Assert.assertFalse(this.userList.getUnassignedList().isEmpty());

        } catch (LazyInitializationException e) {
            fail("No exception expected.");
        }

        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.remove(new User());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#set(int, org.nabucco.framework.base.facade.datatype.NType)}
     * .
     */
    @Test
    public void testSet() {
        User user = new User();
        User dummy = new User();
        dummy.setUsername("Dummy");

        this.userList.addWithoutAssignment(dummy);
        Assert.assertEquals(1, this.userList.size());

        this.userList.set(0, user);

        Assert.assertSame(this.userList.get(0), user);
        Assert.assertNotSame(this.userList.get(0), dummy);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#getState()}.
     */
    @Test
    public void testGetState() {
        Assert.assertEquals(NabuccoCollectionState.INITIALIZED, this.userList.getState());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#setState(org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState)}
     * .
     */
    @Test
    public void testSetState() {
        Assert.assertEquals(NabuccoCollectionState.INITIALIZED, this.userList.getState());
        this.userList = new NabuccoListImpl<User>(NabuccoCollectionState.LAZY);
        Assert.assertEquals(NabuccoCollectionState.LAZY, this.userList.getState());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#clearAll()}.
     */
    @Test
    public void testClearAll() {

        User u1 = new User();
        u1.setUsername("User 1");
        User u2 = new User();
        u2.setUsername("User 2");
        this.userList.addWithoutAssignment(u1);
        this.userList.remove(u1);
        this.userList.add(u2);

        Assert.assertEquals(1, this.userList.size());
        Assert.assertEquals(1, this.userList.getAssignedList().size());
        Assert.assertEquals(1, this.userList.getUnassignedList().size());

        this.userList.clearAll();

        Assert.assertEquals(0, this.userList.size());
        Assert.assertEquals(0, this.userList.getAssignedList().size());
        Assert.assertEquals(0, this.userList.getUnassignedList().size());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#addWithoutAssignment(org.nabucco.framework.base.facade.datatype.NType)}
     * .
     */
    @Test
    public void testAddWithoutAssignment() {
        this.userList.addWithoutAssignment(new User());

        Assert.assertEquals(1, this.userList.size());
        Assert.assertEquals(0, this.userList.getAssignedList().size());
        Assert.assertEquals(0, this.userList.getUnassignedList().size());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#addAllWithoutAssignment(java.util.Collection)}
     * .
     */
    @Test
    public void testAddAllWithoutAssignment() {
        this.userList.addAllWithoutAssignment(Arrays.asList(new User()));

        Assert.assertEquals(1, this.userList.size());
        Assert.assertEquals(0, this.userList.getAssignedList().size());
        Assert.assertEquals(0, this.userList.getUnassignedList().size());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#replace(org.nabucco.framework.base.facade.datatype.NType, org.nabucco.framework.base.facade.datatype.NType)}
     * .
     */
    @Test
    public void testReplace() {
        User user = new User();
        user.setUsername("U1");

        this.userList.add(user);
        Assert.assertTrue(this.userList.getAssignedList().contains(user));
        Assert.assertFalse(this.userList.getAssignedList().contains(new User()));

        this.userList.replace(user, new User());
        Assert.assertFalse(this.userList.getAssignedList().contains(user));
        Assert.assertTrue(this.userList.getAssignedList().contains(new User()));

        Assert.assertTrue(this.userList.contains(new User()));
        Assert.assertFalse(this.userList.contains(user));
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#getAssignedList()}
     * .
     */
    @Test
    public void testGetAssignedList() {
        Assert.assertNotNull(this.userList.getAssignedList());
        Assert.assertSame(this.userList.getAssignedList().getClass(), ArrayList.class);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#getUnassignedList()}
     * .
     */
    @Test
    public void testGetUnassignedList() {
        Assert.assertNotNull(this.userList.getUnassignedList());
        Assert.assertSame(this.userList.getUnassignedList().getClass(), ArrayList.class);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#reload()}.
     */
    @Test
    public void testReload() {
        this.userList.setState(NabuccoCollectionState.LAZY);
        Assert.assertEquals(NabuccoCollectionState.LAZY, this.userList.getState());

        this.userList.reload();
        Assert.assertEquals(NabuccoCollectionState.EAGER, this.userList.getState());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#detach()} .
     */
    @Test
    public void testRemoveLazyCollection() {
        this.userList.add(new User());
        this.userList.setState(NabuccoCollectionState.LAZY);
        this.userList.detach();

        Assert.assertEquals(NabuccoCollectionState.LAZY, this.userList.getState());
        Assert.assertEquals(0, this.userList.getDelegate().size());
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#cloneCollection()}
     * .
     */
    @Test
    public void testCloneCollection() {
        NabuccoList<User> clone = this.userList.cloneCollection();
        Assert.assertEquals(this.userList, clone);
        Assert.assertNotSame(this.userList, clone);
    }

    /**
     * Test method for
     * {@link org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl#filter(NabuccoCollectionFilter)}
     * .
     */
    @Test
    public void testFilter() {

        User user1 = new User();
        user1.setUsername("U1");
        this.userList.add(user1);

        User user2 = new User();
        user2.setUsername("U2");
        this.userList.add(user2);

        NabuccoList<User> filteredList = this.userList.filter(new NabuccoCollectionFilter<User>() {

            @Override
            public boolean filter(User element) {
                return element.getUsername().getValue().equals("U1");
            }

        });

        Assert.assertNotSame(this.userList, filteredList);
        Assert.assertEquals(2, this.userList.size());
        Assert.assertEquals(1, filteredList.size());
    }
}
