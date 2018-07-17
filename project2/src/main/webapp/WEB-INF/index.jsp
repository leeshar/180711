<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ICIA Sensor Board</title>
<!-- 전체적인 기본 스타일 지정 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script>
		
		  document.addEventListener('DOMContentLoaded', function() {
		    var elems = document.querySelectorAll('.container');
		    var instances = M.Carousel.init(elems, options);
		  });


		  $(document).ready(function(){
		    $('.container').carousel();
		  });
		  $(function() {
				$("#logout").on("click", function(e) {
					e.preventDefault();
					var $form = $("<form></form>");
					$form.attr("action","/member/logout");
					$form.attr("method","post");
					$("<input>").attr("type","hidden").attr("name", "${_csrf.parameterName}").val("${_csrf.token}").appendTo($form);
					$form.appendTo("body");
					$form.submit();
				});
				$("#resign").on("click", function(e) {
					e.preventDefault();
					var $form = $("<form></form>");
					$form.attr("action","/member/resign");
					$form.attr("method","post");
					$("<input>").attr("type","hidden").attr("name", "${_csrf.parameterName}").val("${_csrf.token}").appendTo($form);
					$form.appendTo("body");
					$form.submit();
				});
			});
</script>
</head>
<body>
<div id="wrap">
<header>
<div id="header-bar" class="header-wrapper">
	<a href="#" class="logo">LOGO</a>
	
	<sec:authorize access="isAnonymous()">
		<ul id="left" class="loginbar">
			<li><a href="/member/idFind">아이디 찾기</a></li>
			<li><a href="/member/pwdFind">비밀번호 찾기</a></li>
		</ul>
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
		<ul id="left" class="loginbar">
		<li><a href="/member/register">회원가입</a></li>
		<li><a href="/member/login">로그인</a></li>
		</ul>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<ul id="left" class="login">
			<li class="right"><a href="#">${list.id}님 환영합니다</a></li>
			<li class="right-10"><a href="/member/logout" id="logout">로그아웃</a></li>
		</ul>
	
	</sec:authorize>	
</div>
</header>
<nav>
 <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="http://localhost:8081/">MAIN</a>
    </div>
    <div class="navMenu">
    <ul class="menu">
      <li><a href="/">Home</a></li>
      <li><a href="#">이벤트</a></li>
      <li><a href="/board/list">게시판</a></li>
      <li><a href="/notice/list">공지사항</a></li>
      
    </ul>
    </div>
  </div>

</nav>
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
		<a href=# data-target="slide-out" class="sidenav-trigger" id="header-bar">내정보보기</a>
		<script>
		$(".sidenav-trigger").css("position","absolute");
		$(".sidenav-trigger").css("right","0").css("top","0");
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
<section>
	<div class="container">
	 <a class="carousel-item" href="#one!"><img src="/index/img-1.jpg" style="width: 100%;height: 100%;"></a>
    <a class="carousel-item" href="#two!"><img src="/index/img-2.jpg" style="width: 100%;height: 100%;"></a>
    <a class="carousel-item" href="#three!"><img src="/index/img-3.jpg" style="width: 100%;height: 100%;"></a>
	</div>

</section>
<footer>
<div class="mastfoot">
            <div class="inner">
<p>회사명: ICIA</p>
<p>주소 : 학익동 5층</p>
<p>관리자 이메일 : jenny@naver.com
<p>사업자 등록번호 : 211-06-76999</p>
<p>통신판매업 신고 : 제 상동 - 13381호 [사업자 정보 확인]</p>
<p>회사소개   |   쇼핑가이드   |   개인정보보호정책   |   이용약관</p>
            </div>
          </div>
</footer>
</div>
 
</body>
</html>