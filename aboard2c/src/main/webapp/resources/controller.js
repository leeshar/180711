//boardList
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
// boardRead
app.controller('boardsReadCtrl',function($scope,$http,$routeParams,myStorage){
	// routeParams로 Query의 값을 받을 수 있다.
	var bno = $routeParams.bno;
	$scope.bno = bno;
	var rText = $scope.rText;
	// 게시판 글 상세정보
	myStorage.boardsRead(bno).then(function(data){
		myStorage.replyList(bno);
		$scope.resp = data;
	});
	// 댓글 리스트
	myStorage.replyList(bno).then(function(data){
		$scope.list = data;
	});
	// 댓글 작성
	$scope.myFuc = function(rText,bno){
		myStorage.replyInsert(rText,bno).then(function(data){
			myStorage.replyList(bno);
			$scope.rText = "";
			alert(data);
	});
		};
});
// UserController 부분
app.controller('registerCtrl', function($scope, $http,$window,$location){
	// Join
	
	$scope.regFuc = function(){
		$http({
			url:"/aboard2/users/join",
			method:'post',
			data: 'user='+JSON.stringify($scope.user),
			contentType:"application/json;charset=UTF-8",
			headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			
		}).then(function(response){
			alert("회원가입 되었습니다!");
			$window.location.href="http://localhost:6305/aboard2/#!/users/welcome";
		});
	};
	// idCheck
	$scope.idCheck = function(){
		var id = $scope.user.id;
		
		// 아이디가 중복 되면 400(Bad Request) status == 400 중복된아이디
		// 아이디가 중복 되지 않으면 200 status == 200 사용가능
		$http.get("/aboard2/users/idCheck/"+id)
		.then(function(response) {
			$scope.status = response.status;
			console.log(response.status);
		}, function myError(response){
			$scope.status = response.status;
			console.log(response.status);
		});

	};
});
