package com.ozstrategy.service.standingbook.impl;

import com.ozstrategy.dao.menus.MenuDao;
import com.ozstrategy.dao.standingbook.StandingBookDao;
import com.ozstrategy.dao.tables.TableDao;
import com.ozstrategy.dao.user.RoleFieldFeatureDao;
import com.ozstrategy.dao.user.RoleTableFeatureDao;
import com.ozstrategy.model.menus.Menu;
import com.ozstrategy.model.standingbook.StandingBook;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.service.standingbook.StandingBookManager;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
@Service("standingBookManager")
public class StandingBookManagerImpl implements StandingBookManager {
    @Autowired
    private StandingBookDao standingBookDao;
    @Autowired
    private TableDao tableDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleFieldFeatureDao roleFieldFeatureDao;
    @Autowired
    private RoleTableFeatureDao roleTableFeatureDao;




    public List<Map<String,Object>> list(Map<String, Object> params, Integer start, Integer limit) throws Exception{
        return standingBookDao.listBooks(params,start,limit);
    }

    public Integer listCount(Map<String, Object> params) throws Exception{
        return standingBookDao.listBooksCount(params);
    }

    public StandingBook getBySerialNum(String serialNum) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_serialNum_EQ",serialNum);
        List<StandingBook> fieldList=standingBookDao.listPage(map, 0, 1);
        if(fieldList!=null && fieldList.size()>0){
            return fieldList.get(0);
        }
        return null;
    }

    public void save(StandingBook book) {
        if(book.getId()==null){
            standingBookDao.save(book);
        }else{
            standingBookDao.update(book);
            if(!BooleanUtils.toBoolean(book.getHasMenu())){
                MTable table=tableDao.get(book.getTableId());
                table.setHasMenu(false);
                tableDao.update(table);
            }
            Menu menu=menuDao.getByTableId(book.getTableId());
            if(menu!=null){
                menuDao.delete(menu);
            }
        }
        if(BooleanUtils.toBoolean(book.getHasMenu())){
            MTable table=tableDao.get(book.getTableId());
            table.setHasMenu(true);
            tableDao.update(table);
            Menu menu=new Menu();
            menu.setMenuKey(MENU_KEY_PRE + book.getId());
            menu.setName(book.getName() + "(" + book.getSerialNum() + ")");
            menu.setWidget(MENU_Temp_Widget);
            menu.setTableId(table.getId());
            Menu parentMenu=menuDao.getByKey(MENU_Parent_KEY);
            menu.setParentId(parentMenu.getId());
            menuDao.save(menu);
        }

    }

    public StandingBook getById(Long id) {
        return standingBookDao.get(id);
    }

    public void delete(StandingBook book) {
        Menu menu=menuDao.getByTableId(book.getTableId());
        if(menu!=null){
            menuDao.delete(menu);
        }
        MTable table=tableDao.get(book.getTableId());
        table.setHasMenu(false);
        tableDao.update(table);
        standingBookDao.delete(book);
        roleFieldFeatureDao.deleteByTableId(table.getId());
        roleTableFeatureDao.deleteByTableId(table.getId());
    }

    public StandingBook getByTableId(Long tableId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_tableId_EQ",tableId);
        List<StandingBook> fieldList=standingBookDao.listPage(map, 0, 1);
        if(fieldList!=null && fieldList.size()>0){
            return fieldList.get(0);
        }
        return null;
    }
}
