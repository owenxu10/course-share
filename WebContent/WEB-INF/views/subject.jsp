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

<div id="show-page" class="ps-hidden">
	<div id="east-frame">
		<div class="col-xs-6 col-sm-3 sidebar">
	          <div class="list-group">
	            
	            <a href="#" class="list-group-item active">专题分类</a>
	          <% for (Theme t : themes) {%>
	            <a href="#" class="list-group-item"><%=t.getName() %></a>
	            <%} %>
	          </div>
	          
	           <div class="sidebutton">
			    <button type="button" class="btn btn-default btn-lg ">
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


<div id="mamage">
	<div id="manage-menu">
		<ul class="nav nav-tabs nav-justified">
		  <li class="active"><a href="#">专题管理</a></li>
		  <li ><a href="#">文章管理</a></li>
		</ul>
	</div>
	
	<br/>
	
	<div id="manage-theme"  class="ps-hidden">
		<div class="btn-group">
		  <button class="btn btn-default" type="button">增加</button>
		</div>
		
		<div id="subject-center-frame">
		<% for (Theme t : themes) {%>
		<div class="m-row">
		 
		 <div class="ps-col">   
		     <h4>
			<%=t.getName() %>
		  </h4>
		  </div>
		  <div class="ps-col ps-action">   
		    <a class="btn btn-default" name="delete">删除</a>
		  </div>
		</div>
		<%}%>
		</div>
	</div>
	
	
	<div id="manage-subject">
	
		<div id="west-frame">
			<div class="col-xs-6 col-sm-3 sidebar">
		          <div class="list-group">
		            
		          <% for (Theme t : themes) {%>
		            <a href="#" class="list-group-item"><%=t.getName() %></a>
		            <%} %>
		          </div>
		    </div>
		   
		</div>
	
	
		<div >
		  <button class="btn btn-default" type="button" id="manager-button">增加</button>
		   <button class="btn btn-default" type="button" id="manager-button-order">调整顺序</button>
		</div>
		
	
		<div id="subject-manage-center-frame" >
		
		<div id="subject-list">
		
		    <div id="subject-modify" class="ps-hidden">
				<% for (Subject s : subjects) {%>
				<div class="s-row">
				  <h4>
					<%=s.getTitle() %>
				  </h4>
				  <p>
					<%=s.getDescription() %>
				  </p>    
				  <div class="ps-action">   
				    <button class="btn btn-default" type="button">删除</button>
				  </div>
				</div>
				<%}%>
			</div>
		
		
		
			<div id="subject-adjust-order" class="ps-hidden">
			<ol class='example'>
				<% for (Subject s : subjects) {%>
				<li>
				<div class="s-row">
				  <h4>
					<%=s.getTitle() %>
				  </h4>
				  <p>
					<%=s.getDescription() %>
				  </p>    
				  <div class="ps-action">   
				    <button class="btn btn-default" type="button">删除</button>
				  </div>
				</div>
				</li>
				<%}%>
			</ol>
			</div>
			
			<div>
              <ol class="limited_drop_targets vertical">
                <li class="highlight">Item 1</li>
                
                <li class="highlight">Item 3</li>
                
                <li class="highlight">Item 5</li>
                <li class="">Item 2</li><li class="">Item 4</li><li class="">Item 6</li>
              </ol>
            </div>
			
		</div>
		
		
		</div>
		<br/>
		
		
	</div>
	
	
</div>


<jsp:include page="layout-footer.jsp" flush="true" />