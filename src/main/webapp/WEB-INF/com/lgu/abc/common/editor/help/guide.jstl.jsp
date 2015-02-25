<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:set var="browserTitle" value="Biz 그룹웨어"/>
<%@ include file="/WEB-INF/com/lgu/abc/jspf/htmlHead.jspf"%>
<script type="text/javascript" src="<s:url value="/resources/jquery-1.8.3/jquery-1.8.3.min.js"/>"></script>

<style rel="stylesheet" type="text/css">
	*{font:normal 13px Dotum, "돋움체", Verdana, sans-serif}
	*{padding:0;margin:0}
	img,fieldset{border:0}
	table{border-collapse:collapse;background:#515151}
	ul,ol,li{list-style:none;}
	a{text-decoration:none}
	.mT20{margin-top:20px}
	.guide_tabs{width:800px;background:#515151;}
	.guide_tabs ul li{float:left}
</style>

<script type="text/javascript">
$(document).ready(function() {
	$('.tab2_cont').css('display','none');
	$(function() {
		$('.guide_tabs li').click(function() {
			$('.guide_tabs li').each(function() {
				$(this).find('img').attr('src', $(this).find('img').attr('src').replace('_on','_off'));
			});
			$(this).find('img').attr('src', $(this).find('img').attr('src').replace('_off','_on'));
			var tabIndex = $(this).index();
			$('.guide_tabs>div').eq(tabIndex).show().siblings('div').hide();
		});
	});
});
</script>
</head>

<body>
	<div class="guide_tabs">
		<ul>
			<li><a href="#"><img src="/resources/abc/images/editor/help/guide_edit_tab1_on.png" alt="워드와 그룹웨어 에디터 사용하기"></a></li>
			<li><a href="#"><img src="/resources/abc/images/editor/help/guide_edit_tab2_off.png" alt="워드와 그룹웨어 에디터,Daum이나 Naver 에디터 활용하기"></a></li>
		</ul>
		<div class="tab1_cont">
			<img src="/resources/abc/images/editor/help/tab1.png" alt="워드와 그룹웨어 에디터 사용하기" class="mT20">
			<img src="/resources/abc/images/editor/help/guide_edit1.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit01.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit02.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit03.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit04.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit05.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit06.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit_refer.png" alt="">
		</div>
		<div class="tab2_cont">
			<img src="/resources/abc/images/editor/help/tab2.png" alt="워드와 그룹웨어 에디터,Daum이나 Naver 에디터 활용하기" class="mT20">
			<img src="/resources/abc/images/editor/help/guide_edit1.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit01.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit02.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit03.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit04.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit15.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit16.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit17.png" alt="">
			<img src="/resources/abc/images/editor/help/guide_edit_refer.png" alt="">
		</div>
	</div>
</body>
</html>