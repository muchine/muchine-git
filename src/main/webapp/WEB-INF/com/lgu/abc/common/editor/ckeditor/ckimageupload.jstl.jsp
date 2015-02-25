<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/taglib.inc"%>

<script>
var CKEditorFuncNum = "${CKEditorFuncNum}";
var imageURL = "${imageURL}";
if(imageURL != "") { window.parent.CKEDITOR.tools.callFunction(CKEditorFuncNum, imageURL, "전송완료 되었습니다."); }
else { alert("전송실패 하였습니다."); }
</script>