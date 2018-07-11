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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$("#write").on("click", function() {
		if($("#name").val().length==0) {
			$("#helper-name").text("이름은 필수입력입니다").css("color","#ee6e73");
			$("#name").focus();
		}  else
			$("#writeForm").submit();
	});
});
</script>
</head>
<body>
	<form action="/board/write" method="post" id="writeForm" class="col s12" enctype="Multipart/form-data">
		<div class="row">
			<div class="input-field col s9">
				<i class="material-icons prefix">account_circle</i>
				<input type="text" name="product_name" id="name">
				<label for="name">상품명</label>
				<span class="helper-text" id="helper-name"></span>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<textarea id="price" class="materialize-textarea" name="price"></textarea>
				<label for="price">가격 입력하세요</label>
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