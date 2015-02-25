//==============================================================================
// 공통코드 & 공통함수		: 
//------------------------------------------------------------------------------
// - 공통코드 클라이언트 캐싱 및 코드값 출력 등
// - 부서코드
// - 직위코드
// - 직책코드
// - 전자결재양식구분코드
// - 전자결재양식
// - 다국어 메시지
// - 사용자 타임존에 따른 날짜/시간 출력
// - 첨부파일 관련 함수
//------------------------------------------------------------------------------
// 공통코드 클라이언트 캐싱 및 코드값 출력 등
// cacheList() : 전체 공통코드 캐싱
// getNm() : 공통코드값 리턴
var abcCmnCd = {
	map : null,
	cacheList : function() {
		if (abcCmnCd.map == null) {
			$.ajax({
				url: "/cmn/cd.json?model_query_pageable.enable=false",
				contentType: "application/json",
				async: false,
				success:
					function(result) {
						abcCmnCd.map = new Map();
						for (var i=0; i<result.length; i++) {
							var cmn_cd_div_cd = result[i]["cmn_cd_div_cd"];
							var cmn_cd = result[i]["cmn_cd"];
							var cmn_cd_nm = result[i]["cmn_cd_nm"];
							abcCmnCd.map.put(cmn_cd_div_cd +"^"+ cmn_cd, cmn_cd_nm);
						}
					}
			});
		}
	},
	
	getNm : function(divCd, cmnCd) {
		return abcCmnCd.map.get(divCd +"^"+ cmnCd);
	}
};

// 직위코드
var abcPos = {
	map : null,
	cacheList : function() {
		$.ajax({
			url: "/org/company/hierarchy/position.json?model_query_pageable.enable=false",
			contentType: "application/json",
			async: false,
			success:
				function(result) {
					abcPos.map = new Map();
					for (var i=0; i<result.length; i++) {
						var pos_seq = result[i]["id"];
						var pos_nm = result[i]["position"];
						abcPos.map.put(pos_seq, pos_nm);
					}
				}
		});
	},
	
	getNm : function(posSeq) {
		return abcPos.map.get(posSeq);
	}
};

// 직책코드
var abcDuty = {
	map : null,
	cacheList : function() {
		$.ajax({
			url: "/org/company/hierarchy/title.json?model_query_pageable.enable=false",
			contentType: "application/json",
			async: false,
			success:
				function(result) {
					abcDuty.map = new Map();
					for (var i=0; i<result.length; i++) {
						var duty_seq = result[i]["id"];
						var duty_nm = result[i]["title"];
						abcDuty.map.put(duty_seq, duty_nm);
					}
				}
		});
	},
	
	getNm : function(dutySeq) {
		return abcDuty.map.get(dutySeq);
	}
};

// 전자결재양식구분코드
var abcAprFormDiv = {
	map : null,
	cacheList : function() {
		$.ajax({
			url: "/apr/formdiv.json?model_query_pageable.enable=false",
			contentType: "application/json",
			async: false,
			success:
				function(result) {
					abcAprFormDiv.map = new Map();
					for (var i=0; i<result.length; i++) {
						var form_div_cd_seq = result[i]["form_div_cd_seq"];
						var form_div_cd_nm = result[i]["form_div_cd_nm"];
						abcAprFormDiv.map.put(form_div_cd_seq, form_div_cd_nm);
					}
				}
		});
	},
	
	getNm : function(formDivCdSeq) {
		return abcAprFormDiv.map.get(formDivCdSeq);
	}
};

// 전자결재양식
var abcAprForm = {
	divMap : null,
	cacheList : function() {
		$.ajax({
			url: "/apr/form.json?model_query_pageable.enable=false",
			contentType: "application/json",
			async: false,
			success:
				function(result) {
					abcAprForm.divMap = new Map();
					for (var i=0; i<result.length; i++) {
						var form_seq = result[i]["form_seq"];
						var form_div_cd_nm = result[i]["form_div_cd_nm"];
						abcAprForm.divMap.put(form_seq, form_div_cd_nm);
					}
				}
		});
	},
	
	getDivNm : function(formSeq) {
		return abcAprForm.divMap.get(formSeq);
	}
};

// 다국어 메시지
var abcMessage = {
	getMessage: function(code) {
		var message = "";
		$.ajax({
			url: "/message.json?code="+ code,
			type: "GET",
			contentType: "application/json",
			async: false,
			success: function(result) {
				message = result.message;
			}
		});
		return message;
	},
	cacheList: function() {
		$.ajax({
			url: "/message.json",
			contentType: "application/json",
			success:
				function(result) {
					console.log(result);
				}
		});
	}
};

// 사용자 표시
var abcUsrShow = {
	// 사용자표시설정
	getUsrShow: function(usr_key, name, dept_nm, pos_nm) {
		var str = "";
		
		if (!usr_key) usr_key = "";
		if (!name) name = "";
		if (!dept_nm) dept_nm = "";
		if (!pos_nm) pos_nm = "";

		$.ajax({
			url: "/usrShow.json?usr_key="+ usr_key +"&name="+ encodeURI(name) +"&dept_nm="+  encodeURI(dept_nm) +"&pos_nm="+  encodeURI(pos_nm),
			type: "GET",
			contentType: "application/json",
			async: false,
			success: function(result) {
				str = result.show;
			}
		});

		return str;
	},

	// 사용자이름
	getUsrName: function(usr_key, name) {
		var str = "";

		if (!usr_key) usr_key = "";
		if (!name) name = "";

		$.ajax({
			url: "/usrName.json?usr_key="+ usr_key +"&name="+ encodeURI(name),
			type: "GET",
			contentType: "application/json",
			async: false,
			success: function(result) {
//				var decrypted = CryptoJS.AES.decrypt(result.show, "469364faff8abeda57022ae65846f9dd");
//				console.log(result.show +" => "+ decrypted);
//				str = decrypted;

				str = result.show;
			}
		});

		return str;
	}
};


