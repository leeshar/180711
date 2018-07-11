<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
var msg = "${msg}";
	if(msg =='SUCCESS'){
		alert("회원가입을 축하드립니다");
	}
var list = "${list}";
</script>
</head>

<body>
<p>안녕</p>
${list }
</body>
</html>