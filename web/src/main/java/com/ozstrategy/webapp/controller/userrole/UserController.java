package com.ozstrategy.webapp.controller.userrole;

import com.ozstrategy.Constants;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.userrole.RoleManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.BaseResultCommand;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.userrole.SimpleRoleCommand;
import com.ozstrategy.webapp.command.userrole.UserCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao on 7/4/14.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserManager userManager = null;
    @Autowired 
    private RoleManager roleManager = null;
    protected final transient Log log = LogFactory.getLog(getClass());
    @RequestMapping("listUsers")
    public JsonReaderResponse<UserCommand> listUsers(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        map.put("Q_enabled_EQ_BL",true);
        map.put("Q_username_NEQ_S", Constants.Admin);
        List<User> users        = userManager.listPage(map,obtainStart(request), obtainLimit(request));
        List<UserCommand> userCommands = new ArrayList<UserCommand>();
        if ((users != null) && (users.size() > 0)) {
            for (User user : users) {
                UserCommand cmd = new UserCommand(user);
                userCommands.add(cmd);
            }
        }
        int count = userManager.count(map);
        return new JsonReaderResponse<UserCommand>(userCommands,count);
    }
    @RequestMapping("mobileSave")
    public Map<String,Object> mobileSave(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String referee=request.getParameter("referee");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        if(userManager.existByUserName(username)){
            map.put("result",0);
            map.put("result_text","用户已存在");
            return map;
        }
        User user=new User();
        user.setCreateDate(new Date());
        user.setLastUpdateDate(new Date());
        user.setReferee(referee);
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setMobile(phone);
        user.setAddress(address);
        userManager.saveUser(user);
        map.put("result",1);
        map.put("result_text","");
        return map;
    }
    @RequestMapping("mobileUpdate")
    public Map<String,Object> mobileUpdate(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        String username=request.getRemoteUser();
        if(StringUtils.isEmpty(username)){
            map.put("result",0);
            map.put("result_text","登录超时");
            return map;
        }
        User user=userManager.getUserByUsername(username);
        String password=request.getParameter("password");
        String referee=request.getParameter("referee");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        user.setCreateDate(new Date());
        user.setLastUpdateDate(new Date());
        user.setReferee(referee);
        user.setPassword(password);
        user.setEmail(email);
        user.setMobile(phone);
        user.setAddress(address);
        userManager.saveOrUpdate(user);
        map.put("result",1);
        map.put("result_text","");
        return map;
    }

    @RequestMapping("save")
    public BaseResultCommand save(@RequestBody UserCommand userCommand,HttpServletRequest request) {
        Long id=userCommand.getId();
        User user = null;
        if(id!=null){
            user=userManager.get(id);
        }else{
            if(userManager.existByUserName(userCommand.getUsername())){
                return new BaseResultCommand("用户已存在",Boolean.FALSE);
            }
            user=new User();
            user.setCreateDate(new Date());
            user.setPassword(userCommand.getPassword());
        }
        user.setUsername(userCommand.getUsername());
        user.setLastUpdateDate(new Date());
        user.setEmail(userCommand.getEmail());
        user.setMobile(userCommand.getMobile());
        user.setGender(userCommand.getGender());
        user.setReferee(userCommand.getReferee());
        user.setHotPass(userCommand.getHotPass());
        Long roleId=userCommand.getRoleId();
        if(roleId!=null){
            Role defRole=roleManager.get(roleId);
            user.setRole(defRole);
        }
        Set<Role> roles=new HashSet<Role>();
        List<SimpleRoleCommand> simpleRoles=userCommand.getSimpleRoles();
        if(simpleRoles!=null && simpleRoles.size()>0){
            for(SimpleRoleCommand simpleRoleCommand:simpleRoles){
                Role role=roleManager.get(simpleRoleCommand.getId());
                if(role!=null){
                    roles.add(role);
                }
            }
        }
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        userManager.saveUser(user);
        return new BaseResultCommand("",Boolean.TRUE);
    }
    @RequestMapping("remove/{id}")
    public BaseResultCommand remove(@PathVariable("id")Long id,HttpServletRequest request) {
        User user=userManager.get(id);
        if(user!=null){
            try{
                userManager.removeUser(user);
            }catch (AccessDeniedException e) {
                return new BaseResultCommand("只有超级管理员才能修改",Boolean.FALSE);
            }catch (Exception e){
                return new BaseResultCommand(getZhMessage("globalRes.removeFail"),Boolean.FALSE);
            }
        }
        return new BaseResultCommand("",Boolean.TRUE);
    }



    @RequestMapping(value = "adminChangePassword",method = RequestMethod.POST)
    public BaseResultCommand adminChangePassword(HttpServletRequest request) {
        return changePassword(request,true);
    }
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public BaseResultCommand changePassword(HttpServletRequest request) {
        return changePassword(request,false);
    }

    private BaseResultCommand changePassword(HttpServletRequest request,boolean admin){
        String id=request.getParameter("id");
        String oldPassword=request.getParameter("oldPassword");
        String newPassword=request.getParameter("newPassword");
        if(admin){
            if(checkIsNotNumber(id)){
                return new BaseResultCommand(getMessage("message.error.id.null",request),Boolean.FALSE);
            }
        }else{
            if(checkIsEmpty(oldPassword)){
                return new BaseResultCommand(getMessage("message.error.updatePassword.oldPassword.null",request),Boolean.FALSE);
            }
        }

        if(checkIsEmpty(newPassword)){
            return new BaseResultCommand(getMessage("message.error.updatePassword.newPassword.null",request),Boolean.FALSE);
        }
        try {
            User user=null;
            if(admin){
                user=userManager.get(parseLong(id));
            }else{
                user = userManager.getUserByUsername(request.getRemoteUser());
            }
            Integer result=userManager.updateUserPassword(user,oldPassword,newPassword,admin);
            if(result==1){
                return new BaseResultCommand(getMessage("message.error.updatePassword.oldPassword.error",request),Boolean.FALSE);
            }
            return new BaseResultCommand(Boolean.TRUE);
        }catch (AccessDeniedException e) {
            return new BaseResultCommand("只有超级管理员才能修改",Boolean.FALSE);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return new BaseResultCommand(getMessage("message.error.updatePassword.fail",request),Boolean.FALSE);
    }
    @RequestMapping(value = "lockUser",method = RequestMethod.POST)
    public BaseResultCommand lockUser(HttpServletRequest request) {
        try {
            return lockOrUnlockUser(request,true);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage(),e);
            return new BaseResultCommand("只有超级管理员才能修改",Boolean.FALSE);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return new BaseResultCommand(getMessage("message.error.lock.error",request),Boolean.FALSE);
    }
    @RequestMapping(value = "unLockUser",method = RequestMethod.POST)
    public BaseResultCommand unLockUser(HttpServletRequest request) {
        try {
            return lockOrUnlockUser(request,false);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage(),e);
            return new BaseResultCommand("只有超级管理员才能修改",Boolean.FALSE);

        } catch (Exception e) {
            log.error(e.getMessage(),e);

        }
        return new BaseResultCommand(getMessage("message.error.unlock.error",request),Boolean.FALSE);
    }
    private BaseResultCommand lockOrUnlockUser(HttpServletRequest request,boolean lock) throws Exception{
        String id=request.getParameter("id");
        if(checkIsNotNumber(id)){
            return new BaseResultCommand(getMessage("message.error.id.null",request),Boolean.FALSE);
        }
        User    targetUser  = userManager.get(Long.parseLong(id));
        targetUser.setLastUpdateDate(new Date());
        userManager.lockOrUnLockUser(targetUser, lock);
        return new BaseResultCommand(Boolean.TRUE);
    }
}
