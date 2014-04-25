<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
  String path = request.getContextPath();
 
  Cookie cookies[] = request.getCookies() ;
  Cookie c1 = null ;
  if(cookies != null){
	  if(request.getSession().getAttribute("logout")==null){
		  //have cookie but no session logout ---first open
	      for(int i=0;i<cookies.length;i++){
	         c1 = cookies[i] ;
	         if(c1.getName().equals("username")) 
	        	 request.getSession().setAttribute("username",c1.getValue());
	      }
	      response.sendRedirect(path+"/problemset"); 
	  }
 }
  

  
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
	  
	  
	 
</head>
  <body>
  <div class="container">
	  <div id="cs-header" class="navbar navbar-inverse navbar-fixed-top">
	    <div class="container">
	      <div class="navbar-header">
	        <div id="cs-header-brand" class="navbar-brand">
	          <span id="cs-header-title">计算机系统结构资源 </span>
	          <br>
	          <span id="cs-header-subtitle">同济大学软件学院系统结构课程组开发</span>
	        </div>
	      </div>
	    
	      </div> <!-- #cs-header-collapse -->
	    </div> <!-- .container -->
	  </div> <!-- #cs-header -->
<div id="loginForm" >

    <div id="login-wraper" class="login-form"">
         <form class="form">
             <legend>登陆计算机系统结构资源 </legend>
             <div class="body">
                 <label>用户名</label>
                 <input type="text" id="loginusername" class="form-control">
                 
                 <label>密码</label>
                 <input type="password" id="loginpassword" class="form-control">
                 <br/>
                 <div  class="errortext ps-hidden" id="logintext">*用户名或密码错误</div>
                  <br/>
             </div>
          </form> 
         
             <div class="footer">
                 <label class="checkbox inline">
                     <input type="checkbox" id="rememberMe" name="rememberMe" value="rememeber">记住我
                 </label>
                           
                 <button id="login" class="btn btn-success">登陆</button>
             </div>         
         
  	 </div>
       


	   <footer class="white navbar-fixed-bottom">
	     还没有注册用户？ <button id="toRegister" class="btn btn-info">注册</button>
	   </footer>
   
 </div>
 
 <div id="registerForm"  class="ps-hidden register-form">
 	    <div id="register-wraper" >
         <form class="form">
             <legend>注册计算机系统结构资源 </legend>
             <div class="body">
                 <label >用户名</label>
                 <input type="text" class="form-control" id="registerusername">
                 
                 <label >密码</label>
                 <input type="password" class="form-control" id="registerpassword">
                 
                 <label >邮箱</label>
                 <input type="email" class="form-control" id="registeremail">
                 <br/>
                 <div class="errortext  ps-hidden" id="registertext">*该用户名已存在</div>
                  <br/>
             </div>
           </form>
             <div class="footer">
                 <button id="register" class=" btn btn-info ">注册</button>
             </div>
         
       
         
          <footer class="white navbar-fixed-bottom">
	   	  还没有注册用户？ <button id="toLogin" class="btn btn-success">登录</button>
	      </footer>
  	 </div>
</div>
     
  </body>
</html>