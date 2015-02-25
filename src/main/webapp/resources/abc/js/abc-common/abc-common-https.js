var $https = {
	proxyDomain : "https://" + User.getProxyUrl(),

	ajax : function(params) {
//		if ($https.canSupport())
//			$https.parameterize(params);
		
		$.ajax(params);
	},

	parameterize : function(params) {
		params.headers = {
			"x-ajax-session" : User.get("sessionId")
		};

		/*
		 * NOTE: If apache http server sets cookies, a main request is sent with those cookies and fails.
		 * Comment this because we don't need any cookies here.  
		 */
//		params.xhrFields = {
//			withCredentials : true
//		};
		params.crossDomain = true;
		params.url = $https.proxyDomain + params.url;
	},

	canSupport : function() {
		/*
		 * Chrome and IE10+ makes a cross domain request with XMLHttpRequest but IE8 uses XDomainRequest object.
		 * In other words, the browser is over IE8 if it does not have XDomainRequest object. IE10 is exceptional.
		 * IE10 has XDomainRequest as well but it seems not to use it for CORS. We need to look into more condition 
		 * i.e. XMLHttpRequest has withCredentials property, which means a browser can support CORS.
		 */
		return !window.XDomainRequest || 'withCredentials' in new XMLHttpRequest();
	}

};
