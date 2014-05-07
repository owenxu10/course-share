$(function  () {

	var group = $("ol.limited_drop_targets").sortable({
		  group: 'limited_drop_targets',
		  onDrop: function (item, container, _super) {
		    alert(group.sortable("serialize").get().join("\n"));
		    _super(item, container);
		  },
		  serialize: function (parent, children, isContainer) {
		    return isContainer ? children.join() : parent.text()
		  },
		});
});
