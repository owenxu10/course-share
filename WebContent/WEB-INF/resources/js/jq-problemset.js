$(function() {

  /***** Global variables. *****/

  var CLS_P_TYPE = {
    '概念题' : 'cs-frame-grey',
    '填空题' : 'cs-frame-blue',
    '选择题' : 'cs-frame-green',
    '问答题' : 'cs-frame-yellow',
    '综合题' : 'cs-frame-red'
  };

  var CLS_P_DIFF = {
    '1' : 'btn-primary',
    '2' : 'btn-info',
    '3' : 'btn-success',
    '4' : 'btn-warning',
    '5' : 'btn-danger'
  };
 
  var BTN_BASKET_ADD = {
    0: 'btn-success',
    1: 'btn-danger',
    'btn-success': '放入试题篮',
    'btn-danger': '移出试题篮'
  };

  var BTN_BASKET_SELECT = {
    0: 'btn-success',
    1: 'btn-warning',
    'btn-success': '已选',
    'btn-warning': '未选'
  };

  var URL = ROOT + 'problemset/list';

  var PSW = '666888';

  _basket = {};

  /***** Local functions *****/

  /**
   * Return integer of offset.
   */
  var _getOffset = function() {
    var offset = parseInt($('#filter-offset').val(), 10);
    return (isNaN(offset) || offset < 0) ? 0 : offset;
  };

  /**
   * Set offset if given value's greater than the older.
   */
  var _setOffset = function(offset) {
    var offset = parseInt(offset);
    if (isNaN(offset) || offset < 0) {
      offset = 0;
    }
    var old = _getOffset();
    if (offset >= old) {
      $('#filter-offset').val(offset + 1);
    }
  };

  /**
   * Return filtered parameters object of server request.
   */
  var _getParams = function() {
    return {
      problem_type : $('#filter-types').val(),
      difficulty : $('#filter-diffs').val(),
      problem_content : $('#filter-contents').val(),
      knowledge : $('#filter-knows').val(),
      offset : _getOffset()
    };
  };

  /**
   * Set filter parameters of tmp fields from page.
   */
  var _setParams = function() {
    var contents = $('#filter-content').val().replace(/ /g, ',');
    var knows = $('#filter-know').val().replace(/ /g, ',');
    var types = [], diffs = [];
    $('input[name="filter-type"][id!="type-all"]:checked').each(function() {
      types.push($(this).attr('id'));
    });
    $('input[name="filter-diff"][id!="diff-all"]:checked').each(function() {
      diffs.push($(this).attr('id'));
    });
    $('#filter-contents').val(contents);
    $('#filter-knows').val(knows);
    $('#filter-types').val(types.join(','));
    $('#filter-diffs').val(diffs.join(','));
  };
  
  /**
   * Change button type of class and text.
   * Return new state.
   */
  var _chgBtnType = function(selector, btnInfo) {
    var pivot = selector.hasClass(btnInfo[0]) ? 0 : 1;
    var oldCls = btnInfo[pivot], newCls= btnInfo[1 - pivot];
    selector.removeClass(oldCls).addClass(newCls).text(btnInfo[newCls]);
    return 1 - pivot;
  };
  
  /**
   * Return jquery object of problem cloned from problemset list to paper basket.
   * No event is binded.
   */
  var _clone2Basket = function(pid) {
    var e = $('#problemset-list .ps-row[id="' + pid + '"]').clone();
    e.find('.ps-key').remove();
    var btns = _.template($('#basket-btn-tpl').html(), {
      'id' : pid
    });
    e.find('.ps-action').html($.trim(btns));
    var tmp = $("<div></div>");
    tmp.append(e);
    return tmp.html();
  };
    
  /**
   * [Event] Click to add to paper basket.
   */
  var _eAdd2Basket = function(selector) {
    selector.click(function() {
      var pid = $(this).attr('id');
      if(_chgBtnType($(this), BTN_BASKET_ADD) == 1) {
        // Add problem to basket
        _basket[pid] = _clone2Basket(pid);
      }
      else {
        // Remove problem from basket
        delete _basket[pid];
      }
    });
  };
  
  /**
   * [Event] Double click to view image in new tab.
   */
  var _eViewImage = function(selector) {
    selector.addClass('img-responsive').dblclick(function() {
      window.open($(this).attr('src'));
    });
  };

  /**
   * Visualize problems, bind events and add to problem list.
   */
  var _makeProblems = function(problems) {
    var list = [], max = -1;
    $.each(problems, function(k, v) {
      var p = _.template($('#problem-tpl').html(), {
        'id' : v.id,
        'type' : v.problemType,
        'typeCls' : CLS_P_TYPE[v.problemType],
        'diff' : v.difficulty,
        'diffCls' : CLS_P_DIFF[v.difficulty],
        'content' : v.problemContent,
        'know' : v.knowledge,
        'key' : v.keyContent
      });
      list.push($.trim(p));
      max = parseInt(v.id) > max ? parseInt(v.id) : max;
    });
    if (list.length <= 0) {
      return false;
    }
    list = $(list.join(''));
    _setOffset(max);
    // Bind events.
    _eAdd2Basket(list.find('button.basket-add'));
    _eViewImage(list.find('img'));
    // Change button type if the problem is in basket.
    if ($('#ps-pswbar').hasClass('ps-hidden') && $('#ps-passwd').val() == PSW) {
      list.find('button.key').removeClass('ps-hidden');
    }
    for (var id in _basket) {
      var sel = list.find('button.basket-add[id="' + id + '"]');
      if (sel.length > 0) {
        _chgBtnType(sel, BTN_BASKET_ADD);
      }
    }
    $('#problemset-list').append(list);
  };

  /**
   * Enable scroll pagination.
   */
  var _enableScroll = function() {
    $('#problemset-list').scrollPagination({
      url : URL,
      param : _getParams(),
      method : 'GET',
      scrollTarget : $(window),
      heightOffset : 15,
      beforeLoad : function() {
        $('#problemset-load').fadeIn();
        this.param = _getParams();
      },
      afterLoad : function(data) {
        $('#problemset-load').fadeOut();
        if (data == null || data.length <= 0) {
          _disableScroll();
          return false;
        }
        _makeProblems(data);
      }
    });
  };

  /**
   * Disable scroll pagination.
   */
  var _disableScroll = function() {
    $('#problemset-list').stopScrollPagination();
  };

  /**
   * Refresh problems.
   */
  var _refresh = function() {
    _disableScroll();
    $('#problemset-list').html('');
    $('#filter-offset').val(0);
    _setParams();
    $.getJSON(URL, _getParams(), function(data) {
      if (data == null || data.length <= 0) {
        _disableScroll();
        return false;
      }
      _makeProblems(data);
      _enableScroll();
    });
  };

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

  /**
   * Judge if given problem id is in the basket.
   */
  var _inBasket = function(id) {
    return $('#paperbasket-list .ps-row[id="' + id + '"]').length > 0 ? true : false;
  };

  
  /**********************************/
  /********** Global Event **********/
  /**********************************/
  
  var init = function() {
    _enableScroll();
    _eAdd2Basket($('#problemset-list button.basket-add'));
    _eViewImage($('#problemset-list img'));
  }();
  
  /* Event in problemset list */

  $('input#ps-passwd').keypress(function(event) {
    if (event.which == 13) {
      if ($(this).val() == '666888') {
        _show('.ps-key', '#ps-passwd-hide');
        _hide('#ps-pswbar', '#ps-passwd-error');
      } else {
        _show('#ps-passwd-error');
      }
      return false;
    }
  });

  $('button#ps-passwd-hide').click(function() {
    $('input#ps-passwd').val('');
    _show('#ps-pswbar');
    _hide('.ps-key', '#ps-passwd-error', '#ps-passwd-hide');
    return;
  });
  
  $('#ps-passwd-instruct').qtip({
    content : {
      text : '任课教师请发送邮件获取密码: xzhang2000@sohu.com'
    },
    style : {
      classes: 'hover_style',
      width : 185,
      tip : {
        corner : 'top center'
      }
    },
    position : {
      my : 'top center',
      at : 'bottom center'
    }
  });


  /*
   * $('button#selectall').click(function() {
   * $('button.paper').removeClass('btn-success').addClass('btn-danger').text('取消');
   * $('#problemset-list div.ps-row').addClass('cs-border-red'); });
   * 
   * $('button#selectnone').click(function() {
   * $('button.paper').addClass('btn-success').removeClass('btn-danger').text('出题');
   * $('#problemset-list div.ps-row').removeClass('cs-border-red'); });
   */

  /**
   * [Event] Remove problem from paper basket.
   */
  var _eRemoveFromBasket = function(selector) {
    selector.click(function() {
      var pid = $(this).attr('id');
      $('#paperbasket-list .ps-row[id="' + pid + '"]').remove();
      _chgBtnType($('#problemset-list button.basket-add[id="' + pid + '"]'), BTN_BASKET_ADD);
      delete _basket[pid];
    });
  };
  
  /**
   * [Event] Select problem to be made paper.
   */
  var _eSelectInBasket = function(selector) {
    selector.click(function() {
      _chgBtnType($(this), BTN_BASKET_SELECT);
    });
  };
  
  /**
   * Click to show paper basket.
   */
  $('button#ps-basket').click(function() {
    _hide('#cs-problemset-navbar', '#cs-west-frame', '#cs-center-frame');
    _show('#cs-paperbasket-navbar', '#cs-center-dialog');
    var list = [];
    for (var id in _basket) {        
      list.push(_basket[id]);
    }
    $('#paperbasket-list').html('').append(list.join(''));
    _eRemoveFromBasket($('#paperbasket-list button.basket-remove'));
    _eSelectInBasket($('#paperbasket-list button.basket-select'));
    return; 
  });

  $('button#ps-search').click(function() {
    _refresh();
    return;
  });

  $('input#filter-content').keypress(function(event) {
    if (event.which == 13) {
      _refresh();
    }
    return;
  });

  $('input#filter-know').keypress(function(event) {
    if (event.which == 13) {
      _refresh();
    }
    return;
  });

  $('input[name="filter-type"]').click(function() {
    var tid = $(this).attr('id');
    if ($(this).is(':checked')) {
      if (tid == 'type-all') {
        $('input[name="filter-type"][id!="type-all"]').prop('checked', false);
      } else {
        $('input[name="filter-type"][id="type-all"]').prop('checked', false);
      }
    } else {
      if ($('input[name="filter-type"]:checked').size() <= 0) {
        $('input[name="filter-type"][id="type-all"]').prop('checked', true);
      }
    }
    _refresh();
    return;
  });

  $('input[name="filter-diff"]').click(function() {
    var did = $(this).attr('id');
    if ($(this).is(':checked')) {
      if (did == 'diff-all') {
        $('input[name="filter-diff"][id!="diff-all"]').prop('checked', false);
      } else {
        $('input[name="filter-diff"][id="diff-all"]').prop('checked', false);
      }
    } else {
      if ($('input[name="filter-diff"]:checked').size() <= 0) {
        $('input[name="filter-diff"][id="diff-all"]').prop('checked', true);
      }
    }
    _refresh();
    return;
  });

  /* Events in paper basker */

  $('button#ps-basket-quit').click(function() {
    _show('#cs-problemset-navbar', '#cs-west-frame', '#cs-center-frame');
    _hide('#cs-paperbasket-navbar', '#cs-center-dialog');
    return;
  });

  $('button#ps-basket-paper').click(function() {
    var list = [];
    $('#paperbasket-list button.basket-select.btn-success').each(function() {
      list.push($(this).attr('id'));
    });
    if (list.length <= 0) {
      alert('请选择题目');
      return false;
    }
    window.location.href = ROOT + 'problemset/paper?pids=' + list.join(',');
    return;
  });

  $('button#ps-basket-clear').click(function() {
    $('#paperbasket-list .ps-row').remove();
    //_chgBtnType($('#problemset-list button.basket-add'), BTN_BASKET_ADD);
    $('#problemset-list button.basket-add').removeClass('btn-danger').addClass('btn-success').text('放入试题篮');
    _basket = {};
    return;
  });

});
