<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List,java.util.ArrayList" %>
<%@ page import="org.tjsse.courseshare.bean.Image" %>

<%   String path = request.getContextPath(); 
   LibType libType = (LibType) request.getAttribute("libType");
   List<Image> images= new ArrayList<Image>();
   String cssfooter="image-footer";
   String cssheader="image-frame";
	int end=-1;
	int start=0;
   if( (List<Image>) request.getSession().getAttribute("images")!=null){
		
		images = (List<Image>)  request.getSession().getAttribute("images");
		end = images.size()-1;
		
	     cssfooter="cs-footer";
		 cssheader="center-frame";
		
		if( (String) request.getSession().getAttribute("flag")==null){
			 request.getSession().setAttribute("flag","flag");
			 
			 }
		else{
			request.getSession().removeAttribute("flag");
			request.getSession().removeAttribute("images");
		    }
	}
   
%>

<jsp:include page="layout-header.jsp" flush="true" />

  
	 <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	 <script src="<%=path %>/js/lib/jquery.js"></script>
	 <!-- Include all compiled plugins (below), or include individual files as needed -->
	 <script src="<%=path %>/js/lib/bootstrap.min.js"></script>
     <script type="text/javascript" src="<%=path %>/js/jq-image.js"></script>
   
   
<div id="<%=cssheader%>" class="row cs-frame-default">
  <div id="cs-image-navbar">
    <div id="image-searchbar" class="input-group input-group-lg">
      <input type="text" id="image-keyword" class="form-control" placeholder="输入资源关键词">
    </div>
    <button type="button" id="image-search" class="btn btn-lg btn-primary">
      <span class="glyphicon glyphicon-search"></span> 搜索资源
    </button>
    
     <button type="button" id="image-upload" class="btn btn-success btn-lg" data-toggle="modal">
     <span class="glyphicon glyphicon-upload"></span> 上传资源
    </button>
  </div>
  
</div> <!-- #problemset-header -->


 
<div id="image-content">
<% while(end-start>=3){
	Image i0=images.get(start);
	Image i1=images.get(start+1);
	Image i2=images.get(start+2);
	Image i3=images.get(start+3);
%>
	<div class="row">
		
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="#">
	        <img  src='<%=i0.getUrl() %>'  alt="...">
	        <p><%=i0.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i0.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i0.getUserId()%></p> 
	      </div>
	    </div>
		<div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="#">
	        <img  src='<%=i1.getUrl() %>'  alt="...">
	        <p><%=i1.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i1.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i1.getUserId()%></p> 
	      </div>
	    </div>
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="#">
	        <img  src='<%=i2.getUrl() %>'  alt="...">
	        <p><%=i2.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i2.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i2.getUserId()%></p> 
	      </div>
	    </div>
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="#">
	        <img  src='<%=i3.getUrl() %>'  alt="...">
	        <p><%=i3.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i3.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i3.getUserId()%></p> 
	      </div>
	    </div>
	
	</div>
	<br>
<%
	start=start+4;
} 
%>

<div class="row">

<% for(int i= start; i<=end;i++) { 
 Image temp=images.get(i);
 %>

	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="#">
	        <img  src='<%=temp.getUrl() %>'  alt="...">
	        <p><%=temp.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=temp.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=temp.getUserId()%></p> 
	      </div>
	    </div>
	    
	  <% } %>  
</div>	    
 <br>


</div>    

    </div> <!-- #cs-body-content -->
  </div> <!-- #cs-body -->
  
  <div id="<%=cssfooter%>">
    <p>Copyright Tongji University 2013</p>
  </div>