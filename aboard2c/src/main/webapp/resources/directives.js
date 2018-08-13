var app = angular.module('myApp', [
    'ngRoute',
    'ngCookies'
]);
app.directive("headerTpl",function(){
	return{templateUrl:"./template/header.tpl.html"};
}).directive("navTpl",function(){
	return{templateUrl:"./template/nav.tpl.html"};
})
//Input에서 ng-model을 사용했을때 한글 dataBinding을 해주는 directive
.directive('krInput', [ '$parse', function($parse) { return { priority : 2, restrict : 'A', compile : function(element) { element.on('compositionstart', function(e) { e.stopImmediatePropagation(); }); }, }; } ])
.config(function($routeProvider){
	$routeProvider
	.when("/1",{
		templateUrl:"./menu/1.html"
	})
	.when("/2",{
		templateUrl:"./menu/2.html"
	})
	.when("/3",{
		templateUrl:"./menu/3.html"
	})
	.when("/boards/list/:page",{
		// 게시판리스트
		templateUrl:"./boards/list.html",
		controller:'boardsCtrl'
	})	
	.when("/boards/read/:bno",{
		// 게시판 상세내용 
		templateUrl:"./boards/read.html",
		controller:'boardsReadCtrl'
	})
	.when("/boards/writePage",{
		// 게시판 글쓰기페이지
		templateUrl:"./boards/writePage.html"
	})
	.when("/users/agree",{
		// 약관동의 페이지
		templateUrl:"./user/agree.html"
	})
	.when("/users/register",{
		// 회원가입 페이지
		templateUrl:"./user/register.html",
		controller:"registerCtrl"
	})
	.when("/users/welcome",{
		// 회원가입환영 페이지
		templateUrl:"./user/welcome.html"
	})
	.when("/users/login",{
		// 로그인 페이지
		templateUrl:"./user/login.html",
		controller:"LoginCtrl",
		hideMenus: true
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