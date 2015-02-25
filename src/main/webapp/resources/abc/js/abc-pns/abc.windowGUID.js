/**
 * @author 이신희
 * 탭별로 별도의 고유값을 생성한다.
 * 브라우즈 1개, 아이디1개,  탭2개 의 경우 스트로피 접속시에 같은 jsessionId로 메신저 서버에 접속을 한다.
 * 같은 jsessionid로 두개의 브라우즈에서 접속을 한경우 다른 탭에 있는 연결을 끊는다.
 * 끊기 브라우즈에서는 다시 접속을 시도하며, 다른 브라으즈의 접속을 끊는다. 서로 끊어다, 붙였다를 반복한다.
 * 텝별로 다른 jsessionid로 접속하기 위하여 windowGuid(탭별고유값)을 붙여서 메신저 서버에 접속한다.
 * 사용법 :
 *  WindowGUID.windowLoadSetGUID();
 *  WindowGUID.windowLoadSetGUIDOnForms();
 *  
 *  WindowGUID.getWindowGUID();  //실제로 탭별 고유값을 가지고 온다.
 */
var WindowGUID = {
	getWindowGUID : function(){
		 var windowGUID = function () {
		        //----------
		        var S4 = function () {
		            return (
		                    Math.floor(
		                            Math.random() * 0x10000 /* 65536 */
		                        ).toString(16)
		                );
		        };
		        //----------

		        return (
		                S4() + S4() + "-" +
		                S4() + "-" +
		                S4() + "-" +
		                S4() + "-" +
		                S4() + S4() + S4()
		            );
		    };
		    //----------------------------------

		    //-- traverses up in the hierarchy for the outermost window ----------------

		    var topMostWindow = window;

		    while (topMostWindow != topMostWindow.parent) {
		        topMostWindow = topMostWindow.parent;
		    }

		    //-- initialize GUID if needed ---------------------------------------------

		    if (!topMostWindow.name.match(/^GUID-/)) {
		        topMostWindow.name = "GUID-" + windowGUID();
		    }

		    //-- return GUID -----------------------------------------------------------

		    return topMostWindow.name;
	},
	windowLoadSetGUID : function(){
		var dummy = this.getWindowGUID();
	},
	windowLoadSetGUIDOnForms : function(){
		var formList = $('form');
	    var hidGUID = document.createElement("input");

	    hidGUID.setAttribute("type", "hidden");
	    hidGUID.setAttribute("name", "this.window.GUID");
	    hidGUID.setAttribute("value", this.getWindowGUID());

	    console.log(formList.length);
	    if (formList.length == 1) {
	        formList.append(hidGUID);
	    }else {
	        for (var i = 0; i < formList.length; ++i) {
	        	console.log("["+hidGUID+"]")
	            //formList[i].append(this.hidGUID);
	        }
	    }
	}
	
};
	
	