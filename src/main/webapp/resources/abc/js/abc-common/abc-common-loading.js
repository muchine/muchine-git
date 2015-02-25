/**
 * 공통 Loading
 */
var showLoading = function(wrapWindow){
	if(!wrapWindow){
		wrapWindow = "body";
	}
    	var overlay = $("<div class='k-overlay' style='display:none;z-index: 10002; opacity: 0.35;filter: alpha(opacity=35);'></div>");
	    var documentWidth = $(wrapWindow).width();
    	var documentHieght = $(wrapWindow).height();
    	var willLeft = documentWidth /2 - 50;
    	var willTop = documentHieght /2 - 50;
    	var loading = $("<div style='display:none;z-index:10003;position:absolute;' id='loadingView'><img src='/resources/abc/images/mai/loading.gif'></div>");
    	
    	loading.css({"left":willLeft,"top":willTop});
    	
    	$(wrapWindow).append(overlay);
    	$(wrapWindow).append(loading);
    	
    	overlay.fadeIn("fast");
    	loading.fadeIn("slow");
    };
    
var hideLoading = function(){
    	$(".k-overlay").remove();
    	$("#loadingView").remove();
    };
    
var showCustomWindow = function(title,html,init){
	$("body").append("<div id='customWindow'></div>");
	var window = $("#customWindow");
	window.kendoWindow({
		modal : true,
		minwidth: "280px",
		minHeight:"15px",
		title: (title.length>0)?title:false,
		content:{
			template:html
		},
		close : function(){
			$("#customWindow").data("kendoWindow").destroy();
		}
	});
	$(".k-window").css("top","100px");
	window.data("kendoWindow").center();
	window.data("kendoWindow").open();
	init();
};

var closeCustomWindow = function(html,init){
	$("#customWindow").data("kendoWindow").destroy();
};