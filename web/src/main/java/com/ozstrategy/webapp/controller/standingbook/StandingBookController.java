package com.ozstrategy.webapp.controller.standingbook;

import com.ozstrategy.model.standingbook.StandingBook;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.standingbook.StandingBookManager;
import com.ozstrategy.service.tables.TableManager;
import com.ozstrategy.util.EntityUtils;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.standingbook.StandingBookCommand;
import com.ozstrategy.webapp.command.tables.TableCommand;
import com.ozstrategy.webapp.command.tables.TableFieldCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.BooleanUtils;
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
 * Created by lihao1 on 5/1/15.
 */
@RestController
@RequestMapping("/standingbook")
public class StandingBookController extends BaseController{
    @Autowired
    private StandingBookManager standingBookManager;
    @Autowired
    private TableManager tableManager;

    @RequestMapping("listStandingBooks")
    public JsonReaderResponse listStandingBooks(@RequestParam("start")Integer start,@RequestParam("limit")Integer limit,HttpServletRequest request){
        Map<String,Object> map=requestMap(request);
        List<StandingBookCommand> commands=new ArrayList<StandingBookCommand>();
        try{
            List<Map<String,Object>> tables= standingBookManager.list(map, start, limit);
            for(Map<String,Object> table:tables){
                StandingBookCommand command=new StandingBookCommand();
                command = new EntityUtils<StandingBookCommand>(command).populate(table);
                List<TableField> fields=tableManager.getFieldsByTableId(command.getTableId());
                command.populateFields(fields);
                commands.add(command);
            }
            Integer count= standingBookManager.listCount(map);
            return new JsonReaderResponse(commands,true,count,"");
        }catch (Exception e){
            logger.error("listStandingBooks fail",e);
        }
        return new JsonReaderResponse(commands,false,"");
    }
    @RequestMapping("save")
    public JsonReaderSingleResponse save(@RequestBody StandingBookCommand command){
        Long id=command.getId();
        try{
            StandingBook book=null;
            String serialNum=command.getSerialNum();
            if(id==null){
                book=standingBookManager.getBySerialNum(serialNum);
                if(book!=null){
                    return new JsonReaderSingleResponse(null,false,"编码已存在，不能重复创建");
                }
                book=new StandingBook();
                book.setCreateDate(new Date());
            }else{
                book=standingBookManager.getById(id);
                StandingBook newBook=standingBookManager.getBySerialNum(serialNum);
                if(newBook!=null&&newBook.getId()!=id){
                    return new JsonReaderSingleResponse(null,false,"编码已存在，请修改为其他编号");
                }
            }
            book.setName(command.getName());
            book.setTableId(command.getTableId());
            book.setHasMenu(BooleanUtils.toBoolean(command.getHasMenu()));
            book.setSerialNum(command.getSerialNum());
            book.setUrl(command.getUrl());
            book.setCreator("admin");
            standingBookManager.save(book);
        }catch (Exception e){
            logger.error("save",e);
            return new JsonReaderSingleResponse(null,false,"保存失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }
    @RequestMapping("delete")
    public JsonReaderSingleResponse save(@RequestParam("id")Long id){
        try{
            StandingBook book=standingBookManager.getById(id);
            if(book!=null){
                standingBookManager.delete(book);
            }
        }catch (Exception e){
            logger.error("delete",e);
            return new JsonReaderSingleResponse(null,false,"删除失败");
        }
        return new JsonReaderSingleResponse(null,true,"");
    }

}
