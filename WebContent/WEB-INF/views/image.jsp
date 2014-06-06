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
  
  
<!-- Modal -->
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
     <!-- form of uploading problem -->
		<form role="form" id="uploadForm" " method="POST" enctype="multipart/form-data" >
      <div class="modal-header">
        <button type="button" id="ps-uploadClose" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="uploadModalLabel">上传题目</h4>
      </div>
      <div class="modal-body">
		  
	    <label >题型</label>
	    <div>
		 <%
		  String typediffs[][] = {
		    {"concept",  "cs-frame-grey", "概念题"},
		    {"blankfill", "cs-frame-blue", "填空题"},
		    {"choice",  "cs-frame-green", "选择题"},
		    {"question",  "cs-frame-yellow", "问答题"},
		    {"integrate", "cs-frame-red", "综合题"}
		  };
		  for(String[] d : typediffs) { %>
		   <label class="radio-inline">
		      <input type="radio" name="typeRadio"  value="<%=d[2] %>" id="<%=d[0] %>" >
		      <span class="btn cs-radio-text <%=d[1] %>"><%=d[2] %></span>
		   </label>
 		 <% } %>
		</div>
	    <br/>
		<label >难度</label>
		<div>
		 <%
		  String uploaddiffs[][] = {
		    {"btn-primary", "1", "1级"},
		    {"btn-info", "2", "2级"},
		    {"btn-success", "3", "3级"},
		    {"btn-warning", "4", "4级"},
		    {"btn-danger", "5", "5级"}
		  };
		  for(String[] d : uploaddiffs) { %>
		   <label class="radio-inline">
		      <input type="radio" name="diffRadio"  value="<%=d[1] %>" id="<%=d[1] %>">
		      <span class="btn cs-radio-text <%=d[0] %>"><%=d[2] %></span>
		   </label>
 		 <% } %>
		 </div>
		<br/>
		 <div class="form-group">
		    <label >知识点</label>
		    <input type="text" class="form-control" id="inputknowledge" name="inputknowledge" placeholder="知识点...">
		  </div>
		  
		 <div class="form-group">
		    <label >题目内容</label>
		    <textarea class="form-control" id="problemContent" name="problemContent" rows="5" placeholder="输入题目内容..."></textarea>
		  </div>
		  
		  <hr>
		  
		  <label >答案信息</label>
		  
		  <div>
		   <label class="checkbox-inline">
		      <input type="checkbox" name="keyCheckbox"  value="text" id="textkey"> 
		      <span class="cs-typecheckbox-text">文字类型</span>
		   </label>
		   <label class="checkbox-inline">
		      <input type="checkbox" name="keyCheckbox"  value="pic" id="filekey"> 
		      <span class="cs-typecheckbox-text ">图片类型</span>
		   </label>
		 </div>
		 
		 <br/>
		 
		  <div class="form-group ps-hidden" id="textareakey">
		    <label for="exampleInputEmail1">答案内容</label>
		    <textarea class="form-control" name="keyContent" id="keyContent" rows="5" placeholder="输入答案内容..."></textarea>
		  </div>
		
		  <div class="form-group ps-hidden"  id="uploadkey" >
		    <label for="exampleInputFile" >答案文件</label>
		    <input type="file" id="uploadFile" name="uploadFile"  accept="image/*|video/*" required>
		    <p class="help-block">请上传包含答案的文件</p>
		  </div> 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id='problemUpload'>上传</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

  
  
  
  
  <div id="<%=cssfooter%>">
    <p>Copyright Tongji University 2013</p>
  </div>