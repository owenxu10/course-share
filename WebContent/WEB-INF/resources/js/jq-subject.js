$(function  () {
	
	var order="null";
	var theme_id=1;
	var ROOT = "/course-share/";
	var mode="show";
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
	  
	  var _makeSubjects = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/orderedlist",
	  		    data:{themeid:theme_id},
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		        var list = [];
		  		    $.each(data, function(k, v) {
		  		    	
		  		      var p = _.template($('#subject-tpl').html(), {
		  		        'id' : v.subject_id,
		  		        'title' : v.title,
		  		        'description' : v.description
		  		      });
		  		      list.push($.trim(p));
		  		    });
		  		    if (list.length <= 0) {
		  		      return false;
		  		    }
		  		    list = $(list.join(''));
		  		    $(argument).append(list);
	  		    }
	  		  });
	  };
	  
	  var _makeShowSubjects = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/orderedlist",
	  		    data:{themeid:theme_id},
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		        var list = [];
		  		    $.each(data, function(k, v) {
		  		    	
		  		      var p = _.template($('#subject-adjust-tpl').html(), {
		  		        'id' : v.subject_id,
		  		        'title' : v.title,
		  		        'description' : v.description
		  		      });
		  		      list.push($.trim(p));
		  		    });
		  		    if (list.length <= 0) {
		  		      return false;
		  		    }
		  		    list = $(list.join(''));
		  		    $(argument).append(list);
	  		    }
	  		  });
	  };
	  
	  var _makeAdjunstSubjects = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/orderedlist",
	  		    data:{themeid:theme_id},
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		        var list = [];
		  		    $.each(data, function(k, v) {
		  		    	
		  		      var p = _.template($('#subject-show-tpl').html(), {
		  		        'id' : v.subject_id,
		  		        'title' : v.title,
		  		        'description' : v.description,
		  		        'url':v.url
		  		      });
		  		      list.push($.trim(p));
		  		    });
		  		    if (list.length <= 0) {
		  		      return false;
		  		    }
		  		    list = $(list.join(''));
		  		    $(argument).append(list);
		  		    
		  			
	  		    }
	  		  });
	  };
	  
	  
	  
	  var _makeThemes = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/themelist",
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		        var list = [];
		  		    $.each(data, function(k, v) {
		  		    	
		  		      var p = _.template($('#theme-tpl').html(), {
		  		        'themeid' : v.theme_id,
		  		        'name' : v.name,
		  		      });
		  		      list.push($.trim(p));
		  		    });
		  		    if (list.length <= 0) {
		  		      return false;
		  		    }
		  		    list = $(list.join(''));
		  		    
		  			
		  		    $(argument).append(list);
		  		    
		  		    $("a[name='modify-order']").click(function(){
		  				theme_id = this.title;
		  				if(mode=="show")
		  				_makeSubjects("#subject-list");
		  				else
		  				_makeShowSubjects("#order-adjust-list");
		  			});
	  		    }
	  		  });
	  };
	  
	  var _makeManageTheme = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/themelist",
	  		    data:{themeid:theme_id},
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		        var list = [];
		  		    $.each(data, function(k, v) {
		  		    	
		  		      var p = _.template($('#theme-manage-tpl').html(), {
		  		    	 'themeid' : v.theme_id,
			  		        'name' : v.name,
		  		      });
		  		      list.push($.trim(p));
		  		    });
		  		    if (list.length <= 0) {
		  		      return false;
		  		    }
		  		    list = $(list.join(''));
		  		    $(argument).append(list);
		  		    
		  		  $("a[name='delete']").click(function(){
		  			
		  			var deleteThemeid = this.title;
		  			
		  			var delThemeAddress=ROOT+"subject/deleteTheme";
		  			$.ajax({
		  				  type: "GET",
		  				  url: delThemeAddress,
		  				  data: {
		  		  		    	 theme_id:deleteThemeid
		  		  		    	 }
		  				}).done(function(){
		  					//refresh theme list
		  					_makeManageTheme("#theme-list");
		  				});
		  		});
	  		    }
	  		  });
	  };
	  
	  $("#toManage").click(function(){
		  _hide("#show-page");
		  _show("#mamage-page");
		  
	  });
	  
	var group = $("ol.subject-adjust-order-list").sortable({
		  group: 'subject-adjust-order-list',
		  onDrop: function (item, container, _super) {
		    order = group.sortable("serialize").get().join("\n");
		    console.log(order);
		    _super(item, container);
		  },
		  serialize: function (parent, children, isContainer) {
		    return isContainer ? children.join() : parent.text()
		  },
		});
	
	$("a[name='showlist']").click(function(){
		theme_id = this.title;
		_makeShowSubjects("#subject-center-frame");
	});
	
