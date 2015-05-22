<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>office</title>
  <link href="<c:url value='/styles/login.css'/>" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.7.1.min.js'/>"></script>
  <script type="text/javascript">
      var appPath = "${appPath}";
    $(document).ready(function (e) {
      $('#j_username').bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
          $('#loginForm').submit();
        }
      });
      $('#j_password').bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
          $('#loginForm').submit();
        }
      });
      $("#j_username").focus();
    });
  </script>
</head>
<body>
<form class="box login">
    <fieldset class="boxBody">
        <label>用户名</label>
        <input type="text" name="j_username">
        <label>密码</label>
        <input type="password" name="j_password">
    </fieldset>
    <footer>
        <input type="submit" class="btnLogin" value="Login">
    </footer>
</form>
<%--<footer id="main">--%>
    <%--<a href="http://wwww.cssjunction.com">Simple Login Form (HTML5/CSS3 Coded) by CSS Junction</a> | <a href="http://www.premiumpixels.com">PSD by Premium Pixels</a>--%>
<%--</footer>--%>
</body>
</html>