$(function() {
	
	var ROOT = "/course-share/";
	
	 /**
	   * Hide specific element.
	   */
	  var _hide = function() {
	    for (i in arguments) {
	      $(arguments[i]).css('display', '').addClass('ps-hidden');
	    }
	  };

	  /**
	   * Show specific element.
	   */
	  var _show = function() {
	    for (var i in arguments) {
	      $(arguments[i]).fadeIn().css('display', '').removeClass('ps-hidden');
	    }
	  };

	  $('#toRegister').click(function() {

			 _hide('#loginForm');

			 _show('#registerForm');
	     });
	  
	  
	  $('#toLogin').click(function() {
		  
		  	_hide('#registerForm');

			 _show('#loginForm');
	     });
	  

	  $('#login').click(function() {
		  var loginusername= $("#loginusername").val();

		  var loginpassword= $("#loginpassword").val();
		  
		  var rememberMe;
		  
		 if($("#rememberMe").is(':checked'))
			 rememberMe=true;
		 else
			 rememberMe=false;
			 
		 
		  
		  
			var loginAddress = ROOT + 'user/login';
		 		
		 		$.ajax({
		 			  type: "POST",
		 			  url: loginAddress,
		 			  data: {username:loginusername,
		 				  	 password:loginpassword,
		 				  	 rememberMe:rememberMe},
		 			  success:function(data){
		 				  if(data=="success")
		 					 window.location.href = ROOT + 'problemset/';
		 				  else
		 					  _show("#logintext");
			 			}
		 		});
	     });
	  
	  $('#register').click(function() {
		  		  
		  var registeusername= $("#registerusername").val();
		  
		  var registerpassword= $("#registerpassword").val();

		  var registeremail= $("#registeremail").val();
		  
		  var registerAddress = ROOT + 'user/register';
		 		
		 		$.ajax({
		 			  type: "POST",
		 			  url: registerAddress,
		 			  data: {username:registeusername,
		 				  	 password:registerpassword,
		 				  	 email:registeremail
		 				  	 }
		 			})
		 			.error(function(){
		 				_show('#registertext');
		 				 
		 			})
		 			.done(function(){
		 				 window.location.href = ROOT + 'problemset/';
		 			});
		 			
	     });
	  
	  $('#canModify').click(function(){
		  _show("#modifyform");
		  _hide("#infoform");
	  });
	  
	  $('#sureModify').click(function() {
		  
		  var userid = $("#userid").val();
  		  
		  var modifyusername= $("#modifyusername").val();
		  
		  var modifypassword= $("#modifypassword").val();

		  var modifyemail= $("#modifyemail").val();
		  
		  var modifyAddress = ROOT + 'user/modify';
		  
		 		$.ajax({
		 			  type: "POST",
		 			  url: modifyAddress,
		 			  data: {
		 				     userid:userid,
		 				  	 username:modifyusername,
		 				  	 password:modifypassword,
		 				  	 email:modifyemail
		 			  		},
		 			  success:function(){
		 					 window.location.href = ROOT + 'user/info';
			 			}
		 		});
	 			
	     });

});