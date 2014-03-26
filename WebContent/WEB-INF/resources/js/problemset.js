Ext.onReady(function() {

  var borderColor = '#13967e';
  var backColor = '#41ab93';

  /**
   * Get filter values
   */
  var getFilterContent = function() {
    var content = Ext.getCmp('problem-search').getValue();
    var knowledge = Ext.getCmp('knowledge-search').getValue();
    var typeForm = Ext.getCmp('problem-type-form').getForm();
    var diffForm = Ext.getCmp('difficulty-form').getForm();
    var types = [];
    for ( var i in typeForm.getValues()) {
      if (i != 'type_all') {
        types.push(i);
      }
    }
    types = types.length > 0 ? types.join(',') : '';
    var diffs = [];
    for ( var i in diffForm.getValues()) {
      if (i != 'diff_all') {
        diffs.push(i);
      }
    }
    diffs = diffs.length > 0 ? diffs.join(',') : '';
    fetchProblems(types, diffs, content, knowledge);
  };

  /**
   * Read problems from server filtered by some conditions.
   */
  var fetchProblems = function(type, difficulty, content, knowledge) {
    var mask = new Ext.LoadMask(Ext.getCmp('problemset-panel').el, {
      msg : 'Loading...'
    });
    mask.show();
    var params = {};
    if (type != undefined && type.length > 0)
      params['problem_type'] = type;
    if (difficulty != undefined && difficulty.length > 0)
      params['difficulty'] = difficulty;
    if (content != undefined && content.length > 0)
      params['problem_content'] = problemContent;
    if (knowledge != undefined && knowledge.length > 0)
      params['knowledge'] = knowledge;
    Ext.Ajax.request({
      url : root + '/problemset/list',
      method : 'get',
      params : params,
      success : function(resp) {
        mask.hide();
        var problems = Ext.JSON.decode(resp.responseText);
        resetList(problems);
      },
      failure : function() {
        mask.hide();
      }
    });
  };

  /**
   * Reset problems list items
   */
  var resetList = function(problems) {
    var items = [];
    for ( var i in problems) {
      items.push(makeProblemTemplate(problems[i]['problemContent'],
          problems[i]['knowledge'], makeDifficulty(problems[i]['difficulty']),
          '<span class="mystyle_all mystyle_grey">'
              + problems[i]['problemType'] + '</span>'));
    }
    var panel = Ext.getCmp('problem-list-form');
    panel.removeAll();
    panel.add(items);
  };

  /**
   * Make difficulty style
   */
  var makeDifficulty = function(difficulty) {
    switch (difficulty) {
    case 1:
      return '<span class="mystyle_all mystyle_blue" title="难度1级">'
          + difficulty + '</span>';
    case 2:
      return '<span class="mystyle_all mystyle_bluegreen" title="难度2级">'
          + difficulty + '</span>';
    case 3:
      return '<span class="mystyle_all mystyle_green" title="难度3级">'
          + difficulty + '</span>';
    case 4:
      return '<span class="mystyle_all mystyle_yellow" title="难度4级">'
          + difficulty + '</span>';
    case 5:
      return '<span class="mystyle_all mystyle_red" title="难度5级">' + difficulty
          + '</span>';
    }
    return '';
  };

  /**
   * Generate template for each problem item.
   */
  var makeProblemTemplate = function(content, knowledge, difficulty, type) {
    return {
      xtype : 'panel',
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
        bodyPadding : '10 5 5 10'
      },
      items : [ {
        width : 80,
        html : type
      }, {
        width : 550,
        html : content
      }, {
        width : 140,
        html : knowledge
      }, {
        columnWidth : 1,
        html : difficulty
      } ]
    };
  };

  /**
   * Search Panel
   */
  var searchPanel = {
    xtype : 'panel',
    region : 'north',
    margin : 0,
    layout : 'column',
    bodyPadding : 15,
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderRight : 0,
      borderLeft : 0,
      background : backColor
    },
    defaults : {
      height : 30,
      listners : {
        specialkey : function(f, e) {
          if (e.getKey() == e.ENTER) {
            getFilterContent();
          }
        }
      }
    },
    items : [ {
      id : 'knowledge-search',
      xtype : 'textfield',
      name : 'knowledgeSearch',
      width : 300,
      emptyText : '输入知识点（空白为所有知识点）'
    }, {
      id : 'problem-search',
      xtype : 'textfield',
      name : 'problemSearch',
      width : 400,
      margin : '0 0 0 10',
      emptyText : '输入题目关键词'
    }, {
      xtype : 'button',
      // baseCls: 'button_yellow',
      width : 120,
      margin : '0 0 0 10',
      padding : 8,
      text : '查询题目',
      handler : getFilterContent
    }, {
      xtype : 'panel',
      height: 0,
      columnWidth : 1,
      border : false
    } ]
  };

  /**
   * Filter form for problem types
   */
  var problemTypeForm = {
    xtype : 'form',
    id : 'problem-type-form',
    width : 120,
    bodyPadding : '10 15 10 15',
    bodyStyle : {
      borderColor : borderColor,
      borderRight : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    margin : '15 0 0 0',
    defaultType : 'checkboxfield',
    defaults : {
      cls : 'checkbox_white',
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          var form = Ext.getCmp('problem-type-form');
          if (field.name == 'type_all' && newValue) {
            form.queryById('type_concept').setValue(false);
            form.queryById('type_blankfill').setValue(false);
            form.queryById('type_choice').setValue(false);
            form.queryById('type_question').setValue(false);
            form.queryById('type_integrate').setValue(false);
            return;
          }
          form.queryById('type_all').setValue(false);
          var values = form.getForm().getValues();
          var types = [];
          for ( var i in values) {
            types.push(i);
          }
          if (types.length == 0) {
            form.queryById('type_all').setValue(true);
          }
        }
      }
    },
    items : [ {
      boxLabel : '所有题型',
      id : 'type_all',
      name : 'type_all',
      checked : true
    }, {
      boxLabel : '概念题',
      id : 'type_concept',
      name : 'type_concept'
    }, {
      boxLabel : '填空题',
      id : 'type_blankfill',
      name : 'type_blankfill'
    }, {
      boxLabel : '选择题',
      id : 'type_choice',
      name : 'type_choice'
    }, {
      boxLabel : '问答题',
      id : 'type_question',
      name : 'type_question'
    }, {
      boxLabel : '综合题',
      id : 'type_integrate',
      name : 'type_integrate'
    } ]
  };

  /**
   * Filter form for difficulties
   */
  var difficultyForm = {
    xtype : 'form',
    id : 'difficulty-form',
    width : 120,
    bodyStyle : {
      borderColor : borderColor,
      borderRight : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    bodyPadding : '10 15 0 15',
    defaultType : 'checkboxfield',
    defaults : {
      cls : 'checkbox_white',
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          var form = Ext.getCmp('difficulty-form');
          if (field.name == 'diff_all' && newValue) {
            form.queryById('diff1').setValue(false);
            form.queryById('diff2').setValue(false);
            form.queryById('diff3').setValue(false);
            form.queryById('diff4').setValue(false);
            form.queryById('diff5').setValue(false);
            return;
          }
          form.queryById('diff_all').setValue(false);
          var values = form.getForm().getValues();
          var types = [];
          for ( var i in values) {
            types.push(i);
          }
          if (types.length == 0) {
            form.queryById('diff_all').setValue(true);
          }
        }
      }
    },
    items : [ {
      boxLabel : '所有难度',
      id : 'diff_all',
      name : 'diff_all',
      checked : true
    }, {
      boxLabel : '难度：1级',
      id : 'diff1',
      name : '1'
    }, {
      boxLabel : '难度：2级',
      id : 'diff2',
      name : '2'
    }, {
      boxLabel : '难度：3级',
      id : 'diff3',
      name : '3'
    }, {
      boxLabel : '难度：4级',
      id : 'diff4',
      name : '4'
    }, {
      boxLabel : '难度：5级',
      id : 'diff5',
      name : '5'
    } ]
  };

  /**
   * Filter form
   */
  var filterForm = {
    xtype : 'panel',
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    bodyPadding : '15 0 0 0',
    region : 'west',
    width : 120,
    items : [ {
      xtype : 'button',
      // baseCls: 'button_yellow',
      width : 90,
      height: 30,
      margin : '0 0 0 15',
      text : '过滤题目',
      handler : getFilterContent
    }, problemTypeForm, difficultyForm ]
  };

  /**
   * Title for list form
   */
  var listFormTitle = function() {
    var title = makeProblemTemplate('<span class="list_title">题目</span>',
        '<span class="list_title">知识点</span>',
        '<span class="list_title">难度</span>',
        '<span class="list_title">题型</span>');
    title['region'] = 'north';
    title['defaults']['bodyPadding'] = '5 5 5 10';
    return title;
  };

  /**
   * List Form
   */
  var listForm = {
    xtype : 'form',
    region : 'center',
    layout : 'border',
    border : 0,
    overflowY : 'auto',
    defaultType : 'panel',
    items : [ listFormTitle(), {
      id : 'problem-list-form',
      region : 'center',
      border : 0,
      overflowY : 'auto'
    } ],
    listeners : {
      afterrender : function(comp, opts) {
        fetchProblems();
      }
    }
  };

  /**
   * Problem Set Panel (Main)
   */
  Ext.create('Ext.panel.Panel', {
    id : 'problemset-panel',
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