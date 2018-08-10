//boardList
app.controller('boardsCtrl',function($scope, $http,$routeParams){
	// boards 리스트 REST방식으로 page값을 넘긴후 데이터를 가져와서 boardsList에 담는다.
	var page = $routeParams.page;
	 $http.get("/aboard2/boards/list/"+page).then(function(response){
			$scope.boardsList = JSON.parse(response.data.records);
		});
	 
	 $scope.searchFuc=function(){
		 $http.get("/aboard2/boards/listAll").then(function(response){
				$scope.listAll = JSON.parse(response.data.listAll); 
			 });
	 };
	 
  });
// boardRead
app.controller('boardsReadCtrl',function($scope,$http,$routeParams){
	// routeParams로 Query의 값을 받을 수 있다.
	// 그 값을 GET 방식 Ajax비동기 통신으로 전달해서 resp변수에 값을 저장한다.
	var bno = $routeParams.bno;
	// ReplyList
	function replyList(){$http.get("/aboard2/boards/reply/list?bno="+bno)
		.then(function(response){
			$scope.list = response.data.list;
			
		});
		};
	// ReplyList
	$http.get("/aboard2/boards/read/"+bno)
	.then(function(response){
		replyList();
		$scope.resp = response.data;
	});
	// ReplyInsert
	$scope.myFuc=function(){
		// ng-click 시 작동 되는 함수이다.
		// myFuc으로 reply 라는 Object를 컨트롤러에 값을 넘겨준다
		var data = {"replytext":$scope.rText,"bno":bno,"id":"service"};
		$http({
		url : '/aboard2/boards/reply/insert',
		method : 'post',
		data: "reply="+JSON.stringify(data),
		contentType:"application/json;charset=UTF-8",
		headers : {'Content-Type': 'application/x-www-form-urlencoded'},

	}).then(function(response){
		// insert가 성공했을 때
		replyList();
		$scope.rText="";
		alert('zz');
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
