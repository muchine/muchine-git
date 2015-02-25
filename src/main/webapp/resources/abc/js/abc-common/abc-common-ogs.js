//=======================================================================================
//	최종수정일			: 2013.01.08	(beef2kg@navar.com, www.root2.kr)
//=======================================================================================
//	- jsImporter.js		: 1. import(include) 방식으로 필요한 js파일만 동적으로 로드하여 
//						     사용할 수 있도록 만든 라이브러리  +  
//						  2. js 도우미 함수   (common javascript)
//	- Copyright (c) by 고기두근 All rights reserved.
//=======================================================================================

//=== jsImporter 정의 시작
// 네임스페이스 축약($ogs)	:   $ogs = kr_root2_jsImporter
var kr_root2_jsImporter = {
	//-- W3C DOM 지원여부 판별
	isIEMAC		: (navigator.userAgent.toLowerCase().indexOf('mac') != -1 && navigator.userAgent.toLowerCase().indexOf('msie') != -1),
	okDOM		: (document.createElement && document.getElementsByTagName && !this.isIEMAC),
	
	//-- browser
	navi_isIE	: (navigator.appName.indexOf('Microsoft') != -1),
	navi_IEver	: 0,
	
	//-- 기본적으로 로드할 js 파일정의 (default importing)
	jsImportDirPath  	: "/resources/jsImport/",			// jsImport 폴더의 절대경로
	jsImportFile_default: [],								// 기본 로드할 jsImport 폴더의 라이브러리 파일명 지정 -> ['fn_isEmail.js', 'css_animation.js'],
	jsCommonFile_default: [],								// 기본 로드할 일반 js 파일의 경로 또는 URL 지정 -> [document.location.protocol+'//www.root2.kr/js/sample.js']	
															// 참조 : jquery.js 파일은 <header>에서 바로 import 시킨다. (로드 지연문제 방지)
	
	appendedSrc	: [],										// 현재 import 시킨 js 파일 리스트
	readyFunc	: [],										// DOM 조작가능[onReady_Start() 호출]시 실행할 함수 리스트

	//=== 기본 메서드 정의		================================================
	//-- kr_root2_jsImporter 초기화 메서드
	init		: function() {
		// IE. version set
		if(this.navi_isIE) {
			var brVer 		= navigator.userAgent;			// ex) Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)
			var brVerId 	= brVer.indexOf('MSIE');		// 25
			var brNum 		= brVer.substr(brVerId,8);		// MSIE 6.0
			this.navi_IEver = parseInt(brNum.substr(5,3));	// 6.0 -> 6
		}
		
		// 기본(default) 로드할 파일 import(include) 처리
		this.importing(this.jsImportFile_default, "jsImportFile");
		this.importing(this.jsCommonFile_default, "jsCommonFile");
	},
	
	browser : {
		isIE8 : function() {
			return User.get("browser") == "IE8";
		}
	},
	
	//-- js 파일 리스트를 import(include) 시켜주는 메서드
	importing	: function(arrSrc, srcType) {
		if(!this.okDOM)    { return; }
		if(!arrSrc.length) { return; }
		if(srcType != "jsImportFile" && srcType != "jsCommonFile") { return; }
		
		for(var jsSrc, appended, i=0; i < arrSrc.length; i++) {
			jsSrc = arrSrc[i];
			
			// jsImportFile 폴더의 파일이면 jsImportDirPath 경로를 추가하고,
			// 일반 파일인 경우는 직접 지정한 소스파일 경로로 import 시킨다.
			jsSrc = (srcType == "jsImportFile") ? this.jsImportDirPath + jsSrc : jsSrc;
			appended = false;
			
			for(var j=0; j < this.appendedSrc.length; j++) {
				if(jsSrc == this.appendedSrc[j]) {		// import 중복등록 방지
					appended = true;
					break;
				}
			}
			
			if(!appended) {	//alert(jsSrc);
				this.jsAppend(jsSrc);
			}
		}
	},
	
	//-- import 시킬 js 파일을 head 태그에 appending 처리
	jsAppend 		: function(jsSrc) {
		var newScript = document.createElement("script");
			newScript.src = jsSrc;
			document.getElementsByTagName("head")[0].appendChild(newScript);
			
		this.appendedSrc.push(jsSrc);
	},
	
	//-- DOM 조작가능[onReady_Start() 호출]시 실행할 함수 추가
	addFunc_onReady	: function() {						// 호출방식 - $rt.addFunc_onReady(function [, Attr1, Attr2...]);
		if( (arguments.length < 1) || (typeof arguments[0] !== "function") ) { return; }
		
		var addFunc = arguments[0];
		for(var f=0; f < this.readyFunc.length; f++) {	// 중복등록 방지
			if(addFunc == this.readyFunc[f]) { return; }
		}
		
		var addAttr = [];
		for(var i=1; i < arguments.length; i++) {
			addAttr[i-1] = arguments[i];
		}
		addFunc.Attr = addAttr;
		
		this.readyFunc.push(addFunc);
	},
	
	//-- DOM 조작가능시 처리할 함수 리스트를 실행 : readyFunc.shift()
	onReady_Start	: function() {
		while(this.readyFunc.length) {
			var onFunc = this.readyFunc.shift();
				onFunc.apply(this, onFunc.Attr);
		}
	},
	
	//-- kr_root2_jsImporter 디버깅 메서드 : addEvent(onload)를 이용해서 검사할 경우, this는 사용할 수 없다.
	onDebug		: function() {
		var appendSrc_Text = "";
		for(idx in kr_root2_jsImporter.appendedSrc) {
			appendSrc_Text += "["+idx+"] - "+kr_root2_jsImporter.appendedSrc[idx]+"\n";
		}
		alert(appendSrc_Text);
	},
	
	//-- 스크립트 처리 지연 메서드 (기본 500 milliseconds)
	onDelay		: function(milliSeconds) {
		milliSeconds = ( parseInt(milliSeconds) > 0 ) ? milliSeconds : 500;
		
		var now = new Date();
		var exitTime = parseInt(now.getTime()) + parseInt(milliSeconds);
		var flag = true;
		
		while (flag) {
			now = new Date();
			if (now.getTime() > exitTime)
		  		flag = false;
		}
		return true;
	},
	
	//=== Data 출력 및 이동 관련 메서드	====================================
	alert				: function(obj) {
		obj = (obj == "") ? "later on...," : obj;
		var output = "";
		
		if(obj.constructor == String || obj.constructor == Number || obj.constructor == Boolean) {
			output = obj;
		}
		else {	// Object, Array 출력
			try {
				for(var i in obj) {
					if(obj[i].constructor == Function) { continue; }		// 함수는 출력에서 제외
					output += i + " -> " + obj[i] + "  (" + (typeof obj[i]) + ")\n";
				}
			}
			catch (err) {
				output = obj;	// HTML Element.. 등
			}
		}
		alert(output);
	},
	
	goUrl		: function(goUrl, str) {
		if(!goUrl) { return; }
		if(str && !str.isEmpty()) { kr_root2_jsImporter.alert(str); }		
		location.href= goUrl;
	},
	
	replaceUrl	: function(goUrl, str) {	// alert_goUrl 과 차이점은 페이지 history를 남기지 않는다.
		if(!goUrl) { return; }				// ex) 프로세싱 파일에서 주소기록을 남기지 않을 때 호출 - 뒤로가기 오류방지
		if(str && !str.isEmpty()) { kr_root2_jsImporter.alert(str); }
		location.replace(goUrl);
	},
	
	historyBack	: function(str) {
		if(str && !str.isEmpty()) { kr_root2_jsImporter.alert(str); }
		history.back();
	},
	
	selfClose	: function(str) {
		if(str && !str.isEmpty()) { kr_root2_jsImporter.alert(str); }
		self.close();
	},
	
	//=== 이벤트 관련 메서드			====================================
	addEvent	: function(obj, evt, func) {		// 이벤트 추가()
		if(obj.addEventListener)					// 인자 evt - 이벤트네임(load, click...)
			obj.addEventListener(evt, func, false);
		else if(obj.attachEvent)
			obj.attachEvent("on"+evt, func);
	},
	
	removeEvent	: function(obj, evt, func) {		// 이벤트 삭제()
		if(obj.removeEventListener)
			obj.removeEventListener(evt, func, false);
		else if(obj.detachEvent)
			obj.detachEvent("on"+evt, func);
	},
	
	preventDefault : function(e) { 					// 기본 이벤트 취소(e - 이벤트객체)
		var evt = e || window.event;
		if(evt.preventDefault)
			evt.preventDefault();
		else
			evt.returnValue = false;
	},
	
	stopBubble	: function(e) {						// 이벤트 버블링 차단(e - 이벤트객체)
		var evt = e || window.event;
		if (typeof evt.stopPropagation != "undefined")
			evt.stopPropagation();
		else
			evt.cancelBubble = true;
	},
	
	//=== 쿠키 관련 메서드			====================================
	cookie		: {
		// LoacalStorage 미지원 브라우저의 대체 처리를 위한 Cookie 처리 내장함수
		setItem		: function(name, val, sec) {
			if(!navigator.cookieEnabled) { return; }
			var expires = "";
			
			if(sec) {
				var date = new Date();
				date.setTime(date.getTime() + sec*1000);
				expires = "; expires="+date.toGMTString();
			}
			document.cookie = name+"="+val+expires+"; path=/";
		},
		
		getItem		: function(name) {
			if(!navigator.cookieEnabled) { return null; }
			
			var searchName = name + "=";
			var arrName = document.cookie.split(';');
			
			for(var i=0;i < arrName.length;i++) {
				var strCookie = arrName[i];
				while(strCookie.charAt(0)==' ') {
					strCookie = strCookie.substring(1,strCookie.length);
				}
				if(strCookie.indexOf(searchName) == 0) {
					return strCookie.substring(searchName.length, strCookie.length);
				}
			}
			return null;
		},
		
		removeItem	: function(name) {
			this.setItem(name, "", -1*24*60*60*1000);	// 하루
		}
	},
	
	//=== Xml, Ajax 관련 메서드		====================================
	xml			: {
		createHttp 	: function() {
			var xmlhttp = false;
			var xmlhttpVersions = [
				function() { return new XMLHttpRequest(); },
				function() { return new ActiveXObject("Msxm2.XMLHTTP.6.0"); },
				function() { return new ActiveXObject("Msxm2.XMLHTTP.5.0"); },
				function() { return new ActiveXObject("Msxm2.XMLHTTP.4.0"); },
				function() { return new ActiveXObject("Msxm2.XMLHTTP.3.0"); },
				function() { return new ActiveXObject("Msxm2.XMLHTTP"); },
				function() { return new ActiveXObject("Microsoft.XMLHTTP"); }
			];
			
			for(var i=0; i < xmlhttpVersions.length; i++) {
				try {
					xmlhttp = xmlhttpVersions[i]();
				}
				catch (err) {
					continue;
				}
				break;
			}
			return xmlhttp;
		}
	},
	
	//=== Html5, Api 관련 메서드		====================================
	h5api		: {
		// loacalStorage (쿠키 대신, localStorage 사용권장)
		localStorage	: {
			setItem		: function(name, val, sec) {	// sec : 옵션인자 - localStorage 미지원시, 쿠키대용 만료시간
				if(!name || !val)	{ return; }
				
				if(typeof localStorage === "object")
					localStorage.setItem(name, val);
				else
					kr_root2_jsImporter.cookie.setItem(name, val, sec);
			},
			
			getItem 	: function(name) {
				if(!name)	{ return; }
				
				if(typeof localStorage === "object")
					return localStorage.getItem(name);
				else
					return kr_root2_jsImporter.cookie.getItem(name);
			},
			
			removeItem	: function(name) {
				if(typeof localStorage === "object") {
					localStorage.removeItem(name);
				}
				else {
					return kr_root2_jsImporter.cookie.removeItem(name);
				}
			},
			
			clear		: function() {
				if(typeof localStorage === "object") {
					localStorage.clear();
				}
				else {
					// nothing - Storage 대용으로 생성된 쿠키와 직접생성한 쿠키가 섞어있으므로,
					// 쿠키 clear는 안 함.
				}
			}
		}, // end.localStorage

		sessionStorage	: {}		// sessionStorage 유틸리티는 필요시, import 해서 사용.(h5api_sessinoStorage.js)
	}, // end.h5api Category
	
	
	//=== jsImport 폴더의 Library Category	====================================
	//	cookie	: 내장 함수로 선언	- 쿠키 관련 메서드
	//	xml		: 내장 함수로 선언	- Xml, Ajax 관련 메서드
	//	h5api	: 내장 함수로 선언	- Html5, Api 관련 메서드
	fn			: {},				// core, data Valid 등 일반함수 관련 카테고리
	evt			: {},				// 이벤트 관련 카테고리
	dom			: {},				// DOM 조작 등 Element 관련 카테고리(insertAfter, 위치값 얻기, iframe 높이값 얻기 등)
	css			: {},				// css, animation 관련 카테고리
	cls			: {}				// 그 외 class 라이브러리 (클래스함수)
};

