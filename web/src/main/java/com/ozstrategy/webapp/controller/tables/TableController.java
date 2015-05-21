package com.ozstrategy.webapp.controller.tables;

import com.ozstrategy.model.standingbook.StandingBook;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.standingbook.StandingBookManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.tables.TableCommand;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.service.tables.TableManager;
import com.ozstrategy.webapp.command.tables.TableFieldCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
@RestController
@RequestMapping("/table")
public class TableController extends BaseController {
    @Autowired
    private TableManager tableManager;
    @Autowired
    private StandingBookManager standingBookManager;

    @RequestMapping("listTables")
    public JsonReaderResponse listTables(@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,HttpServletRequest request){
        Map<String,Object> map=requestMap(request);
        List<TableCommand> commands=new ArrayList<TableCommand>();
        try{
            List<MTable> tables= tableManager.listTables(map,start,limit);
            for(MTable table:tables){
                TableCommand command=new TableCommand(table);
                List<TableField> fields=tableManager.getFieldsByTableId(table.getId());
                command.populateFields(fields);
                commands.add(command);
            }
            Integer count= tableManager.listTablesCount(map);
            return new JsonReaderResponse(commands,true,count,"");
        }catch (Exception e){
            logger.error("listtabels fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }
    @RequestMapping("listAllTables")
    public JsonReaderResponse listAllTables(HttpServletRequest request){
        List<TableCommand> commands=new ArrayList<TableCommand>();
        try{
            List<MTable> tables= tableManager.listAll();
            for(MTable table:tables){
                TableCommand command=new TableCommand(table);
                commands.add(command);
            }
            return new JsonReaderResponse(commands,true,"");
        }catch (Exception e){
            logger.error("listtabels fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }
    @RequestMapping("listNoMenuTables")
    public JsonReaderResponse listNoMenuTables(HttpServletRequest request){
        List<TableCommand> commands=new ArrayList<TableCommand>();
        try{
            List<MTable> tables= tableManager.listNoMenu();
            for(MTable table:tables){
                TableCommand command=new TableCommand(table);
                commands.add(command);
            }
            return new JsonReaderResponse(commands,true,"");
        }catch (Exception e){
            logger.error("listtabels fail",e);
        }

        return new JsonReaderResponse(commands,false,"");
    }


    @RequestMapping("save")
    public JsonReaderSingleResponse save(@RequestBody TableCommand table){
        Long id=table.getId();
        try{
            MTable mTable=null;
            if(id==null){
                String name=table.getName();
                mTable=tableManager.getByName(name);
                if(mTable!=null){
                    return new JsonReaderSingleResponse(null,false,"数据表名称已存在，不能重复创建");
                }
                mTable=new MTable();
                mTable.setCreateDate(new Date());
            }else{
                mTable=tableManager.getById(id);
            }
            mTable.setName(table.getName());
            mTable.setDisplayName(table.getDisplayName());
            List<TableFieldCommand> fields=table.getFields();
            List<TableField> tableFields=new ArrayList<TableField>();
            for(TableFieldCommand field:fields){
                TableField tableField=new TableField();
                tableField.setAvailability(field.getAvailability());
                tableField.setCid(field.getCid());
                tableField.setName(field.getName());
                tableField.setDataSourceId(field.getDataSourceId());
                tableField.setDifferential(field.getDifferential());
                tableField.setInputDisplayField(field.getInputDisplayField());
                tableField.setInputType(field.getInputType());
                tableField.setInputValueFiled(field.getInputValueFiled());
                tableField.setIsNow(field.getIsNow());
                tableField.setIsnull(field.getIsnull());
                tableField.setIsPrimaryKey(field.getIsPrimaryKey());
                tableField.setIsQuery(field.getIsQuery());
                tableField.setLength(field.getLength());
                tableField.setType(field.getType());
                tableField.setGridHeader(field.getGridHeader());
                tableField.setIndexDex(field.getIndexDex());
                tableField.setTreeParent(field.getTreeParent());
                tableField.setTreeId(field.getTreeId());
                tableField.setIsDept(field.getIsDept());
                tableFields.add(tableField);
            }
            tableManager.saveTable(mTable,tableFields);
        }catch (Exception e){
            logger.error("save",e);
            return new JsonReaderSingleResponse(null,false,"保存失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }
    @RequestMapping("delete")
    public JsonReaderSingleResponse delete(@RequestParam("id") Long id){
        try{
            MTable mTable=tableManager.getById(id);
            if(mTable!=null){
                Boolean hasMenu=mTable.getHasMenu();
                if(hasMenu){
                    StandingBook standingBook=standingBookManager.getByTableId(id);
                    if(standingBook!=null){
                        return new JsonReaderSingleResponse(null,false,"该数据表被台账编号为["+standingBook.getSerialNum()+"]引用，请先删除该台账。");
                    }
                }else{
                    tableManager.deleteTable(mTable);
                }

            }
        }catch (Exception e){
            logger.error("delete",e);
            return new JsonReaderSingleResponse(null,false,"删除失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }

}
