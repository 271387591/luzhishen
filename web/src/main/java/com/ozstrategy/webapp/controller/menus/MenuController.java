package com.ozstrategy.webapp.controller.menus;

import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.tables.TableManager;
import com.ozstrategy.webapp.command.menus.MenuCommand;
import com.ozstrategy.model.menus.Menu;
import com.ozstrategy.service.menus.MenuManager;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 4/29/15.
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuManager menuManager;
    @Autowired
    private TableManager tableManager;
    @RequestMapping("listMenus")
    public List<MenuCommand> listMenus(HttpServletRequest request){
        List<Menu> list=menuManager.listNoParentMenus();
        List<MenuCommand> commandList=new ArrayList<MenuCommand>();
        if(list!=null && list.size()>0){
            for(Menu menu:list){
                if(menu!=null){
                    MenuCommand command=new MenuCommand(menu);
                    command=createMenu(command);
                    commandList.add(command);
                }
            }
        }
        return  commandList;
    }
    private MenuCommand createMenu(MenuCommand command){
        List<Menu> child=menuManager.listMenusByParentId(command.getId());
        if(child!=null && child.size()>0){
            for(Menu menu:child){
                MenuCommand childCommand=new MenuCommand(menu);
                if(menu.getTableId()!=null){
                    List<TableField> fields=tableManager.getFieldsByTableId(menu.getTableId());
                    childCommand.populateFields(fields);
                }
                childCommand.setLeaf(true);
                command.getChildren().add(childCommand);
                command.setLeaf(false);
                createMenu(childCommand);
            }

        }
        return command;
    }
}
