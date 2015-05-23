package com.ozstrategy.webapp.controller.userrole;

import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.RoleFeature;
import com.ozstrategy.service.userrole.RoleManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.userrole.FeatureCommand;
import com.ozstrategy.webapp.command.userrole.RoleCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 5/23/15.
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleManager roleManager = null;
    @RequestMapping("listRoles")
    @ResponseBody
    public JsonReaderResponse<RoleCommand> listRoles(HttpServletRequest request) {
        String roleName=request.getParameter("roleName");
        Integer start=Integer.parseInt(request.getParameter("start"));
        Integer limit=Integer.parseInt(request.getParameter("limit"));
        List<Role> roles        = roleManager.getRoles(roleName,start, limit);
        List<RoleCommand> roleCommands = new ArrayList<RoleCommand>();

        if ((roles != null) && (roles.size() > 0)) {
            for (Role role : roles) {
                RoleCommand command=new RoleCommand(role);
                List<RoleFeature> roleFeatures=roleManager.getRoleFeature(role.getId());
                List<FeatureCommand> featureCommands=new ArrayList<FeatureCommand>();
                if(roleFeatures!=null){
                    for(RoleFeature roleFeature : roleFeatures){
                        featureCommands.add(new FeatureCommand(roleFeature.getFeature()));
                    }
                }
                command.getSimpleFeatures().clear();
                command.getSimpleFeatures().addAll(featureCommands);
                roleCommands.add(command);
            }
        }

        Long count = roleManager.getCount(roleName);

        return new JsonReaderResponse<RoleCommand>(roleCommands, true,count.intValue(),"");
    }

}
