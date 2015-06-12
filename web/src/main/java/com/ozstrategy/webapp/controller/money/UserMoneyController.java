package com.ozstrategy.webapp.controller.money;

import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.money.UserMoneyDetail;
import com.ozstrategy.model.money.UserMoneyType;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.money.UserMoneyDetailManager;
import com.ozstrategy.service.money.UserMoneyManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.BaseResultCommand;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.credits.CreditsDetailCommand;
import com.ozstrategy.webapp.command.credits.UserCreditsCommand;
import com.ozstrategy.webapp.command.money.UserMoneyCommand;
import com.ozstrategy.webapp.command.money.UserMoneyDetailCommand;
import com.ozstrategy.webapp.command.order.CreditsBillCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao1 on 5/30/15.
 */
@RestController
@RequestMapping("money")
public class UserMoneyController extends BaseController {
    @Autowired
    private UserMoneyManager userMoneyManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserMoneyDetailManager userMoneyDetailManager;


    @RequestMapping("getMoney")
    public JsonReaderSingleResponse<UserMoneyCommand> getMoney(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<UserMoneyCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            UserMoney userMoney=user.getUserMoney();
            return new JsonReaderSingleResponse<UserMoneyCommand>(new UserMoneyCommand(userMoney),true,"");

        }catch (Exception e){

        }
        return new JsonReaderSingleResponse<UserMoneyCommand>(null,false,"请求失败");
    }
    @RequestMapping("listMoney")
    public JsonReaderResponse<UserMoneyCommand> listMoney(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        map.put("Q_user.enabled_EQ_BL",true);
        List<UserMoney> roles        = userMoneyManager.listPage(map, obtainStart(request), obtainLimit(request));
        List<UserMoneyCommand> roleCommands = new ArrayList<UserMoneyCommand>();
        if ((roles != null) && (roles.size() > 0)) {
            for (UserMoney role : roles) {
                UserMoneyCommand command=new UserMoneyCommand(role);
                roleCommands.add(command);
            }
        }
        Integer count = userMoneyManager.count(map);
        return new JsonReaderResponse<UserMoneyCommand>(roleCommands, true,count,"");
    }

    @RequestMapping("listDetails")
    public JsonReaderResponse<UserMoneyDetailCommand> listDetails(HttpServletRequest request) {
        String type=request.getParameter("type");
        String moneyId=request.getParameter("moneyId");
        if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(moneyId)){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("Q_money.id_EQ_L",Long.parseLong(moneyId));
            map.put("Q_type_EQ_N",Integer.parseInt(type));
            List<UserMoneyDetail> roles        = userMoneyDetailManager.listPage(map, obtainStart(request), obtainLimit(request));
            List<UserMoneyDetailCommand> roleCommands = new ArrayList<UserMoneyDetailCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (UserMoneyDetail role : roles) {
                    UserMoneyDetailCommand command=new UserMoneyDetailCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = userMoneyDetailManager.count(map);
            return new JsonReaderResponse<UserMoneyDetailCommand>(roleCommands, true,count,"");
        }
        return new JsonReaderResponse<UserMoneyDetailCommand>(new ArrayList<UserMoneyDetailCommand>(), true,0,"");
    }
    @RequestMapping("update")
    public BaseResultCommand update(@RequestBody UserMoneyCommand command,HttpServletRequest request) {
        String username=request.getRemoteUser();
        try{
            User user=userManager.getUserByUsername(username);
            Long id=command.getId();
            UserMoney money=userMoneyManager.get(id);
            money.setTotal(command.getTotal());
            money.setLastUpdateDate(new Date());
            money.setLastType(UserMoneyType.Admin.ordinal());
            Set<UserMoneyDetail> details=new HashSet<UserMoneyDetail>();
            UserMoneyDetail detail=new UserMoneyDetail();
            detail.setCreateDate(new Date());
            detail.setTotal(command.getTotal());
            detail.setLastUpdateDate(new Date());
            detail.setLastUpdater(user);
            detail.setDeal(user);
            detail.setMoney(money);
            detail.setType(UserMoneyType.Admin.ordinal());
            details.add(detail);
            money.getDetails().addAll(details);
            userMoneyManager.saveOrUpdate(money);
            return new BaseResultCommand("",Boolean.TRUE);
        }catch (Exception e){

        }
        return new BaseResultCommand("修改失败",Boolean.FALSE);
    }
}
