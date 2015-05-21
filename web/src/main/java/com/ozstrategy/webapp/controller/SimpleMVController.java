package com.ozstrategy.webapp.controller;

import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.model.user.User;
import com.ozstrategy.model.user.RoleFieldFeature;
import com.ozstrategy.service.user.UserManager;
import com.ozstrategy.webapp.command.user.RoleCommand;
import com.ozstrategy.webapp.command.user.RoleFieldFeatureCommand;
import com.ozstrategy.webapp.command.user.RoleTableFeatureCommand;
import com.ozstrategy.webapp.command.user.UserCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SimpleMVController implements InitializingBean {

    private final transient Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserManager userManager = null;

    public void afterPropertiesSet() throws Exception {
    }
    @RequestMapping("/desktopRes.js")
    public ModelAndView getGlobalRes(HttpServletRequest request, HttpServletResponse response) {
        User user = userManager.getUserByUsername("admin");

        UserCommand userCommand=null;
        if(user!=null){
            RoleCommand command=null;
            Role role=userManager.getRoleById(user.getRoleId());
            List<RoleFieldFeature> features=userManager.listUserFieldFeatureByRoleId(user.getRoleId());
            List<RoleFieldFeatureCommand> commands=new ArrayList<RoleFieldFeatureCommand>();
            for(RoleFieldFeature feature:features){
                commands.add(new RoleFieldFeatureCommand(feature));
            }
            List<RoleTableFeature> tableFeatures=userManager.listRoleTableFeatureByRoleId(user.getRoleId());
            List<RoleTableFeatureCommand> tableFeatureCommands=new ArrayList<RoleTableFeatureCommand>();
            for(RoleTableFeature feature:tableFeatures){
                tableFeatureCommands.add(new RoleTableFeatureCommand(feature));
            }

            if(role!=null){
                command=new RoleCommand(role,commands,tableFeatureCommands);
            }
            userCommand=new UserCommand(user,command);
        }
        return new ModelAndView("res/desktopRes", "command", userCommand);
    }
}
