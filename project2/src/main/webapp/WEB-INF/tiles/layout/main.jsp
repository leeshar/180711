<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ICIA Sensor Board</title>
<!-- 전체적인 기본 스타일 지정 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">

   
</head>
<body>
<div id="wrap">
	<header>
		<tiles:insertAttribute name="header"/>    
	</header>
	<nav>
		<tiles:insertAttribute name="nav"/>    
	</nav>
	<div id="main">
		<aside>
			<tiles:insertAttribute name="aside"/>    
		</aside>
		<section>
			<tiles:insertAttribute name="section"/>    		
		</section>
	</div>
	<footer>
		<tiles:insertAttribute name="footer"/>    
	</footer>
</div>
 
</body>
</html>