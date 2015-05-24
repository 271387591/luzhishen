package com.ozstrategy.webapp.controller.userrole;

import com.ozstrategy.Constants;
import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.userrole.RoleManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.BaseResultCommand;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.userrole.FeatureCommand;
import com.ozstrategy.webapp.command.userrole.RoleCommand;
import com.ozstrategy.webapp.command.userrole.SimpleRoleCommand;
import com.ozstrategy.webapp.command.userrole.UserCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao1 on 5/23/15.
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleManager roleManager = null;
    @Autowired
    private UserManager userManager = null;

    @RequestMapping("listRoles")
    public JsonReaderResponse<RoleCommand> listRoles(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        map.put("Q_enabled_EQ",true);
//        map.put("Q_name_NEQ", Constants.ROLE_ADMIN);
        List<Role> roles        = roleManager.listPage(map, obtainStart(request), obtainLimit(request));
        List<RoleCommand> roleCommands = new ArrayList<RoleCommand>();
        if ((roles != null) && (roles.size() > 0)) {
            for (Role role : roles) {
                RoleCommand command=new RoleCommand(role);
                roleCommands.add(command);
            }
        }
        Integer count = roleManager.count(map);
        return new JsonReaderResponse<RoleCommand>(roleCommands, true,count,"");
    }
    @RequestMapping("listRoleFeatures")
    public List<Map<String,Object>> listRoleFeatures(HttpServletRequest request) {
        Long roleId=parseLong(request.getParameter("roleId"));
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        List<Feature> features=roleManager.listFeatureNoParent();
        Map<Long,Object> existMap=new HashMap<Long, Object>();
        if(roleId!=null){
            Role role=roleManager.get(roleId);
            Set<Feature> existFeatures=role.getFeatures();
            for(Feature feature:existFeatures){
                existMap.put(feature.getId(),feature.getName());
            }
        }
        if(features!=null && features.size()>0){
            for(Feature feature : features){
                Map<String,Object> map=createTree(feature,existMap);
                list.add(map);
            }
        }
        return list;
    }
    private Map<String,Object> createTree(Feature feature,Map<Long,Object> existMap){
        Long id=feature.getId();
        List<Feature> children=roleManager.listFeatureByParent(id);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("name",feature.getName());
        map.put("text",feature.getDisplayName());
        map.put("leaf",true);
        if(existMap.containsKey(feature.getId())){
            map.put("checked",true);
        }else{
            map.put("checked",false);
        }
        if(children!=null && children.size()>0){
            map.remove("leaf");
            List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
            for(Feature child:children){
                Map<String,Object> cMap=createTree(child,existMap);
                list.add(cMap);
            }
            map.put("children",list);
        }
        return map;
    }
    @RequestMapping("save")
    public BaseResultCommand save(@RequestBody RoleCommand command,HttpServletRequest request) {
        Long id=command.getId();
        Role role = null;
        if(id!=null){
            role=roleManager.get(id);
            Role newRole=roleManager.getRoleByName(command.getName());
            if(newRole!=null && newRole.getId()!=id){
                return new BaseResultCommand("名称已存在",Boolean.FALSE);
            }
        }else{
            if(roleManager.getRoleByName(command.getName())!=null){
                return new BaseResultCommand("名称已存在",Boolean.FALSE);
            }
            role=new Role();
            role.setCreateDate(new Date());
        }
        role.setName(command.getName());
        role.setDisplayName(command.getDisplayName());
        role.setLastUpdateDate(new Date());
        Set<Feature> features=new HashSet<Feature>();
        List<FeatureCommand> simpleFeatures=command.getSimpleFeatures();
        if(simpleFeatures!=null && simpleFeatures.size()>0){
            for(FeatureCommand featureCommand:simpleFeatures){
                Feature feature=roleManager.getFeatureById(featureCommand.getId());
                if(feature!=null){
                    features.add(feature);
                }
            }
        }
        role.getFeatures().clear();
        role.getFeatures().addAll(features);
        roleManager.saveOrUpdate(role);
        return new BaseResultCommand("",Boolean.TRUE);
    }
    @RequestMapping("remove/{id}")
    public BaseResultCommand remove(@PathVariable("id")Long id,HttpServletRequest request) {
        Role role=roleManager.get(id);
        if(role!=null){
            try{
                Set<User> users=role.getUsers();
                if(!users.isEmpty()){
                    return new BaseResultCommand("该角色被其他用户引用，不能删除",Boolean.FALSE);
                }
                roleManager.removeRole(role);
            }catch (AccessDeniedException e) {
                return new BaseResultCommand("只有超级管理员才能修改",Boolean.FALSE);
            }catch (Exception e){
                return new BaseResultCommand(getZhMessage("globalRes.removeFail"),Boolean.FALSE);
            }
        }
        return new BaseResultCommand("",Boolean.TRUE);
    }


}