// 사용자 타임존에 따른 날짜/시간 출력
var abcDtime = {
		 
	// 최신글 여부
	isNewAtcl : function(atcl_reg_dtime, type) {
		
		if (!atcl_reg_dtime || atcl_reg_dtime == "") {
			return "";
		}
		
		var dateStr = atcl_reg_dtime;
		var dateYYYY = dateStr.substr(0,4);
		var dateMM = dateStr.substr(4,2);
		var dateDD = dateStr.substr(6,2);
		var returnStr = '';
		
		var atclDtime = new Date(dateYYYY,  parseInt(dateMM)-1, parseInt(dateDD)+1, "23", "59", "59");		
		var currentDtime = new Date();
		currentDtime.setHours("00");
		currentDtime.setMinutes("00");
		currentDtime.setSeconds("00");
		if (atclDtime >= currentDtime) {
			if(type == "COMMENT") {
				returnStr = 'style="color:red;font-weight:bold;"';
			}else{
				returnStr = '<span style="margin-left: 3px;" class="o-icon o-i-new"></span>';
			}
		}
		return returnStr;
	},	
	// 오늘날짜(클라이언트)
	getTodayClient : function() {
		var today = new Date();
		return today;
	},
	// 오늘년도(클라이언트)
	getTodayYearClient: function() {
		return this.getTodayClient().getFullYear();
	},
	// 오늘달(클라이언트)
	getTodayMonthClient: function() {
		return this.getTodayClient().getMonth()+1;
	},
	// 오늘일(클라이언트)
	getTodayDateClient: function() {
		return this.getTodayClient().getDate();
	},
	// 오늘시간(클라이언트)
	getTodayHourClient: function() {
		return this.getTodayClient().getHours();
	},
	// 오늘분(클라이언트)
	getTodayMinuteClient: function() {
		return this.getTodayClient().getMinutes();
	},
	// 1,2... 주 전 날짜(클라이언트) - ex)20131107
	getBeforeWeekClient: function(week) {
		var day = week * 7 - 1;
		var dt = this.getTodayClient();
		dt.setDate(dt.getDate() - day);
		return dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
	},
	// 1,2... 달 전 날짜(클라이언트) - ex)20131107
	getBeforeMonthClient: function(month) {
		var dt = this.getTodayClient();
		dt.setMonth(dt.getMonth() - month);
		return dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
	},
	// 1,2... 주 후 날짜(클라이언트) - ex)20131107
	getAfterWeekClient: function(week) {
		var day = week * 7 - 1;
		var dt = this.getTodayClient();
		dt.setDate(dt.getDate() + day);
		return dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
	},
	// 1,2... 달 후 날짜(클라이언트) - ex)20131107
	getAfterMonthClient: function(month) {
		var dt = this.getTodayClient();
		dt.setMonth(dt.getMonth() + month);
		return dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
	},
	// 해당월의 첫번째날 - ex)return string형 20140101
	getFirstDayString: function(date) {
		return date.getFullYear() + this.addZero(eval(date.getMonth()+1)) + "01";
	},
	// 해당월의 마지막날 - ex)return string형 20140131
	getLastDayString: function(date) {
		return date.getFullYear() + this.addZero(eval(date.getMonth()+1)) + new Date(date.getFullYear(), date.getMonth()+1, 0).getDate();
	},
	// 해당월의 첫번째날 - ex)return date형 20140101
	getFirstDayDate: function(date) {
		return new Date(date.getFullYear(), date.getMonth(), 1);
	},
	// 해당월의 마지막날 - ex)return date형 20140131
	getLastDayDate: function(date) {
		return new Date(date.getFullYear(), date.getMonth(), new Date(date.getFullYear(), date.getMonth()+1, 0).getDate());
	},
	// 날짜
	getDate: function(dtime) {
		var parseDate = kendo.parseDate(dtime, "yyyyMMdd");
		if (parseDate == null) { // kendo 파싱이 안되면 날짜형 데이터로 간주
			parseDate = dtime;
		}
		var str = kendo.toString(parseDate, User.get("date_fmt"));  // 세션에 담아져있는 사용자 날짜 표시
		return str;
	},
	// 날짜 시간
	getDtime: function(dtime, isIncludingSec) { // isIncludingSec : 초를 포함한채로 처리할 것인지의 여부, N이면 초는 제외한다.
		
		if (dtime == null) return "";
		
		var time_fmt = User.get("time_fmt"); // 세션에 담아져있는 사용자 시간 표시
		if (time_fmt == "hh:mm:ss a" || time_fmt == "hh:mm:ss tt") { // 12시간 표시일 때
			if(isIncludingSec == "N") {
				time_fmt = "hh:mm tt";
			} else {
				time_fmt = "hh:mm:ss tt";
			}
		} else {
			if(isIncludingSec == "N") {
				time_fmt = "HH:mm";
			} else {
				time_fmt = "HH:mm:ss";
			}
		}

		var str = kendo.toString(kendo.parseDate(dtime, "yyyyMMddHHmmss"), User.get("date_fmt") + " " + time_fmt);  // 세션에 담아져있는 사용자 날짜, 시간 표시
		return str;
	},
	
	getSundayOfTheWeek : function(date) {
		date = new Date(date);
		var day = date.getDay();
		var diff = date.getDate() - day;
		
		return new Date(date.setDate(diff));
	},
	
	getMondayOfTheWeek : function(date) {
		date = new Date(date);
		var day = date.getDay();
		var diff = date.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday
		
		return new Date(date.setDate(diff));
	},

    getToday: function(delim) {
        var now = new Date();
        return this.converDateString(now, delim);
    },
    
    // 사용자포맷에 따른 현재 날짜/시간을 얻는다.
    getTodayTime : function() {
    	var dt = new Date();    	
    	var fullDay = dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
    	var fullTime = this.addZero(dt.getHours()) + this.addZero(dt.getMinutes()) + this.addZero(dt.getSeconds());
    	var fullDtime = fullDay + "" + fullTime;
    	return this.getDtime(fullDtime);
    },
    
    // 사용자포맷에 따른 timestamp 날짜/시간을 얻는다.
    getDateTimeByTimestamp : function(timestamp) {
    	var dt = new Date(timestamp);
    	var fullDay = dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
    	var fullTime = this.addZero(dt.getHours()) + this.addZero(dt.getMinutes()) + this.addZero(dt.getSeconds());
    	var fullDtime = fullDay + "" + fullTime;
    	return this.getDtime(fullDtime);
    },
    

    // 2013/05/06 형식으로 스트링 포매팅
    converDateString: function(dt, delim) {
        if (delim) {
            return dt.getFullYear() + delim + this.addZero(eval(dt.getMonth()+1)) + delim + this.addZero(dt.getDate());
        } else {
            return dt.getFullYear() + this.addZero(eval(dt.getMonth()+1)) + this.addZero(dt.getDate());
        }
    },

    addZero: function(i) {
        var rtn = i + 100;
        return rtn.toString().substring(1,3);
    },
    addZeroString: function(i) {
    	var rtn = i;
    	if(i.length<4) {
	        rtn = parseInt(rtn) + 100;
	        rtn = rtn.toString().substring(1,3)
    	}
        return rtn;
    },
    // String 날짜 타입의 YYYY 추출
    getYearDateString : function (dtStr) {
    	var fmt = this.getKendoDateFormat();
		dtStr = dtStr.split("-").join("");
    	if (fmt == 'yyyy-MM-dd') {
			return dtStr.substr(0, 4)
		} else if (fmt == 'MM-dd-yyyy') {
			return dtStr.substr(4, 4)
		}
    }, 
    // String 날짜 타입의 MM 추출
    getMonthDateString : function (dtStr) {
    	var fmt = this.getKendoDateFormat();
    	dtStr = dtStr.split("-").join("");
    	if (fmt == 'yyyy-MM-dd') {
			return dtStr.substr(4, 2)
		} else if (fmt == 'MM-dd-yyyy') {
			return dtStr.substr(0, 2)
		}
    }, 
    // String 날짜 타입의 DD 추출
    getDayDateString : function (dtStr) {
    	var fmt = this.getKendoDateFormat();
    	dtStr = dtStr.split("-").join("");
    	if (fmt == 'yyyy-MM-dd') {
			return dtStr.substr(6, 2)
		} else if (fmt == 'MM-dd-yyyy') {
			return dtStr.substr(2, 2)
		}
    }, 
	// String(yyyymmdd) 을 날짜표시 형식에 맞게 변환
	getUsrStronlyDateString: function(dtStr) {
		var fmt = this.getKendoDateFormat();
		if (fmt == 'yyyy-MM-dd') {
			return dtStr.substr(0, 4) + "-" + dtStr.substr(4, 2) + "-" + dtStr.substr(6, 2);
		} else if (fmt == 'MM-dd-yyyy') {
			return dtStr.substr(4, 2) + "-" + dtStr.substr(6, 2) + "-" + dtStr.substr(0, 4);
		}
	},
    // String(yyyy-mm-dd) 을 날짜표시 형식에 맞게 변환
	getUsrStrDateString: function(dtStr) {
		var arrDtStr = dtStr.split("-");
		return this.addZeroString(arrDtStr[0]) + "-" + this.addZeroString(arrDtStr[1]) + "-" + this.addZeroString(arrDtStr[2]);
	},
    
    // Date 를 사용자 날짜표시 형식에 맞게 변환
	getUsrDateString: function(dt) {
		var fmt = this.getKendoDateFormat();

		if (fmt == 'yyyy-MM-dd') {
			return dt.getFullYear() + "-" + this.addZero(eval(dt.getMonth()+1)) + "-" + this.addZero(dt.getDate());
		} else if (fmt == 'MM-dd-yyyy') {
			return this.addZero(eval(dt.getMonth()+1)) + "-" + this.addZero(dt.getDate()) + "-" + dt.getFullYear();
		}
	},
	// 오늘 날짜를 사용자 날짜표시 형식에 맞게 변환
	getUsrToday: function() {
		var now = new Date();
		return this.getUsrDateString(now);
	},

	// 1,2... 주 전 날짜
	getBeforeWeek: function(week) {
		var day = week * 7 - 1;
		var dt = new Date();
		dt.setDate(dt.getDate() - day);
		return this.getUsrDateString(dt);
	},

	// month 달 전 날짜
	getBeforeMonth: function(month) {
		var dt = new Date();
		dt.setMonth(dt.getMonth() - month);
		return this.getUsrDateString(dt);
	},

	// 사용자설정 날짜포맷
	getKendoDateFormat: function() {
		var format = User.get("date_fmt"); // 세션에 담아져있는 사용자 날짜 표시
		return format;
	},

	// kendo 날짜값을 DB값으로(YYYYMMDD) 변환
	getDBDateValue: function(dtStr) {
		var fmt = this.getKendoDateFormat();

		if (fmt == 'yyyy-MM-dd' || fmt == 'yyyy/MM/dd') {
			var y = dtStr.substring(0, 4);
			var m = dtStr.substring(5, 7);
			var d = dtStr.substring(8);

			return y + m + d;

		} else if (fmt == 'MM-dd-yyyy' || fmt == 'MM/dd/yyyy') {
			var m = dtStr.substring(0, 2);
			var d = dtStr.substring(3, 5);
			var y = dtStr.substring(6);

			return y + m + d;
		}
	},

	// 사용자설정 시간포맷(kendo timepicker)
	getKendoTimeFormat: function() {
		var format = User.get("time_fmt"); // 세션에 담아져있는 사용자 시간 표시
		if (format == "hh:mm:ss a") { // 12시간 표시일 때
			format = "hh:mm:ss tt";
		}
		return format;
	},

	// kendo 시간값을 DB값으로(HHmmss) 변환
	getDBTimeValue: function(time) {
		kendo.culture(User.get("lang_cd")); // 사용자 session에 설정된 언어 설정값을 read하여 kendo ui의 기본 사용 코드 Setting
		var len = time.length;
		var hour = time.substring(0, 2);
		var min = time.substring(3, 5);
		var sec = time.substring(6, 8);
		var flag = "";
		var pmlang = kendo.culture().calendars.standard.PM[0]; // 영문 'PM'에 대한 kendo ui에서의 각 언어별 value 중 1번째를 read하여 할당
		var amlang = kendo.culture().calendars.standard.AM[0]; // 영문 'AM'에 대한 kendo ui에서의 각 언어별 value 중 1번째를 read하여 할당
		if (len > 8) {
			flag = time.substring(9);
		}

		//if (flag == 'PM' || flag == 'pm') {
		if(flag == pmlang || flag == pmlang.toLowerCase()) { // Hardcoding되어 있던 pm에 대한 비교를 언어별 값을 read하여 비교하는 방법으로 변경
			if (parseInt(hour) == 12 ) {
				hour = "00";
			}
			hour = parseInt(hour) + 12;
		//} else if (flag == 'AM' || flag == 'am') {
		} else if(flag == amlang || flag == amlang.toLowerCase()) {	 // Hardcoding되어 있던 pm에 대한 비교를 언어별 값을 read하여 비교하는 방법으로 변경
			if (parseInt(hour) == 12 ) {
				hour = "00";
			}
		}

		var ret = hour + min + sec;

		return ret;
	},

	// DB 값을 kendo 시간값으로 변환(사용자설정 적용)
	getKendoTimeValue: function(time) {
		kendo.culture(User.get("lang_cd")); // 사용자 session에 설정된 언어 설정값을 read하여 kendo ui의 기본 사용 코드 Setting
		var ret = "";

		var hour = time.substring(0, 2);
		var min = time.substring(2, 4);
		var sec = time.substring(4, 6);
		var flag = "";

		var format = this.getKendoTimeFormat();
		if (format == 'hh:mm:ss tt') {	// 12시간제
			if (parseInt(hour) >= 12) {
				if ((parseInt(hour) - 12) < 10) {
					hour = "0" + (parseInt(hour) - 12);
				} else {
					hour = (parseInt(hour) - 12);
				}
				//flag = "PM";
				flag = kendo.culture().calendars.standard.PM[0]; // flag값에 대해 기존에는 'PM'이라는 영문값을 고정적으로 set, 그러나 현재는 사용자 환경설정에서의 언어값을 read하여 해당 언어에 대한 kendo ui의 값을 read하여 그에 맞는 'PM'값을 반환
			} else {
				//flag = "AM";
				flag = kendo.culture().calendars.standard.AM[0]; // flag값에 대해 기존에는 'AM'이라는 영문값을 고정적으로 set, 그러나 현재는 사용자 환경설정에서의 언어값을 read하여 해당 언어에 대한 kendo ui의 값을 read하여 그에 맞는 'AM'값을 반환
			}
		} else {
			return hour + ":" + min + ":" + sec;
		}

		ret = hour + ":" + min + ":" + sec +" "+ flag;

		return ret;
	},
	// kendo 날짜, 시간값을 DB값으로(yyyy-MM-dd HH:mm:ss) 변환
	// date : kendoDatePicker의 value(), time : kendoTimePicker의 value()
	getKendoDateFormatter: function(date, time) {
		var tempDate = date.getFullYear() + "-" + this.addZero(eval(date.getMonth()+1)) + "-" + this.addZero(date.getDate());
		var tempTime = this.addZero(time.getHours()) + ":" + this.addZero(time.getMinutes()) + ":" + this.addZero(time.getSeconds());
		return tempDate + " " + tempTime;
	}
};

