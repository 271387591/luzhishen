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
      'Ext.ux': '<c:url value="/scripts/ux/Ext/ux"/>',
      App: '<c:url value="/scripts/app"/>'
    });

    var accessFeature = function(tableId,fieldId){
        for(var i=0;i<accesses.length;i++){
            if((accesses[i].tableId==tableId) && (accesses[i].fieldId==fieldId)){
                return accesses[i];
            }
        }
        return {
            canUpdate:false,
            canAdd:false,
            canQuery:false
        };
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
</head>
<body>
<script type="text/javascript">
    Ext.require([
        'App.Application'
    ]);
    Ext.application({
        name: 'app',
        extend: 'App.Application'
    });
</script>

</body>
</html>