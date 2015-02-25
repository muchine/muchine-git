///##########################//
///		FlashBridge.js      ////
///////////////////////////////

function FlashBridge() {

	if(window.console == undefined){console = { log : function(){}};}

	this.connectSwf = null;

	this.getConnect = function(movieName) {
		
		if(navigator.appName.indexOf("Microsoft") != -1) {
			this.connectSwf = window[movieName];
		}else{
			this.connectSwf  = document[movieName];
		}

		console.log("getConnect ............... " + this.connectSwf );
		console.log("## cookie:"+ document.cookie);
	}

	this.closeWindow = function() {

		if(this.connectSwf == null){
			alert("객체를 가져오지 못했습니다.");
		}else{
			//console.log("this.connectSwf.SendAirDestroy();");
			this.connectSwf.SendAirDestroy();
		}
console.log("gContextPath:"+ gContextPath);
	}

	/***************************************************************************
	 * 실행위치     //수정필요
	 **************************************************************************/
	this.category = function() {
		console.log("##### get category");
		//return "mail";
		console.log("##### fileUploadType value:"+ $("#fileUploadType").val());
		return $("#fileUploadType").val();
	}
//	this.category = function(place) {
//		
//		return place;
//	}

	/***************************************************************************
	 * 개별 파일 업로드 제한  //수정필요
	 **************************************************************************/
	this.uploadFilsize = function() {
		console.log("##### get uploadFilesize");
		//byte 단위
		//return 1048576;
		var checkType = $("#fileUploadType").val();
		
		if(checkType == "mail") {
			return maiSyncEdit.limitUploadNormalFileSize;
		} else {
			return 1048576;
		}
		
	}
//	this.uploadFilsize = function(eachFileSize) {
//		
//		//byte 단위
//		//return 1048576;
//		return eachFileSize;
//		
//	}

	/***************************************************************************
	 * 전체 파일 업로드 제한  //수정필요
	 **************************************************************************/
	this.uploadTotalsize = function() {
		console.log("##### get uploadTotalsize");
		//byte  단위
		//return 5242880;
		var checkType = $("#fileUploadType").val();
		
		if(checkType == "mail") {
			return maiSyncEdit.limitUploadTotalFileSize;
		} else {
			return 5242880;
		}
	}
//	this.uploadTotalsize = function(allFileSize) {
//		
//		//byte  단위
//		//return 5242880;
//		return allFileSize;
//	}
	
	/***************************************************************************
	 * 잔여 업로드 용량   //수정필요
	 **************************************************************************/
	this.getStoragesize = function() {
		console.log("##### get getStoragesize");
		// 없으면 0으로 set
		//byte  단위
		//return 3145728;
		return 9999999999; // 2014.04.25, 현재 잔여 업로드 용량에 대한 특별한 체크가 없는 관계로 최대한 설정할 수 있는 한계치를 정한다.
	}
//	this.getStoragesize = function(remainUploadSize) {
//		
//		//byte  단위
//		//return 3145728;
//		return remainUploadSize;
//	}

	/***************************************************************************
	 * host domain      //수정필요
	 **************************************************************************/
	this.getWebSrcURL = function() {
		if (!window.location.origin) {
			  window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
		}
		console.log("##### get getWebSrcURL");
		console.log("##### srcUrl :"+ window.location.origin);
		
		//return document.domain;
		return window.location.origin;
		//return "http://abc.onnet21.com";
		//return "http://orgos.biz";

	}
//	this.getWebSrcURL = function(host) {
//		
//		//return "http://abc.onnet21.com";
//		if(host == "" || host == null) {
//			return "http://orgos.biz";
//		} else {
//			return host;
//		}
//		//return gContextPath;
//
//	}

	/***************************************************************************
	 * AIR 실행중 Web UI BLOCK 요청/해제
	 **************************************************************************/
	this.setWebBlockToAIR = function(mode) {
		console.log("##### get setWebBlockToAIR" + mode);
		// mode = "true"  --> block 요청
		// mode = "false" --> block 해제
		console.log("UI Block ::::: " + mode);
		
		if(mode == "true") {
			//console.log("return true and show the mask");
			showLoading();
//			$('#mask').css({'width':maskWidth,'height':maskHeight});
//			$('#mask').fadeIn(1000);      
//	        $('#mask').fadeTo("slow",0.8);  
		} else {
			//console.log("return false and hide the mask");
			hideLoading();
			//e.preventDefault();
		    //$('#mask').hide();  
		}


	}
	
	/***************************************************************************
	 * 업로드된 파일의 정보 
	 **************************************************************************/
	this.uploadedFileinfo = function(infos) {
		var checkType = $("#fileUploadType").val(); // AIR 모듈에 대해서 Upload가 어떤 타입인지를 정의해주는 부분(메일에 대해서는 mail로 정의됨)
		console.log("##### get uploadedFileinfo #####");
		console.log("!!!!! infos:"+ infos);
		console.log("#### finish uploadedFileInfo #####");
		var result = JSON.parse(infos);
		console.log("@@@@@ result`s id :"+ result.id);
		//alert(infos);
		hideLoading();
		//maiSyncEdit.onAttachSuccess(result, "temp_atch_file_seq", "maiEditFileMap", "myPC"); // 메일에 대해서 호출되어야 하는 부분
		console.log("##### rendering upload data #####");
		if (checkType = "mail") {
			maiSyncEdit.addAttach(result, "temp_atch_file_seq", "maiEditFileMap", "myPC"); // 메일에 대해서 호출되어야 하는 부분
		}
		console.log("##### rendering upload data #####");
	}

	/***************************************************************************
	 * 쿠키    //수정필요
	 **************************************************************************/
	this.getCookie = function() {
		var jsessionid = User.get("sessionId");
		console.log("##### get getCookie");
		console.log("## cookie:"+ jsessionid);
		return document.cookie + ";JSESSIONID=" + jsessionid;

	}
	
//	this.getCookie = function() {
//		console.log("## cookie:"+ $("#jsessionid").val());
//		//console.log("## cookie:"+ document.cookie);
//		return document.cookie + ";" + $("#jsessionid").val();
//
//	}
	
	/***************************************************************************
	 * 업로드 URL
	 **************************************************************************/
	this.getUploadURL = function() {
		console.log("##### get getUploadURL");
		return "/common/file/upload";
		//return "/common/file/upload";
	}
	// /common/file/upload?type=mail
//	this.getUploadURL = function(type) {
//
//		return "/common/file/upload?type=" + type;
//	}

	/***************************************************************************
	 * AIR msg URL   //수정필요
	 **************************************************************************/
	this.getMsgXMLURL = function() {
		console.log("##### get getMsgXMLURL");
		if (!window.location.origin) {
			  window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
		}
		return window.location.origin + "/resources/abc/air/MsgInfo.xml";
		//return "http://orgos.biz/resources/abc/air/MsgInfo.xml";
		//return document.domain + "/resources/abc/air/MsgInfo.xml";
		//return "http://abc.onnet21.com/resources/abc/air/MsgInfo.xml";
		//return "/resources/abc/js/air/MsgInfo.xml";
	}


	/***************************************************************************
	 * 허용 확장자   //수정필요 (명시된 확장자만 노출됩니다.)
	 **************************************************************************/
	this.getExts = function() {
		var arrList = new Array();
		console.log("##### get getExts");
		arrList = [".jpg",".png",".gif",".bmp",".*"];

		return arrList;
	}

	/***************************************************************************
	 * state provide   //수정 필요없음
	 **************************************************************************/
	 this._installState = "unknown";

	/** 정보를 받아들임 */
	this.setInstallState = function(state) {
		console.log("call... setInstallState");
		this._installState = state;
	}
	/** 설치 상태를 가져온다 */
	this.getInstallState = function() {
		console.log("call... getInstallState");
		return this._installState;
	}

	/***************************************************************************
	 * update info provide  
	 **************************************************************************/
	this.updateXML;

	this.getUpdateXML = function() {
		console.log("call... getUpdateXML");
		return this.updateXML;
	}

	/***************************************************************************
	 * page action 
	 **************************************************************************/
	this.installHandler = null;
	/** 설치 레이어를 보여준다 */
	this.install = function(mode) {
		$("#flashContainer4").lightbox_me(
		{
			centered : true,
			onLoad : function() {
				console.log("##### installHandler install onLoad");
    			printFlash("flashContainer4", "flinstall",
				"/resources/abc/air/UplusBizGroupwareInstallOpener.swf?foo="
				+ new Date().getTime() + "&mode="
				+ mode, 354, 228);
				}
			});
	}

	this.closeInstallHandler = null;
	/** 설치 레이어를 닫는다 */
	this.closeInstaller = function() {
		console.log("call... closeInstaller");
		$("#flashContainer4").trigger("close");
	}
	
	/** 용량 제한등으로 AIR 실행 false 일 경우 alert 호출 */
	this.webAlert = function() {
		console.log("##### call webAlert");
			alert("용량 초과 실행 안시킴");

	}
}