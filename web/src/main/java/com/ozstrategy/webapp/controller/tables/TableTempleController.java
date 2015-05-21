package com.ozstrategy.webapp.controller.tables;

import com.ozstrategy.annotations.Table;
import com.ozstrategy.model.standingbook.StandingBook;
import com.ozstrategy.model.tables.FieldDataSource;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.tables.FieldDataSourceManager;
import com.ozstrategy.service.tables.TableManager;
import com.ozstrategy.service.tables.TableTempleManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.tables.TableCommand;
import com.ozstrategy.webapp.command.tables.TableFieldCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/2/15.
 */
@RestController
@RequestMapping("/temple")
public class TableTempleController extends BaseController {
    @Autowired
    private TableManager tableManager;
    @Autowired
    private TableTempleManager tableTempleManager;
    @Autowired
    private FieldDataSourceManager fieldDataSourceManager;


    @RequestMapping("list")
    public JsonReaderResponse list(
            @RequestParam("start")Integer start,
            @RequestParam("limit")Integer limit,
            @RequestParam("tableId")Long tableId,HttpServletRequest request){
        Map<String,Object> map=requestMap(request);
        try{
            if(tableId!=null){
                MTable table=tableManager.getById(tableId);
                List<Map<String,Object>> list=tableTempleManager.listPage(table,map,start,limit);
                List<TableField> fields=tableManager.getFieldsByTableId(tableId);
                for(Map<String,Object> objectMap:list){
                    for(TableField field:fields){
                        String cid=field.getCid();
                        String inputDisplayField=field.getInputDisplayField();
                        String inputValueFiled=field.getInputValueFiled();
                        if(field.getDataSourceId()!=null && !StringUtils.equals(inputValueFiled,inputDisplayField)){
                            FieldDataSource source=fieldDataSourceManager.getById(field.getDataSourceId());
                            Object value=objectMap.get(cid);
                            Map<String,Object> display=fieldDataSourceManager.getDataByFieldValue(source,inputValueFiled,value);
                            if(display!=null){
                                objectMap.put(cid+"_display",display.get(inputDisplayField));
                            }
                        }
                    }
                }
                Integer count= tableTempleManager.listPageCount(table,map);
                return new JsonReaderResponse(list,true,count,"");
            }
        }catch (Exception e){
            logger.error("list fail",e);
        }
        return new JsonReaderResponse(null,false,"");
    }
    @RequestMapping("save")
    public JsonReaderSingleResponse save(HttpServletRequest request){
        Map<String,Object> map=requestNoPreMap(request);
        Long tableId= NumberUtils.toLong(ObjectUtils.toString(map.get("tableId")));
        Long id= NumberUtils.toLong(ObjectUtils.toString(map.get("id")));
        String uniqueName= ObjectUtils.toString(map.get("uniqueNames"));
        map.remove("uniqueNames");
        Map<String,Object> temp=null;
        try{
            if(id==null || id==0L){
                MTable table=tableManager.getById(tableId);
                List<TableField> fields=tableManager.getFieldsByTableId(tableId);
                if(StringUtils.isNotEmpty(uniqueName)){
                    String[] uniqueNames=uniqueName.split(",");
                    for(String name:uniqueNames){
                        temp=tableTempleManager.getByUniqueField(table,name,map.get(name));
                        if(temp!=null && temp.size()>0){
                            return new JsonReaderSingleResponse(null,false,"["+map.get(name).toString()+"]已存在，不能重复创建");
                        }
                    }

                }

//                fields.add(createFieldForCreator(map));

                tableTempleManager.save(table,fields,map);
            }else {
                MTable table=tableManager.getById(tableId);
                List<TableField> fields=tableManager.getFieldsByTableId(tableId);
                if(StringUtils.isNotEmpty(uniqueName)){
                    String[] uniqueNames=uniqueName.split(",");
                    for(String name:uniqueNames){
                        Map<String,Object> newtemp=tableTempleManager.getByUniqueField(table,name,map.get(name));
                        if(newtemp!=null && newtemp.size()>0){
                            Long tempId=NumberUtils.toLong(ObjectUtils.toString(newtemp.get("id")));
                            if(id!=tempId){
                                return new JsonReaderSingleResponse(null,false,"["+map.get(name).toString()+"]已存在，请修改为其他数据");
                            }
                        }
                    }
                }
                tableTempleManager.update(table,fields,map,id);
            }

        }catch (Exception e){
            logger.error("save",e);
            return new JsonReaderSingleResponse(null,false,"保存失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }
    private TableField createFieldForCreator(Map<String,Object> map){
        TableField field=new TableField();
        field.setCid("creatorId");
        map.put("creatorId",1);
        return field;
    }
    @RequestMapping("delete")
    public JsonReaderSingleResponse delete(@RequestParam("id") Long id,@RequestParam("tableId") Long tableId){
        try{
            MTable table=tableManager.getById(tableId);
            tableTempleManager.delete(table,id);
        }catch (Exception e){
            logger.error("delete",e);
            return new JsonReaderSingleResponse(null,false,"删除失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }
}
