$(function  () {

	var group = $("ol.subject-adjust-order-list").sortable({
		  group: 'subject-adjust-order-list',
		  onDrop: function (item, container, _super) {
		    alert(group.sortable("serialize").get().join("\n"));
		    _super(item, container);
		  },
		  serialize: function (parent, children, isContainer) {
		    return isContainer ? children.join() : parent.text()
		  },
		});
	
});



