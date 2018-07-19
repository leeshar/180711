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
			$("#ebno").prop("readonly", false);
		}
		$("#update").on("click", function() {
			var $updateForm = $("#updateForm");
			$updateForm.attr("action","/eventBoard/update");
			$updateForm.attr("method","post");
			$updateForm.submit();
		});
		$("#delete").on("click", function() {
			var $updateForm = $("#updateForm");
			$updateForm.attr("action","/eventBoard/delete");
			$updateForm.submit();
		});
		$("#list").on("click", function() {
			location.href = "/eventBoard/list";
		});
		
	})
	
</script>
</head>
<body>
	<form id="updateForm" method="post" class="col s12" >
		<table class="striped">
			<tr><td>글번호</td><td><input type="text" id="ebno" name="ebno" value="${eventBoard.ebno }" readonly="readonly"></td></tr>
			<tr><td>글쓴이</td><td><input type="text" id="writer" name="writer" value="${eventBoard.writer }" readonly="readonly"></td></tr>
			<tr><td>작성일</td><td>${eventBoard.write_date }</td></tr>
			<tr><td>제목</td><td><input type="text" id="title" name="title" value="${eventBoard.title }"></td></tr>
			<tr><td>조회수</td><td>${eventBoard.read_cnt }</td></tr>
			<tr><td>배너이미지</td><td><img src="/event/${eventBoard.event_photo }" style="width: 100%; height: 500px;"></td></tr>                              
		</table>
	<!-- 서버에서 update 값을 보내왔을 경우 변경, 삭제 버튼 추가 -->
	<c:if test="${state ne null}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
		
		
		
		<button class="btn waves-effect waves-light" type="button" id="update">변경하기
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