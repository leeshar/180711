<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>

<title>Insert title here</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script>
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
 
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});
$(function(){
	$("#join").on("click",function(){
		var irum_reg = /^[가-힣]{2,5}$/;
		var id_reg = /^[a-z0-9]{8,12}$/;
		var pwd_reg = /^[a-zA-Z0-9]{8,10}$/;
		var email_reg = /^[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+$/;
		var $irum = $("#irum");
		var $pwd = $("#pwd");
		var $pwd2 = $("#pwd2");
		var $email = $("#email");
		var $id = $("#id");
		if($id_reg.text($id.val())==false){
			$("#id_msg").text("ID는 8~12자리 입니다.").css("color","#ee6e73");
		}
		if($id.val()==''){
			$("#id_msg").text("ID는 필수입력입니다").css("color","#ee6e73");			
			return;
		}
		
		if(irum_reg.test($irum.val())==false){
			$("#irum_msg").text("이름은 한글 2~5자 입니다").css("color","#ee6e73");
			return;
}
		else
			$("#irum_msg").text("정확한 이름입니다").css("color","#05c8b4");
		
		if(pwd_reg.test($pwd.val())==false){
			$("#pwd_msg").text("비밀번호는 8~10자입니다").css("color","#ee6e73");
			return;
		}
		
		if(email_reg.test($email.val())==false){
			$("#email_msg").text("올바른 이메일 형태가 아닙니다").css("color","#ee6e73");
			return;
		}
		
		if($pwd.val()!=$pwd2.val()){
			$("#pwd2_msg").text("비밀번호를 확인하십시오").css("color","#ee6e73");
			return;
		}
	$("#joinForm").submit();
});
});



	$(function(){
		$("#idCheck").on("click", function(){
			$.ajax({
				url:"/member/idCheck",
				type:"post",
				data: "id=" + $("#id").val(),
				success: function(result){
					if(result=="true"){
						$("#id_msg").text("멋진아이디군요").css("color","green");
					}
					else{
						$("#id_msg").text("중복된 아이디입니다.").css("color", "blue")
					}
				},
				error: function(request){
					console.log("status:" + request.status);
					$("#id_msg").text("정확하게 입력해주세요.").css("color","red");
				}
					
			
			
			});
			
			
			
		});	
	
	
	});
	$(".form-control").css("margin","0");
</script>
</head>
<body>
	<div class="container">
			<div class="row main">
				<div class="panel-heading">
	               <div class="panel-title text-center">
	               	
	               	</div>
	            </div> 
				<div class="main-login main-center">
					<form id="joinForm" class="form-horizontal" method="post" action="/member/register" enctype="multipart/form-data">
						
						<div class="form-group">
							<label for="name" class="cols-sm-2 control-label">아이디</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="material-icons">person</i><input type="text" class="" name="id" id="id" placeholder="아이디를 입력해주세요"/><span id="id_msg"></span>
								</div>
							</div>
						</div>
						<button type="button" id="idCheck">사용여부확인</button>
							<div class="form-group">
							<label for="password" class="cols-sm-2 control-label">비밀번호</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="fa fa-lock fa-lg"></i>
									<input type="password" class="" name="pwd" id="pwd"  placeholder="비밀번호를 입력하세요"/><span id="pwd_msg"></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="confirm" class="cols-sm-2 control-label">비밀번호 확인</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="fa fa-lock fa-lg"></i>
									<input type="password" class=""  id="pwd2" placeholder="비밀번호 확인"/><span id="pwd2_msg"></span>
								</div>
							</div>
						</div>


						<div class="form-group">
							<label for="username" class="cols-sm-2 control-label">이름</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="fa fa-users fa" ></i>
									<input type="text" class="" name="irum" id="irum"  placeholder="이름을 입력해주세요"/><span id="irum_msg"></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="username" class="cols-sm-2 control-label">이메일</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="fa fa-users fa"></i>
									<input type="text" class="" name="email" id="email"  placeholder="이메일을 입력해주세요"/><span id="email_msg"></span>
								</div>
							</div>
						</div>
						
						
						<div class="form-group">
							<label for="username" class="cols-sm-2 control-label">사진</label>
							<div class="cols-sm-10">
								<div class="input-field col s6">
									<i class="fa fa-users fa"></i>
									<input type="file" class="" name="image" id="image"  placeholder="이쁜사진넣어주세요"/>
								</div>
							</div>
						</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
						<button class="btn waves-effect waves-light" type="button" name="action" id="join">회원가입
    <i class="material-icons right">send</i>
  </button>
						<div class="login-register">
				            <a href="/member/login">로그인하러가기</a>
				         </div>
					</form>
					
				</div>
			</div>
		</div>
</body>
</html>