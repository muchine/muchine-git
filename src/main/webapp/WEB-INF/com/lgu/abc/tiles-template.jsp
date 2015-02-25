<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>
<%ObjectMapper om = new ObjectMapper();%>
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma","no-cache"); // HTTP 1.0.
response.setDateHeader("Expires",0); // Proxies
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<c:set var="browserTitle" value="U+ 그룹웨어"/>
	<%@ include file="/WEB-INF/com/lgu/abc/jspf/htmlHead.jspf"%>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/kendoui/styles/kendo.common.min.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc-common/abc-theme-${sessionScope.user.skin_cnfg }.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc-layout.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc-layout-print.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc-icon.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<!--[if lt IE 9]>
		<script type="text/javascript" src="<s:url value='/resources/html5/html5.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<![endif]-->
	<script type="text/javascript" src="<s:url value='/resources/abc/jsUtils/_class.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/jquery-1.8.3/jquery-1.8.3.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/jquery-1.8.3/jquery-ui-1.10.2.custom.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/jquery.base64/jquery.base64.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/kendoui/js/kendo.dataviz.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/kendoui/js/kendo.web.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/kendoui/js/kendo.fx.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	
	<!-- script type="text/javascript" src="<s:url value='/resources/kendoui/examples/content/shared/js/examples.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script> -->
	<script type="text/javascript" src="<s:url value='/resources/ckeditor/ckeditor.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value="/resources/strophejs-1.0.2/strophe.min.js"/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value="/resources/cryptojs/rollups/aes.js"/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>

	<!-- : Notification ToolTip(Top) : 20130415 이하영 -->
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/jquery.qtip/jquery.qtip.min.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc.topnoti/abc.topnoti.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc.topnoti/abc.topnoti.message.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<script type="text/javascript" src="<s:url value='/resources/jquery.qtip/jquery.qtip.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<!-- 밑으로 순서대로 include 시키셈 -->
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-note.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-mail.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-message.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-sign.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-cal.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-todo.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-add.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-topnoti/abc-topnoti-dummy.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<!-- : Notification ToolTip(Top) : -->

	<!-- : Notification - PNS : 20130410 이하영 -->
	<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/abc-pns/abc.chat.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-pns/abc.chat.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-pns/abc.windowGUID.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-pns/pns.browser.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-pns/pns.client.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<%@ include file="/resources/abc/js/abc-common.jsp"%>
	<script type="text/javascript">
	  var ChatOn = (function() {
	    var dataMap = JSON.parse('${sessionScope.user.chatOnJsonString}');
	    return {
	      containKey : function(id) {
	        return dataMap[id];
	      },
	      put : function(id) {
	        $.ajax({
	          url : '/chat/accept',
	          type : 'POST',
	          data : {
	            msng_id : id
	          },
	          success : function() {
	            dataMap[id] = null;
	          }
	        });
	      }
	    };
	  })();

	  $(function() {
		//새로운 텝을 띄우는 경우 다른 tab에 있는 connection을 끊는다.
		//새 tab이 만들어 질때 새로운 windowGuid를 생성한다.
		WindowGUID.windowLoadSetGUID();
		WindowGUID.windowLoadSetGUIDOnForms();
		
		
		 //alert('iFrame: ' + WindowGUID.getWindowGUID());
		  
		pnsClient = new PNSClient({
	      id : User.get('msng_id'),
	      token : User.get('msng_auth_key'),
	      jsessionId : User.get('sessionId'),
	      windowGuid : WindowGUID.getWindowGUID().substring(5,12)
	    });
	  });
	</script>
	<!-- : Notification - PNS : -->
	<script type="text/javascript" src="<s:url value="/resources/abc/jsUtils/_abc.js"/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value="/resources/abc/js/abc-layout.js"/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>

	<!-- @deprecated
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-notification.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	<script type="text/javascript" src="<s:url value='/resources/abc/js/abc-home.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	-->
</head>

