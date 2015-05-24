<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<%
    String language = response.getLocale().toString();
    if ("en_US".equalsIgnoreCase(language)) {
        language = "en";
    }
%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="webapp.name"/></title>
    <fmt:setLocale value="zh_CN" scope="session"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext/packages/ext-theme-crisp/build/resources/ext-theme-crisp-all.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/app/css/execDashboard-all.css'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/ext/ext-all.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/jscripts/desktopRes.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/jscripts/jscriptRes.js"/>"></script>
    <%--<script type="text/javascript" src="<c:url value='/scripts/ext/locale/ext-lang-${language}.js'/>"></script>--%>
    <script type="text/javascript" src='<c:url value="/scripts/app/global.js"/>'></script>
    <c:set var="language"><%=language %></c:set>
   <script type="text/javascript" src='<c:url value="/demo/demo.js"/>'></script>


</head>
<body>
<div id="loading" class="loading">
    <div class="ozMiddleIcon"></div>
</div>
<div id="tmpForm"></div>
<script type="text/javascript">
    Ext.Loader.setConfig({
                enabled: true,
                disableCaching: true
            }
    );
    Ext.Loader.setPath({
        'Ext.ux': '<c:url value="/demo"/>',
        'App': '<c:url value="/demo"/>'
    });
    Ext.require([
        'App.Application'
    ]);
    Ext.application({
        name: 'app',
        extend: 'App.Application',
        launch:function(){
            var oDiv = document.getElementById('loading');
            oDiv.style.display = "none";
            for (var i = 0; i < oDiv.childNodes.length; i++)
                oDiv.removeChild(oDiv.childNodes[i]);
        }
    });
</script>
</body>
</html>