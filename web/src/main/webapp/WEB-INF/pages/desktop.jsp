<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<%
    String language = response.getLocale().toString();
    if ("en_US".equalsIgnoreCase(language)) {
        language = "en";
    }
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="language"><%=language %></c:set>
    <fmt:setLocale value="zh_CN" scope="session"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext/packages/ext-theme-crisp/build/resources/ext-theme-crisp-all.css'/>"/>
    <%--<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext/packages/sencha-charts/build/crisp/resources/sencha-charts-all.css'/>"/>--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/app/css/execDashboard-all.css'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/ext/ext-all.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/ext/packages/ext-charts/build/ext-charts.js'/>"></script>
    <%--<script type="text/javascript" src="<c:url value='/scripts/ext/packages/sencha-charts/build/sencha-charts.js'/>"></script>--%>
    <script type="text/javascript" src="<c:url value="/jscripts/desktopRes.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/jscripts/jscriptRes.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/ext/packages/ext-locale/build/ext-locale-zh_CN.js'/>"></script>



  <script type="text/javascript">
      var basePath='<c:url value="/"/>';
    Ext.Loader.setConfig({
                enabled: true,
                disableCaching: true
            }
    );
    Ext.Loader.setPath({
        'Lzs.ux': '<c:url value="/scripts/ux/app"/>',
        App: '<c:url value="/scripts/app"/>'
    });

  </script>
    <script type="text/javascript" src='<c:url value="/scripts/global.js"/>'></script>

</head>
<body>
<div id="loading" class="loading">
    <div class="ozMiddleIcon"></div>
</div>
<div id="tmpForm"></div>

<script type="text/javascript">
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