kr_root2_jsImporter.init();			// 초기화
$ogs = kr_root2_jsImporter;			// 네임스페이스 재정의(축약)

//=====    dom_getOffsetLeftTop.js    	=============
//최종수정일					: 2011.03.27 (beef2kg@navar.com, www.root2.kr)
//element(요소) 오프셋 위치(left, top) 얻기
kr_root2_jsImporter.dom.getOffsetLeftTop = function(element) {
	
	// 반환 값(left, top)
	var reVal = {};
		reVal.left = reVal.top = 0;
		
	// left 값 얻기
	for(var el=element; el; el=el.offsetParent) {
		reVal.left += el.offsetLeft;
	}
	
	for(el=element.parentNode; el && el != document.body; el=el.parentNode) {
		if(el.scrollLeft) { reVal.left -= el.scrollLeft; }
	}
	
	// Top 값 얻기
	for(var el=element; el; el=el.offsetParent) {
		reVal.top += el.offsetTop;
	}
	
	for(el=element.parentNode; el && el != document.body; el=el.parentNode) {
		if(el.scrollTop) { reVal.top -= el.scrollTop; }
	}
	
	return reVal;
};

//=====    파일 생성할 것! dom_getZindex_highest.js    	=============
//최종수정일					: 2013.06.05 (beef2kg@navar.com, www.root2.kr)
//최상위 z-index 값 얻기
kr_root2_jsImporter.dom.getZindex_highest = function() {
	var elements = document.getElementsByTagName("*");
	var highest_index = 0;

	for (var i = 0; i < elements.length - 1; i++) {
	    if (parseInt(elements[i].style.zIndex) > highest_index) {
	        highest_index = parseInt(elements[i].style.zIndex);
	    }
	}
	return highest_index;
};

