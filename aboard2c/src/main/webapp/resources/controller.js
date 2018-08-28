//boardsList
app.controller('boardsCtrl',function($scope, $http, $routeParams, boardStorage,$window,$location)
{
// boards 리스트 REST방식으로 page값을 넘긴후 데이터를 가져와서 boardsList에 담는다.
	var page = $routeParams.page;
	var categoriName = $routeParams.categoriName;
	$scope.categoriName = categoriName;
	if (categoriName == "공지사항")
		$scope.write = 1;
	if (categoriName == "이벤트")
		$scope.write = 2;
// 게시판 리스트 메소드
	boardStorage.get(page, categoriName).then(function(data) {
// 게시판 리스트 데이터
	$scope.boardsList = data.list;
	console.log($scope.boardsList);
// 게시판 페이징 데이터
	$scope.pagination = data.pagination;
	$scope.startPage = data.pagination.startPage;
	var startPage = data.pagination.startPage;
	var endPage = data.pagination.endPage;
	var render = [];
	for (var startPage = startPage; startPage < endPage + 1; startPage++) {
		// push로 해결. ui-bootstrap 안쓰고 페이징
		render.push(startPage);
	}
	$scope.render = render;
				
	});	
// 게시판 검색 메소드
	$scope.searchFuc = function(search) {
		var page = 1;
		var search = $scope.search;
		console.log(search);
		$window.location.href="/aboard2/#!/boards/search/"+search+"/"+page+"/"+categoriName;
		};

		
});
// boardsSearch
app.controller('boardsSearchCtrl',function($scope,$http,boardStorage,$routeParams,$window,$location){
	var search = $routeParams.search;
	var page = $routeParams.page;
	var categoriName = $routeParams.categoriName;
	$scope.categoriName = categoriName;
	if (categoriName == "공지사항")
		$scope.write = 1;
	if (categoriName == "이벤트")
		$scope.write = 2;
	boardStorage.boardSearch(search,page,categoriName).then(function(data) {	
		$scope.search = search;
		// 검색 데이터
		console.log(data.boardSearch);
		if(data.boardSearch=='NO')
			$window.location.href="/aboard2/#!/boards/searchNoPage";
		
		$scope.boardSearch = data.boardSearch;
		// 검색 페이징
		$scope.pagination = data.pagination;
		$scope.startPage = data.pagination.startPage;
		var startPage = data.pagination.startPage;
		var endPage = data.pagination.endPage;
		var render = [];
		for (var startPage = startPage; startPage < endPage + 1; startPage++) {
			// push로 해결. ui-bootstrap 안쓰고 페이징
			render.push(startPage);
		}
		$scope.render = render;
		console.log(endPage);
		console.log($scope.render);
	
	});
	// Sort
	 $scope.sortBy = function(propertyName) {
		    $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
		    $scope.propertyName = propertyName;
		  };
});
// boardsWrite
app.controller('boardsWriteCtrl', function($scope, $routeParams, $cookieStore) {
	$scope.categoriName = $routeParams.categoriName;
	$scope.writer = $cookieStore.get("userId");
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "content", // textarea ID
		sSkinURI : "resources/smarteditor/SmartEditor2Skin.html", // skin경로
		fCreator : "createSEditor2",
	});
	$scope.write = function(title) {
		if (title == null) {
			document.getElementById('title').focus();
			return alert("제목을 입력해주세요");
		}
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		var content = document.getElementById('content');
		if (content.value === "<p>&nbsp;</p>")
			return alert("내용을 입력해주세요");

		document.getElementById('writeFrm').submit();

	};
});
// boardsUpdate
app.controller('boardsUpdateCtrl',function($scope,$http, $routeParams, $cookieStore, boardStorage){
	var bno = $routeParams.bno;
	$scope.bno = bno;
	var categoriName = $routeParams.categoriName;
	$scope.categoriName = categoriName;
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "content", // textarea ID
		sSkinURI : "resources/smarteditor/SmartEditor2Skin.html", // skin경로
		fCreator : "createSEditor2",
	});
	boardStorage.boardsRead(bno, categoriName).then(function(data) {
		$scope.content = data.board.CONTENT;
		$scope.updateWriter = data.board.WRITER;
		$scope.title = data.board.TITLE;
		console.log($scope.updateWriter);
		document.getElementById('content').innerHTML = $scope.content;
	});
	$scope.update = function(title){
		if (title == null) {
			document.getElementById('title').focus();
			return alert("제목을 입력해주세요");
		}
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		var content = document.getElementById('content');
		if (content.value === "<p>&nbsp;</p>")
			return alert("내용을 입력해주세요");
		document.getElementById('updateFrm').submit();
	};
});

