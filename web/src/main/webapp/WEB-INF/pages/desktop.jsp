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
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ozstrategy/css/flexcenter.css'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/ext/ext-all.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/jscripts/desktopRes.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/jscripts/jscriptRes.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/ext/locale/ext-lang-${language}.js'/>"></script>
    <script type="text/javascript" src='<c:url value="/scripts/ozstrategy/global.js"/>'></script>

  <script type="text/javascript">
    var theme = Ext.util.Cookies.get('FlexCenter_Ext_Theme');
    if(theme!='undefined'){
        Ext.util.CSS.swapStyleSheet("FlexCenter_Ext_Theme", extTheme + theme + ".css");
    }
    Ext.Loader.setConfig({
          enabled: true,
          basePath: '<c:url value="/scripts/ext/src"/>',
          disableCaching: true
        }
    );

    Ext.Loader.setPath({
      'Ext.ux': '<c:url value="/scripts/ux/Ext/ux"/>',
      FlexCenter: '<c:url value="/scripts/ozstrategy"/>'
    });

  </script>

  <script type="text/javascript">
    var apps = {};
    Ext.require([
      'FlexCenter.App',
      'FlexCenter.Constants',
      'Ext.data.ArrayStore',
      'Ext.util.CSS'
    ]);

    var flexCenterApp;
    var treeRegister;
    var surveyRegister;
    Ext.onReady(function () {
      flexCenterApp = new FlexCenter.App();
      var oDiv = document.getElementById('loading');
      oDiv.style.display = "none";
      for (var i = 0; i < oDiv.childNodes.length; i++)
        oDiv.removeChild(oDiv.childNodes[i]);

      //perform when press backspace on desktop
      function doKey(e) {
        var ev = e || window.event;//bet event obj
        var obj = ev.target || ev.srcElement;//get event source
        var t = obj.type || obj.getAttribute('type');//get event soruce type

        if (ev.keyCode == 8 && t == null) {
          return false;
        }
//          else if(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea"){
//              return false;
//          }
      }

      if (Ext.isIE) {
        //in IE,Chrome
        document.onkeydown = doKey;
      } else {
        //in Firefox,Opera
        document.onkeypress = doKey;
      }
    });
  </script>

</head>
<body>
<div id="loading" class="loading">
    <div class="ozMiddleIcon"></div>
</div>
<div id="tmpForm"></div>
</body>
</html>