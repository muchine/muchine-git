<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>
<%ObjectMapper om = new ObjectMapper();%>
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
</head>

<body><div id="wrap" data-global="<%=request.getContextPath()%>">
	<t:insertAttribute name="content" />
</body>
</html>


