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
	   
	   if(searchKeyWord!=""){
		   $.ajax({
	 			  type: "POST",  
	 			  url: searchAddress,
	 			  data: {
	 				 searchKeyWord:searchKeyWord
	 				  },
	 				  
	 			}).done(function(){
	 				  location.reload();
	 			});
		   
	   }
	});
	
	$('button#image-upload').click(function(){
		  $('#uploadModal').modal({
			    backdrop:true,
			    keyboard:true,
			    show:true
			});
	  });
	
	 $("#imagekey").click(function(){
		  if ($("#imagekey").is(":checked"))  {        
			  _show('#uploadImage',"#imageUpload");
			  _hide('#uploadURL',"#flashUpload");
		  }
		  else{
			  _hide('#uploadImage',"#imageUpload");
			  _show('#uploadURL',"#flashUpload");
		  }
		});
	  
	  $("#flashkey").click(function(){
		  if ($("#flashkey").is(":checked"))  {
			  _hide('#uploadImage',"#imageUpload");
			  _show('#uploadURL',"#flashUpload");
			 }
		  else{
			  _show('#uploadImage',"#imageUpload");
			  _hide('#uploadURL',"#flashUpload");
		  }
			 
	  });
	
	  $('button#imageUpload').click(function() {
		  
		  var updateAddress = ROOT + 'image/uploadimage';
		  var uploadForm = new FormData();
		  
		  //get the data from page
		  var inputname = $("#inputname").val();
		  var inputknowledge= $("#inputknowledge").val();
		  
		  var inputtype = "image";
		  var inputImage = uploadFile.files[0] ;
		  
		  
		  $("#uploadFile").change(function() {
			  var ext = $('#uploadFile').val().split('.').pop().toLowerCase();
			  if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			      alert('invalid extension!');
			  }
		 });
		  
		  $("input[name='keyradio']:checked").each(function() {
				 if($(this).val()=="image") inputtype="image";
				 if($(this).val()=="flash") inputtype="flash";
			    });
			 
		  
		  uploadForm.append("resourceName", inputname);
		  uploadForm.append("resourceknowledge", inputknowledge);
		  uploadForm.append("resourceType", inputtype);
		  uploadForm.append("resourceImage", inputImage);
		  
		  $.ajax({
			    url: updateAddress,
			    data: uploadForm,
			    dataType: 'text',
			    processData: false,
			    contentType: false,
			    type: 'POST',
			    success: function(data){
			    	$('#uploadModal').modal('hide');
			 		$('#successModal').modal({
			   	    backdrop:true,
			   	    keyboard:true,
			   	    show:true
			 		});
			    },
			    error: function(request) {
	                alert("请输入完整的资源信息！");
	            }
			  });
	});
	
	  
	  $('button#flashUpload').click(function() {
		  
		  var updateAddress = ROOT + 'image/uploadflash';
		  var uploadForm = new FormData();
		  
		  //get the data from page
		  var inputname = $("#inputname").val();
		  var inputknowledge= $("#inputknowledge").val();
		  
		  var inputtype = "flash";
		  
		  var inputURL= $("#inputURL").val();
		  
		  if(uploadFile.files[0]==null){
			  inputImage = null;
		  }
			 
		  uploadForm.append("resourceName", inputname);
		  uploadForm.append("resourceknowledge", inputknowledge);
		  uploadForm.append("resourceType", inputtype);
		  uploadForm.append("resourceURL",  inputURL);
		  
		  $.ajax({
			    url: updateAddress,
			    data: uploadForm,
			    dataType: 'text',
			    processData: false,
			    contentType: false,
			    type: 'POST',
			    success: function(data){
			    	$('#uploadModal').modal('hide');
			 		$('#successModal').modal({
			   	    backdrop:true,
			   	    keyboard:true,
			   	    show:true
			 		});
			    },
			    error: function(request) {
	                alert("请输入完整的资源信息！");
	            }
			  });
	});
	
});