//	$("a[name='modify-order']").click(function(){
//		theme_id = this.title;
//		alert(theme_id);
//		_makeSubjects("#subject-list");
//	});
	
	
	$("a[name='manage-menu']").click(function(){
		if(this.title=="totheme"){
			
			_show("#manage-theme");
			_hide("#manage-subject");

			 $("#li-menu-t").css('display', '').addClass('active');
			 $("#li-menu-s").css('display', '').removeClass('active');
		}
		else{
			_hide("#manage-theme");
			_show("#manage-subject");

			 $("#li-menu-s").css('display', '').addClass('active');
			 $("#li-menu-t").css('display', '').removeClass('active');
			 theme_id=1;
			 _makeSubjects('#subject-list');
			 _makeThemes("#manage-theme-list");
			
		}
	});
	
	$("#manager-button-order").click(function(){

	    $('#subject-list').empty();
		_show("#order-ok","#order-cancel","#subject-adjust-order");
		_hide("#manager-button-order","#add-button","#subject-modify");
		$("#subject-manage-center-frame").css("margin-top","-60px");
		mode="adjust";
	});
	
	$("#order-cancel").click(function(){
		_hide("#order-ok","#order-cancel","#subject-adjust-order");
		_show("#manager-button-order","#add-button","#subject-modify");
		$("#subject-manage-center-frame").css("margin-top","0px");

		_makeSubjects('#subject-list');
		mode="show";
	});

	
	$("#order-ok").click(function(){
		
		var orderAddress=ROOT+"subject/order";
		
		$.ajax({
			  type: "GET",
			  url: orderAddress,
			  data: {
				  	 order:order,
	  		    	 theme_id:theme_id
	  		    	 }
			}).done(function(){
				_hide("#order-ok","#order-cancel","#subject-adjust-order");
				_show("#manager-button-order","#add-button","#subject-modify");
				$("#subject-manage-center-frame").css("margin-top","0px");
			});
		_makeSubjects('#subject-list');
		mode="show";
		
	});
	
	$("#add-button").click(function(){
		_show("#add-subject","#add-cancel");
		_hide("#subject-list","#manager-button-order","#add-button");
	});
	
	$("#add-cancel").click(function(){
		_hide("#add-subject","#add-cancel");
		_show("#subject-list","#manager-button-order","#add-button");
	});
	
	$("#subjectUpload").click(function(){

		var inputAddress=ROOT+"subject/importSubject";
		
		var title = $("#s-title").val();
		var description = $("#s-description").val();
		var url = $("#s-url").val();
		
		
		$.ajax({
			  type: "GET",
			  url: inputAddress,
			  data: {
				     title:title,
	  		    	 theme_id:theme_id,
	  		    	 description:description,
	  		    	 url:url
	  		    	 }
			}).done(function(){
				_hide("#add-subject","#add-cancel");
				_show("#subject-list","#manager-button-order","#add-button");
				_makeSubjects('#subject-list');
				
			});
		
	});
	
	
	$("a[name='delete']").click(function(){
		
		var deleteThemeid = this.title;
		
		var delThemeAddress=ROOT+"subject/deleteTheme";
		$.ajax({
			  type: "GET",
			  url: delThemeAddress,
			  data: {
	  		    	 theme_id:deleteThemeid
	  		    	 }
			}).done(function(){
				//refresh theme list
				_makeManageTheme("#theme-list");
			});
	});
	
	
	$("#add-theme").click(function(){
		  _show("#insert-theme");
	  });
	
	$("#themeUploadcancel").click(function(){
		_hide("#insert-theme");
	  });
	  
	$("#themeUpload").click(function(){
		var addThemeAddress=ROOT+"subject/addTheme";
		var name = $("#t-title").val();
		$.ajax({
			  type: "GET",
			  url: addThemeAddress,
			  data: {
				  name:name
	  		    	 }
			}).done(function(){
				//refresh theme list
				_makeManageTheme("#theme-list");
				_hide("#insert-theme");
			});
	  });
	
});



