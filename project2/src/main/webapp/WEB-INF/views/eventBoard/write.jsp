<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#content {
		width: 95%;
		min-height: 400px;
	}
</style>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script>
var list = "${list}";
$(document).ready(function() {
	$("#write").on("click", function() {
		if($("#name").val().length==0) {
			$("#helper-name").text("제목은 필수입력입니다").css("color","#ee6e73");
			$("#name").focus();
		}  else
			$("#writeForm").submit();
	});
});
</script>
</head>
<body>
	<form action="/eventBoard/write" method="post" id="writeForm" class="col s12" enctype="Multipart/form-data">
		<div class="row">
			<div class="input-field col s9">
				<i class="material-icons prefix">account_circle</i>
				<input type="text" name="title" id="name">
				<label for="name">제목</label>
				<span class="helper-text" id="helper-name"></span>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<textarea id="content" class="materialize-textarea" name="content"></textarea>
				<label for="content">내용을 입력하세요</label>
			</div>
		<input type="file" name="image" id="url" placeholder="사진을 넣어주세요">
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<button class="btn waves-effect waves-light" type="button" id="write">작성하기
    		<i class="material-icons right">send</i>
  		</button>
	</form>
</body>
</html>