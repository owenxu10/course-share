<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%
  String path = request.getContextPath();
  LibType libType = (LibType) request.getAttribute("libType");
  String jsName = libType.toString().toLowerCase();
%>  

    </div> <!-- #cs-body-content -->
  </div> <!-- #cs-body -->
  
  <div id="cs-footer">
    <p>Copyright Tongji University 2013</p>
  </div>
  
<!-- Javascript Library -->  
<script src="<%=path %>/js/lib/jquery-1.9.1.min.js"></script>
<script src="<%=path %>/js/lib/bootstrap.min.js"></script>
<script src="<%=path %>/js/lib/underscore-min.js"></script>
<script src="<%=path %>/js/lib/jquery.qtip.min.js"></script>
<script>
var ROOT = '<%=path %>/';

_.templateSettings = {
  interpolate: /\<\@\=(.+?)\@\>/gim,
  evaluate: /\<\@(.+?)\@\>/gim,
  escape: /\<\@\-(.+?)\@\>/gim
};

//document.oncontextmenu=new Function("event.returnValue=false;");
//document.onselectstart=new Function("event.returnValue=false;");
</script>
<script src="<%=path%>/js/lib/scrollpagination.js"></script>
<script src="<%=path%>/js/jq-<%=jsName %>.js"></script> 

</body>
</html>