// boardsRead
app.controller('boardsReadCtrl', function($scope, $http, $routeParams,$cookieStore,
		$location,$window,$rootScope,boardStorage) {
	// routeParams로 Query의 값을 받을 수 있다.
	var bno = $routeParams.bno;
	$scope.bno = bno;
	var categoriName = $routeParams.categoriName;
	var rText = $scope.rText;
	var id = $cookieStore.get('userId');
	$scope.recommendState = $cookieStore.get("recommendState");
	// 쿠키에 담긴 값이 없을 때 false
	if($scope.recommendState==null)
		$scope.recommendState = false;
	var list = $cookieStore.get("bno");
	//쿠키리스트 확인
	$scope.like=false;
	// 쿠키가 null일 때 false, if값 만족못하면 false
	if(list!=null){
	for(var i = 0; i< list.length; i++){
		if(list[i]==bno)
			$scope.like=true;
	};
	}
	
	// 게시판 글 상세정보
	boardStorage.boardsRead(bno, categoriName).then(function(data) {
		replyList(bno);
		$scope.resp = data;
		$scope.content = data.board.CONTENT;
		$scope.writer = data.board.WRITER;
		$scope.recommendCnt = data.board.RECOMMENDCNT;
		document.getElementById('content').innerHTML = $scope.content;
	});
	// 게시판 글 삭제
	$scope.delete = function(){
		boardStorage.boardsDelete(id,bno,categoriName).then(function(data){
			if(data==="NO")
				return alert("게시글을 삭제할 수 없습니다");
			if(data==="YES"){
				alert("게시글을 삭제 했습니다");
				return $window.location.href="/aboard2/#!/boards/list/1/"+categoriName;
			}
	});
	};
	// 게시판 글 수정
	$scope.update = function(){
		if(id==$scope.writer){
			return $window.location.href="/aboard2/#!/boards/update/"+bno+"/"+categoriName;
		}
		
	};
	// 게시판 글 추천
	$scope.recommend = function(){
		if(id==null)
			return alert('회원만 좋아요를 누를 수 있습니다');
		
		boardStorage.boardsRecommend(bno,id).then(function(data){
			console.log(data);
			if(data=="OK"){
				var reserve = $cookieStore.get("bno");
				// null 값일 때 배열값 추가
				if($cookieStore.get("bno")==null)
					var reserve = [];
				reserve.push(bno);
				$cookieStore.put("bno", reserve);
				$cookieStore.put("recommendState", true);
				boardStorage.boardsRead(bno, categoriName).then(function(data) {
					$scope.recommendCnt = data.board.RECOMMENDCNT;
				});
				$scope.like = true;
				return $scope.recommendState = true;
				
			}
			
			alert("자신의 글은 좋아요를 누를 수 없습니다");
		});
		
	}
	//  게시판 글 추천 취소 
	$scope.unrecommed = function(){
		if(id==null)
			return alert('회원만 좋아요를 누를 수 있습니다');
		boardStorage.boardsUnRecommend(bno,id).then(function(data){
			$cookieStore.remove("recommendState");
			//중복 쿠키 제거
			for(var i = 0; i<list.length; i++){
				if(list[i]==bno)
				list.splice(i,i);
			}
			$cookieStore.put("bno",list);
			$cookieStore.put("recommedState",false);
			$scope.cookiebno = $cookieStore.get("bno");
			boardStorage.boardsRead(bno, categoriName).then(function(data) {
				$scope.recommendCnt = data.board.RECOMMENDCNT;
			});
			$scope.like = false;
			return $scope.recommendState = false;
		});
	}
	
	// 댓글 리스트
	// 함수로 만들어준 이유는 작성후에 리로드 하기 위해서 이다.
	function replyList(bno) {
		boardStorage.replyList(bno).then(function(data) {
			$scope.replyList = data;
			$scope.cno = data.cno;
		});
	}
	// 댓글 작성

	$scope.myFuc = function(rText, bno) {
		boardStorage.replyInsert(rText, bno).then(function(data) {
			replyList(bno);
			$scope.rText = "";
			alert(data);
		});
	};
	// 댓글 삭제
	$scope.replyDelete = function(cno){
		boardStorage.replyDelete(cno,id).then(function(data){
			replyList(bno);
			alert(data);
		});
	};
	
});
// usersRegister
app.controller('registerCtrl',function($scope, $http, $filter, $window, $location,userStorage) {
// 아이디 중복확인
	$scope.idCheck = function(id) {
	userStorage.idCheck(id).then(function(data) {
			$scope.status = data;
			});
				};
	$scope.month = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ];
// 유저 회원가입
	$scope.regFuc = function(user, isValid) {
			console.log(isValid);
			var address = document.getElementById('sample6_address').value;
			var postBno = document.getElementById('sample6_postcode').value;
			// 주소 합치기
			user.realAddress = postBno + "/" + address + "/"+ $scope.user.detailAddress;
			// 생년월일 합치기
			user.realBirth = $scope.user.year + "0"+ $scope.user.month + $scope.user.day;
						
			if (user.pwd != user.pwdCheck) {
				document.getElementById('pwdCheck').focus();
				return alert("비밀번호가 같지 않습니다");
			}
			if (!isValid) {
				return alert("제대로 입력해주세요");	
			}
			if (isValid) {
				userStorage.join(user).then(function(data) {
				alert(data);
				$window.location.href = "http://localhost:8081/aboard2/#!/users/welcome";
				});
			};	
				};

				});
