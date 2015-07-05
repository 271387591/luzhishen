package com.ozstrategy.webapp.controller.comments;

import com.ozstrategy.model.comments.Comments;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.comments.CommentsManager;
import com.ozstrategy.service.userrole.UserManager;
import com.ozstrategy.webapp.command.BaseResultCommand;
import com.ozstrategy.webapp.command.JsonReaderResponse;
import com.ozstrategy.webapp.command.comments.CommentsCommand;
import com.ozstrategy.webapp.command.credits.CreditsDetailCommand;
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
    @RequestMapping("list")
    public JsonReaderResponse<CommentsCommand> listDetails(HttpServletRequest request) {
        Map<String,Object> map=requestMap(request);
        List<Comments> commentses=commentsManager.listPage(map,obtainStart(request),obtainLimit(request));
        List<CommentsCommand> commentsCommands=new ArrayList<CommentsCommand>();
        if(commentses!=null && commentses.size()>0){
            for(Comments comments:commentses){
                commentsCommands.add(new CommentsCommand(comments));
            }
        }
        Integer count = commentsManager.count(map);
        return new JsonReaderResponse<CommentsCommand>(commentsCommands, true,count,"");
    }
    @RequestMapping("delete")
    public BaseResultCommand delete(HttpServletRequest request) {
        String ids=request.getParameter("ids");
        List<Comments> commentses=new ArrayList<Comments>();
        if(StringUtils.isNotEmpty(ids)){
            String[] ida=ids.split(",");
            for(String id:ida){
                Comments comments=commentsManager.get(parseLong(id));
                commentses.add(comments);
            }

        }
        try{
            commentsManager.batchRemove(commentses);
            return new BaseResultCommand("",Boolean.TRUE);
        }catch (Exception e){
            return new BaseResultCommand("删除失败",Boolean.FALSE);
        }
    }

}