//===================================================
// 첨부파일 관련 함수 정의
var abcUploadFileForm = {

	// 파일 크기에 따른 형식 조정
/*	convertSize : function(fileSize) {
		var bytes = parseInt(fileSize);
		var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
		var e = Math.floor(Math.log(bytes)/Math.log(1024));
		if (e == "-Infinity") {
			return "0"+s[0];
		} else {
			var returnData = (bytes/Math.pow(1024, Math.floor(e)));
			if (returnData.toString().indexOf(".") != -1) { // 정수이면
				returnData = returnData.toFixed(2);
			}
			return returnData + "" + s[e];
		}
	},*/
	convertSize : function(bytes) {
//		if(bytes < 1024) return bytes + ' B';
		var units = ['KB','MB','GB','TB','PB'];
		var u = -1;
		do {
			bytes /= 1024;
			++u;
		} while(bytes >= 1024);
		return bytes.toFixed(2) + '' + units[u];
	},
	
	/*
	 * 파일 확장자에 따른 형식 조정
	 * @see also FileIcon class 자바에서 page rendering에는 FileIcon 클래스 사용으로 대체
	 */
	convertIcon : function(fileNM) {
		var returnStr = "";
		var fileNMArr = fileNM.split(".");
		var fileExt = fileNMArr[fileNMArr.length-1];
		fileExt = fileExt.toLowerCase();
		
		if (fileExt == "gif" || fileExt == "jpg" || fileExt == "png" || fileExt == "bmp" || fileExt == "jpeg") {
			returnStr = '<span class="fileIcon file_image" title="image"></span>';
		} else if (fileExt == "zip" || fileExt == "rar" || fileExt == "arz" || fileExt == "7z") {
			returnStr = '<span class="fileIcon file_zip" title="zip">압축파일</span>';
		} else if (fileExt == "hwp") {
			returnStr = '<span class="fileIcon file_hwp" title="hwp">HWP</span>';
		} else if (fileExt == "xls" || fileExt == "xlsx") {
			returnStr = '<span class="fileIcon file_xls" title="excel">XLS</span>';
		} else if (fileExt == "doc" || fileExt == "docx") {
			returnStr = '<span class="fileIcon file_doc" title="word">DOC</span>';
		} else if (fileExt == "txt") {
			returnStr = '<span class="fileIcon file_txt" title="txt">TXT</span>';
		} else if (fileExt == "exe") {
			returnStr = '<span class="fileIcon file_exe" title="exe">EXE</span>';
		} else if (fileExt == "pdf") {
			returnStr = '<span class="fileIcon file_pdf" title="pdf">PDF</span>';
		} else if (fileExt == "html" || fileExt == "html" || fileExt == "js" || fileExt == "css") {
			returnStr = '<span class="fileIcon file_html" title="html">HTML</span>';
		} else if (fileExt == "ppt" || fileExt == "pptx") {
			returnStr = '<span class="fileIcon file_ppt" title="ppt">PPT</span>';
		} else {
			returnStr = '<span class="fileIcon file_ect" title="etc">기타</span>';
		}
		return returnStr;
	},
	
	isImageFilename : function(fileNM) {				
		var fileNMArr = fileNM.split(".");		
		var fileExt = fileNMArr[fileNMArr.length-1];
		fileExt = fileExt.toLowerCase();
		
		if (fileExt == "gif" || fileExt == "jpg" || fileExt == "png" || fileExt == "bmp" || fileExt == "jpeg") return true;
		else return false;
	},
		
		
		
	// 신규글 작성 중 임시파일 제거
	// obj : 이벤트발생객체 
	// val : 임시첨부파일 seq  
	// tempAtchFileSeqID : 임시 첨부파일 seq가 저장될 input 요소의 아이디
	removeTempFile : function(obj, val, tempAtchFileSeqID){
		var _fileSeqArray = new Array();
		var _tempAtchFileSeqID = tempAtchFileSeqID;
		console.log("removeTempfile_val="+$("#"+_tempAtchFileSeqID).val());
		_fileSeqArray = $("#"+_tempAtchFileSeqID).val().split(",");
		console.log("removeTempfile_length="+_fileSeqArray.length);
		_fileSeqArray.removeByValue(val);		
		$(obj).parent().parent().remove();
		$("#"+_tempAtchFileSeqID).val(_fileSeqArray.toString());
		var _uploadCnt = _fileSeqArray.length;
		$("#uploadFileCntText").html(_uploadCnt);
	},

	// PC 파일첨부 완료 이벤트(비동기)
	// e : 이벤트 객체-kendo에서 리턴 
	// tempAtchFileSeqID : 임시 첨부파일 seq가 저장될 input 요소의 아이디
	// noFileTextID : "첨부된 파일이 없습니다." 메세지 아이디 
	// fileListViewID : 첨부된 파일이 표시될 div 아이디
	onUploadSuccess : function(e, tempAtchFileSeqID, noFileTextID, fileListViewID, onlyImage) {
		var _res = JSON.parse(e.XMLHttpRequest.responseText);
		var _tempAtchFileSeqID = tempAtchFileSeqID;
		var _noFileTextID = noFileTextID;
		var _fileListViewID = fileListViewID;
		var _temp_atch_file_seq = $("#"+_tempAtchFileSeqID).val();		
		if (_temp_atch_file_seq == '0') _temp_atch_file_seq = "";		
		if (_temp_atch_file_seq.isEmpty() == false) {
			_temp_atch_file_seq = _temp_atch_file_seq + "," + _res.temp_atch_file_seq;
		} else {
			_temp_atch_file_seq = _res.temp_atch_file_seq;
		}
		if(onlyImage==true && !abcUploadFileForm.isImageFilename(_res.temp_atch_file_nm)) {alert("이미지가 아닌 파일은 제외됩니다.");}
		else $("#"+_tempAtchFileSeqID).val(_temp_atch_file_seq);				
		for (var i=0; i<e.files.length; i++) {
			if(onlyImage==true && !abcUploadFileForm.isImageFilename(_res.temp_atch_file_nm)) continue;
			var html  = '<div class="fileBox" data-fileseq="' + _res.temp_atch_file_seq + '">';	
				html += '<div class="fileName F_12_black" style="vertical-align: top;">';
				html += abcUploadFileForm.convertIcon(_res.temp_atch_file_nm)+" <span style='vertical-align: top;'>"+_res.temp_atch_file_nm+" ("+abcUploadFileForm.convertSize(_res.temp_atch_file_vol+"")+")</span>";
				html += "<span onclick='abcUploadFileForm.removeTempFile(this,"+_res.temp_atch_file_seq+", \"temp_atch_file_seq\")' class='o-icon o-listDelete floatR'></span>";
				html += "</div>";
				html += "</div>";			
			if ($("#"+_noFileTextID).text() == "") {				
				$("#"+_fileListViewID).append(html);
			} else {
				$("#"+_fileListViewID).html(html);
			}			
		}
		//var _tempArray = _temp_atch_file_seq.split(",");
		//var _uploadCnt = _tempArray.length;
		//$("#uploadFileCntText").html(_uploadCnt);		
		var _uploadCnt = $("#uploadFileCntText").text();
		_uploadCnt = parseInt(_uploadCnt);
		$("#uploadFileCntText").html(_uploadCnt+e.files.length);		
	},
	
	/**
	 * Refactored uploaded event handler
	 * 
	 * @param e
	 * @param tempAtchFileSeqID
	 * @param noFileTextID
	 * @param fileListViewID
	 * @param onlyImage
	 */
	onUploaded : function(e, files, noFileTextID, filesView, onlyImage) {
		var response = JSON.parse(e.XMLHttpRequest.responseText);
		var ids = $("#"+files).val();
		
		if (ids == '0') ids = "";
		if (!ids.isEmpty()) ids += ",";
		ids += response.id;
				
		if(onlyImage && !abcUploadFileForm.isImageFilename(response.property.name)) {alert("이미지가 아닌 파일은 제외됩니다.");}
		else $("#"+files).val(ids);
		
		for (var i=0; i<e.files.length; i++) {
			if(onlyImage && !abcUploadFileForm.isImageFilename(response.property.name)) continue;
			
			var html  = '<div class="fileBox" data-fileseq="' + response.id + '">';	
				html += '<div class="fileName F_12_black" style="vertical-align: top;">';
				html += abcUploadFileForm.convertIcon(response.property.name)+" <span style='vertical-align: top;'>"+response.property.name+" ("+abcUploadFileForm.convertSize(response.volume.size)+")</span>";
				html += "<span onclick='abcUploadFileForm.removeTempFile(this,"+response.id+", \"" + files + "\")' class='o-icon o-listDelete floatR'></span>";
				html += "</div>";
				html += "</div>";			
			if ($("#"+noFileTextID).text() == "") {				
				$("#"+filesView).append(html);
			} else {
				$("#"+filesView).html(html);
			}
		}

		var _uploadCnt = $("#uploadFileCntText").text();
		_uploadCnt = parseInt(_uploadCnt);
		$("#uploadFileCntText").html(_uploadCnt+e.files.length);	
	},

	// 글 수정시 저장된 파일제거
	// obj : 이벤트발생객체 
	// url : 각 모듈별 file map 삭제를 수행 할 URL 지정
	// progressTargetID :  로딩아이콘 표시가 될 타겟의 아이디
	// moduleUID : 해당 모듈의 key값(예: 게시판 = atcl_seq)
	// seq : 해당 글의 seq;
	// atchFileSeq : 해당 첨부파일의 seq
	removeSavedFile : function(obj, url, progressTargetID, moduleUID, seq, atchFileSeq) {		
		var _progressTarget = $("#"+progressTargetID);
		kendo.ui.progress(_progressTarget, true);		
		var _url = url + ""+ moduleUID+"="+seq+"&atch_file_seq="+atchFileSeq;
		$.ajax({
			url: _url,
			type:"POST",
			cache:false,
			data:{
				"atch_file_seq":atchFileSeq
			},
			success:function(result){			
				$(obj).parent().parent().remove();
				var _uploadCnt = $("#uploadFileCntText").text();
				$("#uploadFileCntText").html(_uploadCnt-1);
			},
			error:function(result) {
				console.log(result);
			},
			complete:function() {				
				kendo.ui.progress(_progressTarget, false);
			}
		});
		return false;
	},
	
	onSelect :  function(e, limitSize, ableExtension){
		if (typeof(limitSize) == "undefined") {
			limitSize = 104857600; // 100MB
		} else {
			limitSize = Number(limitSize);
		}
		if (typeof(ableExtension) == "undefined") {
			ableExtension = ["all"];
		}
		$.map(e.files, function(file){
			if(file.name.length>=80){
				e.preventDefault();
				layCmn.alert({
					msg : " 파일 이름은 80자 까지 가능합니다.파일 명을 확인해 주세요.",
					callback : function() {}
				});
			}
			if(file.size>=limitSize){
				e.preventDefault();
				layCmn.alert({
					msg : file.name+" 파일이 " + abcUploadFileForm.convertSize(limitSize) + "를 초과 합니다.<br/>" + abcUploadFileForm.convertSize(limitSize) + " 이하만 첨부 가능 합니다.",
					callback : function() {}
				});
				//alert(file.name+"이 20MB를 초과 합니다. 20MB 이하만 첨부 가능 합니다.");
			}
			var isAbleExtension = false;
			for (var i=0 ; i<ableExtension.length ; i++) {
				if (ableExtension[i].toLowerCase() == "all" ||
						file.extension.toLowerCase().replace(/./, "") == ableExtension[i].toLowerCase()) {
					isAbleExtension = true;
					break;
				}
			}
			if (!isAbleExtension) {
				e.preventDefault();
				layCmn.alert({
					msg : ableExtension + " 파일확장자만 첨부 가능 합니다.",
					callback : function() {}
				});
			}
		});
	},
	onUploadFail : function(e){
		e.preventDefault();
		layCmn.alert({
			msg : file.name+" 파일을 업로드에 실패 하였습니다.",
			callback : function() {}
		});
		
	},
	onProgress : function(e){
		var proText="<div id=\"onUploadWindow\" class=\"dispNone\">"
			+"<div class=\"clear\"></div>"
			+"<p class=\"loading_bar\"><span id=\"onUploadProgressBar\" style=\"width:10%;\"></span></p>"
			+"<p class=\"loading_txt_no\"><span class=\"no\">업로드 진행중...</span><span class=\"progress\"><em id=\"onUploadProgressText\"></em></span></p>"
			+"<div class=\"clear\"></div>"
			+"</div>";
		$("#fileLodingBar").html(proText);
		
		$("#onUploadWindow").show();
		var textHtml = e.percentComplete+"%";
		$("#onUploadProgressBar").css({"width" : e.percentComplete+"%"});
		$("#onUploadProgressText").html(textHtml);		
		if (e.percentComplete >= 100) {
			$("#onUploadWindow").hide();
		}
	}
};



