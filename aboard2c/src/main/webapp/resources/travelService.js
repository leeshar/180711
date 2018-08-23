angular.module('myApp').factory('travelStorage',function($http,$cookieStore){
	return{
		// 추가하기
		createTravel: function(travel,id){
			var data = {'title':travel.title, 'content':travel.content, 'id':id};
			return $http({
				url:"/aboard2/travel/create",
				method:"post",
				data:"createTravel="+JSON.stringify(data),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function(response){
				
				
			});
			
		},
		// 리스트 보여주기
		listTravel: function(id){
			console.log(id);
			return $http({
				url:"/aboard2/travel/listTravel/"+id
			}).then(function(response){
				console.log(response);
				return response.data;
			});
			
		},
		// 업데이트
		updateTravel: function(data){
			console.log(data);
			return $http({
				url:"/aboard2/travel/update",
				method:"POST",
				data:"updateTravel="+ JSON.stringify(data),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function(response){
				console.log(response);
				return response;
				
				
			});
		},
		// 상세정보
		detailTravel: function(travelBno){
			return $http.get("/aboard2/travel/detail/"+travelBno)
			.then(function(response){
				console.log(response);
				return response.data;
			});
			
		}
	};
});
