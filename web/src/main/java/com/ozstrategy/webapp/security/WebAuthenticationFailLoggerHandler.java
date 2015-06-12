package com.ozstrategy.webapp.security;


import com.ozstrategy.Constants;
import com.ozstrategy.webapp.command.BaseResultCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihao on 7/1/14.
 */
public class WebAuthenticationFailLoggerHandler extends WebAuthenticationLoggerHandler implements AuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String platform=request.getParameter("platform");
        if(StringUtils.equals(PLATFORM, platform)){
            response.sendRedirect("login?error=true");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if(StringUtils.equals(request.getMethod(), "GET")){
            BaseResultCommand command=new BaseResultCommand("请求方式错误，不支持GET请求",false);
            response.getWriter().print(objectMapper.writeValueAsString(command));
            return;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("result",0);
        map.put("result_text","");
        String result=objectMapper.writeValueAsString(map);
        response.getWriter().print(result);
        return;
        
    }
}
