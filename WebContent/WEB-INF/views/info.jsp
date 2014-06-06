<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%

String path = request.getContextPath();
String pageTitle = "计算机系统结构资源 ";
String devName = "同济大学软件学院系统结构课程组开发";

String username = (String)request.getSession().getAttribute("username");
String email= (String) request.getAttribute("email");
String password= (String) request.getAttribute("password");
Integer userid = (Integer) request.getSession().getAttribute("id");
  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>计算机系统结构资源</title>

	<link href="<%=path %>/css/layout.css" rel="stylesheet">
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
  	<link href="<%=path %>/css/jquery.qtip.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=path %>/css/user.css">
    
	 <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	 <script src="<%=path %>/js/lib/jquery.js"></script>
	 <!-- Include all compiled plugins (below), or include individual files as needed -->
	 <script src="<%=path %>/js/lib/bootstrap.min.js"></script>
     <script language="javascript" src="<%=path %>/js/user.js"></script>
	 <script language="javascript" src="<%=path %>/js/header.js"></script>
	   
	 <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
  	 <link href="<%=path %>/css/layout.css" rel="stylesheet">
	 
</head>
  <body>
 <div id="cs-header" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <div id="cs-header-brand" class="navbar-brand">
          <span id="cs-header-title"><%=pageTitle %></span>
          <br>
          <span id="cs-header-subtitle"><%=devName %></span>
        </div>
      </div>
      <div class="collapse navbar-collapse navbar-ex1-collapse pull-right">
        <ul id="cs-header-nav" class="nav navbar-nav">
           <li>
            <a href="<%=path%>/problemset">
              <span class="glyphicon glyphicon-book"></span> 题库
            </a>
          </li>
          
          <li>
            <a href="<%=path%>/subject">
              <span class="glyphicon glyphicon-list-alt"></span> 专题库
            </a>
          </li>
          
          
          
           <li >
            <a href="<%=path%>/image">
              <span class="glyphicon glyphicon-inbox"></span>资源库
            </a>
          </li>
          

          <li class="dropdown active">
	          <a class="dropdown-toggle" data-toggle="dropdown"><span class=" glyphicon glyphicon-user"></span> <%=username %> <b class="caret"></b></a>
	          <ul  id ="drowline" class="dropdown-menu  inverse-dropdown">
	            <li>
	            	<a id="modifyInfo" >修改信息</a>
	            </li>
            	<li class="divider"></li>
	            <li>
	            	<a  id="logout" >登出</a>
	            </li>
	          </ul>
	        </li>
          
         
        </ul> <!-- #cs-header-nav -->
      </div> <!-- #cs-header-collapse -->
    </div> <!-- .container -->
  </div> <!-- #cs-header -->
  
 <div id="infoForm"  class="register-form">
 	    <div id="info-wraper" >
 	    	<div id ="modifyform"  class="ps-hidden">
	         <form class="form">
	             <legend>用户信息</legend>
	             <div  class="body">
	            	 <label >用户ID</label> 
	             	 <input type="text" class="form-control" id="userid" value="<%= userid%>" readonly>
	             	 
	                 <label >用户名</label> 
	                 <input type="text" class="form-control" id="modifyusername" value="<%= username%>" readonly>
	                 
	                 <label >密码</label>       
	                 <input type="password" class="form-control" id="modifypassword" value="<%= password%>">
	                  
	                 <label >邮箱</label>  
	                 <input type="email" class="form-control" id="modifyemail" value="<%= email%>">
	                  <br/>
	             </div>
	           </form>
	             <div class="footer">
	                 <button id="sureModify" class=" btn btn-success ">确定修改</button>
	             </div>
       	  </div> 
       	  
       	  
       	  
       	  <div id ="infoform"  class="">
	         <form class="form">
	             <legend>用户信息</legend>
	             <div  class="body">
	             	 <label >用户ID</label> 
	             	 <input type="text" class="form-control" value="<%= userid%>" readonly>
	             	 
	                 <label >用户名</label> 
	                 <input type="text" class="form-control" value="<%= username%>" readonly>
	                               
	                 <label >密码</label>       
	                 <input type="password" class="form-control"  value="<%= password%>" readonly>
	                 
	                 <label >邮箱</label>  
	                 <input type="text" class="form-control" value="<%= email%>" readonly>
	                  <br/>
	             </div>
	           </form>
	             <div class="footer">
	                 <button id="canModify" class=" btn btn-danger ">修改信息</button>
	             </div>
       	  </div> 
  	 </div>
</div>



     
  </body>
</html>