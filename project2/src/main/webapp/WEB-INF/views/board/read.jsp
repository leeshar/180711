<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#list.mok{
		position: absolute;
		left: 50%;
	}
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script>
	var list = "${list}";
	$(function(){
		var $state = "${state}";
		if($state=="update") {
			$("#product_id").prop("readonly", false);
		}
		$("#update").on("click", function() {
			var $updateForm = $("#updateForm");
			$updateForm.attr("action","/board/update");
			$updateForm.attr("method","post");
			$updateForm.submit();
		});
		$("#delete").on("click", function() {
			var $updateForm = $("#updateForm");
			$updateForm.attr("action","/board/delete");
			$updateForm.submit();
		});
		$("#list").on("click", function() {
			location.href = "/board/list";
		});
		$("#baguni").on("click", function() {
			var $updateForm = $("#updateForm");
			$updateForm.attr("action","/cart/buy");
			$updateForm.attr("method","post");
			$updateForm.submit();
		});
	})
	
</script>
</head>
<body>
	<form id="updateForm" method="post" class="col s12" >
		<table class="striped">
			<tr><td>상품번호</td><td><input type="text" id="product_id" name="product_id" value="${product.product_id }" readonly="readonly"></td></tr>
			<tr><td>판매자</td><td><input type="text" id="seller" name="seller" value="${product.seller }" readonly="readonly"></td></tr>
			<tr><td>상품명</td><td><input type="text" id="product_name" name="product_name" value="${product.product_name }"></td></tr>
			<tr><td>조회수</td><td>${product.read_cnt }</td></tr>
			<tr><td>추천수</td><td>${product.like_cnt }</td></tr>
			<tr><td>가격</td><td>
				<textarea id="price" name="price" class="materialize-textarea">${product.price }</textarea>
			</td></tr>
			<tr><td>상품이미지</td><td><img src="/product/${product.photo_url }"></td></tr>                              
		</table>
	<!-- 서버에서 update 값을 보내왔을 경우 변경, 삭제 버튼 추가 -->
	<c:if test="${state ne null}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
		
		
		
		<button class="btn waves-effect waves-light" type="button" id="update">변경하기
    		<i class="material-icons right">send</i>
  		</button>
  		<button class="btn waves-effect waves-light" type="button" id="baguni">장바구니에 담기
    		<i class="material-icons right">send</i>
  		</button>
  		<button class="btn waves-effect waves-light" type="button" id="delete">삭제하기
    		<i class="material-icons right">send</i>
  		</button>
	</c:if>	
	</form>
	
	
	
  	<%@include file="comment.jsp" %>
  	<button class="mok" type="button" id="list">목록으로</button>
</body>
</html>