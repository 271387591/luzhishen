package com.ozstrategy.webapp.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozstrategy.Constants;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.order.CreditsBill;
import com.ozstrategy.model.order.CreditsBillStatus;
import com.ozstrategy.model.order.CreditsOrder;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.credits.UserCreditsManager;
import com.ozstrategy.service.order.CreditsBillManager;
import com.ozstrategy.service.order.CreditsOrderManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.util.RSAUtils;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.credits.UserCreditsCommand;
import com.ozstrategy.webapp.command.money.UserMoneyCommand;
import com.ozstrategy.webapp.command.order.CreditsBillCommand;
import com.ozstrategy.webapp.command.order.CreditsOrderCommand;
import com.ozstrategy.webapp.command.userrole.UserCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao1 on 5/26/15.
 */
@RestController
@RequestMapping("order")
public class CreditsBillController extends BaseController {
    @Autowired
    private CreditsBillManager creditsBillManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserCreditsManager userCreditsManager;
    @Autowired
    private CreditsOrderManager creditsOrderManager;

    @RequestMapping("listOrders")
    public JsonReaderResponse<CreditsOrderCommand> listOrders(HttpServletRequest request) {
        try{
            Map<String,Object> map=requestMap(request);
//            map.put("Q_loseDate_GE_D", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            List<CreditsOrder> orders=creditsOrderManager.listPage(map,obtainStart(request), obtainLimit(request));
            List<CreditsOrderCommand> commands=new ArrayList<CreditsOrderCommand>();
            if(orders!=null && orders.size()>0){
                for(CreditsOrder order:orders){
                    commands.add(new CreditsOrderCommand(order));
                }
            }
            Integer count = creditsOrderManager.count(map);
            return new JsonReaderResponse<CreditsOrderCommand>(commands, true,count,"");

        }catch (Exception e){

        }
        return new JsonReaderResponse<CreditsOrderCommand>(new ArrayList<CreditsOrderCommand>(), true,0,"查询数据异常");
    }
    @RequestMapping("loadChart")
    public List<Map<String,Object>> loadChart(HttpServletRequest request) {
        try{
            List<Map<String,Object>> list=creditsOrderManager.getOrderChart();
            if(list!=null && list.size()>0){
                for(Map<String,Object> map:list){
                    String status=map.get("status").toString();
                    if(StringUtils.equals("0",status)){
                        map.put("title","待支付");
                    }else if(StringUtils.equals("1",status)){
                        map.put("title","已支付");
                    }else{
                        map.put("title","支付失败");
                    }
                }
            }
            return list;
        }catch (Exception e){

        }
        return new ArrayList<Map<String, Object>>();
    }

