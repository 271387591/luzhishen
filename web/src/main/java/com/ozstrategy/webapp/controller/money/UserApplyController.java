package com.ozstrategy.webapp.controller.money;

import com.ozstrategy.alipay.util.AlipaySubmit;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.*;
import com.ozstrategy.model.order.CreditsBill;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.money.UserAliInfoManager;
import com.ozstrategy.service.money.UserApplyManager;
import com.ozstrategy.service.money.UserMoneyManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.money.UserAliInfoCommand;
import com.ozstrategy.webapp.command.money.UserApplyCommand;
import com.ozstrategy.webapp.command.money.UserMoneyCommand;
import com.ozstrategy.webapp.command.order.CreditsBillCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by lihao1 on 5/30/15.
 */
@RestController
@RequestMapping("apply")
public class UserApplyController extends BaseController {
    @Autowired
    private UserApplyManager userApplyManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserAliInfoManager userAliInfoManager;
    @Autowired
    private UserMoneyManager userMoneyManager;



    @RequestMapping("applyRmb")
    public JsonReaderSingleResponse<CreditsBillCommand> applyRmb(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"登录超时");
        }
        String total=request.getParameter("total");
        String rmb=request.getParameter("rmb");
        String email=request.getParameter("email");
        String name=request.getParameter("name");
        try{
            User user=userManager.getUserByUsername(username);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_creator.id_EQ_L",user.getId());
            map.put("Q_name_EQ_S",email);
            UserAliInfo userAliInfo=userAliInfoManager.getByParams(map);
            if(userAliInfo==null){
                userAliInfo=new UserAliInfo();
                userAliInfo.setCreator(user);
                userAliInfo.setLastUpdater(user);
                userAliInfo.setCreateDate(new Date());
                userAliInfo.setLastUpdateDate(new Date());
                userAliInfo.setEmail(email);
                userAliInfo.setName(name);
                userAliInfoManager.save(userAliInfo);
            }
            UserMoney userMoney=user.getUserMoney();
            if(userMoney.getTotal()<=0){
                return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"账户余额不足");
            }

            UserApply userApply=new UserApply();
            userApply.setCreator(user);
            userApply.setCreateDate(new Date());
            userApply.setTotal(new Double(total));
            userApply.setRmb(new Double(rmb));
            userApply.setStatus(UserApplyStatus.NoHandle.ordinal());
            userApply.setUserAliInfo(userAliInfo);
            userApply.setUserMoney(userMoney);
            userApplyManager.saveUserApply(userApply,userMoney);
            return new JsonReaderSingleResponse<CreditsBillCommand>(null,true,"");
        }catch (Exception e){
        }
        return new JsonReaderSingleResponse<CreditsBillCommand>(null,false,"参数错误");
    }


    @RequestMapping("listAliEmail")
    public JsonReaderResponse<UserAliInfoCommand> listAliEmail(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderResponse<UserAliInfoCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_creator.id_EQ_L",user.getId());
            List<UserAliInfo> roles        = userAliInfoManager.listPage(map, obtainStart(request), obtainLimit(request));
            List<UserAliInfoCommand> roleCommands = new ArrayList<UserAliInfoCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (UserAliInfo role : roles) {
                    UserAliInfoCommand command=new UserAliInfoCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = userApplyManager.count(map);
            return new JsonReaderResponse<UserAliInfoCommand>(roleCommands, true,count,"");

        }catch (Exception e){

        }
        return new JsonReaderResponse<UserAliInfoCommand>(null,false,"请求错误");
    }
    @RequestMapping("getApply")
    public JsonReaderResponse<UserApplyCommand> getApply(HttpServletRequest request) {
        String username=request.getRemoteUser();
        Integer status=parseInteger(obtain(request,"status"));
        if(StringUtils.isEmpty(username)){
            return new JsonReaderResponse<UserApplyCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_creator.id_EQ_L",user.getId());
            map.put("Q_status_EQ_N",status);
            List<UserApply> roles        = userApplyManager.listPage(map, obtainStart(request), obtainLimit(request));
            List<UserApplyCommand> roleCommands = new ArrayList<UserApplyCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (UserApply role : roles) {
                    UserApplyCommand command=new UserApplyCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = userApplyManager.count(map);
            return new JsonReaderResponse<UserApplyCommand>(roleCommands, true,count,"");
        }catch (Exception e){

        }


        return new JsonReaderResponse<UserApplyCommand>(null,false,"获取失败");
    }
    @RequestMapping("listApply")
    public JsonReaderResponse<UserApplyCommand> listApply(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        map.put("Q_creator.enabled_EQ_BL",true);
        List<UserApply> roles        = userApplyManager.listPage(map, obtainStart(request), obtainLimit(request));
        List<UserApplyCommand> roleCommands = new ArrayList<UserApplyCommand>();
        if ((roles != null) && (roles.size() > 0)) {
            for (UserApply role : roles) {
                UserApplyCommand command=new UserApplyCommand(role);
                roleCommands.add(command);
            }
        }
        Integer count = userApplyManager.count(map);
        return new JsonReaderResponse<UserApplyCommand>(roleCommands, true,count,"");
    }

    @RequestMapping("cancelApply")
    public JsonReaderSingleResponse cancelApply(HttpServletRequest request){
        String ids=request.getParameter("ids");
        String cancelDetail=request.getParameter("cancelDetail");
        if(StringUtils.isNotEmpty(ids)){
            String[] idstrs=ids.split(",");
            List<UserApply> applies=new ArrayList<UserApply>();
            if(idstrs!=null && idstrs.length>0){
                for(String id:idstrs){
                    Long payId=Long.parseLong(id);
                    UserApply userApply=userApplyManager.get(payId);
                    userApply.setCancelDetail(cancelDetail);
                    applies.add(userApply);
                }
            }
            userApplyManager.bachCancel(applies);
        }
        return new JsonReaderSingleResponse(null);
    }


    @RequestMapping("bathPay")
    public ModelAndView bathPay(HttpServletRequest request,HttpServletResponse response){
        String ids=request.getParameter("ids");
        try {
            if(StringUtils.isNotEmpty(ids)){
                String[] idstrs=ids.split(",");
                if(idstrs!=null && idstrs.length>0){
                    List<UserApply> applies=new ArrayList<UserApply>();
                    String username=request.getRemoteUser();
                    User user=userManager.getUserByUsername(username);
                    for(String id:idstrs){
                        Long payId=Long.parseLong(id);
                        UserApply userApply=userApplyManager.get(payId);
                        applies.add(userApply);
                    }
                    SystemRmb rmb=new SystemRmb();
                    rmb.setCreateDate(new Date());
                    rmb.setLastUpdateDate(new Date());
                    rmb.setCreator(user);
                    rmb.setStatus(SystemRmbStatus.Nopay.ordinal());
                    Map<String,String> requestMap = userApplyManager.createBachPayRequest(rmb, applies);
                    Map<String,Object> data=new HashMap<String, Object>();
                    data.put("requestMap",requestMap);
//                    String sHtmlText = AlipaySubmit.buildRequest(requestMap, "get", "确认");
//                    response.getWriter().println(sHtmlText);
                    return new ModelAndView("alipayapi",data);
                }
            }
        } catch (Exception e) {

        }
        return new ModelAndView("alipayapifail");

    }
}
