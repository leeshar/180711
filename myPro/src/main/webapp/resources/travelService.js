angular.module('myApp').factory('travelStorage',function($http,$cookieStore){
	return{
		//위치정보
		areaSearch: function(areaCode,sigunguCode,page){
			// 시군구 코드가 없을 때
			console.log("페이징영기");
			console.log(page==null);
			if(sigunguCode===""){
				return $http.get("/aboard2/travel/areaSearch/"+areaCode+"/"+page)
				.then(function(response){
					
					return response.data;
				}); 
			}
				
			return $http.get("/aboard2/travel/areaSearch/"+areaCode+"/"+sigunguCode+"/"+page)
			.then(function(response){
				
				return response.data;
			});
			
		},
		// 숙소 상세정보
		stayDetail : function(contentId){
			return $http.get("/aboard2/travel/stayDetail/"+contentId)
			.then(function(response){
				console.log(response);
				return response.data;
			});
		},
		// 숙소 상세정보 intro
		introInfo : function(contentId){
			return $http.get("/aboard2/travel/introInfo/"+contentId)
			.then(function(response){
				console.log(response);
				return response.data;
			});
		},
		//관광지정보
		tour: function(areaCode,sigunguCode,page){
			if(sigunguCode===""){
				return $http.get("/aboard2/travel/tourSearch/"+areaCode+"/"+page)
				.then(function(response){
					
					return response.data;
				}); 
			}
			return $http.get("/aboard2/travel/tourSearch/"+areaCode+"/"+sigunguCode+"/"+page)
			.then(function(response){
				
				return response.data;
			});
			
		}
	};
});