<body><div id="wrap" data-global="<%=request.getContextPath()%>">
	
	<!-- 상단 영역 -->
	<header id="layHeader" class="layHeader">
		<h1>
        	<a href="/"><abc:corpLogo corpSeq="${sessionScope.session.company.id}" /></a>
		</h1>
  
  		<%@ include file="jspf/notification-counters.jspf" %>
		<!-- end.LnbBx -->
 
		<ul class="Gnb">
			<%@ include file="jspf/login-info.jspf" %>
			<!-- 20131030 겸직 임시 주석 
			<li>
				<input id="currentDeptAuth" style="width: 150px;"/>			
			</li>
			 -->
			<li><img src="/resources/abc/images/gnbline.jpg"></li>
			
			<li style="line-height:0px;">
				<div id="search_all" class="search_all">
					<input type="text" id="search_all_keyword" />
					<!--<span class="searchOff" id="search_all_off">
					"검색기능끄기"
					<span class="offBtn"></span>
					</span>
				   -->
				</div>
			</li>
			
		    <li id="search_all_on" class="gnbItem gnbSearch" title="검색" style="cursor:pointer"></li>
		    <li><img src="/resources/abc/images/gnbline.jpg"></li>
		    <li class="gnbItem gnbSett" title="설정" onclick="layHeader.onToggle_layer(event, 'sett')" style="cursor:pointer">설정</li>
		</ul>
		
		<!-- 개인정보수정 레이어 -->
		<div id="layHeader_log_layer" class="log_layer dispNone">
			<dl class="tail"></dl>
			<dl class="logItem">
				<dt>
					<a href="/org/user/option/?target=Usr"><span class="log_priedit"></span>개인정보수정</a>
				</dt>
				<dt>
					<a href="#none" onclick="layHeader.clickLogout()"><span class="log_logout"></span>로그아웃</a>
				</dt>
			</dl>
		</div>
		
		<!-- 환경설정 레이어 -->
		<div id="layHeader_sett_layer" class="sett_layer dispNone">
			<dl class="tail"></dl>
			<dl class="logItem">
				<dt>
					<a href="/org/user/option/"><span class="sett_usr"></span>사용자환경설졍</a>
				</dt>
			<c:if test='${sessionScope.user.usr_pwr_cd == 2}'>
				<dt>
					<a href="/org/company/option/"><span class="sett_gw"></span>그룹웨어관리자</a>
				</dt>
			</c:if>
			</dl>
		</div>
	</header>

	<!-- 메인메뉴 영역 -->
	<%@ include file="jspf/top-menus.jspf" %>

	<!-- 메인 영역 -->
	<div class="layHeader_layMenu_height"></div>
	<div class="layMain">
		<!-- 왼쪽영역 : 서브메뉴 네비게이션 -->
		<aside id="laySide" class="laySide"><div id="laySide_outBdr">
			<!-- 서브메뉴 영역 -->
			<div id="laySubMenuBx" cw_menuid=""></div>
			
			<nav id="laySubMenu_mini" class="laySubMenu_mini" menuhomeobj="">
				<ul class="subMenuSet">
	                <li class="subMenuKeep" onclick="laySide.submenu_toggle('wide')">메뉴감추기</li>
				</ul>
	            <ul id="coworkWriteButton" class="btnQuickWrite" style="display:block">
	                <li class="subMenuico" onclick="laySide.submenu_new()"></li>
	            </ul>
			</nav>
		</div></aside>

		<!-- 중앙영역 : 메인 콘텐츠 -->
		<div id="layContent" class="layContent">
			<div id="layContainer" class="layContainer">
				<t:insertAttribute name="content" />
			</div>
		</div>
		
		<!-- 오른쪽영역 : 퀵 메뉴 -->
		<aside id="layQuick" class="layQuick"><div id="layQuick_outBdr">
			<nav>
				<button type="button" class="quickAdd" onclick="layQuick.onOpen_toggle(event)">추가</button>
				<ul id="layQuick_sortable"></ul>
			</nav>
		</div></aside>
		<p class="cb"></p>
		
		<form name="layQuickWinForm" id="layQuickWinForm" method="post" action="/usr/quickmenu/updateQuickMenu.json" onsubmit="return false;">
			<input type="hidden" name="qkUsed_ids" value="" />
		</form>
	</div>
	<!-- end.layMain -->
	
	<!-- 하단 영역 -->
	<footer id="layFooter" class="layFooter">
        <ul>
            <li class="footerBxcenter">
            	<div id="layFooter_newsOutBx">
            		<ul id="layFooter_newsBx"></ul>
            	</div>
            </li>
            <li class="footerBxleft"><img src="/resources/abc/images/lglogo.jpg" alt="엘지로고" style=" margin-right:50px;" /><span class="dispNone">엘지로고</span></li>
            <li class="footerBxright">
                <ul>
                    <li>
                    	<select name="footer_rel_link" id="footer_rel_link" size="1" title="관계사링크" onchange="layFooter.relLink.change()" style="height:20px; margin:0 8px; padding:0 8px;"></select>
                    </li>
                </ul>
            </li>
        </ul>
	</footer>
	<img id="layFooter_btnToggle" src="/resources/abc/images/footerclose.png" onclick="layFooter.onToggle()" />

</div> <!-- end.wrap -->


<!-- 전역 레이어 -->
	<!-- 사용자 프로필 -->
	<%@include file="/INC-JSP/layer/layCmn_profile.jspf" %>
	<!-- 모달.블라인드 박스 -->
	<%@include file="/INC-JSP/layer/layCmn_blind.jspf" %>
	<!-- Alert -->
	<%@include file="/INC-JSP/layer/layCmn_alert.jspf" %>
	<!-- Confirm -->
	<%@include file="/INC-JSP/layer/layCmn_confirm.jspf" %>
	<!-- 우측 슬라이딩 윈도우 팝업 :: 전자결재, 일정, 메일, 쪽지 -->
	<%@include file="/INC-JSP/layer/layCmn_myRightPopup.jspf" %>


<%@ include file="jspf/notifications-action-templates.jspf" %>
</body>
</html>

<!-- 20131029 kendo language set -->
<c:choose>
	<c:when test="${sessionScope.user.lang_cd eq 'ko'}">
		<script type="text/javascript" src="<s:url value='/resources/kendoui/js/cultures/kendo.culture.ko.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	</c:when>
	<c:when test="${sessionScope.user.lang_cd eq 'zh'}">
		<script type="text/javascript" src="<s:url value='/resources/kendoui/js/cultures/kendo.culture.zh.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	</c:when>
	<c:when test="${sessionScope.user.lang_cd eq 'ja'}">
		<script type="text/javascript" src="<s:url value='/resources/kendoui/js/cultures/kendo.culture.ja.min.js'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"></script>
	</c:when>
</c:choose>

