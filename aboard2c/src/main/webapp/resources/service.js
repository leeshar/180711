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
			}
			
	};
	
}]);