//==============================================================================
//	Common 함수					: jsImport 폴더의 유틸리티 파일 중 자주 사용하는 함수 발췌.
//==============================================================================
//	Form 요소 관련 함수			: jsImport/fn_formInput.js
//------------------------------------------------------------------------------
//		- setTrimInput(HTMLInputElement)		: input Element 의 값을 trim 한 값으로 reset 한 후, 그 값을 반환한다.
//		- isCheckedInput(HTMLInputElement)		: 체크박스, 라디오버튼 선택유무 확인, 선택된 것이 있으면 true 반환
//		- getCheckedInputIndex(HTMLInputElement): 체크박스, 라디오버튼의 선택된 인덱스 리스트를 Array 값으로 반환 

// input Element 의 값을 trim 한 값으로 reset 한 후, 그 값을 반환.
kr_root2_jsImporter.fn.setTrimInput = function(oInput) {
	oInput.value = oInput.value.replace(/^\s*/,'').replace(/\s*$/, '');
	return oInput.value;
};

// 체크박스, 라디오버튼 선택유무 확인 (선택된 것이 있으면 true)
kr_root2_jsImporter.fn.isCheckedInput = function(oInput) {
	if(oInput.length > 1){
		for(var i=0; i < oInput.length; i++) {
			if(oInput[i].checked) return true;
		}
	}
	else if(oInput.checked) {	// 체크박스 or 라디오버튼 요소가 1개인 경우
		return true;
	}
	return false;
};

