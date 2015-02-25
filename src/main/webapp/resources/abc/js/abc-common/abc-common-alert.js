/**
 * 공통 Alert & Confirm
 * @example comm_alert.jsp
 * @author 하영
 */
(function() {
  var _alertTemplate = '# if (title != null) { #';
  _alertTemplate += '<div class="k-window-titlebar k-header"><span class="k-window-title">#=title#</span>';
  // _alertTemplate += '<div class="k-window-actions"></div>'; // 추후 확장을 위해 남겨놈.
  _alertTemplate += '</div>';
  _alertTemplate += '# } #';
  _alertTemplate += '<div class="alert_warp"><div class="alert_cont txtAlignC">';
  _alertTemplate += '# if (className != "") { #<span class="disInline #=className#"></span># } #';
  _alertTemplate += '<span class="disInline alert_txt">#=message#</span>';
  _alertTemplate += '<div class="marginC " abc-role="buttonbar">';
  _alertTemplate += '# for (var i = 0, len = buttons.length; i < len; i++) { #';
  _alertTemplate += '<button class="k-button #=buttons[i].className#" type="button" title="" value="">#=buttons[i].text#</button>';
  _alertTemplate += '# } #';
  _alertTemplate += '</div></div></div>';

  commonAlert = function(options, templateOptions) {
    var _defaultTemplateOptions = {
      title : null,
      message : "",
      className : "",
      buttons : [ {
        "text" : "확인",
        "className" : "",
        "onClick" : null
      } ]
    };

    // fixed option
    var _forceOptions = {
      actions : '',
      title : false,
      modal : true,
      iframe : false, // ?!
      open : function(e) {
        $target.find('[abc-role="buttonbar"] button.k-button').click(function(e) {
          var oc = templateOptions.buttons[$(this).index()].onClick;
          (oc && oc.call($target, $target.find('.alert_cont:first')));
          e.preventDefault();
          _alert.close();
        });

        (options.open && options.open.call(this, e, this.element.find('.alert_cont:first')));
      },
      close : function() {
        // TODO 켄도 UI 자원 해제 확인
        _alert.destroy();
        $target.remove();
      }
    };

    // can be optional and more use with kendo.window options
    var _defaultOptions = {
      draggable : false,
      resizable : false,
      minWidth : 220,
      title : false,
      content : {
        template : kendo.template(_alertTemplate)($.extend(true, _defaultTemplateOptions, templateOptions))
      }
    };

    var $target = $('<div class="abc_alert">').appendTo('body');
    var _alert = $target.kendoWindow($.extend(_defaultOptions, options, _forceOptions)).data('kendoWindow').center();
    
    return _alert;
  };

  /**
   * 엔터 혹은 스페이스키 누를시, 알림 닫힘.
   * 
   * @example
   * <pre>
   * alert_('제목', '내용', 콜백함수);
   * alert_('내용', 콜백함수);
   * </pre>
   * 
   * @param {String} title - 제목
   * @param {String} message - 내용
   * @param {Function} callback(div.alert-content-wrap) 확인 후 콜백함수
   */
/*
	alert_ = function(options) {
		options.msg = options.message;
		layCmn.alert(options);
	};
*/
  
  alert_ = function(options) {
    var _templateOptions = {
      className : 'o-i-layalert',
      buttons : [ {
        text : "확인",
        // className : "mailsendBtn",
        onClick : options.callback
      } ]
    };
  
    commonAlert({
      'open' : function() {
        var t = this;
        $(document).on('keyup.alert_', function(e) {
          // ENTER or ESC
          if (e.keyCode == 13 || e.keyCode == 27) {
            $(this).off('keyup.alert_');
            t.element.find('.k-button:first').click();
          }
        });
      }
    }, $.extend(_templateOptions, options));
  };

  /**
   * @example
   * <pre>
   * alert_('제목', '내용', 콜백함수);
   * alert_('내용', 콜백함수);
   * </pre>
   * 
   * @param {String} title - 제목
   * @param {String} message - 내용
   * @param {Function} callback(true/false, div.alert-content-wrap) 콜백함수
   */
/*
	confirm_ = function(options) {
		options.msg = options.message;
		layCmn.confirm(options);
	};
*/
	
  confirm_ = function(options) {
    var _templateOptions = {
      className : 'o-i-layalert_q',
      buttons : [ {
        text : "확인",
        className : "",
        onClick : function(e) {
          var cb = options.callback;
          (cb && cb.call(this, true, e));
        }
      }, {
        text : "취소",
        className : "",
        onClick : function(e) {
          var cb = options.callback;
          (cb && cb.call(this, false, e));
        }
      } ]
    };

    commonAlert({
      'open' : function() {
        var t = this;
        $(document).on('keyup.confirm_', function(e) {
          switch (e.keyCode) {
          case 13: // ENTER
            t.element.find('.k-button:first').click();
            break;
          case 27: // ESC
            t.element.find('.k-button:last').click();
            break;
          default:
            return;
          }
          $(this).off('keyup.confirm_');
        });
      }
    }, $.extend(_templateOptions, options));
  };
})();