    @RequestMapping("listBills")
    public JsonReaderResponse<CreditsBillCommand> listBills(HttpServletRequest request) {
        String sort=request.getParameter("sort");
        try{
            Map<String,String> sortMap=new HashMap<String, String>();
            //时间
            if(StringUtils.equals("1",sort)){
                sortMap.put("createDate","DESC");
            }else if(StringUtils.equals("2",sort)){
                sortMap.put("creditsSum","DESC");
            }else if(StringUtils.equals("3",sort)){
                sortMap.put("price","DESC");
            }
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_status_EQ_N", CreditsBillStatus.Unlock.ordinal());
            List<CreditsBill> roles        = creditsBillManager.listSortPage(map, obtainStart(request), obtainLimit(request),sortMap);
            List<CreditsBillCommand> roleCommands = new ArrayList<CreditsBillCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (CreditsBill role : roles) {
                    CreditsBillCommand command=new CreditsBillCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = creditsBillManager.count(map);
            return new JsonReaderResponse<CreditsBillCommand>(roleCommands, true,count,"");

        }catch (Exception e){

        }
        return new JsonReaderResponse<CreditsBillCommand>(new ArrayList<CreditsBillCommand>(), true,0,"查询数据异常");
    }

    @RequestMapping("createBills")
    public JsonReaderSingleResponse<CreditsBillCommand> createBills(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"登录超时");
        }
        String creditsSum=request.getParameter("creditsSum");
        String price=request.getParameter("price");
        try{
            if(NumberUtils.isNumber(creditsSum) && NumberUtils.isNumber(price)){
                User user=userManager.getUserByUsername(username);
                UserCredits userCredits=user.getUserCredits();
                if(userCredits.getTotal()<new Double(creditsSum)){
                    return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"积分不够");
                }
                CreditsBill bill=new CreditsBill();
                bill.setCreateDate(new Date());
                bill.setLastUpdateDate(new Date());
                bill.setCreator(user);
                bill.setLastUpdater(user);
                bill.setPrice(new Double(price));
                bill.setCreditsSum(new Double(creditsSum));
                creditsBillManager.createBill(user,bill);
                return new JsonReaderSingleResponse<CreditsBillCommand>(new CreditsBillCommand(bill));
            }
        }catch (Exception e){
        }
        return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"参数错误");
    }
    @RequestMapping("cancelBills")
    public JsonReaderSingleResponse<CreditsBillCommand> cancelBills(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"登录超时");
        }
        String billId=request.getParameter("billId");
        try{
            if(NumberUtils.isNumber(billId)){
                User user=userManager.getUserByUsername(username);
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("Q_creator.id_EQ_L",user.getId());
                map.put("Q_id_EQ_L",Long.parseLong(billId));
                CreditsBill bill=creditsBillManager.getByParams(map);
                if(bill==null){
                    return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"取消失败，你没有该商品");
                }
                if(bill.getStatus()==CreditsBillStatus.Lock.ordinal()){
                    return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"取消失败，该商品被其他用户购买");
                }
                bill.setLastUpdateDate(new Date());
                bill.setLastUpdater(user);
                creditsBillManager.cancelBills(bill);
                return new JsonReaderSingleResponse<CreditsBillCommand>(new CreditsBillCommand(bill));
            }
        }catch (Exception e){
        }
        return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"参数错误");
    }

    @RequestMapping("createOrder")
    public Map<String,Object> createOrder(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String, Object>();
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            map.put("success",false);
            map.put("message","登录超时");
            return map;
        }
        String billId=request.getParameter("billId");
        try{
            if(NumberUtils.isNumber(billId)){
                User user=userManager.getUserByUsername(username);
                CreditsBill bill=creditsBillManager.get(Long.parseLong(billId));
                if(bill==null || bill.getStatus()==CreditsBillStatus.Lock.ordinal()){
                    map.put("success",false);
                    map.put("message","该商品已被出售");
                    return map;
                }
                CreditsOrder order=new CreditsOrder();
                order.setCreateDate(new Date());
                order.setCreator(user);
                order.setLastUpdater(user);
                order.setPrice(bill.getPrice());
                order.setCredits(bill.getCreditsSum());
                String data= creditsOrderManager.createOrder(user, order,bill);
                map.put("success", true);
                map.put("message", "");
                map.put("data", data);
                return map;
            }
        }catch (Exception e){
        }
        map.put("success",false);
        map.put("message","提交订单失败");
        return map;
    }
    @RequestMapping("getOrders")
    public JsonReaderResponse<CreditsOrderCommand> getOrders(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderResponse<CreditsOrderCommand>(null,false,"登录超时");
        }
        String status=request.getParameter("status");
        try{
            if(NumberUtils.isNumber(status)){
                User user=userManager.getUserByUsername(username);
                Map<String,Object> map=new HashMap<String, Object>();
//                map.put("Q_loseDate_GE_D", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                map.put("Q_status_EQ_N", new Integer(status));
                map.put("Q_creator.id_EQ_L", user.getId());
                List<CreditsOrder> orders=creditsOrderManager.listPage(map,obtainStart(request), obtainLimit(request));
                List<CreditsOrderCommand> commands=new ArrayList<CreditsOrderCommand>();
                if(orders!=null && orders.size()>0){
                    for(CreditsOrder order:orders){
                        commands.add(new CreditsOrderCommand(order));
                    }
                }
                Integer count = creditsOrderManager.count(map);
                return new JsonReaderResponse<CreditsOrderCommand>(commands, true,count,"");
            }
        }catch (Exception e){
            logger.error("getorder",e);
        }
        return new JsonReaderResponse<CreditsOrderCommand>(new ArrayList<CreditsOrderCommand>(), true,0,"查询数据异常");
    }
    @RequestMapping("getCredits")
    public JsonReaderSingleResponse<UserCreditsCommand> getCredits(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<UserCreditsCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            UserCredits userCredits=user.getUserCredits();
            return new JsonReaderSingleResponse<UserCreditsCommand>(new UserCreditsCommand(userCredits),true,"");

        }catch (Exception e){

        }
        return new JsonReaderSingleResponse<UserCreditsCommand>(null,false,"请求失败");
    }
    @RequestMapping("getBills")
    public JsonReaderResponse<CreditsBillCommand> getBills(HttpServletRequest request) {
        String username=request.getRemoteUser();
        String status=obtain(request,"status");
        if(StringUtils.isEmpty(username)){
            return new JsonReaderResponse<CreditsBillCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_status_EQ_N", new Integer(status));
            map.put("Q_creator.id_EQ_L", user.getId());
            List<CreditsBill> roles        = creditsBillManager.listPage(map, obtainStart(request), obtainLimit(request));
            List<CreditsBillCommand> roleCommands = new ArrayList<CreditsBillCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (CreditsBill role : roles) {
                    CreditsBillCommand command=new CreditsBillCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = creditsBillManager.count(map);
            return new JsonReaderResponse<CreditsBillCommand>(roleCommands, true,count,"");

        }catch (Exception e){
        }
        return new JsonReaderResponse<CreditsBillCommand>(null,Boolean.FALSE,"获取失败");
    }

}
