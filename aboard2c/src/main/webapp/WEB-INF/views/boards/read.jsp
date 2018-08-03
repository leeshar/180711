<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script>

	var app = angular.module("myApp",[]);
	app.controller("myCtrl", function($scope, $http){

		var root = document.location.href;
		var str = root.split("=");
		var bno = str[1];
		alert("글번호" + bno);
		$http.get("/aboard2/boards/read/"+bno)
		.then(function(response){
			$scope.resp = response.data;
		})
	});
</script>
</head>
<body>
	<div ng-app="myApp" ng-controller="myCtrl">
		<div id="upper">
			<div id="title"></div>
			<div id="writer"></div>
			
		</div>
		<div id="lower">
			<div id="left" ng-repeat="x in resp">글번호:{{x.bno}}<span id="bno"></span><br>
			<span id="title">제목:{{x.title}}</span><br>
			<span id="content">내용:{{x.content}}</span><br>
			<span id="writer">작성자:{{x.writer}}</span><br>
			<span id="writeDate">작성일:{{x.writeDate | date:'yyyy-MM-dd HH:mm:ss '}}</span><br>
			<span id="reportCnt">신고누적수:{{x.reportCnt}}</span><br>
			<span id="recommendCnt">추천누적수:{{x.recommendCnt}}</span><br>
			<span id="readCnt">조회수:{{x.readCnt}}</span><br>
			<div>이미지<img src="/upload/{{x.savedFileName}}" style="height: 500px; width: 500px;"></div>
			</div>
			<div id="right">추천<span id="recommendCnt"></span>조회<span id="readCnt"></span></div>
		</div>
		<div id="body">
			<textarea id="content">
			</textarea>
		</div>
		<div >
			<ul id="attach">
			</ul>
		</div>
		<div id="btnArea" ng-repeat="x in resp">
			<button type="button" id="btnRecommend">추천</button>
    		<button type="button" id="btnReport">신고</button>
    		<button type="button" id="btnReport"><a href="/aboard2/boards/delete?bno={{x.bno}}">삭제</a></button>
    		<button type="button" id="btnReport"><a href="/aboard2/boards/">목록</a></button>
		</div>
	</div>

</body>
</html>