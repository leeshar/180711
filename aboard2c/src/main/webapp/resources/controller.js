//boardsList
app.controller('boardsCtrl',function($scope, $http,$routeParams,boardStorage){
	// boards 리스트 REST방식으로 page값을 넘긴후 데이터를 가져와서 boardsList에 담는다.
	var page = $routeParams.page;
	var categoriName = $routeParams.categoriName;
	$scope.categoriName = categoriName;
	if(categoriName=="공지사항")
		$scope.write=1;
	if(categoriName=="이벤트")
		$scope.write=2;
	// 게시판 리스트 메소드
	console.log(categoriName);
	boardStorage.get(page,categoriName).then(function(data){
		$scope.boardsList = data;
	});
	// 게시판 검색 메소드
	 $scope.searchFuc = function(search){
		 boardStorage.listAll().then(function(data){
		 $scope.listAll = data;
		 });
		 
	 };
  });
// boardsWrite
app.controller('boardsWriteCtrl',function($scope,$routeParams){
	$scope.categoriName = $routeParams.categoriName;
});

// boardsRead
app.controller('boardsReadCtrl',function($scope,$http,$routeParams,boardStorage){
	// routeParams로 Query의 값을 받을 수 있다.
	var bno = $routeParams.bno;
	var categoriName = $routeParams.categoriName;
	$scope.bno = bno;
	var rText = $scope.rText;
	// 게시판 글 상세정보
	boardStorage.boardsRead(bno,categoriName).then(function(data){
		replyList(bno);
		$scope.resp = data;
	});
	// 댓글 리스트
	// 함수로 만들어준 이유는 작성후에 리로드 하기 위해서 이다.
	function replyList(bno){
		boardStorage.replyList(bno).then(function(data){
		$scope.list = data;
			});
	}
	// 댓글 작성
	
	$scope.myFuc = function(rText,bno){
		boardStorage.replyInsert(rText,bno).then(function(data){
			replyList(bno);
			$scope.rText = "";
			alert(data);
	});
		};
});
// usersRegister
app.controller('registerCtrl', function($scope, $http,$window,$location,userStorage){
	// 아이디 중복확인
	$scope.idCheck = function(id){
		userStorage.idCheck(id).then(function(data){
			$scope.status = data;
		});
	};
	// 유저 회원가입
	$scope.regFuc = function(user){
		userStorage.join(user).then(function(data){
			alert(data);
			$window.location.href="http://localhost:8081/aboard2/#!/users/welcome";
		});
	};
	
});
// 아이디 찾기
app.controller('findIdCtrl',function($rootScope,$scope, $http,$window, $location,userStorage){
	$rootScope.authToken = Math.floor((Math.random() * 99999) + 1);
	// 1. 인증번호
	$scope.emailAuth = function(isValid){
       if(!isValid)
    	   alert("제대로 입력해주세요");
       if(isValid){
    	   $scope.dataLoading = true;
   		userStorage.emailToken(email).then(function(data){
   			alert(data);
   			$scope.emailInput=1;
   			$scope.dataLoading = false;
   		});
       }
	};
	// 2. 아이디 찾기
	$scope.findId = function(find,emailToken){
		console.log(find.irum);
		console.log(emailToken);
		userStorage.findId(find, emailToken).then(function(data){
			if(data=='NO')
				$rootScope.success=2;
			if(data!='NO')
				$rootScope.success=1;
			$rootScope.findId=data;
			console.log($rootScope.success);
			$window.location.href="http://localhost:8081/aboard2/#!/users/findId/result";
		});
	}
});
// 아이디 찾기 결과
app.controller('findIdResultCtrl', function($scope,$rootScope){
	$scope.success = $rootScope.success;
	
});
// 비밀번호 찾기
app.controller('findPwdCtrl',function($scope,$http, userStorage){
	// 비밀번호 아이디
	$scope.findPwd = function(id){
		userStorage.findPwd(id).then(function(data){
			$scope.pwdSuccess=1;
			$scope.email=data;
			
			
		});
	};
	// 비밀번호 이메일
	$scope.pwdSend = function(id){
		userStorage.pwdSend(id).then(function(data){
			
		});
	};
	
	
});
//회원정보수정
app.controller('updateUserCtrl',function($scope,$http,$window,$cookieStore,userStorage){
	var id = $cookieStore.get("userId");
	// 회원정보
	userStorage.userInfo(id).then(function(data){
		$scope.id=data.ID;
		$scope.email=data.EMAIL;
		$scope.irum=data.IRUM;
	});
	// 회원정보수정
	$scope.updateUser = function(pwd,email,id){
		userStorage.updateUser(pwd,email,id)
		.then(function(data){
			alert(data);
			$window.location.href="http://localhost:8081/aboard2";
		});
		
		
	};
	
});

// 로그인 인증 부분
angular.module('myApp')
.controller('LoginCtrl',
    ['$scope', '$rootScope', '$location', 'AuthenticationService','$http',
    function ($scope, $rootScope, $location, AuthenticationService, $http) {
        // reset login status
        AuthenticationService.ClearCredentials();
 
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.id, $scope.pwd, function(response) {
                if(response.status==200&&response.data==="성공") {
                    AuthenticationService.SetCredentials($scope.id, $scope.pwd);
                    $location.path('/');
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);
// slide nav
app.controller("slideCtrl",function($scope){
	$scope.openNav = function openNav() {
	    document.getElementById("mySidenav").style.width = "250px";
	    document.getElementById("main").style.marginLeft = "250px";
	    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
	}
	$scope.closeNav = function closeNav() {
	    document.getElementById("mySidenav").style.width = "0";
	    document.getElementById("main").style.marginLeft= "0";
	    document.body.style.backgroundColor = "white";
	}

});