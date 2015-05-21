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
    <c:set var="language"><%=language %></c:set>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext/packages/ext-theme-crisp/build/resources/ext-theme-crisp-all.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/app/css/flexcenter.css'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/ext/ext-all.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/ext/packages/ext-locale/build/ext-locale-${language}.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/desktopRes.js"/>"></script>


    <script type="text/javascript">

        Ext.Loader.setConfig({
                    enabled: true,
                    disableCaching: true
                }
        );
        Ext.Loader.setPath({
            App: '<c:url value="/demo"/>'
        });
        var accessFeature = function(tableId,fieldId){

            for(var i=0;i<accesses.length;i++){
                if((accesses[i].tableId==tableId) && (accesses[i].fieldId==fieldId)){
                    return accesses[i];
                }
            }
            return {};
        };
        var accessTableFeature = function(tableId,roleId){
            for(var i=0;i<tableAccesses.length;i++){
                if((tableAccesses[i].tableId==tableId) && (tableAccesses[i].roleId==roleId)){
                    return tableAccesses[i];
                }
            }
            return {};
        };

    </script>


  <script type="text/javascript" src='<c:url value="/demo/demo.js"/>'></script>


</head>
<body>
<div id="loading" class="loading">
    <div class="ozMiddleIcon"></div>
</div>
<div id="tmpForm"></div>
<style type="text/css" >
    .x-message-box .ext-mb-loading {
        background: url("<c:url value="/scripts/ext/packages/ext-theme-crisp/build/resources/images/grid/loading.gif"/>") no-repeat scroll 6px 0 transparent;
        height: 52px !important;
    }
</style>
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

            function doKey(e) {
                var ev = e || window.event;//bet event obj
                var obj = ev.target || ev.srcElement;//get event source
                var t = obj.type || obj.getAttribute('type');//get event soruce type

                if (ev.keyCode == 8 && t == null) {
                    return false;
                }
            }

            if (Ext.isIE) {
                document.onkeydown = doKey;
            } else {
                document.onkeypress = doKey;
            }
        }
    });
</script>
</body>
</html>