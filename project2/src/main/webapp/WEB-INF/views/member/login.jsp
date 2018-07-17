<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
   
    <title></title>
<link href="${pageContext.request.contextPath}/resources/css/signin.css" rel="stylesheet">

  
  </head>

  <body>

    <div class="container">

      <form class="form-signin" action="/" method="POST" style="text-align: center;">
        <h2 class="form-signin-heading" style="font-size: 25px; margin-bottom: 40px;">로그인</h2>
        <label for="inputEmail" class="sr-only">ID</label>
        <input type="text" id="id" name="id" class="form-control" placeholder="ID" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="pwd" name="pwd" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button class="btn btn-lg btn-primary btn-block" type="submit">LOGIN</button>
      </form>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${pageContext.request.contextPath}/resources/assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
