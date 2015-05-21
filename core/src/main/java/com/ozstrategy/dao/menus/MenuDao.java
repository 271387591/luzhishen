package com.ozstrategy.dao.menus;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.menus.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/29/15.
 */
public interface MenuDao extends BaseDao<Menu> {
    List<Menu> listNoParentMenus();
    List<Menu> listMenusByParentId(Long parentId);
    Menu getByTableId(Long tableId);
    Menu getByKey(String key);
}