//===================================================
// ckeditor 에서 작성된 html문서의 태그를 style 형식으로 변환
var abcConvertContent = {
		
	convert : function(convertId) {
		
		// 테이블 태그를 스타일로 변환
		$("#"+convertId + " table").each(function() { 
			var border = $(this).attr("border");
			var cellpadding = $(this).attr("cellpadding");
			var cellspacing = $(this).attr("cellspacing");			
			if (border) {
				$(this).css("border", border+"px solid");
				$(this).contents().find("th").each(function() {
					$(this).css("border", border+"px solid");
				});
				$(this).contents().find("td").each(function() {
					$(this).css("border", border+"px solid");
				});
			}	
			if (cellpadding) {
				$(this).css("padding", cellpadding);
				$(this).contents().find("th").each(function() {
					$(this).css("padding", cellpadding+"px");
				});
				$(this).contents().find("td").each(function() {
					$(this).css("padding", cellpadding+"px");
				});
			}	
			if (cellspacing) {
				$(this).css("border-spacing", cellspacing+"px");
				$(this).contents().find("th").each(function() {
					$(this).css("border-spacing", cellspacing+"px");
				});
				$(this).contents().find("td").each(function() {
					$(this).css("border-spacing", cellspacing+"px");
				});
			}
		});
		
		// strong 태그를 스타일로 변환
		$("#"+convertId + " strong").each(function() { 
			$(this).css("font-weight", "bold");
		});
		
	}
		
};


