/* ===================================================  
2013.06.26 100c  
=================================================== */

// common var ******************************************************************
var _regex_num_slash_dash_colon = /[0-9\/\-\:\s*]$/;
var _regex_num_slash_dash = /[0-9\/\-]$/;
var _regex_num_colon = /[0-9\:]$/;

// return old valid value
var _day_delimit = "/";

var _afterRegex = "";

var _dtNow = new Date();

var _addMin = 0;

var _newDt = new Date( _dtNow.getTime() + _addMin * 60 * 1000 );

var _newDate = _newDt.getFullYear() 
				+ _day_delimit 
				+ ("0" + (_newDt.getMonth() + 1)).slice(-2) 
				+ _day_delimit 
				+ ("0" + _newDt.getDate()).slice(-2);

//console.log("_newDate:" + _newDate );

var _newH = ("0" + _newDt.getHours()).slice(-2);
var _newM = ("0" + _newDt.getMinutes()).slice(-2);

// var _min0 = _newM.substr( 0, 1);

// console.log("newM:" + _newM );
// console.log("min0:" + _min0 );


// var newM2 = min0 + "0"; 
// console.log("newM2:" + newM2 );
// var _newTime = _newH + ":" + _newM;
var _newTime = _newH + ":00";
var _newTimeSs = _newH + ":00:00";

//console.log("_newTime:" + _newTime );

