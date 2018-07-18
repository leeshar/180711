<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	var list = "${list}";
	$(function() {
		$("#write").on("click", function() {
			location.href= "/eventBoard/write"
		});
	})
	var msg = "${msg}";
		if(msg==='WRITE'){
			alert("작성성공");
			
		}
		if(msg==='UPDATE'){
			alert("수정성공");
		}
		
</script>
</head>
<body>
	<h3>페이지 결과가 없습니다. 돌아가주세요</h3>
	<button class="btn waves-effect waves-light" type="button" id="write">글쓰기
    		<i class="material-icons right">send</i>
  		</button>
</body>
</html>