//===================================================
// 현재 마우스 포인터 영역에서만 휠 스크롤이 동작하도록 설정 (스크롤이 생기는 영역의 ID를 인자로 받는다)
var abcScrollLock = {	
		
	set_id : function(elementId, e) {		
		$("#"+elementId).bind("DOMMouseScroll mousewheel" ,function(e, delta) {	
			
			var sTop = $("#"+elementId).scrollTop();
			if(delta > 0) { 
				var movePointer = sTop-50; 
				$("#"+elementId).animate({scrollTop: movePointer},0); 
			} else { 
				var movePointer = sTop+50; 
				$("#"+elementId).animate({scrollTop: movePointer},0); 
			}
			console.log("sTop : " + sTop);
			console.log("delta : " + delta);
			console.log("e.originalEvent.wheelDelta : " + e.originalEvent.wheelDelta);
			console.log("movePointer : " + movePointer);
			
			e = e ? e : window.event;			
			$ogs.stopBubble(e);

			return false;
		}); 		
	},

	set_class : function(elementClass, e) {		
		$("."+elementClass).bind("DOMMouseScroll mousewheel" ,function(e, delta) {			
			var sTop = $("."+elementClass).scrollTop();
			if(delta > 0) { 
				var movePointer = sTop-50; 
				$("."+elementClass).animate({scrollTop: movePointer},0); 
			} else { 
				var movePointer = sTop+50; 
				$("."+elementClass).animate({scrollTop: movePointer},0); 
			}
			console.log("sTop : " + sTop);
			console.log("e.originalEvent.wheelDelta : " + e.originalEvent.wheelDelta);
			console.log("movePointer : " + movePointer);
			
			e = e ? e : window.event;
			$ogs.stopBubble(e);			
		}); 
	},
	
	cancleEvent : function(e) {
		
		e = e ? e : window.event;
		if(e.stopPropagation) {
			e.stopPropagation();
		}	
		if(e.preventDefault) {
			e.preventDefault();
		}
		e.cancelBubble = true;
		e.cancel = true;
		e.returnValue = false;
		return false;
		
	}

};

var abcHashManager = {
	hash:'',
	// 브라우저 hash 에서 특정 key 의 value 가져오기
	getValue : function(key){
		var hash = window.location.hash;
		if(hash.indexOf(key)!=-1){
			var startLocation = hash.indexOf(key+'=')+key.length+1;
			var endLocation = hash.indexOf('#',startLocation);
			var value = hash.substring(startLocation,endLocation);
			return value;
		}
		else{
			return null;
		}
	},
	// hash 변수에 key, value 값 추가
	setValue : function(key,value){
		if (value == null) value = "";
		var hash = abcHashManager.hash;
		var text = key+'='+value+'#';
		if(hash.indexOf(key)!=-1){
			var startLocation = hash.indexOf(key+'=');
			var endLocation = hash.indexOf('#',startLocation)+1;
			var keyset = hash.substring(startLocation,endLocation);
			hash = hash.replace(keyset,text);
		}else{
			hash+=text;
		}
		abcHashManager.hash = hash;
	},
	// 브라우저 hash 초기화
	initWindowHash : function(){
		window.location.hash = '';
	},
	// hash 변수 초기화
	initHash : function() {
		abcHashManager.hash = '';
	},
	// hash 변수에서 key 값 삭제
	remove : function(key){
		var hash = abcHashManager.hash;
		if(hash.indexOf(key)!=-1){
			var startLocation = hash.indexOf(key+'=');
			var endLocation = hash.indexOf('#',startLocation)+1;
			var keyset = hash.substring(startLocation,endLocation);
			hash = hash.replace(keyset,'');
		}
		abcHashManager.hash = hash;
	},
	// hash 변수를 브라우저 hash 로 셋팅
	setHash : function(){
		window.location.hash = abcHashManager.hash;
		abcHashManager.hash = window.location.hash;
	},
	// hash 변수 값 셋팅
	setHashValue : function() {
		abcHashManager.hash = window.location.hash;
	},
	// hash 변수값 가져오기
	getHash : function() {
		return abcHashManager.hash;
	},
	// 브라우저 hash
	getWindowHash : function() {
		return window.location.hash;

	}
};

