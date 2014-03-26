var root = '/course-share';

Ext.Loader.setConfig({
  enabled : true
});

Ext.require([ 'Ext.ux.layout.Center' ]);

Ext.onReady(function() {

  var header = {
    xtype: 'panel',
    region: 'north',
    layout: 'fit',
    height: 50,
    cls: '',
    baseCls: '',
    html: ['<nav class="navbar navbar-inverse"  role="navigation>',
           '</nav>'].join()
  };
  
  var header2 = {
    xtype : 'panel',
    region : 'north',
    layout : 'absolute',
    height : 60,
    bodyStyle : 'background: #F9FAF7',
    border : false,
    items : [ {
      xtype : 'box',
      x : 0,
      y : 0,
      html : '<h2>计算机系统结构资源——' + pageTitle + '</h2>'
    }, {
      xtype : 'toolbar',
      x : 400,
      y : 15,
      defaults : {
        margins : '0 5 0 5'
      },
      items : [ '->', {
        text : '素材库',
        handler : function() {
          window.location.href = root + '/image';
        }
      }, {
        text : '动画库',
        handler : function() {
          window.location.href = root + '/flash';
        }
      }, {
        text : '专题库',
        handler : function() {
          window.location.href = root + '/subject';
        }
      }, {
        text : '题库',
        handler : function() {
          window.location.href = root + '/problemset';
        }
      } ]
    } ]
  };

  var content = {
    xtype : 'panel',
    id : 'content-panel',
    layout : 'fit',
    region : 'center',
    bodyStyle : 'background: #F9FAF7',
    border : false
  };

  var footer = {
    xtype : 'box',
    region : 'south',
    bodyStyle : 'background: #F9FAF7',
    border : false,
    align : 'center',
    html : '<div id="footer"><p>Copyright Xian Yikun, 2013.08<p></div>'
  };

  Ext.create('Ext.container.Viewport', {
    style : {
      backgroundColor : '#F9FAF7'
    },
    layout : 'border',
    items : [ header, content ]
  });

});