// 체크박스, 라디오버튼의 선택된 인덱스 리스트를 Array 값으로 반환 
kr_root2_jsImporter.fn.getCheckedInputIndex = function(oInput) {
	var idxArr = [];
	
	if(oInput.length > 1){
		for(var i=0; i < oInput.length; i++) {
			if(oInput[i].checked) idxArr.push(i);
		}
	}
	else if(oInput.checked) {	// 체크박스 or 라디오버튼 요소가 1개인 경우
		idxArr.push(0);
	}
	return idxArr;
};



//==============================================================================
//	기본 자료형 & 내장객체 Prototype Set
//==============================================================================
//	String Prototype Set		: jsImport/prototype_String.js
//------------------------------------------------------------------------------
//	Core Object 관련 prototype 정의 함수 
// (Core prototype 정의는 최소범위 내에서만 재정의하자.! - 다른 개발자 혼동유발 방지)
//
//		- isNull()				: 문자열 내용이 비어있는 확인 (문자열이 null 또는 "" 이면 true)
//		- isEmpty()				: 문자열 내용이 비어있는 확인 (문자열이 null 또는 "" 또는 공백("  ") 이면 true)

//		- isEn()				: 영어 여부 확인(char)
//		- isEnStr()				: 오직 영어로만 된 문자열인지 확인
//		- isNum()				: 숫자 여부 확인(char)
//		- isNumStr()			: 오직 숫자로만 된 문자열인지 확인
//		- isEnNum()				: 오직 영어 + 숫자로만 된 문자열인지 확인
//		- isHan()				: 한글 유무 확인 (한글이 포함되어 있으면 true;)
//
//		- isHexEn()				: 색상값 - Hex 코드 영문자인지 확인(char : a ~ f)
//		- isHexStr()			: 색상값 - Hex 코드 문자열 유무 확인(Hex 영문자 + 숫자 조합) 
//
//		- hasChars(chars)		: 특정문자가 포함되어 있는지 확인 	(특수문자 입력여부 확인 : chars = "!@#$%^&*~:;,.\|")
//		- hasOnlyChars(chars)	: 특정문자만을 포함하고 있는지 확인 (허용문자만 입력여부 확인, 전화번호 : char = "-0123456789")
//		- getByteLen()			: 문자열의 byte 크기 반환
//		- getCutStrByte()		: 문자열을 지정한 byteLen 크기(Byte) 만큼 자른후 문자열 반환
//		- getCutStr()			: 문자열을 지정한 len 보다 크면 len 만큼 자른후 defStr 붙여서 문자열 반환
//
//		- trim()				: trim (Left + Right)
//		- trimLeft()			: trim (Left)
//		- trimRight()			: trim (Right)

