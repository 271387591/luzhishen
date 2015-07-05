package com.ozstrategy.webapp.controller.credits;

import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.credits.CreditsDetailManager;
import com.ozstrategy.service.credits.UserCreditsManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.BaseResultCommand;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.credits.CreditsDetailCommand;
import com.ozstrategy.webapp.command.credits.UserCreditsCommand;
import com.ozstrategy.webapp.command.order.CreditsOrderCommand;
import com.ozstrategy.webapp.command.userrole.RoleCommand;
import com.ozstrategy.webapp.command.userrole.SimpleRoleCommand;
import com.ozstrategy.webapp.command.userrole.UserCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao1 on 5/25/15.
 */
@RestController
@RequestMapping("credits")
public class CreditsController extends BaseController {
    @Autowired
    private UserCreditsManager userCreditsManager;
    @Autowired
    private CreditsDetailManager creditsDetailManager;
    @Autowired
    private UserManager userManager;

    @RequestMapping("listCredits")
    public JsonReaderResponse<UserCreditsCommand> listCredits(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        map.put("Q_user.enabled_EQ_BL",true);
        List<UserCredits> roles        = userCreditsManager.listPage(map, obtainStart(request), obtainLimit(request));
        List<UserCreditsCommand> roleCommands = new ArrayList<UserCreditsCommand>();
        if ((roles != null) && (roles.size() > 0)) {
            for (UserCredits role : roles) {
                UserCreditsCommand command=new UserCreditsCommand(role);
                roleCommands.add(command);
            }
        }
        Integer count = userCreditsManager.count(map);
        return new JsonReaderResponse<UserCreditsCommand>(roleCommands, true,count,"");
    }
    @RequestMapping("listDetails")
    public JsonReaderResponse<CreditsDetailCommand> listDetails(HttpServletRequest request) {
        String type=request.getParameter("type");
        String creditsId=request.getParameter("creditsId");
        if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(creditsId)){
            List<CreditsDetail> roles        = creditsDetailManager.listDetails(null, new Long(creditsId),new Integer(type), obtainStart(request), obtainLimit(request));
            List<CreditsDetailCommand> roleCommands = new ArrayList<CreditsDetailCommand>();
            if ((roles != null) && (roles.size() > 0)) {
                for (CreditsDetail role : roles) {
                    CreditsDetailCommand command=new CreditsDetailCommand(role);
                    roleCommands.add(command);
                }
            }
            Integer count = creditsDetailManager.listDetailsCount(null, new Long(creditsId),new Integer(type));
            return new JsonReaderResponse<CreditsDetailCommand>(roleCommands, true,count,"");
        }
        return new JsonReaderResponse<CreditsDetailCommand>(new ArrayList<CreditsDetailCommand>(), true,0,"");
    }
    @RequestMapping("getUserCredits")
    public JsonReaderSingleResponse<UserCreditsCommand> getUserCredits(HttpServletRequest request) {
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            return new JsonReaderSingleResponse<UserCreditsCommand>(null,false,"登录超时");
        }
        try{
            User user=userManager.getUserByUsername(username);
            UserCredits userCredits = user.getUserCredits();
            if(userCredits!=null){
                return new JsonReaderSingleResponse<UserCreditsCommand>(new UserCreditsCommand(userCredits));
            }
        }catch (Exception e){
        }
        return new JsonReaderSingleResponse<UserCreditsCommand>(null,false,"数据库异常");
    }

    @RequestMapping("update")
    public BaseResultCommand update(@RequestBody UserCreditsCommand command,HttpServletRequest request) {
        String username=request.getRemoteUser();
        try{
            User user=userManager.getUserByUsername(username);
            Long id=command.getId();
            Double credits=command.getTotal();
            userCreditsManager.updateCredits(id,credits,user);
            return new BaseResultCommand("",Boolean.TRUE);
        }catch (Exception e){

        }
        return new BaseResultCommand("修改失败",Boolean.FALSE);
    }
    @RequestMapping("reportPoints")
    public BaseResultCommand reportPoints(HttpServletRequest request) {
        String user_id=request.getParameter("user_id");
        String time =request.getParameter("time");
        String SSID =request.getParameter("SSID");
        String BSSID =request.getParameter("BSSID");
        String points =request.getParameter("points");
        String points1 =request.getParameter("points1");
        try{
            User user=userManager.get(parseLong(user_id));
            userCreditsManager.reportPoints(user, SSID, BSSID, points);
            UserCredits credits=userCreditsManager.get(user.getUserCredits().getId());
            return new BaseResultCommand(new UserCreditsCommand(credits));
        }catch (Exception e){

        }
        return new BaseResultCommand("上传失败",Boolean.FALSE);
    }
    @RequestMapping("givePoints")
    public BaseResultCommand givePoints(HttpServletRequest request) {
        String user_id=request.getParameter("user_id");
        String give_id =request.getParameter("give_id");
        String points =request.getParameter("points");
        try{
            User user=userManager.get(parseLong(user_id));
            User target=userManager.getUserByUsername(give_id);
            if(target==null){
                return new BaseResultCommand("对方账号不存",Boolean.FALSE);
            }
            userCreditsManager.givePoints(user, target, parseDouble(points));
            return new BaseResultCommand("",Boolean.TRUE);
        }catch (Exception e){
        }
        return new BaseResultCommand("赠送失败",Boolean.FALSE);
    }







}
