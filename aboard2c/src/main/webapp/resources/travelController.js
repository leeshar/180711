// travelCtrl
app.controller('travelCtrl',function($http,$scope,$cookieStore,travelStorage,$window,$location,$rootScope){
	var id = $cookieStore.get("userId");
	console.log(id);
	$scope.openOneModal = function(){
		document.querySelector('#oneModal').modal({show:true});
	};
	travelStorage.listTravel(id).then(function(data){
		console.log(data);
		$scope.travelList=data;
	});
	$scope.makeTravel = function(travel){
		travelStorage.createTravel(travel,id).then(function(data){
			$window.location.reload();
			console.log(data);
		});
		
	};
	
});
// travelAddCtrl
app.controller('travelAddCtrl',function($http,$scope,travelStorage,$routeParams,$rootScope){
	var travelBno = $routeParams.travelBno;
	var title = $routeParams.title;
	travelStorage.detailTravel(travelBno).then(function(data){
		
		$rootScope.content = data.CONTENT;
		console.log($rootScope.content);
	});
	$scope.dateAdd = function(){
		var content = $rootScope.content;
		var startDate = $scope.startDate;
		var endDate = $scope.endDate;
		var data = {'title':title,'travelStartDate':startDate,
				'travelEndDate': endDate,'content':content,
				'travelBno':travelBno
		};
		travelStorage.updateTravel(data).then(function(data){
			
		});
		
	}
});