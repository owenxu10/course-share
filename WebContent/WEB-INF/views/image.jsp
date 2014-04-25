<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>

<%   String path = request.getContextPath(); 
	 LibType libType = (LibType) request.getAttribute("libType");
%>


<script type="text/javascript" src="<%=path %>/js/image.js"></script>

<jsp:include page="layout-header.jsp" flush="true" />

<div id="center-frame" class="row cs-frame-default">
  <div id="cs-image-navbar">
    <div id="image-searchbar" class="input-group input-group-lg">
      <span class="input-group-addon">输入资源关键词</span>
      <input type="text" id="filter-content" class="form-control" placeholder="关键词">
    </div>
    <button type="button" id="image-search" class="btn btn-lg btn-primary">
      <span class="glyphicon glyphicon-search"></span> 搜索资源
    </button>
  </div>
</div> <!-- #problemset-header -->



<jsp:include page="layout-footer.jsp" flush="true" />