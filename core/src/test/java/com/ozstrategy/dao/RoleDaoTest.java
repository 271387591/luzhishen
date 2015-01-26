package com.ozstrategy.dao;

import com.ozstrategy.Constants;
import com.ozstrategy.dao.userrole.RoleDao;
import com.ozstrategy.model.userrole.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RoleDaoTest extends BaseDaoTestCase {
    @Autowired
    private RoleDao dao;

    @Test
    public void testGetRoleInvalid() throws Exception {
        Role role = dao.getRoleByName("badrolename");
        assertNull(role);
    }

    @Test
    public void testGetRole() throws Exception {
    }

    @Test
    public void testUpdateRole() throws Exception {
        Role role = dao.getRoleByName("ROLE_USER");
        role.setDescription("test descr");
        dao.save(role);
        flush();
        
        role = dao.getRoleByName("ROLE_USER");
        assertEquals("test descr", role.getDescription());
    }

    @Test
    public void testAddAndRemoveRole() throws Exception {
        Role role = new Role("testrole");
        role.setDescription("new role descr");
        dao.save(role);
        flush();
        
        role = dao.getRoleByName("testrole");
        assertNotNull(role.getDescription());

        dao.removeRole("testrole");
        flush();

        role = dao.getRoleByName("testrole");
        assertNull(role);
    }

    @Test
    public void testFindByNamedQuery() {
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        List<Role> roles = dao.findByNamedQuery("findRoleByName", queryParams);
        assertNotNull(roles);
        assertTrue(roles.size() > 0);
    }
}
