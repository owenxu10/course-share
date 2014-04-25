<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>

<%   String path = request.getContextPath(); 
	 LibType libType = (LibType) request.getAttribute("libType");
%>

 	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	 <script src="<%=path %>/js/lib/jquery.js"></script>
	 <script type="text/javascript" src="<%=path %>/js/jq-image.js"></script>

<jsp:include page="layout-header.jsp" flush="true" />

<div id="center-frame" class="row cs-frame-default">
  <div id="cs-image-navbar">
    <div id="image-searchbar" class="input-group input-group-lg">
      <input type="text" id="filter-content" class="form-control" placeholder="输入资源关键词">
    </div>
    <button type="button" id="image-search" class="btn btn-lg btn-primary">
      <span class="glyphicon glyphicon-search"></span> 搜索资源
    </button>
    
     <button type="button" id="image-upload" class="btn btn-success btn-lg" data-toggle="modal">
     <span class="glyphicon glyphicon-upload"></span> 上传资源
    </button>
  </div>
</div> <!-- #problemset-header -->





<jsp:include page="layout-footer.jsp" flush="true" />