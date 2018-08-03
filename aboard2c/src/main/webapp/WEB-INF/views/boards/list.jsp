<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
<div id="wrap">
	<div ng-app="myApp" ng-controller="myCtrl">
		<table class="table table-dark">	
		
			<tr><th scope="col">글번호</th><th scope="col">제목</th><th scope="col">글쓴이</th><th scope="col">날짜</th><th scope="col">조회수</th></tr>
			<tr ng-repeat="x in resp"><td>{{x.bno}}</td><td><a href="/aboard2/boards/read?bno={{x.bno}}" style="color: white;">{{x.title}}</a></td><td>{{x.writer}}</td><td>{{x.writeDate}}</td><td>{{x.readCnt}}</td>
		</table>
		
	{{pag}}
	<nav aria-label="Page navigation example">
  <ul class="pagination" style="margin-left: 500px;">
    <li class="page-item" ><a class="page-link" href="/aboard2/boards">prev</a></li>
    <li class="page-item" ><a class="page-link" href="/aboard2/boards">1</a></li>
     <li class="page-item" ><a class="page-link" href="/aboard2/boards">2</a></li>
      <li class="page-item" ><a class="page-link" href="/aboard2/boards">3</a></li>
       <li class="page-item" ><a class="page-link" href="/aboard2/boards">4</a></li>
        <li class="page-item" ><a class="page-link" href="/aboard2/boards">5</a></li>
    <li class="page-item" ><a class="page-link" href="/aboard2/boards">next</a></li>
 
  </ul>
  
 	
</nav>
	
		
		<a href="/aboard2/boards/write"><button type="button" id="write" class="btn btn-dark">글쓰기</button></a>

	</div>

</div>	
<script>
var app = angular.module("myApp",[]);


app.controller("myCtrl", function($scope,$http,$location){
	$http.get("/aboard2/boards2").then(function(response){
		$scope.resp = JSON.parse(response.data.records);
	});
	$http.get("/aboard2/boards3").then(function(response){
		
		$scope.pag = JSON.parse(response.data.records);
	});
	$location.url("/aboard2/boards/read/"+$scope.bno);
	
	
})
</script>
</body>
</html>