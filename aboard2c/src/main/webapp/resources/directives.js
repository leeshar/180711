var app = angular.module('myApp', [
    'ngRoute',
    'ngCookies'
]);
app.directive("headerTpl",function(){
	return{templateUrl:"./template/header.tpl.html",
		   controller:"slideCtrl"};
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
	.when("/boards/list/:page/:categoriName",{
		templateUrl:"./menu/3.html"
	})
	.when("/boards/list/:page/:categoriName",{
		// 게시판리스트
		templateUrl:"./boards/list.html",
		controller:'boardsCtrl'
	})
	.when("/boards/search/:search/:page/:categoriName",{
		// 게시판 검색 결과
		templateUrl:"./boards/searchPage.html",
		controller:'boardsSearchCtrl'
	})
	.when("/boards/searchNoPage",{
		templateUrl:"./boards/searchNoPage.html"
	})
	.when("/boards/read/:bno/:categoriName",{
		// 게시판 상세내용 
		templateUrl:"./boards/read.html",
		controller:'boardsReadCtrl'
	})
	.when("/boards/writePage/:categoriName",{
		// 게시판 글쓰기페이지
		templateUrl:"./boards/writePage.html",
		controller:'boardsWriteCtrl'
	})
	.when("/boards/update/:bno/:categoriName",{
		// 게시판 글수정페이지
		templateUrl:"./boards/updatePage.html",
		controller:'boardsUpdateCtrl'
	})
	.when("/users/agree",{
		// 약관동의 페이지
		templateUrl:"./user/agree.html",
		controller:"agreeCtrl"
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
	})
	.when("/users/findId",{
		// 아이디 찾기 페이지
		templateUrl:"./user/findId.html",
		controller:"findIdCtrl"
	})
	.when("/users/findId/result",{
		// 아이디 찾기 결과 페이지
		templateUrl:"./user/findIdResult.html",
		controller:"findIdResultCtrl"
	})
	.when("/users/findPwd",{
		// 비밀번호 찾기 페이지
		templateUrl:"./user/findPwd.html",
		controller:"findPwdCtrl"
	})
	.when("/users/updateUser",{
		// 회원정보수정
		templateUrl:"./user/updateUser.html",
		controller:"updateUserCtrl"
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
    }]);