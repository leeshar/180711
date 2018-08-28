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
	$scope.area = { 1:'서울', 2:'인천', 3:'대전', 4:'대구',5:'광주', 6:'부산', 7:'울산', 8:'세종특별자치'};
	$scope.do = { 31: '경기도', 32:'강원도', 33:'충청북도', 34:'충청남도', 35:'경상북도', 36:'경상남도', 37:'전라북도', 38:'전라남도', 39:'제주도'};
	$scope.seoulgu = { 1: '강남구', 2:'강동구', 3:'강북구', 4:'강서구', 5:'관악구', 6:'광진구', 7:'구로구', 8:'금천구',
				9: '노원구', 10:'도봉구', 11:'동대문구', 12:'동작구', 13:'마포구', 14:'서대문구', 15:'서초구', 16:'성동구',
				17: '성북구',18: '송파구', 19: '양천구', 20:'영등포구',21: '용산구',22: '은평구',23: '종로구',24: '중구',25: '중랑구'};
	$scope.incheongu = {1: '강화군',2 :'계양구',3: '남구',4: '남동구',5: '동구',6: '부평구',7 :'서구',8: '연수구',9: '옹진군',10: '중구'};
	$scope.daejeongu = {1: '대덕구', 2:'동구', 3:'서구', 4:'유성구', 5:'중구'};
	$scope.daegugu = {1: '남구', 2:'달서구', 3:'달성군', 4:'동구', 5:'북구', 6:'서구', 7:'수성구', 8:'중구'};
	$scope.gwangjugu = {1:'광산구', 2:'남구', 3:'동구', 4:'북구', 5:'서구'};
	$scope.busangu = {1:'강서구', 2:'금정구', 3:'기장군', 4:'남구', 5:'동구', 6:'동래구', 7:'부산진구', 8:'북구', 9:'사상구', 10:'사하구',
11:'서구', 12:'수영구', 13:'연제구', 14:'영도구', 15:'중구', 16:'해운대구'};
	$scope.ulsangu= {1:'중구', 2:'남구', 3:'동구', 4:'북구', 5:'울주군'};
	$scope.sejonggu={1:'세종특별자치시'};
	$scope.gyeonggi={1:'가평군',2:'고양시',3:'과천시',4:'광명시',5:'광주시',6:'구리시',7:'군포시',8:'김포시',9:'남양주시',10:'동두천시',11:'부천시',
			12:'성남시',13:'수원시',14:'시흥시',15:'안산시',16:'안성시',17:'안양시',18:'양주시',19:'양평군',20:'여주시',21:'연천군',
			22:'오산시',23:'용인시',24:'의왕시',25:'의정부시',26:'이천시',27:'파주시',28:'평택시',29:'포천시',30:'하남시',31:'화성시'};
	var gyggiLength = Object.keys($scope.gyeonggi).length;
	$scope.gangwon={1:'강릉시',2:'고성군',3:'동해시',4:'삼척시',5:'속초시',6:'양구군',7:'양양군',8:'영월군',9:'원주시',10:'인제군'
,11:'정선군',12:'철원군',13:'춘천시',14:'태백시',15:'평창군',16:'홍천군',17:'화천군',18:'횡성군'};
	var gangwonLength= Object.keys($scope.gangwon).length;
	$scope.chungbuk={1:'괴산군',2:'단양군',3:'보은군',4:'영동군',5:'옥천군',6:'음성군',7:'제천시',8:'진천군',
9:'청원군',10:'청주시',11:'충주시',12:'증평군'};
	var chungbukLength= Object.keys($scope.chungbuk).length;
	$scope.chungnam={1:'공주시',2:'금산군',3:'논산시',4:'당진시',5:'보령시',6:'부여군',7:'서산시',8:'서천군',		
9:'아산시',10:'예산군',11:'천안시',12:'청양군',13:'태안군',14:'홍성군',15:'계룡시'};
	var chungnamLength= Object.keys($scope.chungnam).length;
	$scope.gyeongbuk={1:'경산시',2:'경주시',3:'고령군',4:'구미시',5:'군위군',6:'김천시',7:'문경시',8:'봉화군',
9:'상주시',10:'성주군',11:'안동시',12:'영덕군',13:'영양군',14:'영주시',15:'영천시',16:'예천군',17:'울릉군'
,18:'울진군',19:'의성군',20:'청도군',21:'청송군',22:'칠곡군',23:'포항시'};
	var gyeongbukLength= Object.keys($scope.gyeongbuk).length;
	$scope.gyeongnam={1:'거제시',2:'거창군',3:'고성군',4:'김해시',5:'남해군',6:'마산시',7:'밀양시',8:'사천시',9:'산청군',
10:'양산시',11:'의령군',12:'진주시',13:'진해시',14:'창녕군',15:'창원시',16:'통영시',17:'하동군',18:'함안군',19:'함양군',20:'합천군'};
	var gyeongnamLength= Object.keys($scope.gyeongbuk).length;
	$scope.jeonbuk={1:'고창군',2:'군산시',3:'김제시',4:'남원시',5:'무주군',6:'부안군',7:'순창군',8:'완주군',
9:'익산시',10:'임실군',11:'장수군',12:'전주시',13:'정읍시',14:'진안군'};
	var jeonbukLength= Object.keys($scope.jeonbuk).length;
	$scope.jeonnam={1:'강진군',2:'고흥군',3:'곡성군',4:'광양시',5:'구례군',6:'나주시',7:'담양군',8:'목포시',
9:'무안군',10:'보성군',11:'순천시',12:'신안군',13:'여수시',14:'',15:'',16:'영광군',17:'영암군',18:'완도군',19:'장성군',
20:'장흥군',21:'진도군',22:'함평군',23:'해남군',24:'화순군'};
	var jeonnamLength= Object.keys($scope.jeonnam).length;
	$scope.zezudo={1:'남제주군', 2:'북제주군', 3:'서귀포시', 4:'제주시'};
	var zezudoLength = Object.keys($scope.zezudo).length;
	// 여행 자세히 보기
	travelStorage.detailTravel(travelBno).then(function(data){
		
		$rootScope.content = data.CONTENT;
		console.log($rootScope.content);
	});
	// 여행생성
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
		
	};
	// 숙소 검색
	$scope.areaSearch = function(city){
		// "도" 부분 처리 완료
		if(city.area==null){
			// map search창 value
			$scope.searchCode = city.area+" "+city.gu;
			var areaCode=city.do;
			var areaLength = Object.keys($scope.do).length;
			var value = Object.keys($scope.do)[0];
			for(var i = value; i<40; i++){
				if($scope.do[i]===areaCode)
					areaCode=i;
			}
			var sigunguCode=city.gu;
			var oj;
			switch(areaCode){
			// 도 - 구
			
			case 31:
				oj = $scope.gyeonggi;
				break;
			case 32:
				oj = $scope.gangwon;
				break;
			case 33:
				oj = $scope.chungbuk;
				break;
			case 34:
				oj = $scope.chungnam;
				break;
			case 35:
				oj = $scope.gyeongbuk;
				break;
			case 36:
				oj = $scope.gyeongnam;
				break;
				
			case 37:
				oj = $scope.jeonbuk;
				break;
			case 38:
				oj = $scope.jeonnam;
				break;
			case 39:
				oj = $scope.zezudo;
				break;
			}
			var ojLength = Object.keys(oj).length;
			for(var i = 1; i<ojLength+1; i++){
				if(oj[i]===sigunguCode)
					sigunguCode=i;
			}
		}
		// 여긴 "시" 부분
		if(city.area!=null){

			// map search창 value
			$scope.searchCode = city.area +" "+ city.gu;
			var areaCode=city.area;
			var areaLength = Object.keys($scope.area).length;
			for(var i = 1; i<areaLength+1; i++){
				if($scope.area[i]===areaCode)
					areaCode=i;
			}
			var sigunguCode = city.gu;
			var oj;
			switch(areaCode){
			// 도 - 구
			
			case 1:
				oj = $scope.seoulgu;
				break;
			case 2:
				oj = $scope.incheongu;
				break;
			case 3:
				oj = $scope.daejeongu;
				break;
			case 4:
				oj = $scope.daegugu;
				break;
			case 5:
				oj = $scope.gwangjugu;
				break;
			case 6:
				oj = $scope.busangu;
				break;
				
			case 7:
				oj = $scope.ulsangu;
				break;
			case 8:
				oj = $scope.sejonggu;
				break;
			
			}
			var ojLength=Object.keys(oj).length;
			for(var i = 1; i<ojLength+1; i++){
				if(oj[i]===sigunguCode)
					sigunguCode=i;
			}
		
		}
		// 숙소 목록
		travelStorage.areaSearch(areaCode,sigunguCode).then(function(data){
			parser = new DOMParser();
			xmlDoc = parser.parseFromString(data,"text/xml");
			var tot = xmlDoc.getElementsByTagName("totalCount")[0].childNodes[0].nodeValue;
			var x = xmlDoc.getElementsByTagName("item");
			var data = [];
			for(var i = 0;i<x.length;i++){
				var obj = {};
				//image
				obj.contentId = x[i].getElementsByTagName("contentid")[0].childNodes[0].nodeValue;	 
				if(x[i].getElementsByTagName("firstimage2")[0])
					obj.img = x[i].getElementsByTagName("firstimage2")[0].childNodes[0].nodeValue;	 
				if(!x[i].getElementsByTagName("firstimage2")[0])
					obj.img = "http://localhost:8081/upload/none.jpg";
				//tel
				if(x[i].getElementsByTagName("tel")[0])
					obj.tel = x[i].getElementsByTagName("tel")[0].childNodes[0].nodeValue;
				if(!x[i].getElementsByTagName("tel")[0])
					obj.tel = "번호없음";
				//title
				if(x[i].getElementsByTagName("title")[0])
					obj.txt = x[i].getElementsByTagName("title")[0].childNodes[0].nodeValue;
				if(!x[i].getElementsByTagName("title")[0])
					obj.txt = "제목없음";
				if(obj.txt.indexOf("[")!=-1){
					obj.txt = obj.txt.substring(0,obj.txt.indexOf("[",0));
					}
				
				if(x[i].getElementsByTagName("mapx")[0])
					obj.lng = x[i].getElementsByTagName("mapx")[0].childNodes[0].nodeValue;
				if(!x[i].getElementsByTagName("mapx")[0])
					obj.lng = location.push("위치없음");
				
				if(x[i].getElementsByTagName("mapy")[0])
					obj.lat = x[i].getElementsByTagName("mapy")[0].childNodes[0].nodeValue;
				if(!x[i].getElementsByTagName("mapy")[0])
					obj.lat = "위치없음";
			//""안에 변수 사용법 `` 백퀕+${변수명} console.log(`"${i}"`);
			
			data.push(obj);
			};
			$scope.data = data;
			console.log(data);
			
				
		});
	}
});

// detailCtrl
app.controller('travelDetailCtrl',function($scope,$http,travelStorage,$routeParams){
	var contentId = $routeParams.contentId;
	console.log(contentId);
	travelStorage.stayDetail(contentId).then(function(data){
		console.log(data);
		parser = new DOMParser();
		xmlDoc = parser.parseFromString(data,"text/xml");
		$scope.image = xmlDoc.getElementsByTagName("firstimage")[0].childNodes[0].nodeValue;
		$scope.txt = xmlDoc.getElementsByTagName("overview")[0].childNodes[0].nodeValue;
	});
	
});