var ValidatorUtils =  {

	validateNumber : function(object, length, nextObjectId) {
		ValidatorUtils.allowsOnlyNumber(object);
		if (object.value.length == length) {
			$("#" + nextObjectId).focus();
			$("#" + nextObjectId).select();
		}
	},
	
	validateIp : function(object, length, nextObjectId) {
		cnfgCommon.numbersAndMultipleOnly(object);
		if (object.value.length == length || object.value.indexOf(".") >= 0) {
			object.value = object.value.replace(".","");
			$("#" + nextObjectId).focus();
			$("#" + nextObjectId).select();
		}
	},
		
	allowsOnlyNumber : function(object) {
		var validated = abcValidCheck.getRegexpReplaceByValue(object.value, /[^\d]/, "");
		object.value = validated;
	},
	
	allowsNumbersAndDash : function(object) {
		var validated = abcValidCheck.getRegexpReplaceByValue(object.value, /[^\d-]/, "");
		object.value = validated;
	},
	
	validateEmail : function(form, nameField, domainField, errorId) {
		var name = $ogs.fn.setTrimInput(form[nameField]);
		var domain = $ogs.fn.setTrimInput(form[domainField]);
		
		if (ValidatorUtils.isNull(name) && ValidatorUtils.isNull(domain)) return true;
		
		if (!ValidatorUtils.validateNotNullField(form, nameField, errorId)) return false;
		if (!ValidatorUtils.validateNotNullField(form, domainField, errorId)) return false;
		
		var email = name + "@" + domain;
		
		if (!$ogs.fn.isEmail(email)) {
			ValidatorUtils.notifyFailure(errorId, "유효한 이메일 형식이 아닙니다.");
			return false;
		}
		
		if (email.length > 40) {
			ValidatorUtils.notifyFailure(errorId, "이메일은 40자 이내여야 합니다.");
			return false;
		}
		
		return true;
	},
	
	validatePhone : function(elementId, errorId) {
		var area	= $.trim($("input[name=" + elementId + "\\.area]").val());
		var region	= $.trim($("input[name=" + elementId + "\\.region]").val());
		var local	= $.trim($("input[name=" + elementId + "\\.local]").val());
		
		if (!area && !region && !local) return true;
		if (!area || !region || !local) {
			ValidatorUtils.notifyFailure(errorId, "유효한 전화번호가 아닙니다.");
			return false;
		}
		
		return true;
	},
	
	validateZip : function(elementId, errorId) {
		var first	= $.trim($("input[name=" + elementId + "\\.first]").val());
		var last	= $.trim($("input[name=" + elementId + "\\.last]").val());
		
		if (!first && !last) return true;
		if (!first || !last) {
			ValidatorUtils.notifyFailure(errorId, "유효한 우편번호가 아닙니다.");
			return false;
		}
		
		return true;
	},
	
	validateNotNullValue : function(value, errorId) {
		if(ValidatorUtils.isNull(value)) {
			ValidatorUtils.notifyFailure(errorId, "필수 입력 항목입니다.");
			return false;
		}
		
		return true;
	},
	
	/**
	 * Validate mulpitle value/errorId array
	 * @param params[n].value a value to inspect
	 * @param params[n].errorId an element id to display error message
	 */
	validateNotNullValues : function(params) {
		var valid = true;
		for (var i = 0; i < params.length; i++) {
			if (!ValidatorUtils.validateNotNullValue(params[i].value, params[i].errorId)) {
				valid = false;
			}
		}
		
		return valid;
	},
	
	validateNotNullField : function(form, fieldName, errorId) {
		var value = $ogs.fn.setTrimInput(form[fieldName]);
		return ValidatorUtils.validateNotNullValue(value, errorId);
	},
	
	isNull : function(value) {
		return !value || value.length < 1;
	},
	
	alertFailure : function(message) {
		alert_({
			message : message,
			callback : function(e) {}
		});
	},
	
	notifyFailure : function(errorId, message) {
		$("#" + errorId).text(message).show();
	}
	
};

