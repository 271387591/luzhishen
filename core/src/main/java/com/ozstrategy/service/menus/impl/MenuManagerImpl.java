package com.ozstrategy.service.menus.impl;

import com.ozstrategy.dao.menus.MenuDao;
import com.ozstrategy.model.menus.Menu;
import com.ozstrategy.service.menus.MenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/29/15.
 */
@Service("menuManager")
public class MenuManagerImpl implements MenuManager {
    @Autowired
    private MenuDao menuDao;
    public List<Menu> listNoParentMenus() {
        return menuDao.listNoParentMenus();
    }

    public List<Menu> listMenusByParentId(Long parentId) {
        return menuDao.listMenusByParentId(parentId);
    }
}
