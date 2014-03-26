Ext.onReady(function() {

  var borderColor = '#ba3d66';
  var backColor = '#c8547c';

  /**
   * Read flashs from server filtered by some conditions.
   */
  var fetchFlashs = function(flashContent) {
    // Ext.MessageBox.wait('Loading......', '');
    var mask = new Ext.LoadMask(Ext.getCmp('flash-panel').el, {
      msg : 'Loading...'
    });
    mask.show();
    var params = {};
    if (flashContent != undefined && flashContent.length > 0) {
      params['flash_content'] = flashContent;
    }
    var panel = Ext.getCmp('flash-list-panel');
    panel.removeAll();
    Ext.Ajax.request({
      url : root + '/flash/list',
      method : 'get',
      params : params,
      success : function(resp) {
        mask.hide();
        var flashs = Ext.JSON.decode(resp.responseText);
        var items = [];
        for ( var i in flashs) {
          items.push(makeFlashTemplate(flashs[i]));
        }
        panel.add(items);
        // Ext.MessageBox.hide();
      },
      failure : function() {
        mask.hide();
        // Ext.MessageBox.hide();
      }
    });
  };

  /**
   * Generate template for each flash object.
   */
  var makeFlashTemplate = function(flash) {
    return {
      xtype : 'panel',
      id : 'flash_' + flash['id'],
      layout : 'column',
      width : 838,
      bodyStyle : {
        borderColor : borderColor,
        borderTop : 0,
        borderRight : 0,
        borderLeft : 0
      },
      defaultType : 'panel',
      defaults : {
        border : false,
        bodyPadding : '15 5 5 15'
      },
      items : [ {
        columnWidth : 0.8,
        html : '<p>' + flash['name'] + '</p><p>' + flash['knowledge'] + '</p>'
      }, {
        columnWidth : 0.2,
        xtype : 'button',
        text : '播放动画',
        margin : '15 10 0 0',
        // url : flash['url'],
        handler : function(btn) {
          window.open(flash['url']);
          /*
           * Ext.widget('window', { title : flash['name'], layout : 'fit', width :
           * 800, height : 600, resizable : true, items : { xtype : 'flash', url :
           * btn.flash_url } }).show();
           */
        }
      } ]
    };
  };

  var getFilterContent = function() {
    var content = Ext.getCmp('flash-search').getValue();
    fetchFlashs(content);
  };

  var searchPanel = {
    xtype : 'panel',
    region : 'north',
    margin : 0,
    bodyPadding : 15,
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderRight : 0,
      borderLeft : 0,
      background : backColor
    },
    layout : 'column',
    items : [ {
      xtype : 'textfield',
      id : 'flash-search',
      name : 'flashSearch',
      width : 500,
      height : 30,
      emptyText : '搜索动画',
      listeners : {
        specialkey : function(f, e) {
          if (e.getKey() == e.ENTER) {
            getFilterContent();
          }
        }
      }
    }, {
      xtype : 'button',
      height : 30,
      width : 120,
      //baseCls : 'button_yellow',
      margin : '0 0 0 10',
      padding : 8,
      text : '查询动画',
      handler : getFilterContent
    }, {
      xtype : 'panel',
      columnWidth : 1,
      border : false
    } ]
  };

  var filterForm = {
    xtype : 'form',
    id : 'flash-type-form',
    region : 'west',
    width : 120,
    bodyPadding : '10 15 10 15',
    bodyStyle : {
      borderColor : borderColor,
      background : backColor
    },
    border : false,
    margin : '15 0 0 0',
    defaultType : 'checkboxfield',
    defaults : {
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          if (!newValue)
            form.queryById('type_all').setValue(true);
          /*
           * var form = Ext.getCmp('problem-type-form'); if (field.name ==
           * 'type_all' && newValue) {
           * form.queryById('type_concept').setValue(false);
           * form.queryById('type_blankfill').setValue(false);
           * form.queryById('type_choice').setValue(false);
           * form.queryById('type_question').setValue(false);
           * form.queryById('type_integrate').setValue(false); return; }
           * form.queryById('type_all').setValue(false); var values =
           * form.getForm().getValues(); var types = []; for ( var i in values) {
           * types.push(i); } if (types.length == 0) {
           * form.queryById('type_all').setValue(true); }
           */
        }
      }
    },
    items : [ {
      xtype : 'button',
      text : '过滤动画',
      margin : '0 0 10 0',
      width: 90,
      //baseCls : 'button_yellow',
      handler : getFilterContent
    }, {
      margin : '15 0 0 0',
      boxLabel : '所有动画',
      cls : 'checkbox_white',
      id : 'type_all',
      name : 'type_all',
      checked : true
    } ]
  };

  var listForm = {
    xtype : 'panel',
    id : 'flash-list-panel',
    region : 'center',
    border : 0,
    autoScroll : true,
    listeners : {
      afterrender : function(comp, opts) {
        fetchFlashs();
      }
    }
  };

  /**
   * Flash Set Panel (Main)
   */
  Ext.create('Ext.panel.Panel', {
    id : 'flash-panel',
    layout : 'border',
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderBottom : 0,
      background : backColor
    },
    height : Ext.get('main-content').getHeight(),
    renderTo : 'main-content',
    items : [ searchPanel, filterForm, listForm ]
  });
});