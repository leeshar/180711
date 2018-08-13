angular.module('myApp').factory('myStorage',['$http',function($http){
	
	return {
		// 게시판 리스트를 불러오는 메소드
			get: function(page){
				return $http.get("/aboard2/boards/list/"+page).then(function(response){
					return JSON.parse(response.data.records);
					});
				
			},
		// 게시판 검색결과를 불러오는 메소드
			listAll: function(){
				 return $http.get("/aboard2/boards/listAll").then(function(response){
						return JSON.parse(response.data.listAll); 
					 });
			},
		// 게시글 상세내용 불러오는 메소드
			boardsRead: function(bno){
				return $http.get("/aboard2/boards/read/"+bno)
				.then(function(response){
					return response.data;
				});
			},
		// 댓글 리스트를 불러오는 메소드
			replyList: function(bno){
				return $http.get("/aboard2/boards/reply/list?bno="+bno)
				.then(function(response){
					return response.data.list;
					
					});
			},
		// 댓글 작성 메소드
			replyInsert: function(rText,bno){
				var data = {"replytext":rText,"bno":bno,"id":"service"};
				return $http({
				url : '/aboard2/boards/reply/insert',
				method : 'post',
				data: "reply="+JSON.stringify(data),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'},
					})
				.then(function(response){
					return 'zz';
					
					});
			},
		// 유저 회원가입 (아이디 찾기) 메소드
			idCheck: function(id){	
				// 아이디가 중복 되면 400(Bad Request) status == 400 중복된아이디
				// 아이디가 중복 되지 않으면 200 status == 200 사용가능
				return $http.get("/aboard2/users/idCheck/"+id)
				.then(
				function(response) {
					return response.status;
					console.log(response.status);
				},
				function myError(response){
					return response.status;
					console.log(response.status);
					});
			},
		// 유저 회원가입 메소드
			join: function(user){
				return $http({
					url:"/aboard2/users/join",
					method:'post',
					data: 'user='+JSON.stringify(user),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
					
				}).then(function(response){
					return "회원가입을 환영합니다!";
				});
			}
			
	};
	
}]);

// 로그인 인증 부분
angular.module('myApp')
 
.factory('AuthenticationService',
    ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
    function (Base64, $http, $cookieStore, $rootScope, $timeout) {
        var service = {};
        service.Login = function (id, pwd, callback) {
           
        	var loginUser = {"id":id, "pwd":pwd};
            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
          $timeout(function(){$http({
				url:"/aboard2/users/login",
				method:'post',
				data: 'login='+JSON.stringify(loginUser),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				
			}).then(function(response){
				console.log(response.data);
				if(!response.data){
					response.message = '아이디와 비밀번호가 틀립니다';
				}
				callback(response);
			})
           
          , 1000});
        }
 
        service.SetCredentials = function (id, pwd) {
            var authdata = Base64.encode(id + ':' + pwd);
 
            $rootScope.globals = {
                currentUser: {
                    id: id,
                    authdata: authdata
                }
            };
 
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        };
 
        service.ClearCredentials = function () {
            $rootScope.globals = null;
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };
 
        return service;
    }])
 
.factory('Base64', function () {
    /* jshint ignore:start */
 
    var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
 
    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
 
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
 
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
 
                output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);
 
            return output;
        },
 
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));
 
                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;
 
                output = output + String.fromCharCode(chr1);
 
                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }
 
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
 
            } while (i < input.length);
 
            return output;
        }
    };
 
});