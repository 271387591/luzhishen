package com.ozstrategy.webapp.controller.tables;

import com.ozstrategy.bo.DataSourceFiledBo;
import com.ozstrategy.model.menus.Menu;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.menus.MenuCommand;
import com.ozstrategy.webapp.command.tables.DataSourceFieldCommand;
import com.ozstrategy.model.tables.FieldDataSource;
import com.ozstrategy.service.tables.FieldDataSourceManager;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController extends BaseController {
    @Autowired
    private FieldDataSourceManager fieldDataSourceManager;
    @RequestMapping("listDataSources")
    public JsonReaderResponse listDataSources(@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,HttpServletRequest request){
        List<FieldDataSource> dataSources= null;
        try {
            dataSources = fieldDataSourceManager.listAll();
        } catch (Exception e) {
            logger.error("listDataSources fail",e);
        }
        return new JsonReaderResponse(dataSources,true,"");
    }
    @RequestMapping("listDataSourceFields")
    public JsonReaderResponse listDataSourceFields(@RequestParam("id")Long id){
        List<DataSourceFieldCommand> commands= new ArrayList<DataSourceFieldCommand>();
        try {
            FieldDataSource source=fieldDataSourceManager.getById(id);
            if(source==null){
                return new JsonReaderResponse(null,false,"未配置数据源");
            }
            List<DataSourceFiledBo> filedBos=fieldDataSourceManager.getDataSourceFields(source);
            if(filedBos!=null && filedBos.size()>0){
                for(DataSourceFiledBo bo:filedBos){
                    commands.add(new DataSourceFieldCommand(bo));
                }
            }
        } catch (Exception e) {
            logger.error("listDataSourceFields fail",e);
        }
        return new JsonReaderResponse(commands,true,"");
    }
    @RequestMapping("listDataSourceDatas")
    public JsonReaderResponse listDataSourceDatas(@RequestParam("id")Long id,@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,HttpServletRequest request){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        try {
            FieldDataSource source=fieldDataSourceManager.getById(id);
            if(source!=null){
                Map<String,Object> map=requestMap(request);
                list=fieldDataSourceManager.listDataSourceDatas(source,map,start,limit);
                Integer count=fieldDataSourceManager.listDataSourceDatasCount(source,map);
                return new JsonReaderResponse(list,true,count,"");
            }
        } catch (Exception e) {
            logger.error("listDataSourceFields fail",e);
        }
        return new JsonReaderResponse(null,false,"");
    }
    @RequestMapping("listDataSourceTreeDatas")
    public List<Map<String,Object>> listDataSourceTreeDatas(@RequestParam("id")Long id,@RequestParam("parent")String parent,@RequestParam("treeId")String treeId,@RequestParam("parentDisplay")String parentDisplay){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        try {
            FieldDataSource source=fieldDataSourceManager.getById(id);
            if(source!=null){
                list=fieldDataSourceManager.listDataSourceTreeDatas(source, parent, null);
                if(list!=null && list.size()>0){
                    for(Map<String,Object> map:list){
                        map.put("text",map.get(parentDisplay));
                        map.put("leaf",true);
                        map.put("checked",false);
                        createTree(source,parent,treeId,parentDisplay,map);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("listDataSourceTreeDatas fail",e);
        }
        return list;
    }
    private Map<String,Object> createTree(FieldDataSource source,String parent,String treeId,String parentDisplay,  Map<String,Object> map){
        List<Map<String,Object>> children=fieldDataSourceManager.listDataSourceTreeDatas(source,parent,map.get(treeId));
        List<Map<String,Object>> newChildren=new ArrayList<Map<String, Object>>();
        if(children!=null && children.size()>0){
            map.put("leaf",false);
            for(Map<String,Object> child:children){
                child.put("checked",false);
                child.put("text",child.get(parentDisplay));
                List<Map<String,Object>> hasChild=fieldDataSourceManager.listDataSourceTreeDatas(source,parent,child.get(treeId));
                if(hasChild!=null && hasChild.size()<1){
                    child.put("leaf",true);
                }
                newChildren.add(child);
                map.put("children",newChildren);
                createTree(source,parent,treeId,parentDisplay,child);
            }


        }
        return map;
    }



}
