// travelCtrl
app.controller('travelCtrl',function($http,$scope,$cookieStore,travelStorage,$window,$location){
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