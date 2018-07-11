<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>   <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
</head>
<body  onload="javascript:window_onload()">

  <div class="preloader-wrapper big active">
    <div class="spinner-layer spinner-blue-only">
      <div class="circle-clipper left">
        <div class="circle"></div>
      </div><div class="gap-patch">
        <div class="circle"></div>
      </div><div class="circle-clipper right">
        <div class="circle"></div>
      </div>
    </div>
 
  </div>
  <div>
  	5초뒤에 메인화면으로 이동합니다
  	
  </div>
 <script>
	var id = "${id}";
	
  

      function window_onload(){

         setTimeout('go_url()',5000)  // 5초후 go_url() 함수를 호출한다.

      }

 

      function go_url(){

         location.href="/"  // 페이지 이동...

      }
	
 

  </script>
</body>
</html>