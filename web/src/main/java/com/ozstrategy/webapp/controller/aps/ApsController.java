package com.ozstrategy.webapp.controller.aps;

import com.ozstrategy.model.aps.Aps;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.aps.ApsManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.aps.ApsCommand;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lihao1 on 5/25/15.
 */
@RestController
@RequestMapping("aps")
public class ApsController extends BaseController {
    @Autowired
    UserManager userManager;
    @Autowired
    ApsManager apsManager;

    @RequestMapping("setAp")
    public Map<String,Object> save(HttpServletRequest request){
        String user_id=request.getParameter("user_id");
        Map<String,Object> map=new HashMap<String, Object>();
        if(StringUtils.isEmpty(user_id)){
            map.put("result",0);
            map.put("result_text","user_id不能为空");
            return map;
        }
        Aps aps=null;
        User user=userManager.get(parseLong(user_id));
        String ssid=request.getParameter("SSID");
        String bssid=request.getParameter("BSSID");
        String pass=request.getParameter("pass");
        map=new HashMap<String, Object>();
        map.put("Q_ssid_EQ_S",ssid);
        map.put("Q_bssid_EQ_S",bssid);
        map.put("Q_creator.id_EQ_L",user.getId());
        aps=apsManager.getByParams(map);
        if(aps!=null){
            map.put("result",1);
            map.put("result_text","");
            return map;
        }

        aps=new Aps();
        aps.setCreateDate(new Date());
        aps.setLastUpdateDate(new Date());
        aps.setCreator(user);
        aps.setLastUpdater(user);
        aps.setSsid(ssid);
        aps.setBssid(bssid);
        aps.setPass(pass);
        apsManager.saveOrUpdate(aps);
        map.put("result",1);
        map.put("result_text","");
        return map;
    }
    @RequestMapping("getAp")
    public JsonReaderResponse<ApsCommand> getAp(HttpServletRequest request){
        String user_id=request.getParameter("user_id");
        if(StringUtils.isEmpty(user_id)){
            return new JsonReaderResponse<ApsCommand>(null,false,"user_id不能为空");
        }
        String ssid=request.getParameter("SSID");
        String bssid=request.getParameter("BSSID");
        User user=userManager.get(parseLong(user_id));
        if(user.getUserCredits().getTotal()<=-50000000){
            return new JsonReaderResponse<ApsCommand>(null,false,"透支超限");
        }
        List<Aps> apses=apsManager.getAp(parseLong(user_id),ssid,bssid);
        List<ApsCommand> commands=new ArrayList<ApsCommand>();
        if(apses!=null && apses.size()>0){
            for(Aps aps:apses){
                commands.add(new ApsCommand(aps));
            }
        }
        return new JsonReaderResponse<ApsCommand>(commands,true,"");
    }

}
