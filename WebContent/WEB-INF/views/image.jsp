<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List,java.util.ArrayList" %>
<%@ page import="org.tjsse.courseshare.bean.Resource" %>

<%   String path = request.getContextPath(); 
   LibType libType = (LibType) request.getAttribute("libType");
   List<Resource> images= new ArrayList<Resource>();
   String cssfooter="image-footer";
   String cssheader="image-frame";
   String keyWord = "";
	int end=-1;
	int start=0;
	if( (String) request.getSession().getAttribute("keyword")!=null)
		 keyWord = (String)request.getSession().getAttribute("keyword");

   if( (List<Resource>) request.getSession().getAttribute("images")!=null){
		
		images = (List<Resource>)  request.getSession().getAttribute("images");
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
      <input type="text" id="image-keyword" class="form-control" placeholder="输入资源关键词" value=<%=keyWord %>>
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
	Resource i0=images.get(start);
	Resource i1=images.get(start+1);
	Resource i2=images.get(start+2);
	Resource i3=images.get(start+3);
%>
	<div class="row">
		
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="<%=i0.getUrl() %>">
	        <img  src='<%=i0.getAddress() %>'  alt="...">
	        <p><%=i0.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i0.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i0.getUserName()%></p> 
	      </div>
	    </div>
		<div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="<%=i1.getUrl() %>">
	        <img  src='<%=i1.getAddress()  %>'  alt="...">
	        <p><%=i1.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i1.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i1.getUserName()%></p> 
	      </div>
	    </div>
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="<%=i2.getUrl() %>">
	        <img  src='<%=i2.getAddress()  %>'  alt="...">
	        <p><%=i2.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i2.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i2.getUserName()%></p> 
	      </div>
	    </div>
	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="<%=i3.getUrl() %>">
	        <img  src='<%=i3.getAddress() %>'  alt="...">
	        <p><%=i3.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=i3.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=i3.getUserName()%></p> 
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
	Resource temp=images.get(i);
 %>

	    <div class="col-xs-6 col-md-3">
	      <div class="thumbnail">
	        <a href="<%=temp.getUrl() %>">
	        <img  src='<%=temp.getAddress() %>'  alt="...">
	        <p><%=temp.getName()%></p>
	      </a>
	       <p class="image-knowledge">知识点：<%=temp.getKnowledge()%></p> 
	      <p class="image-author">上传者：<%=temp.getUserName()%></p> 
	      </div>
	    </div>
	    
	  <% } %>  
</div>	    
 <br>


</div>    

    </div> <!-- #cs-body-content -->
  </div> <!-- #cs-body -->
  
  
<!-- Modal -->
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
     <!-- form of uploading problem -->
		<form role="form" id="uploadForm" " method="POST" enctype="multipart/form-data" >
      <div class="modal-header">
        <button type="button" id="ps-uploadClose" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="uploadModalLabel">上传资源</h4>
      </div>
      <div class="modal-body">
          <div class="form-group">
		    <label >资源名称</label>
			 <input type="text" class="form-control" id="inputname" name="inputname" placeholder="资源名称...">
		 </div>
		 
		 <div class="form-group">
		     <label >知识点</label>
		    <input type="text" class="form-control" id="inputknowledge" name="inputknowledge" placeholder="知识点...">
		  </div>
		  
		  
		  <label >资源类型</label>
		  <div>
		   <label class="radio-inline">
		      <input type="radio" name="keyradio"  value="image" id="imagekey" checked> 
		      <span class="cs-typecheckbox-text">图片类型</span>
		   </label>
		   <label class="radio-inline">
		      <input type="radio" name="keyradio"  value="flash" id="flashkey"> 
		      <span class="cs-typecheckbox-text ">Flash类型</span>
		   </label>
		 </div>
		  <br/>
		  <div class="form-group "  id="uploadImage" >
		    <label for="exampleInputFile" >图片文件</label>
		    <input type="file" id="uploadFile" name="uploadFile"  accept="image/*|video/*" required>
		    <p class="help-block">请上传图片文件</p>
		  </div> 
		  
		  <div class="form-group ps-hidden" id="uploadURL">
		   	<div class="form-group">
		     <label >Flash URL</label>
		   	 <input type="url" class="form-control" id="inputURL" name="inputURL" placeholder="URL..">
		  	</div>
		 </div>
		
		  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id='imageUpload'>上传</button>
        <button type="button" class="btn btn-primary ps-hidden" id='flashUpload'>上传</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

  
<!-- Modal -->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      
      <div class="modal-body">
         <button type="button" class="close" id="closeUploadSuccess" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h4 class="modal-title" id="uploadModalLabel">上传成功</h4>
	  	</div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
  
  
  
  <div id="<%=cssfooter%>">
    <p>Copyright Tongji University 2013</p>
  </div>