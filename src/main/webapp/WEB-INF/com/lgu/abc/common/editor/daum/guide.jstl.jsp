<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:set var="browserTitle" value="Biz 그룹웨어"/>
<%@ include file="/WEB-INF/com/lgu/abc/jspf/htmlHead.jspf"%>
<link rel="stylesheet" type="text/css" href="<s:url value='/resources/abc/css/cnfg/corp/apr.form.otherEditor.jstl.css'/>?version=<s:eval expression='@abcProp[\'version.cache\']'/>"/>
</head>

<body>
	<ul class="searchboxBg">
		<li class="first"><b>* 표가 포함된 양식을 좀 더 쉽게 작성하기 위해서 아래의 순서를 따라서 작업해주세요.</b></li>
		<li>1. 양식은 엑셀에서 작성을 완료한 후 복사(Ctrl+C) 하여 붙여 넣는(Ctrl+V) 것이 가장 편리하고 쉽습니다.</li>
		<li>2. 엑셀에서 붙여 넣었거나 에디터에서 직접 작성을 완료한 후 오른쪽 상단의 HTML을 체크합니다.</li>
		<li>3. 화면에 HTML 코드가 보이면 전체 선택(Ctrl+A) 한 후 복사(Ctrl+C) 합니다.</li>
		<li>4. 팝업창 뒤편의 양식관리 작성 창으로 이동한 후 편집기 왼쪽 상단의 소스 버튼을 클릭합니다.(다음 에디터 창은 그대로 유지합니다)</li>
		<li>5. 소스 버튼이 클릭된 상태에서 붙여넣기(Ctrl+V)를 하고 왼쪽 상단의 소스 버튼을 다시 한 번 눌러서 클릭을 해제하면 화면이 보입니다.</li>
		<li>6. 양식의 내용을 확인하시고 추가 작업이 필요하시면 다음 에디터 창으로 이동하여 위의 순서대로 작업합니다.</li>
	</ul>

	<!-- <iframe src="http://uie.daum.net/openeditor/sample/latest/editor.html" width="800" height="500"></iframe> -->
	<iframe src="/resources/daumeditor-7.4.18/editor.html" width="950" height="580" scrolling="yes" frameborder="0"></iframe>
</body>
</html>