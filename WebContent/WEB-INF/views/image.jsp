<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>

<%   String path = request.getContextPath(); 
	 LibType libType = (LibType) request.getAttribute("libType");
%>



<jsp:include page="layout-header.jsp" flush="true" />

<script type="text/javascript" src="<%=path %>/js/image.js"></script>
<jsp:include page="layout-footer.jsp" flush="true" />