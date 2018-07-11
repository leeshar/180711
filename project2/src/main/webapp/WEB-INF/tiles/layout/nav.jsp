<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.container-fluid{
		font-size: 28px;
		
		
	}
	.navbar-inverse .navbar-nav li a{
		color: white;
	}
	.navMenu .menu li{
		margin-left: 70px;
		display:inline-block;
	}
	.navMenu{
		float: left;
		text-align: center;
		margin-left: 300px;
	}
	a:hover{
	text-decoration: none;
		}
</style>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<script>
		$("nav").css("background-color","#2196f3");
	</script>
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="http://localhost:8081/">MAIN</a>
    </div>
    <div class="navMenu">
    <ul class="menu">
      <li><a href="/">Home</a></li>
      <li><a href="#">이벤트</a></li>
      <li><a href="/board/list">게시판</a></li>
      <li><a href="/pboard/productPage">상품</a></li>
      
    </ul>
    </div>
  </div>

</body>
</html>