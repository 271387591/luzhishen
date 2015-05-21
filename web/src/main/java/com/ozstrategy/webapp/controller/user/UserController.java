package com.ozstrategy.webapp.controller.user;

import com.ozstrategy.model.user.*;
import com.ozstrategy.service.user.UserManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.user.RoleCommand;
import com.ozstrategy.webapp.command.user.RoleFieldFeatureCommand;
import com.ozstrategy.webapp.command.user.RoleTableFeatureCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by lihao1 on 5/5/15.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserManager userManager;
    @RequestMapping("listRoles")
    public JsonReaderResponse listRoles(){
        List<RoleCommand> commands=new ArrayList<RoleCommand>();
        try{
            List<Role> tables= userManager.listAllRoles();
            if(tables!=null && tables.size()>0)
                for(Role table:tables){
                    RoleCommand command=new RoleCommand(table, Collections.EMPTY_LIST,Collections.EMPTY_LIST);
                    commands.add(command);
                }
            return new JsonReaderResponse(commands,true,"");
        }catch (Exception e){
            logger.error("listUsers fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }

    @RequestMapping("listTableRoles")
    public JsonReaderResponse listTableRoles(@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,@RequestParam("tableId")Long tableId){
        List<RoleCommand> commands=new ArrayList<RoleCommand>();
        try{
            List<Role> tables= userManager.listTableRoles(tableId);
            for(Role table:tables){
                Long userId=table.getId();
                List<RoleFieldFeature> features=userManager.listTableRoleFieldFeature(userId, tableId);
                List<RoleFieldFeatureCommand> featureCommands=new ArrayList<RoleFieldFeatureCommand>();
                if(features!=null && features.size()>0){
                    for(RoleFieldFeature feature:features){
                        featureCommands.add(new RoleFieldFeatureCommand(feature));
                    }
                }
                List<RoleTableFeature> tableFeatures=userManager.listRoleTableFeature(userId, tableId);
                List<RoleTableFeatureCommand> tableFeatureCommands=new ArrayList<RoleTableFeatureCommand>();
                if(tableFeatures!=null && tableFeatures.size()>0){
                    for(RoleTableFeature feature:tableFeatures){
                        tableFeatureCommands.add(new RoleTableFeatureCommand(feature));
                    }
                }


                RoleCommand command=new RoleCommand(table, featureCommands,tableFeatureCommands);
                commands.add(command);
            }
            return new JsonReaderResponse(commands,true,"");
        }catch (Exception e){
            logger.error("listUsers fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }
    @RequestMapping("listTableFeatures")
    public JsonReaderResponse listTableFeatures(@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,@RequestParam("tableId")Long tableId,@RequestParam("roleId")Long roleId){
        List<RoleFieldFeatureCommand> commands=new ArrayList<RoleFieldFeatureCommand>();
        try{
            if(roleId!=null && tableId!=null){
                List<RoleFieldFeature> features=userManager.listTableRoleFieldFeature(roleId, tableId);
                for(RoleFieldFeature feature:features){
                    commands.add(new RoleFieldFeatureCommand(feature));
                }
                return new JsonReaderResponse(commands,true,"");
            }
        }catch (Exception e){
            logger.error("listUsers fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }


    @RequestMapping("saveFeature")
    public JsonReaderSingleResponse save(@RequestBody List<RoleCommand> commands){
        try{
            List<RoleFieldFeature> features=new ArrayList<RoleFieldFeature>();
            List<RoleTableFeature> tableFeatures=new ArrayList<RoleTableFeature>();
            Long tableId=null;
            for(RoleCommand command:commands){
                Long userId=command.getId();
                List<RoleFieldFeatureCommand> fieldFeatureCommands=command.getFeatures();
                for(RoleFieldFeatureCommand fieldFeatureCommand:fieldFeatureCommands){
                    tableId=fieldFeatureCommand.getTableId();
                    RoleFieldFeature feature=new RoleFieldFeature();
                    feature.setRoleId(userId);
                    feature.setTableId(fieldFeatureCommand.getTableId());
                    feature.setCanAdd(fieldFeatureCommand.getCanAdd());
                    feature.setCanQuery(fieldFeatureCommand.getCanQuery());
                    feature.setCanUpdate(fieldFeatureCommand.getCanUpdate());
                    feature.setFieldId(fieldFeatureCommand.getFieldId());
                    feature.setIsDept(fieldFeatureCommand.getIsDept());
                    feature.setIsCreator(fieldFeatureCommand.getIsCreator());
                    features.add(feature);
                }
                List<RoleTableFeatureCommand> tableFeatureCommands=command.getTableFeatures();
                for(RoleTableFeatureCommand fieldFeatureCommand:tableFeatureCommands){
                    RoleTableFeature feature=new RoleTableFeature();
                    feature.setRoleId(userId);
                    feature.setTableId(tableId);
                    feature.setId(null);
                    feature.setQueryType(fieldFeatureCommand.getQueryType());
                    tableFeatures.add(feature);
                }

            }
            userManager.saveFeature(features,tableFeatures,tableId);
        }catch (Exception e){
            logger.error("save",e);
            return new JsonReaderSingleResponse(null,false,"保存失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }

}
