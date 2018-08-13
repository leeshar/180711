//boardsList
app.controller('boardsCtrl',function($scope, $http,$routeParams,myStorage){
	// boards 리스트 REST방식으로 page값을 넘긴후 데이터를 가져와서 boardsList에 담는다.
	var page = $routeParams.page;
	// 게시판 리스트 메소드
	myStorage.get(page).then(function(data){
		$scope.boardsList = data;
	});
	// 게시판 검색 메소드
	 $scope.searchFuc = function(search){
		 myStorage.listAll().then(function(data){
		 $scope.listAll = data;
		 });
		 
	 };
  });
// boardsRead
app.controller('boardsReadCtrl',function($scope,$http,$routeParams,myStorage){
	// routeParams로 Query의 값을 받을 수 있다.
	var bno = $routeParams.bno;
	$scope.bno = bno;
	var rText = $scope.rText;
	// 게시판 글 상세정보
	myStorage.boardsRead(bno).then(function(data){
		replyList(bno);
		$scope.resp = data;
	});
	// 댓글 리스트
	// 함수로 만들어준 이유는 작성후에 리로드 하기 위해서 이다.
	function replyList(bno){
		myStorage.replyList(bno).then(function(data){
		$scope.list = data;
			});
	}
	// 댓글 작성
	
	$scope.myFuc = function(rText,bno){
		myStorage.replyInsert(rText,bno).then(function(data){
			replyList(bno);
			$scope.rText = "";
			alert(data);
	});
		};
});
// usersRegister
app.controller('registerCtrl', function($scope, $http,$window,$location,myStorage){
	// 아이디 중복확인
	$scope.idCheck = function(id){
		myStorage.idCheck(id).then(function(data){
			$scope.status = data;
		});
	};
	// 유저 회원가입
	$scope.regFuc = function(user){
		myStorage.join(user).then(function(data){
			alert(data);
			$window.location.href="http://localhost:8081/aboard2/#!/users/welcome";
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
                if(response.data) {
                    AuthenticationService.SetCredentials($scope.id, $scope.pwd);
                    $location.path('/');
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);
