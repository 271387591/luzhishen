package com.ozstrategy.webapp.controller.comments;

import com.ozstrategy.model.comments.Comments;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.comments.CommentsManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihao1 on 5/25/15.
 */
@RestController
@RequestMapping("comments")
public class CommentsController extends BaseController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private CommentsManager commentsManager;

    @RequestMapping("save")
    public Map<String,Object> save(HttpServletRequest request){
        String username=request.getRemoteUser();
        Map<String,Object> map=new HashMap<String, Object>();
        if(StringUtils.isEmpty(username)){
            map.put("result",0);
            map.put("result_text","登录超时");
            return map;
        }
        String commentsStr=request.getParameter("comments");
        String contract=request.getParameter("contract");
        User user=userManager.getUserByUsername(username);
        Comments comments=new Comments();
        comments.setCreateDate(new Date());
        comments.setLastUpdateDate(new Date());
        comments.setCreator(user);
        comments.setLastUpdater(user);
        comments.setComments(commentsStr);
        comments.setContract(contract);
        commentsManager.saveOrUpdate(comments);
        map.put("result",1);
        map.put("result_text","");
        return map;
    }
}
