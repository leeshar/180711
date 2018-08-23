angular.module('myApp').factory('travelStorage',function($http,$cookieStore){
	return{
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
		listTravel: function(id){
			console.log(id);
			return $http({
				url:"/aboard2/travel/listTravel/"+id
			}).then(function(response){
				console.log(response);
				return response.data;
			});
			
		}
	};
});
