<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>

<c:choose>
	<c:when test="${contents.isEmpty()}">
		<div class="noti_list">
			<div style="padding-top:20px;">${messageForNoData}</div>
		</div>
	</c:when>
	<c:otherwise>
		<c:forEach var="item" varStatus="status" items="${contents}">
		<div class="noti_list">
			<a href="${item.uri()}">
				<div class="userPic"><img src="${item.user().photo.url}" height="51" width="51"></div>
				<div class="notiContents">
					<span class="noti_name">${item.user().name}</span>
					<span class="noti_txt">${item.title()}</span>
					<span class="noti_date">${item.elapsed()}</span>
				</div>
			</a>
		</div>
		</c:forEach>
	</c:otherwise>
</c:choose>