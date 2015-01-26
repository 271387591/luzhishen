package com.ozstrategy.webapp.controller.levers;

import com.ozstrategy.model.levers.Lever;
import com.ozstrategy.service.levers.LeverManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.levers.LeverCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihao on 1/23/15.
 */
@RestController
@RequestMapping("lever")
public class LeverController extends BaseController {
    @Autowired
    private LeverManager leverManager;
    @RequestMapping(value = "/list")
    public JsonReaderResponse<LeverCommand> listLevers(HttpServletRequest request) {
        String start=request.getParameter("start");
        String limit=request.getParameter("limit");
        if(checkIsNotNumber(start)){
            return new JsonReaderResponse<LeverCommand>(emptyData, Boolean.FALSE,getZhMessage("message.error.start"));
        }
        String keyword=request.getParameter("keyword");
        
        List<LeverCommand> commands=new ArrayList<LeverCommand>();
        List<Lever> levers=leverManager.list(keyword,parseInteger(start),parseInteger(limit));
        if(levers!=null && !levers.isEmpty()){
            for(Lever lever : levers){
                commands.add(new LeverCommand(lever));
            }
        }
        int count = leverManager.listCount(keyword);
        return new JsonReaderResponse<LeverCommand>(commands,count);
    }
    @RequestMapping(value = "/get/{id}")
    public JsonReaderSingleResponse<LeverCommand>  getLever(@PathVariable("id")Long id){
        try{
            Lever lever=leverManager.get(id);
            if(lever!=null){
                return new JsonReaderSingleResponse<LeverCommand>(new LeverCommand(lever));
            }
        } catch (Exception e){
            logger.error("get lever fail",e);
        }                                                          
       
        return new JsonReaderSingleResponse<LeverCommand>(null,false,getZhMessage("message.error.data.notfound"));
    }
    @RequestMapping(value = "/delete/{id}")
    public JsonReaderSingleResponse<LeverCommand>  deleteLever(@PathVariable("id")Long id){
        try{
            Lever lever=leverManager.get(id);
            if(lever!=null){
                leverManager.remove(lever);
                return new JsonReaderSingleResponse<LeverCommand>();
            }
        }catch (Exception e){
            logger.error("delete lever fail",e);
        }
        return new JsonReaderSingleResponse<LeverCommand>(null,false,getZhMessage("message.error.data.notfound"));
    }
    @RequestMapping(value = "/save")
    public JsonReaderSingleResponse<LeverCommand>  saveLever(HttpServletRequest request){
        try{
            return saveOrUpdate(request,true);
        }catch (Exception e){
           logger.error("save or update lever error",e); 
        }
        return new JsonReaderSingleResponse<LeverCommand>(null,false,getZhMessage("globalRes.addFail"));
    }
    @RequestMapping(value = "/update")
    public JsonReaderSingleResponse<LeverCommand>  updateLever(HttpServletRequest request){
        try{
            return saveOrUpdate(request,false);
        }catch (Exception e){
           logger.error("save or update lever error",e); 
        }
        return new JsonReaderSingleResponse<LeverCommand>(null,false,getZhMessage("globalRes.updateFail"));
    }
    
    private JsonReaderSingleResponse<LeverCommand> saveOrUpdate(HttpServletRequest request,boolean save){
        String id=obtain(request,"id");
        String xnName=obtain(request,"xnName");
        String xnNo=obtain(request,"xnNo");
        String xzNo=obtain(request,"xzNo");
        String police=obtain(request,"police");
        String policeNo=obtain(request,"policeNo");
        String village=obtain(request,"village");
        String villageNo=obtain(request,"villageNo");
        String leverNo=obtain(request,"leverNo");
        String longitude=obtain(request,"longitude");
        String latitude=obtain(request,"latitude");
        String allNo=obtain(request,"allNo");
        String leverType=obtain(request,"leverType");
        String leverAddress=obtain(request,"leverAddress");
        String leverAddressAlias=obtain(request,"leverAddressAlias");
        Long xId=null;
        if(StringUtils.isNotEmpty(id)){
            xId=parseLong(id);
        }
        Lever lever=null;
        if(xId==null){
            if(!save){
                return new JsonReaderSingleResponse<LeverCommand>(null,false,getZhMessage("message.error.id.null"));
            }
            lever=new Lever();
            lever.setCreateDate(new Date());
        }else{
            lever=leverManager.get(xId);
        }
        lever.setXnName(xnName);
        lever.setXnNo(xnNo);
        lever.setXzNo(xzNo);
        lever.setPolice(police);
        lever.setPoliceNo(policeNo);
        lever.setVillage(village);
        lever.setVillageNo(villageNo);
        lever.setLeverNo(leverNo);
        lever.setLeverType(leverType);
        lever.setLongitude(longitude);
        lever.setLatitude(latitude);
        lever.setAllNo(allNo);
        lever.setLeverAddress(leverAddress);
        lever.setLeverAddressAlias(leverAddressAlias);
        lever.setLastUpdateDate(new Date());
        leverManager.saveOrUpdate(lever);
        return new JsonReaderSingleResponse<LeverCommand>(new LeverCommand(lever));
    }
    
}
