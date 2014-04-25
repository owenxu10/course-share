$(function() {
	
	var ROOT = "/course-share/";
	
	var logoutAddress = ROOT + 'user/logout';
	$("#logout").click(function() {
		//to /logout
		$.ajax({
			  type: "POST",
			  url: logoutAddress
			})
			.done(function(){
				 window.location.href = ROOT + 'user/';
			});
			 

     });
	
	
	$("#modifyInfo").click(function() {
		//to /modify
		
     });
	
	
});