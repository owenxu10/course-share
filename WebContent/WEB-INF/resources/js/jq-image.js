$(function() {
	
	var ROOT = "/course-share/";
	var atTopPosition = false;
	
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
	  
	  
	$("#image-search").click(function(){
		//UI change
		if(atTopPosition == false){
		//	$("#center-frame").animate({top: "-=40%"}, "slow");
			atTopPosition = true;
		}
		
		
		//_show("#image-content");
		
		
	   // do some transactions
	   var searchKeyWord = $("#image-keyword").val();
	   var searchAddress = ROOT + 'image/search';
	   
	   alert(searchKeyWord);
	   
	   if(searchKeyWord!=""){
		   alert("1");
		   $.ajax({
	 			  type: "POST",  
	 			  url: searchAddress,
	 			  data: {
	 				 searchKeyWord:searchKeyWord
	 				  },
	 				  
	 			}).done(function(response){
	 				alert("2");
 					var obj = JSON.parse(response);
 					alert(obj);
	 			});
		   
	   }
		   
		
		
		
		
	});
	
});