// 공통 유효성 체크
var abcValidCheck = {
	getRegexpReplaceByObj : function(obj, regexp, defStr, isObjSet) { // Object를 받아서 regexp(정규식)가 유효한지 검사하는 함수(유효하지않다면 defStr 로 치환) / return 유효한 문자 
		var str = obj.value;
		if (regexp.test(str)) {
			str = str.split(regexp).join(defStr);
			if (isObjSet) { // 해당 object에 값 세팅
				obj.value = str;
			}
		}
		return str;
	},
	getRegexpReplaceByValue : function(val, regexp, defStr) { // Value를 받아서 regexp(정규식)가 유효한지 검사하는 함수(유효하지않다면 defStr 로 치환) / return 유효한 문자 
		var str = val;
		if (!str) {
			return str;
		}
		if (regexp.test(str)) {
			str = str.split(regexp).join(defStr);
		}
		return str;
	},
	getRegexpFormat : function(val, regexp, format) { // Value를 받아서 regexp(정규식)과 format(유형)에 맞게 리턴하는 함수 
		var str = val;
		if (regexp.test(str)) {
			// ex) 앞번호(02, 82, 070, 01X, XXX)-중간번호(앞,끝나머지)-끝번호(XXXX)
			// regexp = /(^02.{0}|^82.{0}|^070.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/
			// format = $1-$2-$3
			str = str.replace(regexp, format);
		}
		return str;
	},
	/*
	 * strTemp  : [필수] 크로스사이트 스크립팅을 검사할 문자열
	 * level    : [옵션] 검사레벨
	 *            0 (기본) -> XSS취약한 문자 제거
	 *            1 (선택) -> 단순한 <, > 치환
	 */
	getCheckXSS : function(strTemp, level) { // XSS 체크 함수
		if (level == undefined || level == 0) {
			strTemp = strTemp.replace(/\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|\-/g,"");
		} else if (level != undefined && level == 1) {
			strTemp = strTemp.replace(/\</g, "&lt;");
			strTemp = strTemp.replace(/\>/g, "&gt;");
		}
		return strTemp;
	},
	getEscapeData : function(text) { // UnEscape 데이터를 Escape 데이터 시키는 함수
		return text.replace(/&/gi, "&amp;").replace(/</gi, "&lt;").replace(/>/gi, "&gt;").replace(/"/gi, "&quot;").replace(/'/gi, "&apos;").replace(/ /gi, "&nbsp;");
	},
	getUnEscapeData : function(text) { // Escape 데이터를 UnEscape 데이터 시키는 함수
		return text.replace(/&amp;/gi, "&").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "\"").replace(/&apos;/gi, "'").replace(/&nbsp;/gi, " ");
	},
	escapeHtmlTags : function(formId, tagNameArray) {
		for (var i=0 ; i<tagNameArray.length ; i++) {
			var tagValueEscape = $("#" + formId + " [name='"+ tagNameArray[i] +"']");
			tagValueEscape.val(tagValueEscape.val().replace(/</gi, "&lt;").replace(/>/gi, "&gt;"));
		}
	},
	unEscapeHtmlTags : function(formId, tagNameArray) {
		for (var i=0 ; i<tagNameArray.length ; i++) {
			var tagValueEscape = $("#" + formId + " [name='"+ tagNameArray[i] +"']");
			tagValueEscape.val(tagValueEscape.val().replace(/&amp;/gi, "&").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "\""));
		}
	},
	chkValidPassword : function(str){
		var cnt=0;
		var regexp =[/^(?=.*[A-Z]).*$/,/^(?=.*[a-z]).*$/,/^(?=.*[0-9]).*$/
					,/^(?=.*[~`!@#$%^&*()\{}:;\[\]\'\"\?\\\/<>.,]).*$/];
		 
		 for(i=0;i<regexp.length;i++){
			if(regexp[i].test(str)) cnt++;
			
		 }
		 if(cnt>2) return true;
		 else return false;
	},
	chkValidUsrId : function(str){
		//아이디의경우 영문,숫자,마침표만 사용 가능하다.
		var regexp =  /^[a-z0-9._-]+$/;
		return regexp.test(str); 
	},
	// XSS필터를 우회하기 위해 메일주소의 <,>문자를 {,}문자로 변환시킴, 20130-12-18, jgbak 
	getReplaceCharForXSS : function(mailAddresses) {
		// 받는사람
		if(mailAddresses == "" || typeof(mailAddresses) == "undefined") {
			mailAddresses = "";
		} else {
			mailAddresses = abcUIUtil.setStr_replace(['<', '>'], ['{', '}'], mailAddresses);
		}
		return mailAddresses;
	},
	
	notNull : function(objectId, message) {
		var value = $("#" + objectId).val();
		if (!value || value.length < 1) {
			abcValidCheck.alertFailure(message);
			return false;
		}
		
		return true;
	},
		
	alertFailure : function(message) {
		alert_({
			message : message,
			callback : function(e) {}
		});
	}
};

// 공통 UI 체크
var abcUICheck = {
	setDisplayCheckById : function(id) { // 해당 id의 style display를 체크해서 반대로 구성하는 함수
		var curId = $("#" + id);
		var curDisplay = curId.css("display");
		if (curDisplay == "block") {
			curId.css({display : "none"});
		} else {
			curId.css({display : "block"});
		}
	},
	setSubMenuStyleOn : function(id) { // 고정 서브메뉴 선택시 선택표시 세팅 함수
		$(".subMenulist li").each(function() {
			$(this).removeClass("_on");
		});
		$("#" + id).addClass("_on");
	}
};

// 공통 UI 유틸
var abcUIUtil = {
	noCount : 0, // noCount : 순번 카운트(반복 전역변수)
	getNoCount : function(total, page, pageSize) { // 리스트페이지 순번 구하는 함수
		// total : 전체 게시물 수
		// page : 불러오는 페이지 번호(초기값이 1이어서 '-1'을 해줌)
		// pageSize : 한페이지에 보여줄 목록 수
		// noCount : 순번 카운트(반복 전역변수) - 초기값 0
		// 계산 : ((total - ((page-1) * pageSize))- noCount)
		// 주의 : 전역변수 noCount 는 페이징 전환시 초기값 0 으로 세팅해야함
		var count = ((total - ((page-1) * pageSize))- abcUIUtil.noCount);
		abcUIUtil.noCount++;
		return count;
	},
	callUrl : function(url) { // url로 페이지 이동 함수
		location.href = url;
	},
	getUserShow : function(data, userName, position, title, deptName) { // 관리자 설정(사용자 표시 설정)에 따라 사용자 표시 구하는 함수 ex) 홍길동 / 대리 / 테스트팀
		var designation = abcUIUtil.getDesignation(position, title); 
		
		if(User.get("usr_show_cnfg") == "user/designation/dept"){
			return userName + "/" + designation + "/" + deptName;
		} else if(User.get("usr_show_cnfg") == "user/dept/designation"){
			return userName + "/" + deptName + "/" + designation;
		} else {
			return userName;
		}
	},
	
	getDesignation : function(position, title) {
		if(User.get("designationType") == "1"){ //직급
			return position;
		} else if(User.get("designationType") == "2"){ //직책
			return (title == null || title == "undefined") ?  position : title;
		}
	},
	
	/*
	 * Fold/Unfold content of the page. This is toggled with clicking +, - button.
	 */
	toggleTableContent : function(buttonId, tableId) {
		abcUIUtil.toggleContent(buttonId, tableId + " tbody tr");
	},
	
	toggleContent : function(buttonId, contentId) {
		$("#" + contentId).toggle();
		$("#" + buttonId).toggleClass("o-i-view-plus");
		$("#" + buttonId).toggleClass("o-i-view-minu");
	},
		
	setStr_replace : function(search, replace, subject, count) { // 20130-12-18, jgbak 
		var i = 0,
		j = 0,
		temp = '',
		repl = '',
		sl = 0,
		fl = 0,
		f = [].concat(search),
		r = [].concat(replace),
		s = subject,
		ra = Object.prototype.toString.call(r) === '[object Array]',
		sa = Object.prototype.toString.call(s) === '[object Array]';
		s = [].concat(s);
		if (count) {
			this.window[count] = 0;
		}
	
		for (i = 0, sl = s.length; i < sl; i++) {
			if (s[i] === '') {
				continue;
			}
			for (j = 0, fl = f.length; j < fl; j++) {
				temp = s[i] + '';
				repl = ra ? (r[j] !== undefined ? r[j] : '') : r[0];
				s[i] = (temp).split(f[j]).join(repl);
				if (count && s[i] !== temp) {
					this.window[count] += (temp.length - s[i].length) / f[j].length;
				}
			}
		}
		return sa ? s : s[0];
	},
	isHasCharByArray : function(content, checkCharArray) { // 내용안에 배열문자가 있는지 여부
		for (var num=0 ; num<checkCharArray.length ; num++) {
			if (content.toLowerCase().indexOf(checkCharArray[num]) != -1) {
				return true;
			}
		}
		return false;
	},
	setFirstInputFocus : function(formId) { // 첫번째 input 태그에 foucs 주는 함수
		setTimeout(function () { $("#" + formId + " :input:text:visible:enabled:eq(0)").focus();}, 500);
	}
};

// 공통 Action 하는 함수
var abcAction = {
	tempAutoSave : null, // 자동저장 변수
	setAutoTempSave : function(callback) { // 자동 임시저장 세팅 함수
		if (typeof callback != "function") { // 함수가 아니면
			return false;
		} else {
			window.clearTimeout(abcAction.tempAutoSave);
			//var autoTime = 1000 * 1800; // 1000 * 초
			var autoTime = 1000 * 1200; // 1000 * 초
			abcAction.tempAutoSave = window.setTimeout(callback, autoTime);
		}
	}
};

var abcSearchBox = {
	bind : function(textId, callback) {
		$("#" + textId).on("click", function(evt) {
			var e = evt || window.event;
			var eTarget = e.target || e.srcElement;
			$(eTarget).css("imeMode", "active");	// IE-한영변환오류처리 : 기본 한글로 지정
			$("body").trigger("click");				// 포커스 이동을 한 번 시켜줘야 적용된다.
			$(eTarget).focus();
		});
		
		$("#" + textId).on("keydown", function(evt) {
			var e = evt || window.event;
			if(e.which == 13) {
				callback();
				$ogs.stopBubble(e);
				$ogs.preventDefault(e);
				return false;
			}
		});
	}	
};

// 공통 KendoGrid
var abcKendoGrid = {
	setRefresh : function(id) { // KendoGrid 리프래쉬 
		$("#" + id).find(".k-i-refresh").click();
	},
	setNodata : function(colspanNum, kendoGridId) { // KendoGrid 리스트 데이터가 없을때 해당 hmtl에 그려주는 함수
		var html = "<tr><td colspan='" + colspanNum + "' style='text-align: center;'>데이터가 없습니다.</td></tr>";
		$("table tbody", "#" + kendoGridId).html(html);
	},
	getPageablePageSizes : function() { // KendoGrid 페이징 사이즈 얻는 함수
		var pageSizes = [10, 15, 20, 25]; // 페이징 사이즈 선택
		return pageSizes;
	},
	getPageableMessages : function() { // KendoGrid 페이징 Text 얻는 함수
		var messages = { // Text 정의
			display : "총 {2}건 중 {0}-{1}번째",
			empty : "총 0건",
			itemsPerPage : "페이지 당 항목수",
			first : "첫번째 페이지로 이동",
			last : "마지막 페이지로 이동",
			next : "다음 페이지로 이동",
			previous : "이전 페이지로 이동",
			refresh : "새로고침"
		};
		return messages;
	},
	setListColumnCheckBox : function(checkBoxidOrClass, showButtons, hideButtons) { // 체크박스 세팅 함수
		var checkCount = $("input:checkbox[name=check]").length; // 개별 체크박스 갯수
		var checkedCount = 0; // 개별 체크박스 체크된 갯수
		$("input:checkbox[name=check]:checked").each(function() {
			checkedCount++;
		});
		if (checkCount == 0) {
			checkBoxidOrClass.attr("checked", false); // 전체 체크박스 체크 해제
		} else if (checkCount == checkedCount) {
			checkBoxidOrClass.attr("checked", true); // 전체 체크박스 체크
		} else {
			checkBoxidOrClass.attr("checked", false); // 전체 체크박스 체크 해제
		}
		if(checkedCount > 0){
			if(showButtons != null){
				for(var i=0;i<showButtons.length;i++){
					$("#"+showButtons[i]).show();
				}
			}
			if(hideButtons != null){
				for(var i=0;i<hideButtons.length;i++){
					$("#"+hideButtons[i]).hide();
				}
			}
		}else{
			if(showButtons != null){
				for(var i=0;i<showButtons.length;i++){
					$("#"+showButtons[i]).hide();
				}
			}
			if(hideButtons != null){
				for(var i=0;i<hideButtons.length;i++){
					$("#"+hideButtons[i]).show();
				}
			}
		}
	}
};

// 공통 KendoDatePicker
var abcKendoDatePicker = {
	paintPicker : function(id, onChange) {
		var picker = $("#" + id).kendoDatePicker({
			format : abcDtime.getKendoDateFormat(),
			month: {content : "#=abcKendoDatePicker.getMonthContent(data)#"}, // 일(day) 표시
			change : onChange
		}).data("kendoDatePicker");
		$("#" + id).attr("readonly", "true");
		
		return picker;
	},
	
	paintPeriod : function(startId, endId, onChange) {
		var start = abcKendoDatePicker.paintPicker(startId, function() {
			if (onChange) onChange();
			abcKendoDatePicker.setStartDateChange(startId, endId);
		});
		
		var end = abcKendoDatePicker.paintPicker(endId, function() {
			if (onChange) onChange();
		});
		
		end.min(start.value());
	},
	
	setStartDateChange : function(startDateId, endDateId) { // KendoDate 시작일 선택시(change) 세팅 함수
		var startDatePicker = $("#" + startDateId).data("kendoDatePicker"); // 시작일 kendoDatePicker
		var endDatePicker = $("#" + endDateId).data("kendoDatePicker"); // 종료일 kendoDatePicker
		if (startDatePicker.value() > endDatePicker.value()) { // 시작일이 종료일보다 크면
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 시작일로 세팅
			endDatePicker.value(startDatePicker.value()); // 종료일 현재날짜 시작일로 세팅
		} else {
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 시작일로 세팅
		}
	},
	setEndDateChange : function(startDateId, endDateId) { // KendoDate 종료일 선택시(change) 세팅 함수
		/*
		var startDatePicker = $("#" + startDateId).data("kendoDatePicker"); // 시작일 kendoDatePicker
		var endDatePicker = $("#" + endDateId).data("kendoDatePicker"); // 종료일 kendoDatePicker
		if (startDatePicker.value() > endDatePicker.value()) { // 시작일이 종료일보다 크면
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 시작일로 세팅
			endDatePicker.value(startDatePicker.value()); // 종료일 현재날짜 시작일로 세팅
		} else {
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 시작일로 세팅
		}
		*/
	},
	/* 
	 * 아래 주석은 날짜 선택시 시작일과 종료일 사이를 계산하여 세팅하는 로직이 포함되어 있음
	 * 이 로직을 적용하려면 각각 페이지별로 코딩이 많이 들어가기에 주석처리 함
	startDate : null, // KendoDate 시작일(해당페이지 호출될때 세팅-공통으로 사용되기에 탭이동시 세팅된 값이 계속 사용됨)
	endDate : null,// KendoDate 종료일(해당페이지 호출될때 세팅-공통으로 사용되기에 탭이동시 세팅된 값이 계속 사용됨)
	getStartEndDateBetween : function() { //  KendoDate (시작일 - 종료일) 얻는 함수
		return abcKendoDatePicker.startDate - abcKendoDatePicker.endDate;
	},
	getEndStartDateBetween : function() { //  KendoDate (종료일 - 시작일) 얻는 함수
		return abcKendoDatePicker.endDate - abcKendoDatePicker.startDate;
	},
	setStartDateChange : function(startDateId, endDateId) { // KendoDate 시작일 선택시(change) 세팅 함수
		var startDatePicker = $("#" + startDateId).data("kendoDatePicker"); // 선택시작일 kendoDatePicker
		var endDatePicker = $("#" + endDateId).data("kendoDatePicker"); // 종료일 kendoDatePicker
		if (startDatePicker.value() < abcKendoDatePicker.startDate) { // 선택시작일이 시작일값보다 작으면
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 선택시작일로 세팅
		} else if (startDatePicker.value() > endDatePicker.value()) { // 선택시작일이 종료일보다 크면
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 선택시작일로 세팅
			endDatePicker.value(startDatePicker.value()); // 종료일 현재날짜 선택시작일로 세팅
		} else {
			endDatePicker.min(startDatePicker.value()); // 종료일 최소날짜 선택시작일로 세팅
			endDatePicker.value(new Date(startDatePicker.value().getTime() + abcKendoDatePicker.getEndStartDateBetween())); // 종료일 (선택시작일 + (종료일 - 시작일))로 세팅
		}
		abcKendoDatePicker.startDate = startDatePicker.value(); // 시작일 세팅
		abcKendoDatePicker.endDate = endDatePicker.value(); // 종료일 세팅
	},
	setEndDateChange : function(startDateId, endDateId) { // KendoDate 종료일 선택시(change) 세팅 함수
		var startDatePicker = $("#" + startDateId).data("kendoDatePicker"); // 시작일 kendoDatePicker
		var endDatePicker = $("#" + endDateId).data("kendoDatePicker"); // 종료일 kendoDatePicker
		abcKendoDatePicker.startDate = startDatePicker.value(); // 시작일 세팅
		abcKendoDatePicker.endDate = endDatePicker.value(); // 종료일 세팅
	}
	*/
	// 기본적으로 제공하는 kendo CSS(k-weekend) 사용하지않음 - 토요일, 공휴일도 표시될수 있기 때문에
	// 기본적으로 제공하는 kendo CSS(k-other-month) 사용함
	// css는 common.css 에 있음
	getMonthContent : function(data) { // 일(day) 표시 얻는 함수
		var parentDiv = $('<div/>');
		var div = $('<div/>');
		if (data.date.getDay() == 0) { // 일요일
			div.addClass("kendoCalendarSunday");
		} else if (data.date.getDay() == 6) { // 토요일
			div.addClass("kendoCalendarSaturday");
		}
		div.text(data.value);
		parentDiv.append(div);
		return parentDiv.html();
	}
};

var abcKendoDropDownList = {
	/**
	 * Paint Kendo drop down list on a given element.
	 * @param context.elementId the id of an element on which drop down list is painted.
	 * @param context.url the url string or function to fetch content
	 * @param context.map.text text field name
	 * @param context.map.value value field name
	 * @param context.parameterize parameterization callback function
	 */
	paint : function(context) {
		var url = typeof(context.url) == "function" ? context.url : function() {
			return context.url;
		};
		
		var params = {
			optionLabel		: "선택해주세요",
			dataTextField	: context.map.text,
			dataValueField	: context.map.value,
			dataSource		: {
		    	serverFiltering: true,
			    transport: {
			    	read: function(options) {
			    		abcKendoDropDownList.fetchContent(options, url);
			    	}
			    }
		    }
		} 
		
		if (context.parameterize) context.parameterize(params);		
		$("#" + context.elementId).kendoDropDownList(params);	
	},
	
	fetchContent : function(options, url) {
		$.ajax({
			type	: "get",
			url		: url(),
			data	: options.data,
			contentType	: "application/json",
			success	: function(response) {
				options.success(response);
			}
		});
	}
};

// 공통 KendoData
var abcKendoData = {
	getData : function(jsonData) { // JSON 데이터 파싱받는 함수
		var returnData = $.customParam(jsonData);
		return returnData;
	},
	getSortOrders : { // 정렬
		getUsrInfoSearch : function() { // 조직도 검색 공통
			// var returnData = [{"property":"A.KOR_NM", "direction" : "1"}, {"property":"B.DEPT_NM", "direction" : "-1"}];
			var returnData = [{"property":"A.KOR_NM", "direction" : "1"}]; // 한국이름
			return returnData;
		}
	},
	getPageSize : { // 페이지 사이즈
		getUsrInfoSearch : function() { // 조직도 검색 공통
			var pageSize = 1000; // 페이지 사이즈 하드코딩
			return pageSize;
		}
	},
	getPageNumber : { // 페이지 번호
		getUsrInfoSearch : function() { // 조직도 검색 공통
			var pageNumber = 0; // 페이지 번호 하드코딩
			return pageNumber;
		}
	}
};

// 공통 KendoAutoComplete
var abcKendoAutoComplete = {
	getTemplate : { // 템플릿
		getOrg : function() { // 조직도 자동검색
			// 사용자 표시 설정적용 user/duty/dept user/position/dept
			var user_show_config = "";
			if(User.get("usr_show_cnfg") == "user/duty/dept"){
				user_show_config = '#: kor_nm ##: (duty_nm)?"/"+duty_nm:"" ##: (dept_nm)?"/"+dept_nm:"" #';
			}else if(User.get("usr_show_cnfg") == "user/position/dept"){
				user_show_config = '#: kor_nm ##: (pos_nm)?"/"+pos_nm:"" ##: (dept_nm)?"/"+dept_nm:"" #';
			}
			return user_show_config;
		}
	}
};

// 공통 KendoTreeView
var abcKendoTreeView = {
	setSelectedNodeRemoveClass : function(kendoTreeViewId) { // 트리에서 이미 선택된 노드 재선택 가능하도록 세팅하는 함수
		$("#" + kendoTreeViewId + " .k-state-selected").live("click", function(evt) {
			$("#" + kendoTreeViewId + " .k-state-selected").removeClass("k-state-selected");
		});
	}
};

// 공통 KendoWindow
var abcKendoWindow = {
	removeAlreadyKendoWindow : function(kendoWindowId) { // 트리에서 이미 선택된 노드 재선택 가능하도록 세팅하는 함수
		// kendoWindowId 로 kendoWindow 가 계속 만들어지는 현상 때문에 이미 있으면 강제로 삭제함
		var alreadyKendoWindow = $("div[id=" + kendoWindowId + "]:eq(1)").data("kendoWindow"); // kendoWindowId 의 kendoWindow 변수
		if (typeof (alreadyKendoWindow) != "undefined") { // kendoWindowId 로 이미 kendoWindow 가 있으면
			alreadyKendoWindow.destroy(); // kendoWindowId 의 kendoWindow 삭제
		}
	}
};

// 쿠키 사용 
var abcCookie = {
	setCookie : function(cookieName, cookieValue, cookieExpire, cookiePath, cookieDomain, cookieSecure){
		var cookieText=escape(cookieName)+'='+escape(cookieValue);
		cookieText+=(cookieExpire ? '; EXPIRES='+cookieExpire.toGMTString() : '');
		cookieText+=(cookiePath ? '; PATH='+cookiePath : '');
		cookieText+=(cookieDomain ? '; DOMAIN='+cookieDomain : '');
		cookieText+=(cookieSecure ? '; SECURE' : '');
		document.cookie=cookieText;
	},  
	getCookie : function(cookieName){
		var cookieValue=null;
		if(document.cookie){
			var array=document.cookie.split((escape(cookieName)+'=')); 
			if(array.length >= 2){
				var arraySub=array[1].split(';');
				cookieValue=unescape(arraySub[0]);
			}
		}
		return cookieValue;
	},
	deleteCookie : function(cookieName){
		var expireDate = new Date();
		expireDate.setDate( expireDate.getDate() - 1 );
		var temp=abcCookie.getCookie(cookieName);
		var domainString = User.get("login_id").split("@")[1];
		if(temp){
			abcCookie.setCookie(cookieName,temp,expireDate,"/",domainString, 0);
		}
	}
};