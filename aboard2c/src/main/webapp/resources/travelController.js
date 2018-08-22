app.controller('travelCtrl',function($scope){
	$scope.openModal = function(){
		document.querySelector('#exampleModal').modal({show:true});
	};
	$scope.makeTravel = function(travel){
		console.log(travel);
		
	};
});