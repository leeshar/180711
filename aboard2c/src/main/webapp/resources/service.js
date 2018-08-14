angular.module('myApp').factory('myStorage',['$http',function($http){
	
	return {
		// 게시판 리스트를 불러오는 메소드
			get: function(page){
				return $http.get("/aboard2/boards/list/"+page).then(function(response){
					return JSON.parse(response.data.records);
					});
				
			},
		// 게시판 검색결과를 불러오는 메소드
			listAll: function(){
				 return $http.get("/aboard2/boards/listAll").then(function(response){
						return JSON.parse(response.data.listAll); 
					 });
			},
		// 게시글 상세내용 불러오는 메소드
			boardsRead: function(bno){
				return $http.get("/aboard2/boards/read/"+bno)
				.then(function(response){
					return response.data;
				});
			},
		// 댓글 리스트를 불러오는 메소드
			replyList: function(bno){
				return $http.get("/aboard2/boards/reply/list?bno="+bno)
				.then(function(response){
					return response.data.list;
					
					});
			},
		// 댓글 작성 메소드
			replyInsert: function(rText,bno){
				var data = {"replytext":rText,"bno":bno,"id":"service"};
				return $http({
				url : '/aboard2/boards/reply/insert',
				method : 'post',
				data: "reply="+JSON.stringify(data),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'},
					})
				.then(function(response){
					return 'zz';
					
					});
			},
		// 유저 회원가입 (아이디 찾기) 메소드
			idCheck: function(id){	
				// 아이디가 중복 되면 400(Bad Request) status == 400 중복된아이디
				// 아이디가 중복 되지 않으면 200 status == 200 사용가능
				return $http.get("/aboard2/users/idCheck/"+id)
				.then(
				function(response) {
					return response.status;
					console.log(response.status);
				},
				function myError(response){
					return response.status;
					console.log(response.status);
					});
			},
		// 유저 회원가입 메소드
			join: function(user){
				return $http({
					url:"/aboard2/users/join",
					method:'post',
					data: 'user='+JSON.stringify(user),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
					
				}).then(function(response){
					return "회원가입을 환영합니다!";
				});
			}
			
	};
	
}]);