app.directive("selectTpl",function(){
	return{templateUrl:"./template/select.tpl.html",
		   controller:"travelCommonCtrl"};});
//Input에서 ng-model을 사용했을때 한글 dataBinding을 해주는 directive
app.directive('krInput', [ '$parse', function($parse) { return { priority : 2, restrict : 'A', compile : function(element) { element.on('compositionstart', function(e) { e.stopImmediatePropagation(); }); }, }; } ])
.config(function($routeProvider){
	$routeProvider
	.when("/mytravel/list",{
		templateUrl:"./travel/list.html",
		controller:"travelCtrl"
	})
	.when("/mytravel/detail/:travelBno/:title",{
		templateUrl:"./travel/mytravel.html",
		controller:"mytravelCtrl"
	})
	.when("/travel/add",{
		templateUrl:"./travel/add.html",
		controller:"travelAddCtrl"
	})
	.when("/travel/detail/:contentId",{
		templateUrl:"./travel/detailPage.html",
		controller:"travelDetailCtrl",
		controller:"travelCommonCtrl"
	})
	.when("/travel/introInfo/:contentId",{
		templateUrl:"./travel/introInfo.html",
		controller:"travelDetailCtrl"
	})
	.when("/travel/addTour",{
		templateUrl:"./travel/addTour.html",
		controller:"travelTourAddCtrl"
	})
	.when("/travel/addLast",{
		templateUrl:"./travel/addLast.html",
		controller:"travelLastAddCtrl"
	});

	
})
.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}])
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/users/login' && !$rootScope.globals.currentUser) {
                $location.path('/users/login');
            }
        });
       
    }]);