//		- removeSpace()			: 공백문자 제거
//		- removeSpaceEnter()	: 공백문자 + Enter 제거
//		- removeComma()			: 콤마문자(',') 제거
//		- addCommaWon()			: 콤마문자를 원단위로 추가 (원단위로 표시 : 1000 -> 1,000)

//		- lpad(padString, length)
//		- isIP()

String.prototype.isNull = function() {				// 문자열이 null 또는 "" 이면 true
	return (this == null || this == "") ? true : false;
};
String.prototype.isEmpty = function() {				// 문자열이 null 또는 "" 또는 공백("  ") 이면 true
	return (this == null || this.replace(/ /gi,"") == "") ? true : false;
};

String.prototype.isEn = function() {				// 영어 char 면 true
	return (this.match(/[a-z]/gi) == null) ? false : true;
};
String.prototype.isEnStr = function() {				// 오직 영어로만 된 문자열이면 true
	for(var i=0, ch=""; i< this.length; i++) {
		ch = this.charAt(i);
		if(!ch.isEn()) {
			return false;
			break;
		}
	}
	return true;
};
String.prototype.isNum = function() {				// 숫자 char 면 true
	return (this.match(/[0-9]/g) == null) ? false : true;
};
String.prototype.isNumStr = function() {			// 오직 숫자로만 된 문자열이면 true
	for(var i=0, ch=""; i< this.length; i++) {
		ch = this.charAt(i);
		if(!ch.isNum()) {
			return false;
			break;
		}
	}
	return true;
};
String.prototype.isEnNum = function() {				// 오직 영어나 숫자로만 된 문자열이면 true
	for(var i=0, ch=""; i< this.length; i++) {
		ch = this.charAt(i);
		if(!ch.isEn() && !ch.isNum()) {
			return false;
			break;
		}
	}
	return true;
};
String.prototype.isHan = function() {				// 한글이 포함되어 있으면 true
	var rVal = false;
	
	for(var i=0, ch=""; i< this.length; i++) {
		ch = this.charAt(i);
		if(escape(ch).length > 4) {
			rVal = true;
			break;
		}
	}
	return rVal;
};

String.prototype.isHexEn = function() {				// char 가 색상값, Hex 코드 영문자(char : a ~ f) 이면 true
	return (this.match(/[a-f]/gi) == null) ? false : true;
};
String.prototype.isHexStr = function() {			// 색상값 문자열(Hex 영문자 + 숫자 조합) 이면 true
	if(this.length != 6) {
		return false;
	}
	
	for(var i=0, ch=""; i< this.length; i++) {
		ch = this.charAt(i);
		if(!ch.isHexEn() && !ch.isNum()) {
			return false;
			break;
		}
	}
	return true;
};

