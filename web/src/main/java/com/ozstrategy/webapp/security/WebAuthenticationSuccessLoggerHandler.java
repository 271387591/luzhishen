package com.ozstrategy.webapp.security;

import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.userrole.FeatureManager;
import com.ozstrategy.webapp.command.JsonReaderSingleResponse;
import com.ozstrategy.webapp.command.login.LoginCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: liuqian
 * Date: 13-7-3
 * Time: PM3:07
 * To change this template use File | Settings | File Templates.
 */
public class WebAuthenticationSuccessLoggerHandler extends WebAuthenticationLoggerHandler implements AuthenticationSuccessHandler {

  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html;charset=UTF-8");
      String platform=request.getParameter("platform");
      if(StringUtils.equals(PLATFORM, platform)){
          response.sendRedirect("dispatcherPage.action");
          return;
      }
      User user = (User)authentication.getPrincipal();
      user  = userManager.getUserByUsername(user.getUsername());
      if (user == null) {
        return;
      }
      Map<String,Object> map=new HashMap<String, Object>();
      map.put("result",1);
      map.put("result_text","");
      Map<String,Object> data=new HashMap<String, Object>();
      data.put("user_id",user.getId());
      data.put("email",user.getEmail());
      data.put("username",user.getUsername());
      data.put("phone",user.getMobile());
      data.put("reg_date", DateFormatUtils.format(user.getCreateDate(),"yyyy-MM-dd HH:mm:ss:"));
      data.put("address",user.getAddress());
      data.put("credits",user.getUserCredits().getTotal());
      data.put("money",user.getUserMoney().getTotal());
      map.put("data",data);
      String result=objectMapper.writeValueAsString(map);
      response.getWriter().print(result);
  }
}
