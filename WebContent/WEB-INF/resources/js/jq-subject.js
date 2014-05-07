$(function  () {
	var order;
	var theme_id=1;
	var ROOT = "/course-share/";
	
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
	
	$("#order-ok").click(function(){
		
		var orderAddress=ROOT+"subject/order";
		alert(orderAddress);
		$.ajax({
  		    url: orderAddress,
  		    data:{
  		    	 order:order,
  		    	 theme_id:theme_id
  			    },
  		    type: 'GET',
  		    success: function(data){
  		    	alert(data);
  		    }
  		  });
	});
	
});



