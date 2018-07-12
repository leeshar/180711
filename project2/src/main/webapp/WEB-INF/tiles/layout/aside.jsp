<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<title>Insert title here</title>
<style>
	#aside{
		height: 1000px;
		border: 2px solid black;
		text-align: center;
		font-size: 30px;
	
	}
	#aside div{
		height:180px;
		margin-top:50px;
		border: 2px solid black;
		line-height: 180px;
		
	}
</style>
</head>
<body>
	<div id="aside">
		<div><h3>광고문의</h3>
			<h5>1599-1440</h5>
		</div>
		<div><h3>광고문의</h3>
			<h5>1599-1440</h5>
		</div>
		<div><h3>광고문의</h3>
			<h5>1599-1440</h5>
		</div>
		
	</div>
	<sec:authorize access="hasRole('ROLE_USER')">
		<ul id="slide-out" class="sidenav">
    <li><div class="user-view">
      <div class="background">
        <img src="/upload/33.jpg">
      </div>
      <a href="#user"><img class="circle" src="/upload/${list.photo }"></a>
      <a href="#name"><span class="white-text name">${list.id}님 환영합니다</span></a>
      <a href="#email"><span class="white-text email">${list.email }</span></a>
    </div></li>
    <li><a href="/"><i class="material-icons">cloud</i>메인화면으로</a></li>
    <li><div class="divider"></div></li>
    <li><a class="subheader">내 메뉴</a></li>
    <li><a class="waves-effect" href="/cart/list"><i class="material-icons">local_grocery_store</i>장바구니</a></li>
    <li><a class="waves-effect" href="/board/list"> <i class="material-icons">edit</i>게시판</a></li>
	<li><a class="waves-effect" href="/member/resign" id="resign"> <i class="material-icons">person</i>회원탈퇴</a></li>
	</ul>
		<a href=# data-target="slide-out" class="sidenav-trigger">내정보보기</a>
		<script>
		$(".sidenav-trigger").css("position","absolute");
		$(".sidenav-trigger").css("right","0").css("top","0");
		$(".sidenav-trigger").css("font-family","sans-serif").css("font-size","22px");
		var list = "${list}";
		 document.addEventListener('DOMContentLoaded', function() {
	          var elems = document.querySelectorAll('.sidenav');
	          var instances = M.Sidenav.init(elems, options);
	         
	        });
	
	    
	        $(document).ready(function(){
	          
	        	$('.sidenav').sidenav();
	        });
	            
		</script>
		</sec:authorize>
</body>
</html>

