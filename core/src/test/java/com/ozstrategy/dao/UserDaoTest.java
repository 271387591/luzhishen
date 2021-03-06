package com.ozstrategy.dao;

import com.ozstrategy.Constants;
import com.ozstrategy.dao.userrole.RoleDao;
import com.ozstrategy.dao.userrole.UserDao;
import com.ozstrategy.model.userrole.Address;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate4.HibernateSystemException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserDaoTest extends BaseDaoTestCase {
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao rdao;

    @Test(expected = ObjectRetrievalFailureException.class)
    public void testGetUserInvalid() throws Exception {
        // should throw DataAccessException
        dao.get(1000L);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = dao.get(1L);

        assertNotNull(user);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testGetUserPassword() throws Exception {
        User user = dao.get(1L);
        String password = dao.getUserPassword(user.getUsername());
        assertNotNull(password);
        log.debug("password: " + password);
    }

    @Test( expected = HibernateSystemException.class )
    public void testUpdateUser() throws Exception {
        User user = dao.get(1L);

        Address address = user.getAddress();
        address.setAddress("new address");

        dao.saveUser(user);
        flush();

        user = dao.get(1L);
        assertEquals(address, user.getAddress());
        assertEquals("new address", user.getAddress().getAddress());

        // verify that violation occurs when adding new user with same username
        user.setId(null);

        // should throw DataIntegrityViolationException
        dao.saveUser(user);
    }

    @Test
    public void testAddUserRole() throws Exception {
        User user = dao.get(1L);
        assertEquals(1, user.getRoles().size());

        Role role = rdao.getRoleByName(Constants.ADMIN_ROLE);
        user.addRole(role);
        dao.saveUser(user);
        flush();

        user = dao.get(1L);
        assertEquals(2, user.getRoles().size());

        //add the same role twice - should result in no additional role
        user.addRole(role);
        dao.saveUser(user);
        flush();

        user = dao.get(1L);
        assertEquals("more than 2 roles", 2, user.getRoles().size());

        user.getRoles().remove(role);
        dao.saveUser(user);
        flush();

        user = dao.get(1L);
        assertEquals(1, user.getRoles().size());
    }

    @Test              (expected = DataAccessException.class)
    public void testAddAndRemoveUser() throws Exception {
        User user = new User("testuser");
        user.setPassword("testpass");
        user.setFirstName("Test");
        user.setLastName("Last");
        Address address = new Address();
        address.setCity("Denver");
        address.setProvince("CO");
        address.setCountry("USA");
        address.setPostalCode("80210");
        user.setAddress(address);
        user.setEmail("testuser@appfuse.org");
        user.setWebsite("http://raibledesigns.com");


        user = dao.saveUser(user);
        flush();

        assertNotNull(user.getId());
        user = dao.get(user.getId());
        assertEquals("testpass", user.getPassword());

        dao.remove(user.getId());
        flush();

        // should throw DataAccessException
        dao.get(user.getId());
    }

    @Test
    public void testUserExists() throws Exception {
        boolean b = dao.exists(1L);
        assertTrue(b);
    }

    @Test
    public void testUserNotExists() throws Exception {
        boolean b = dao.exists(-1L);
        assertFalse(b);
    }
}