String.prototype.hasChars = function(chars) {		// 특정문자가 포함되어 있는지 확인 	(특수문자 입력여부 확인 : chars = "!@#$%^&*~:;,.\|")
	for (var i=0; i < this.length; i++){
		if (chars.indexOf(this.charAt(i)) != -1){
			return true;
		}
	}
	return false;
};
String.prototype.hasOnlyChars = function(chars) {	// 특정문자만을 포함하고 있는지 확인(허용문자만 입력여부 확인, 전화번호 : char = "-0123456789")
	for (var i=0; i < this.length; i++){
		if (chars.indexOf(this.charAt(i)) == -1){
			return false;
		}
	}
	return true;
};
String.prototype.getByteLen = function() {			// 문자열의 byte 크기 반환
	var count = 0;
	for ( var i = 0; i < this.length; i++ ) {
		var onechar = this.charAt( i );
		if ( this.charCodeAt( i ) >= 128 ) {
			count += 2;
		} else if ( onechar != '\r' ) {
			count++;
		}
	}
	return count;
};

String.prototype.getCutStrByte = function(byteLen) { // 문자열을 지정한 byteLen 크기(Byte) 만큼 자른후 문자열 반환
	var tmpStr = this;
	while (tmpStr.getByteLen() > byteLen) {
		var len = tmpStr.length;
		tmpStr = tmpStr.substr( 0, len - 1 );
	}
	return tmpStr;
};

String.prototype.getCutStr = function(StrLen, defStr) { // 문자열을 지정한 StrLen 보다 크면 len 만큼 자른후 defStr 붙여서 문자열 반환
	var tmpStr = this;
	if (this.length > StrLen) {
		tmpStr = tmpStr.substr( 0, StrLen) + defStr;
	}
	return tmpStr;
};

String.prototype.trim = function() {				// trim (left + right)
	return this.replace(/^\s*/,'').replace(/\s*$/, '');
};
String.prototype.trimLeft = function() {			// trim (left)
	return this.replace(/(^ *)/g, "");
};
String.prototype.trimRight = function() {			// trim (right)
	return this.replace(/( *$)/g, "");
};
String.prototype.removeSpace = function() {			// 공백문자 제거
	return this.replace(/(\n| )/g,"");
};
String.prototype.removeSpaceEnter = function() {	// 공백문자 + Enter 제거
	return this.replace(/\s/g,"");
};
String.prototype.removeComma = function() {			// 콤마문자(',') 제거
	return this.replace(/,/gi,"");
};
String.prototype.addCommaWon = function() {			// 콤마추가(원단위 콤마추가 : 1,000)
	var reg = /(^[+-]?\d+)(\d{3})/;
	var str = this;
	while (reg.test(str)) {
		str = str.replace(reg, '$1' + ',' + '$2');
	}
	
	return str;
};
String.prototype.lpad = function(padString, length) {
    var str = this;
    while (str.length < length)
        str = padString + str;
    return str;
};
String.prototype.isIP = function() {
	//console.log(this.split(".").length);
	if(this.length == 0 || this.length > 15 || this.split(".").length !=4 || !(this.split(".").join("").hasOnlyChars("0123456789*"))) return false;
	else return true;
};


//==============================================================================
//	Array Prototype Set		: jsImport/prototype_Array.js
//------------------------------------------------------------------------------
//배열에 특정값이 포함되어 있는지 확인
Array.prototype.hasValue = function(obj) {
	var flag = false;

	for(var i=0; i < this.length; i++) {
		if(this[i] == obj) {
			flag = true;
			break;
		}
	}
	return flag;
};
// 배열의 특정 요소 제거 - by Index
Array.prototype.removeByIndex = function(index) {
	return this.splice(index, 1);
};
// 배열의 특정 요소 제거 - by value
Array.prototype.removeByValue = function(val) {
    for(var i=0; i<this.length; i++) {
        if(this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
};

//==============================================================================
//  Date Prototype Set		: jsImport/prototype_Date.js
//------------------------------------------------------------------------------
// 날짜 사이의 간격(일) 구하기
Date.prototype.getInterval = function(otherDate) {
	var interval;

	if(this > otherDate) {
		interval = this.getTime() - otherDate.getTime();
	}
	else {
		interval = otherDate.getTime() - this.getTime();
	}

	return Math.floor(interval / (1000 * 60 * 60 * 24));
};



//=== IE. console.log 오류 처리	==================
if(! window.console) {				// 개발자 모드가 아닌 경우는 IE. console 지원 안 함.
	window.console = {};
	window.console.log   = function() {};
	window.console.debug = function() {};
}
else {
	if(! window.console.debug) {	// IE 는 debug 지원 안 함. -> IE. console 로 변경
		window.console.debug = window.console.log; 
	}
	else {
		// 주석해제 == debug 출력 숨기기
		//window.console.debug = function() {};
	}
	// 주석해제 == log 출력 숨기기
	//window.console.log   = function() {};
}