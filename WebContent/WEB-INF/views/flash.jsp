<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<% String path = request.getContextPath();System.out.println(path);%>

<jsp:include page="layout.jsp" flush="true" />

<script type="text/javascript" src="<%=path %>/js/lib/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/flash.js"></script>