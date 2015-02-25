///##########################//
///		FlashUtils.js      ////
///////////////////////////////

if (!console) {
	var console = {
		log : function(str) {
		}
	}
}

function falert(msg) {
	//parent.list.document.getElementById("alert_layer_msg").innerHTML = msg;
	//parent.list.document.getElementById("alert_layer").style.display = "block";
	alert(msg);
}

function printFlash(containerid, objid, swfuri, width, height, quality,
		allowScriptAccess, bgcolor) {

//	if (!quality) {
//		quality = "high";
//	}
//	if (!bgcolor) {
//		bgcolor = "#ffffff";
//	}
//	if (!allowScriptAccess) {
//		allowScriptAccess = "sameDomain";
//	}
//
//
//	var str = '';
//	str += '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"';
//	str += ' id="' + objid + '" width="' + width + '" height="' + height + '"';
//	str += ' codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">';
//	str += '<param name="movie" value="' + swfuri + '" />';
//	str += '<param name="quality" value="' + quality + '" />';
//	str += '<param name="bgcolor" value="' + bgcolor + '" />';
//	str += '<param name="wmode" value="transparent"/>';
//	str += '<param name="allowScriptAccess" value="' + allowScriptAccess
//			+ '" />';
//	str += '<embed src="' + swfuri + '" quality="' + quality + '" bgcolor="'
//			+ bgcolor + '"';
//	str += ' width="' + width + '" height="' + height + '" name="' + objid
//			+ '" align="middle"';
//	str += ' play="true" loop="false" allowScriptAccess="' + allowScriptAccess
//			+ '"';
//	str += ' type="application/x-shockwave-flash"';
//	str += ' pluginspage="http://www.macromedia.com/go/getflashplayer">';
//	str += '</embed>';
//	str += '</object>';
//
//	var container = document.getElementById(containerid);
//	container.innerHTML = str;
	
	if (!quality) {
        quality = "high";
	}

	if (!bgcolor) {
        bgcolor = "#ffffff";
	}

	if (!allowScriptAccess) {
        allowScriptAccess = "sameDomain";
	}

	var str = '';

	str += '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"';
	str += ' id="' + objid + '" width="' + width + '" height="' + height + '"';
	str += ' codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">';
	str += '<param name="movie" value="' + swfuri + '" />';
	str += '<param name="quality" value="' + quality + '" />';
	str += '<param name="bgcolor" value="' + bgcolor + '" />';
	str += '<param name="wmode" value="opaque "/>';   // flash 위에 html 엘리먼트를 위치시킬 때
	str += '<param name="allowScriptAccess" value="' + allowScriptAccess+ '" />';
	str += '<embed src="' + swfuri + '" quality="' + quality + '" bgcolor="' + bgcolor + '"';
	str += ' width="' + width + '" height="' + height + '" name="' + objid + '" align="middle"';
	str += ' play="true" loop="false" allowScriptAccess="' + allowScriptAccess + '"';
	str += ' wmode="opaque"';
	str += ' type="application/x-shockwave-flash"';
	str += ' pluginspage="http://www.macromedia.com/go/getflashplayer">';
	str += '</embed>';
	str += '</object>';

	var container = document.getElementById(containerid);
	container.innerHTML = str;
}

function getSwfMovie(SwfObjectIdAndEmbedName) {
	if (navigator.appName.indexOf("Microsoft") != -1) {
		return window[SwfObjectIdAndEmbedName];
	} else {
		return document[SwfObjectIdAndEmbedName];
	}
}