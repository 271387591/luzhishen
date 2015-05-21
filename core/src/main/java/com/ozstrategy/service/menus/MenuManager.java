package com.ozstrategy.service.menus;

import com.ozstrategy.model.menus.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/29/15.
 */
public interface MenuManager {
    List<Menu> listNoParentMenus();
    List<Menu> listMenusByParentId(Long parentId);
}
