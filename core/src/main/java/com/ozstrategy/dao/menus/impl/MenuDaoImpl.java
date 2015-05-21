package com.ozstrategy.dao.menus.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.menus.MenuDao;
import com.ozstrategy.model.menus.Menu;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/29/15.
 */
@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {
    public MenuDaoImpl() {
        super(Menu.class);
    }

    public List<Menu> listNoParentMenus() {
        String sql="select * from t_menu t where t.parentId is null";
//        Map<String,Object> map=new HashMap<String, Object>();
//        map.put("Q_parentId_ISNULL",null);
//        return listAll(map);

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(Menu.class));
    }

    public List<Menu> listMenusByParentId(Long parentId) {
        String sql="select * from t_menu t where t.parentId=?";
        return jdbcTemplate.query(sql, new Object[]{parentId}, new BeanPropertyRowMapper<Menu>(Menu.class));
    }

    public Menu getByTableId(Long tableId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_tableId_EQ",tableId);
        List<Menu> list=listAll(map);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Menu getByKey(String key) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_menuKey_EQ",key);
        List<Menu> list=listAll(map);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
