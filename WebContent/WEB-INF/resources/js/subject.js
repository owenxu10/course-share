Ext.onReady(function() {

  var borderColor = '#297395';
  var backColor = '#30a7de';

  /**
   * Read subjects from server filtered by some conditions.
   */
  var fetchSubjects = function(subjectContent) {
    // Ext.MessageBox.wait('Loading......', '');

    var mask = new Ext.LoadMask(Ext.getCmp('subject-panel').el, {
      msg : 'Loading...'
    });
    mask.show();
    var params = {};
    if (subjectContent != undefined && subjectContent.length > 0) {
      params['subject_content'] = subjectContent;
    }
    var panel = Ext.getCmp('subject-list-panel');
    panel.removeAll();
    Ext.Ajax.request({
      url : root + '/subject/list',
      method : 'get',
      params : params,
      success : function(resp) {
        mask.hide();
        var subjects = Ext.JSON.decode(resp.responseText);
        items = [];
        for ( var i in subjects) {
          items.push(makeSubjectTemplate(subjects[i]));
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
   * Generate template for each subject object.
   */
  var makeSubjectTemplate = function(subject) {
    return {
      xtype : 'panel',
      id : 'subject_' + subject['id'],
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
      items : [
          {
            columnWidth : 0.8,
            html : '<p><a href="' + subject['url'] + '">' + subject['name']
                + '</a></p><p class="link_color">' + subject['url'] + '</p><p>'
                + subject['description'] + '</p>'
          },
          {
            columnWidth : 0.2,
            html : '专题：<span class="mystyle_all mystyle_grey">'
                + subject['subjectName'] + '</span>'
          } ]
    };
  };

  var getFilterContent = function() {
    var content = Ext.getCmp('subject-search').getValue();
    fetchSubjects(content);
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
      id : 'subject-search',
      name : 'subjectSearch',
      width : 500,
      height : 30,
      emptyText : '输入专题关键词',
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
      text : '查询专题',
      handler : getFilterContent
    }, {
      xtype : 'panel',
      columnWidth : 1,
      border : false
    } ]
  };

  var filterForm = {
    xtype : 'form',
    id : 'subject-type-form',
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
      text : '过滤专题',
      //baseCls : 'button_yellow',
      margin : '0 0 10 0',
      width: 90,
      handler : getFilterContent
    }, {
      margin : '15 0 0 0',
      cls : 'checkbox_white',
      boxLabel : '所有专题',
      id : 'type_all',
      name : 'type_all',
      checked : true
    } ]
  };

  var listForm = {
    xtype : 'panel',
    id : 'subject-list-panel',
    region : 'center',
    border : 0,
    autoScroll : true,
    listeners : {
      afterrender : function(comp, opts) {
        fetchSubjects();
      }
    }
  };

  /**
   * Subject Set Panel (Main)
   */
  Ext.create('Ext.panel.Panel', {
    id : 'subject-panel',
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