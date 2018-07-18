<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script>
	var list = "${list}";
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	 
	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});
	$(function(){
		$("#memberUpdateBtn").on("click",function(){
			var pwd_reg = /^[a-zA-Z0-9]{8,10}$/;
			var email_reg = /^[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+$/;
			var $pwd = $("#pwd");
			var $pwdCheck = $("#pwdCheck");
			var $email = $("#email");
		
			if(pwd_reg.test($pwd.val())==false){
				$("#pwd_msg").text("비밀번호는 8~10자입니다").css("color","#ee6e73");
				return;
			}
			
			if(email_reg.test($email.val())==false){
				$("#email_msg").text("올바른 이메일 형태가 아닙니다").css("color","#ee6e73");
				return;
			}
			
			if($pwd.val()!=$pwdCheck.val()){
				$("#pwdCheck_msg").text("비밀번호를 확인하십시오").css("color","#ee6e73");
				return;
			}
		$("#updateForm").submit();
	});
	});
</script>
<style>
	.photo{
		width: 200px;
		height: 200px;
		margin-left: 200px;
	}
	#updateForm{
		
		margin-left: 200px;
		width: 60%;
		height: 70%;
	}
</style>
</head>
<body>
<div class="photo">
	<img src="/upload/${list.photo }" style="width: 200px; height: 200px;">
</div>
<form id="updateForm" action="/member/update" method="post" enctype="multipart/form-data">
<div class="input-field col s1">
	ID<input class="validate" id="id" type="text" value="${list.id }" readonly="readonly">

</div>
<div class="input-field col s1">
	이름<input class="validate" id="irum" type="text" value="${list.irum }" readonly="readonly">
	
	</div>
<div class="input-field col s1">
	<input class="validate" type="password" id="pwd" name="pwd"><span id="pwd_msg"></span>
	<label for="pwd">비밀번호</label>
	</div>
<div class="input-field col s1">
	<input class="validate" type="password" id="pwdCheck" name="pwdCheck"><span id="pwdCheck_msg"></span>
	<label for="pwdCheck">비밀번호 확인</label>
	</div>
<div class="file-field input-field">
	<div class="btn">
        <span>사진</span>
        <input type="file">
      </div>
      <div class="file-path-wrapper">
        <input class="file-path validate" type="text">
      </div>
	
	</div>
<div class="input-field col s1">
	<input class="validate" type="email" value="${list.email }" id="email" name="email"><span id="email_msg"></span>
	<label for="email">이메일</label>
</div>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="button" id="memberUpdateBtn">정보수정</button>
</form>
</body>
</html>