// 약관동의
app.controller('agreeCtrl', function($scope) {

});
// 아이디 찾기
app.controller('findIdCtrl',function($rootScope, $scope, $http, $window, $location,userStorage) {
	var authToken = Math.floor((Math.random() * 99999) + 1);
	console.log(authToken);
	var email = document.getElementById('email').value;
// 1. 인증번호	
	$scope.emailAuth = function(isValid) {
			if (!isValid)
				alert("제대로 입력해주세요");
			if (isValid) {
				$scope.dataLoading = true;
				userStorage.emailToken(email,authToken).then(function(data) {
				alert(data);
				$scope.emailInput = 1;
				$scope.dataLoading = false;
							});
						}
					};
// 2. 아이디 찾기
	$scope.findId = function(find,emailToken) {
			console.log(find.irum);
			console.log(emailToken);
			console.log(authToken);
			userStorage.findId(find,emailToken,authToken).then(function(data) {
				if (data == 'NO')
					$rootScope.success = 2;
				if (data != 'NO')
					$rootScope.success = 1;
				$rootScope.findId = data;
				$window.location.href = "http://localhost:8081/aboard2/#!/users/findId/result";
			});
					}
				});
// 아이디 찾기 결과
app.controller('findIdResultCtrl', function($scope, $rootScope) {
	$scope.success = $rootScope.success;

});
// 비밀번호 찾기
app.controller('findPwdCtrl', function($scope, $http, userStorage) {
	// 비밀번호 아이디
	$scope.findPwd = function(id) {
		userStorage.findPwd(id).then(function(data) {
			if (data == '해당이메일없음') {
				alert('해당이메일이 없습니다');
				$scope.pwdSuccess = 2;
			}
			console.log(data);
			if (data != '해당이메일없음')
				$scope.pwdSuccess = 1;
			$scope.email = data;

		});
	};
	// 비밀번호 이메일
	$scope.pwdSend = function(id) {
		$scope.dataLoading = true;
		userStorage.pwdSend(id).then(function(data) {
			alert(data);
			$scope.dataLoading = false;
		});
	};

});
// 회원정보수정
app.controller('updateUserCtrl', function($scope, $http, $window, $cookieStore,
		userStorage) {
	var id = $cookieStore.get("userId");
	// 회원정보
	userStorage.userInfo(id).then(function(data) {
		$scope.id = data.ID;
		$scope.email = data.EMAIL;
		$scope.irum = data.IRUM;
	});
	// 회원정보수정
	$scope.updateUser = function(pwd, email, id) {
		if (pwd && email && id != null) {
			userStorage.updateUser(pwd, email, id).then(function(data) {
				alert(data);
				$window.location.href = "http://localhost:8081/aboard2";
			});
		}
		;

	};

});

// 로그인 인증 부분
angular.module('myApp').controller(
		'LoginCtrl',
		[
				'$scope',
				'$rootScope',
				'$location',
				'AuthenticationService',
				'$http',
				function($scope, $rootScope, $location, AuthenticationService,
						$http) {
					// reset login status
					AuthenticationService.ClearCredentials();

					$scope.login = function() {
						$scope.dataLoading = true;
						AuthenticationService.Login($scope.id, $scope.pwd,
								function(response) {
									if (response.status == 200
											&& response.data === "성공") {
										AuthenticationService.SetCredentials(
												$scope.id, $scope.pwd);
										$location.path('/');
									} else {
										$scope.error = response.message;
										$scope.dataLoading = false;
									}
								});
					};
				} ]);
// slide nav
app.controller("slideCtrl", function($scope) {
	$scope.openNav = function openNav() {
		document.getElementById("mySidenav").style.width = "250px";
		document.getElementById("main").style.marginLeft = "250px";
		document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
	}
	$scope.closeNav = function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("main").style.marginLeft = "0";
		document.body.style.backgroundColor = "white";
	}

});