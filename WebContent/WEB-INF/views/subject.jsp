<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List" %>
<%@ page import="org.tjsse.courseshare.bean.Subject" %>
<%@ page import="org.tjsse.courseshare.bean.Theme" %>
<% String path = request.getContextPath(); 
List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");

List<Theme> themes = (List<Theme>) request.getAttribute("themes");
%>

<jsp:include page="layout-header.jsp" flush="true" />

<script type="text/javascript" src="<%=path %>/js/subject.js"></script>

<br/>


<div class="jumbotron">
  <div class="container">
  <h1>计算机系统结构</h1>
  <p>本课程是计算机专业一门重要的专业课，它是在学生学习完主要的软硬件基础课程之后，让学生从整体系统、总体设计的角度来理解和研究计算机系统，学习如何根据各种实际应用的需要，综合考虑软硬件，设计和构建合理的计算机系统结构。本课程的目标是提高学生从总体结构、系统分析这一层次来研究和分析计算机系统的能力，帮助学生建立整机系统的概念；使学生掌握计算机系统结构的基本概念、基本原理、基本结构。</p>
  </div>
</div>

<div id="show-page" >
	<div id="east-frame">
		<div class="col-xs-6 col-sm-3 sidebar">
	          <div class="list-group">
	            
	            <a href="#" class="list-group-item active">专题分类</a>
	          <div id="show-theme-list">
	          <% for (Theme t : themes) {%>
	            <a class="list-group-item"  name="showlist" title="<%=t.gettheme_id()%>"><%=t.getName() %></a>
	            <%} %>
	          </div>
	          </div>
	          
	           <div class="sidebutton">
			    <button type="button" class="btn btn-default btn-lg" id="toManage">
			 	  <span class="glyphicon glyphicon-cog"></span>管理文章 
				</button>
				</div>	
	    </div>
	   
	</div>
	
	<div id="subject-center-frame">
	<% for (Subject s : subjects) {%>
	<div class="s-row">
	  <h4>
		<%=s.getTitle() %>
	  </h4>
	  <p>
		<%=s.getDescription() %>
	  </p>    
	  <div class="ps-action">   
	    <a class="btn btn-default" href="<%=s.getUrl() %>" target="_blank">原文链接</a>
	  </div>
	</div>
	<%}%>
	</div>
	
</div>