var ValidDateJsPrv = {

		Left : function (str, n){
			if (n <= 0)
			    return "";
			else if (n > String(str).length)
			    return str;
			else
			    return String(str).substring(0,n);
		},
		
		Right : function (str, n){
		    if (n <= 0)
		       return "";
		    else if (n > String(str).length)
		       return str;
		    else {
		       var iLen = String(str).length;
		       return String(str).substring(iLen, iLen - n);
		    }
		},
		
		getNowDate : function (delimit){
			
			if( delimit == "/" ){
				return _newDate;
			}
			else{
				return _newDate.replace(/\//g,delimit);
			}
			
		},
		
		getNowTime : function (){
			return _newTime;
		},
		
		getNowTimeSs : function (){
			return _newTimeSs;
		}
		
};

var ValidDateJs = {

		// for Date Type =======================================================
		
		formatDate_keyup : function (event, obj, delimit, exVal){
			
			if(event.keyCode == 13 || event.keyCode == 37 || event.keyCode == 38) return;
			
			// @@todo 좌우 화살표도 return 시키기
			
			this.dateFormat_KeyUp(obj, delimit, exVal);
		},
		
		formatDate_onblur : function (event, obj, delimit, exVal){
			this.dateFormat_Blur(obj, delimit, exVal);
		},
	
		dateFormat_Blur : function (obj, delimit, exVal) {
			
			var val = $(obj).val();
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			//alert( onlyNum.length );
			
			if ( onlyNum.length == 0 && val.length == 0){
				return;
			}
			else if ( onlyNum.length == 4 || onlyNum.length == 6 || onlyNum.length == 8 ){

				if ( onlyNum.length == 4 ){
					onlyNum = onlyNum + "0101"; 
				}
				else if ( onlyNum.length == 6 ){
					onlyNum = onlyNum + "01";
				}
				else if ( onlyNum.length == 8 ){
					return;
				}	
//			console.log(onlyNum);
			
				$(obj).val( onlyNum );
				//alert("go");
				this.dateFormat_KeyUp(obj, delimit, exVal);
				return;
			}
			
			if( exVal == null || exVal == "" ){
				$(obj).val( "" );
				return;
			}
			else if( exVal == "NOW" ){
				$(obj).val( ValidDateJsPrv.getNowDate(delimit) );
				return;
			}
			else{
				$(obj).val( exVal );
				return;
			}
			
		},
		
		dateFormat_KeyUp : function (obj, delimit, exVal) {
			
			var val = $(obj).val();
			var rVal = "";
			
			var x = _regex_num_slash_dash.test( val );
			
			if( val == "" )
			{
				_afterRegex = "";
				return;
			}
			
			if( ! x )
			{
//				console.log('rex fail');
//				console.log("afterRegex:" + _afterRegex + "::" );
				
				$(obj).val(_afterRegex);
			}
			else
			{
//				console.log('rex pass');
				
				_afterRegex = obj.value;
			}
			
//			console.log( val );

			var onlyNum = val.replace(/[^0-9.]/g, "");
			
//			console.log( "onlyNum:" + onlyNum );
//			console.log( "onlyNum len:" + onlyNum.length );
			
			if ( onlyNum.length >= 8  )
			{
//				console.log( "onlyNum:" + onlyNum );
//				console.log( "substr:" + onlyNum.substring(0,8));
//				console.log( "dateFormat:" + ValidDateJs.dateFormat( delimit, onlyNum.substring(0,8) ));
				
				rVal = ValidDateJs.dateFormat( delimit, onlyNum.substring(0,8) );
				
				$(obj).val( rVal );
				
				if ( ! ValidDateJs.isValidDateStr( rVal ) )
				{
					// alert("입력일자가 유효하지 않습니다. 수정해주세요");
					if( exVal == null || exVal == "" ){
						$(obj).val( "" );
						return;
					}
					else if( exVal == "NOW" ){
						$(obj).val( ValidDateJsPrv.getNowDate(delimit) );
						return;
					}
					else{
						$(obj).val( exVal );
						return;
					}
					

//					if( alertMsg == null || alertMsg == "") alertMsg = "일자가 유효하지 않습니다";
//
//					layCmn.alert({
//						msg : alertMsg,
//						callback : function() {
//							$(obj).focus();
//						}
//					});
					
				}
			}
		},
		
		isValidDateStr : function(val){
			
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			var yyyy = onlyNum.substr(0,4);
			var mm = onlyNum.substr(4,2);
			var dd = onlyNum.substr(6,2);			
			
//			console.log( "yyyy:" + yyyy );
//			console.log( "mm:" + mm );
//			console.log( "dd:" + dd );
			
			return ValidDateJs.isValidDate(yyyy,mm,dd);	
		},
		
		isValidDate : function (yyyy,mm,dd){
			
			var iY = yyyy;
			var iM = parseInt(mm, 10)-1;
			var iD = parseInt(dd, 10);
			
			var vDate = new Date(iY, iM, iD);
//			console.log( iY + "/" + iM + "/" + iD );
			
//			console.log( "vDate.getFullYear():" + vDate.getFullYear() );
//			console.log( "vDate.getMonth():" + vDate.getMonth() );
//			console.log( "vDate.getDate():" + vDate.getDate() );
			
			if( vDate.getFullYear() == iY 
					&& vDate.getMonth() == iM
					&& vDate.getDate() == iD )
			{
				return true;
			}
			else
			{
				return false;
			}
		},
		
		dateFormat : function(delimit, inVal){
			
		    var value = inVal;
		    
		    var rVal = "";
		    
		    if( delimit == null || delimit == "" ) delimit = "/";
		    
//		    console.log("value:" + value );
//		    console.log("delimit:" + delimit + "::" );
		    
		    if(delimit == "/"){
		    	rVal = value.replace(/^([\d]{4})([\d]{2})([\d]{2})$/,"$1/$2/$3");
		    }
		    else if(delimit == "-"){
		    	rVal = value.replace(/^([\d]{4})([\d]{2})([\d]{2})$/,"$1-$2-$3");
		    }
		    
//		    console.log("rVal:" + rVal );
		    
		    return rVal;
		},
		
		// for Date Type ======================================================= <<
		
		// for Time Type =======================================================		
		formatTime_keyup : function (event, obj){
			
			if(event.keyCode == 13 || event.keyCode == 37 || event.keyCode == 38) return;
			
			this.timeFormat_KeyUp(obj);
		},

		formatTime_onblur : function (event, obj, exVal){
			
			this.timeFormat_Blur(obj, exVal);
			
		},
	
		timeFormat_Blur : function (obj, exVal) {
			
			var val = $(obj).val();
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			//alert( onlyNum.length );
			
			if ( onlyNum.length == 0 && val.length == 0 ){
				return;
			}
			
			if ( onlyNum.length >= 1){
				
				if ( onlyNum.length == 1 ){
					onlyNum = "0" + onlyNum + "00"; 
				}
				else if ( onlyNum.length == 2 ){
					onlyNum = onlyNum + "00";
				}
				else if ( onlyNum.length == 3 ){
					onlyNum = onlyNum.substr(0,2) + "00";
				}
				else if ( onlyNum.length == 4 ){
					return;
				}
				else if ( onlyNum.length > 4 ){
					onlyNum = onlyNum.substr(0,4);
				}
	//			console.log(onlyNum);
				
				$(obj).val( onlyNum );
				
				this.timeFormat_KeyUp(obj, exVal);
				return;
			}
			
			if( exVal == null || exVal == "" ){
				$(obj).val( "" );
				return;
			}
			else if( exVal == "NOW" ){
				$(obj).val( ValidDateJsPrv.getNowTime() );
				return;
			}
			else if( exVal == "0" ){
				$(obj).val( "00:00" );
				return;
			}
			else{
				$(obj).val( exVal );
				return;
			}			
		},
		
		timeFormat_KeyUp : function (obj, exVal) {
			
			var val = $(obj).val();
			var rVal = "";
			
			var x = _regex_num_colon.test( val );
			
			if( val == "" )
			{
				_afterRegex = "";
				return;
			}
			
			if( ! x )
			{
//				console.log('rex fail');
//				console.log("afterRegex:" + _afterRegex + "::" );
				$(obj).val(_afterRegex);
			}
			else
			{
//				console.log('rex pass');
				_afterRegex = val;
			}
			
//			console.log( val );

			var onlyNum = val.replace(/[^0-9.]/g, "");
			
//			console.log( "onlyNum:" + onlyNum );
			
//			console.log( "onlyNum len:" + onlyNum.length );
			
			if ( onlyNum.length >= 4  )
			{
//				console.log( "substr4:" + onlyNum.substring(0,4));
				
//				console.log( "dateFormat:" + ValidDateJs.timeFormat( onlyNum.substring(0,4) ));
				
				rVal = ValidDateJs.timeFormat( onlyNum.substring(0,4) );
				
				$(obj).val( rVal );
				
				if ( ! ValidDateJs.isValidTimeStr( rVal ) )
				{
					if( exVal == null || exVal == "" ){
						$(obj).val( "" );
						return;
					}
					else if( exVal == "NOW" ){
						$(obj).val( ValidDateJsPrv.getNowTime() );
						return;
					}
					else if( exVal == "0" ){
						$(obj).val( "00:00" );
						return;
					}
					else{
						$(obj).val( exVal );
						return;
					}					

					
//					if( alertMsg == null || alertMsg == "") alertMsg = "일시가 유효하지 않습니다";
//
//					layCmn.alert({
//						msg : alertMsg,
//						callback : function() {
//							$(obj).focus();
//						}
//					});
				}
				// $(this).val()
			}
			
		},
		
		isValidTimeStr : function(val){
			
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			var hh = onlyNum.substr(0,2);
			var mi = onlyNum.substr(2,2);
			
//			console.log( "hh:" + hh );
//			console.log( "mi:" + mi );
			
			return ValidDateJs.isValidTime(hh,mi);	
		},

		isValidTime : function (mi, ss){
			
			var iM = parseInt(mi, 10);
			var iS = parseInt(ss, 10);
			
			if ( iM >= 0 && iM < 24 ){
//				console.log("iM is OK");
			}
			else{
//				console.log("iM is NOT OK");
				return false;
			}
			
			if ( iS >= 0 && iS < 60 ){
//				console.log("iS is OK");
			}
			else{
//				console.log("iS is NOT OK");
				return false;
			}
			return true;
		},
		
		timeFormat : function(inVal){
		    var value = inVal;       
		    //rVal = value.replace(/^([\d]{4})([\d]{2})([\d]{2})$/,"$1/$2/$3");
		    var rVal = value.replace(/^([\d]{2})([\d]{2})$/,"$1:$2");
		    return rVal;
		},
		
		// for Time Type ======================================================= <<
		
		// for 오전/오후 Time Type =======================================================		
		formatApmTime_keyup : function (event, obj, exVal){
			
			if(event.keyCode == 13 || event.keyCode == 37 || event.keyCode == 38) return;
			
			this.apmTimeFormat_KeyUp(obj, exVal);
		},

		formatApmTime_onblur : function (event, obj, exVal){
			
			this.apmTimeFormat_Blur(obj, exVal);
			
		},
	
		apmTimeFormat_Blur : function (obj, exVal) {
			
			var val = $(obj).val();
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			//alert( onlyNum.length );
			
			if ( onlyNum.length == 0 && val.length == 0 ){
				return;
			}
			else if( onlyNum.length >= 1 ){
				
				if ( onlyNum.length == 1 ){
					onlyNum = "0" + onlyNum + "00"; 
				}
				else if ( onlyNum.length == 2 ){
					onlyNum = onlyNum + "00";
				}
				else if ( onlyNum.length == 3 ){
					onlyNum = onlyNum.substr(0,2) + "00";
				}
				else if ( onlyNum.length == 4 ){
					return;
				}
				else if ( onlyNum.length > 4 ){
					onlyNum = onlyNum.substr(0,4);
				}
					
//				console.log(onlyNum);
				
				$(obj).val( onlyNum );
				
				this.apmTimeFormat_KeyUp(obj, exVal);
				
				return;
			}
		
			if( exVal == null || exVal == "" ){
				$(obj).val( "" );
				return;
			}
			else if( exVal == "NOW" ){
				$(obj).val( ValidDateJsPrv.getNowTime() );
				this.apmTimeFormat_KeyUp(obj, exVal);
				return;
			}
			else if( exVal == "0" ){
				$(obj).val( "오전 00:00" );
				return;
			}
			else{
				$(obj).val( exVal );
				return;
			}			
			
		},
		
		apmTimeFormat_KeyUp : function (obj, exVal) {
			
			var val = $(obj).val();
			var rVal = "";
			
			if( val.substring(0,2) == "오전" || val.substring(0,2) == "오후" )
			{
				_afterRegex = "";
				return;
			}
			
			var x = _regex_num_colon.test( val );
			
			if( val == "" )
			{
				_afterRegex = "";
				return;
			}
			
			if( ! x )
			{
//				console.log('rex fail');
//				console.log("afterRegex:" + _afterRegex + "::" );
				$(obj).val(_afterRegex);
			}
			else
			{
//				console.log('rex pass');
				_afterRegex = val;
			}
			
//			console.log( val );

			var onlyNum = val.replace(/[^0-9.]/g, "");
			
//			console.log( "onlyNum:" + onlyNum );
//			console.log( "onlyNum len:" + onlyNum.length );
			
			if ( onlyNum.length >= 4  )
			{
//				console.log( "substr4:" + onlyNum.substring(0,4));
//				console.log( "dateFormat:" + ValidDateJs.timeFormat( onlyNum.substring(0,4) ));
				
				rVal = ValidDateJs.timeFormat( onlyNum.substring(0,4) );
				
				if ( ! ValidDateJs.isValidTimeStr( rVal ) )
				{
					$(obj).val( rVal );

					
					if( exVal == null || exVal == "" ){
						$(obj).val( "" );
						return;
					}
					else if( exVal == "NOW" ){
						$(obj).val( ValidDateJsPrv.getNowTime() );
						this.apmTimeFormat_KeyUp(obj);
						return;
					}
					else if( exVal == "0" ){
						$(obj).val( "오전 00:00" );
						return;
					}
					else{
						$(obj).val( exVal );
						return;
					}
					
//					if( alertMsg == null || alertMsg == "") alertMsg = "일시가 유효하지 않습니다";
//
//					layCmn.alert({
//						msg : alertMsg,
//						callback : function() {
//							$(obj).focus();
//						}
//					});
				}
				else {
					
					var apmVal = "오전 ";
					
					if( parseInt( rVal.substring(0,2), 10 ) > 12 )
					{
						tempHH = parseInt( rVal.substring(0,2), 10 ) - 12;
						
						rVal = tempHH + rVal.substr(2,3);
						
						apmVal = "오후 ";
					}
					
					$(obj).val( apmVal + rVal );
					
				}
			}
		},
		// for 오전/오후 Time Type ======================================================= <<
		
		// for 오전/오후 DateTime Type =======================================================
		
		formatDateTime_keyup : function (event, obj, delimit, exVal){
			
			if(event.keyCode == 13 || event.keyCode == 37 || event.keyCode == 38) return;
			
			this.dateTimeFormat_KeyUp(obj, delimit, exVal);
		},
		
		formatDateTime_onblur : function (event, obj, delimit, exVal){
			
			this.dateTimeFormat_Blur(obj, delimit, exVal);
			
		},
	
		dateTimeFormat_Blur : function (obj, delimit, exVal) {
			
			var val = $(obj).val();
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			//alert( onlyNum.length );
			
			if ( onlyNum.length == 0 + 8 ){
				var strDay = ValidDateJs.dateFormat( delimit, onlyNum );
				var strTime = "00:00:00";
				$(obj).val( strDay + " " + strTime);
				return;
			}
			
			var strDay = ValidDateJsPrv.Left( onlyNum, 8);
			
			strDay = ValidDateJs.dateFormat( delimit, strDay );
			
			var strTime = onlyNum.substring(8, onlyNum.length);
			
			if( strTime.length >= 1 )
			{
//				console.log("strDay:" + strDay);
//				console.log("strTime:" + strTime);
				
				//alert(strTime);
				//return;
				
				if ( strTime.length == 1 ){
					strTime = "0" + strTime + "0000"; 
				}
				else if ( strTime.length == 2){
					strTime = strTime + "0000";
				}
				else if ( strTime.length == 3){
					strTime = strTime.substr(0,2) + "0000";
				}
				else if ( strTime.length >= 4 && strTime.length <= 5){
					strTime = strTime.substr(0,4) + "00";
				}
				else if ( strTime.length == 6){
					return;
				}
				else if ( strTime.length > 6){
					strTime = strTime.substr(0,6);
				}
				
				$(obj).val( strDay + strTime );
				this.dateTimeFormat_KeyUp(obj, delimit, exVal);
				return;
			}
			
			this.dateTimeFormat_SetExVal(obj, delimit, exVal);
			
		},
		
		dateTimeFormat_SetExVal : function (obj, delimit, exVal, strDate){
			if( exVal == null || exVal == "" ){
				$(obj).val( "" );
				return;
			}
			else if( exVal == "NOW0" || exVal == "NOWNOW" ){
				
				var setDate = "";
				var setTime = "";
				
				if(strDate != null && strDate != "" ){
					setDate = strDate;
				}
				else{
					setDate = ValidDateJsPrv.getNowDate(delimit);
				}
				
				if( exVal == "NOW0" ){
					setTime = "00:00:00";
				}
				else if( exVal == "NOWNOW" ){
					setTime = ValidDateJsPrv.getNowTimeSs();
				}

				$(obj).val( setDate + " " + setTime );
				
			}
			else{
				$(obj).val( exVal );
				return;
			}
		},
		
		dateTimeFormat_KeyUp : function (obj, delimit, exVal) {
			
			var val = $(obj).val();
			var rVal = "";
			
			if( val == "" )
			{
				_afterRegex = "";
				return;
			}
			
//			console.log( val );

			var x = _regex_num_slash_dash_colon.test( val );
			
			if( ! x )
			{
//				console.log('rex fail');
//				console.log("afterRegex:" + _afterRegex + "::" );
				
				$(obj).val(_afterRegex);
			}
			else
			{
//				console.log('rex pass');
				
				_afterRegex = obj.value;
			}


			var onlyNum = val.replace(/[^0-9.]/g, "");
			
//			console.log( "onlyNum:" + onlyNum );
			
//			console.log( "onlyNum len:" + onlyNum.length );

			
//			console.log( "right1:" + ValidDateJsPrv.Right( $(obj).val(), 1) );
			
			if ( 
					( onlyNum.length == 8 &&   ValidDateJsPrv.Right( $(obj).val(), 1) == " " )
					|| ( onlyNum.length > 8  )
			)
			{
				
				if( onlyNum.length >= 14){
					
					var strDate = ValidDateJsPrv.Left( $(obj).val(), 10);
					var strTime = ValidDateJs.timeSsFormat( ValidDateJsPrv.Right( onlyNum, 6) );
					
//					console.log( "strDate:" + strDate );
//					console.log( "strTime:" + strTime );
					
					rVal = strDate + " " + strTime;
					
					$(obj).val( rVal );
					
					
					if ( ! ValidDateJs.isValidDateStr( strDate ) )
					{
						this.dateTimeFormat_SetExVal(obj, exVal);
					}
					else if ( ! ValidDateJs.isValidTimeSsStr( strTime ) )
					{
						this.dateTimeFormat_SetExVal(obj, exVal, strDate);
					}

				}
				
//				console.log("handle time");
				return;
			}
			
			if ( onlyNum.length == 8  )
			{
//				console.log( "substr:" + onlyNum.substring(0,8));
//				console.log( "dateFormat:" + ValidDateJs.dateFormat( delimit, onlyNum.substring(0,8) ));
				
				rVal = ValidDateJs.dateFormat( delimit, onlyNum.substring(0,8) );
				
				$(obj).val( rVal );
				
				if ( ! ValidDateJs.isValidDateStr( rVal ) )
				{
					this.dateTimeFormat_SetExVal(obj, exVal);
				}
				// $(this).val()
			}
			
		},
		
		timeSsFormat : function(inVal){
		    var value = inVal;       
		    //rVal = value.replace(/^([\d]{4})([\d]{2})([\d]{2})$/,"$1/$2/$3");
		    var rVal = value.replace(/^([\d]{2})([\d]{2})([\d]{2})$/,"$1:$2:$3");
		    return rVal;
		},
		
		
		isValidTimeSsStr : function(val){
			
			var onlyNum = val.replace(/[^0-9.]/g, "");
			
			var hh = onlyNum.substr(0,2);
			var mi = onlyNum.substr(2,2);
			var ss = onlyNum.substr(4,2);
			
//			console.log( "hh:" + hh );
//			console.log( "mi:" + mi );
//			console.log( "ss:" + ss );
			
			return ValidDateJs.isValidTimeSs(hh,mi,ss);	
		},

		isValidTimeSs : function (mi, ss, xx){
			
			var iM = parseInt(mi, 10);
			var iS = parseInt(ss, 10);
			var iX = parseInt(xx, 10);
			
			if ( iM >= 0 && iM < 24 ){
//				console.log("iM is OK");
			}
			else{
//				console.log("iM is NOT OK");
				return false;
			}
			
			if ( iS >= 0 && iS < 60 ){
//				console.log("iS is OK");
			}
			else{
//				console.log("iS is NOT OK");
				return false;
			}
			
			
			if ( iX >= 0 && iX < 60 ){
//				console.log("iX is OK");
			}
			else{
//				console.log("iX is NOT OK");
				return false;
			}
			
			return true;
		}
		
		// for 오전/오후 DateTime Type ======================================================= <<
		
};
