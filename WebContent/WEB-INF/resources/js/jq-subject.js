$(function  () {
	
	var order="null";
	var theme_id=1;
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
	  
	  
	  var _makeSubjects = function(argument) {
		  $(argument).empty();
		  $.ajax({
	  		    url: ROOT+"subject/orderedlist",
	  		    data:{order:order},
	  		    dataType: 'json',
	  		    type: 'GET',
	  		    success: function(data){
	  		    	console.log(data);
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
	
	$("a[name='modify-order']").click(function(){
		theme_id = this.title;
	});
	
	$("a[name='manage-menu']").click(function(){
		if(this.title=="totheme"){
			
			_show("#manage-theme");
			_hide("#manage-subject");
			 $("#li-menu-s").css('display', '').addClass('active');
			 $("#li-menu-t").css('display', '').removeClass('active');
		}
		else{
			_hide("#manage-theme");
			_show("#manage-subject");
			
			 $("#li-menu-t").css('display', '').addClass('active');
			 $("#li-menu-s").css('display', '').removeClass('active');
			 _makeSubjects('#subject-list');
			
		}
	});
	
	$("#manager-button-order").click(function(){

	    $('#subject-list').empty();
		_show("#order-ok","#order-cancel","#subject-adjust-order");
		_hide("#manager-button-order","#manager-button","#subject-modify");
		$("#subject-manage-center-frame").css("margin-top","-60px");
	});
	
	$("#order-cancel").click(function(){
		_hide("#order-ok","#order-cancel","#subject-adjust-order");
		_show("#manager-button-order","#manager-button","#subject-modify");
		$("#subject-manage-center-frame").css("margin-top","0px");
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
				_show("#manager-button-order","#manager-button","#subject-modify");
				$("#subject-manage-center-frame").css("margin-top","0px");
			});
		_makeSubjects('#subject-list');
		
	});
	
});