<div id="mamage-page" class="ps-hidden">
	<div id="manage-menu">
		<ul class="nav nav-tabs nav-justified">
		  <li id="li-menu-t" class="active"><a title="totheme" name="manage-menu" >专题管理</a></li>
		  <li id="li-menu-s" ><a title="tosubject" name="manage-menu" >文章管理</a></li>
		</ul>
	</div>
	
	<br/>
	
	<div id="manage-theme" >
		<div class="btn-group">
		  <button class="btn btn-default" type="button" id="add-theme">增加</button>
		  
		</div>
		
		
		<div id="insert-theme" class="ps-hidden">
				 <div class="form-group">
				    <label >专题名称</label>
				    <input type="text" class="form-control" id="t-title" name="title" placeholder="专题名称...">
				  </div>
				 
			    <div class="modal-footer">
			      <button type="button" class="btn btn-primary" id='themeUpload'>上传</button>
			       <button type="button" class="btn btn-danger" id='themeUploadcancel'>取消</button>
			    </div>
			    
  	</div>
		<div id="theme-list">
		<% for (Theme t : themes) {%>
		<div class="m-row">
		 <div class="ps-col">   
		     <h4>
			<%=t.getName() %>
		  </h4>
		  </div>
		  <div class="ps-col ps-action">   
		    <a class="btn btn-default" name="delete" title=<%=t.gettheme_id()%>>删除</a>
		  </div>
		</div>
		<%}%>
		</div>
		
		
	  
	</div>
	
	
	<div id="manage-subject"  class="ps-hidden">
	
		<div id="west-frame">
			<div class="col-xs-6 col-sm-3 sidebar">
		          <div class="list-group" id="manage-theme-list">
		            
		          <% for (Theme t : themes) {%>
		            <a class="list-group-item" name="modify-order" title=<%=t.gettheme_id()%>><%=t.getName() %></a>
		            <%} %>
		          </div>
		    </div>
		   
		</div>
	
	
		<div >
		  <button class="btn btn-default manage-button" type="button" id="add-button">增加</button>
		   <button class="btn btn-default ps-hidden manage-button" type="button" id="add-cancel">取消</button>
		   <button class="btn btn-default" type="button" id="manager-button-order">调整顺序</button>
		   <button class="btn btn-default manage-button ps-hidden" type="button" id="order-ok">确定</button>
		   <button class="btn btn-default ps-hidden" type="button" id="order-cancel">取消</button>
		   
		</div>
		
	
		<div id="subject-manage-center-frame" >
			<div id="add-subject" class="ps-hidden">
				<div class="s-row">
					
			      	<div class="modal-body">
					  
				    
					 <div class="form-group">
					    <label >题目</label>
					    <input type="text" class="form-control" id="s-title" name="title" placeholder="题目...">
					  </div>
					  
					 <div class="form-group">
					    <label >简介</label>
					    <input type="text" class="form-control" id="s-description" name="description" placeholder="简介...">
					 </div>

					 <div class="form-group">
					    <label >URL</label>
					    <input type="text" class="form-control" id="s-url" name="url" placeholder="URL...">
					 </div>
					 
				    <div class="modal-footer">
				      <button type="button" class="btn btn-primary" id='subjectUpload'>上传</button>
				    </div>
				    
				    </div>
			  </div>
			</div>
			
			<div id="subject-list">
			
			</div>
		
			<div id="subject-adjust-order"  class="ps-hidden ">
			<ol id="order-adjust-list" class='subject-adjust-order-list vertical'>
				<% for (Subject s : subjects) {%>
				<li>
				<div class="s-row thumbnail">
				<div class="ps-hidden"> <%=s.getSubject_id()%></div>
				  <h4>
					<%=s.getTitle() %>
				  </h4>
				  <p>
					<%=s.getDescription() %>
				  </p>    
				</div>
				</li>
				<%}%>
			</ol>
			</div>
			
			
		
		</div>
		<br/>
		
		
	</div>
	
	
</div>

<script type="text/template" id="subject-tpl">
<div class="s-row">
	<h4>
		<@=title @>
	</h4>
	<p>
		<@=description @>
	</p>    				 
	<div class="ps-action">   				    
		<button class="btn btn-default" type="button" id="<@=id @>">删除</button>			  
	</div>				
</div>

</script>

<script type="text/template" id="subject-show-tpl">
	<div class="s-row">
	  <h4>
		<@=title @>
	  </h4>
	  <p>
		<@=description @>
	  </p>    
	  <div class="ps-action">   
	    <a class="btn btn-default" href=<@=url @> target="_blank">原文链接</a>
	  </div>
	</div>

</script>



<script type="text/template" id="theme-tpl">
	<a class="list-group-item" name="modify-order" title=<@=themeid @> ><@=name @></a>
</script>

<script type="text/template" id="theme-manage-tpl">
		<div class="m-row">
		 
		 <div class="ps-col">   
		     <h4>
			<@=name @>
		  </h4>
		  </div>
		  <div class="ps-col ps-action">   
		    <a class="btn btn-default" name="delete" title=<@=themeid @>>删除</a>
		  </div>
		</div>
</script>
		
<script type="text/template" id="subject-adjust-tpl">
		<li>
		<div class="s-row thumbnail">
		<div class="ps-hidden"> <@=id @></div>
		  <h4>
			<@=title @>
		  </h4>
		  <p>
			<@=description @>
		  </p>    
		</div>
		</li>
</script>



<jsp:include page="layout-footer.jsp" flush="true" />