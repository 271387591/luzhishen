<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>office</title>
  <link href="<c:url value='/styles/login.css'/>" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.7.1.min.js'/>"></script>

</head>
<body>
<form class="box login" id="loginForm" action="j_security_check" method="POST">
    <input type="hidden" value="PC" name="platform">
    <fieldset class="boxBody">
        <label>用户名</label>
        <input type="text" name="username">
        <label>密码</label>
        <input type="password" name="password">
    </fieldset>
    <footer>
        <c:if test="${param.error=='true'}">
            <label style="color: #ff0000;"><fmt:message key="errors.password.mismatch"/> </label>
        </c:if>
        <input type="submit" class="btnLogin" value="登录">
    </footer>
</form>
<%--<footer id="main">--%>
    <%--<a href="http://wwww.cssjunction.com">Simple Login Form (HTML5/CSS3 Coded) by CSS Junction</a> | <a href="http://www.premiumpixels.com">PSD by Premium Pixels</a>--%>
<%--</footer>--%>
<script type="text/javascript">
    $(document).ready(function (e) {
        $('input[name=username]').bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                $('#loginForm').submit();
            }
        });
        $('input[name=password]').bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                $('#loginForm').submit();
            }
        });
        $("#j_username").focus();
    });
</script>
</body>
</html>