// 게시판 서비스
angular.module('myApp').factory('boardStorage',['$http','$cookieStore',function($http,$cookieStore){
	
	return {
		// 게시판 리스트를 불러오는 메소드
			get: function(page,categoriName){
				return $http.get("/aboard2/boards/list/"+page+"/"+categoriName).then(function(response){
					console.log(response.data);
					return response.data;
					});
				
			},
		// 게시판 검색결과를 불러오는 메소드
			boardSearch: function(search,page,categoriName){
				console.log(search);
				 return $http({
					url:"/aboard2/boards/search/"+search+"/"+page+"/"+categoriName,
					method:"get"
				}).then(function(response){
					console.log(response.data);
					 return response.data;
				 });
			},
		// 게시글 상세내용 불러오는 메소드
			boardsRead: function(bno,categoriName){
				return $http.get("/aboard2/boards/read/"+bno+"/"+categoriName)
				.then(function(response){
					return response.data;
				});
			},
		// 게시글 삭제 하는 메소드
			boardsDelete: function(id,bno,categoriName){
				return $http({
					url:"/aboard2/boards/delete",
					method:"POST",
					data:"board="+JSON.stringify({'id':id,'bno':bno,'categoriName':categoriName}),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}).then(function(response){
					return response.data;
				});
			},
		// 게시판 글 추천
			boardsRecommend : function(bno,id){
				return $http({
					url:"/aboard2/boards/recommend",
					method:"POST",
					data: "recommend="+JSON.stringify({'bno':bno, 'id':id }),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}).then(function(response){
					return response.data;
				});
				
			},
		// 게시판 글 추천 취소
			boardsUnRecommend : function(bno,id){
				return $http({
					url:"/aboard2/boards/unrecommend",
					method:"POST",
					data: "unrecommend="+JSON.stringify({'bno':bno, 'id':id}),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}).then(function(response){
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
			// 로그인 작성시
			if($cookieStore.get("globals")!=null){
				var cookie = $cookieStore.get("globals");
				var currentUser = cookie[Object.keys(cookie)[0]];
				var id = currentUser[Object.keys(currentUser)[0]];
				var data = {"replytext":rText,"bno":bno,"id":id};
			}
			//비로그인 댓글 작성시
			if($cookieStore.get("globals")==null)
				var data = {"replytext":rText, "bno":bno, "id":"익명"};
				return $http({
				url : '/aboard2/boards/reply/insert',
				method : 'post',
				data: "reply="+JSON.stringify(data),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
					})
				.then(function(response){
					return 'zz';
					
					});
			},
		// 댓글 삭제
			replyDelete: function(cno,id){
				if(id==null)
					return "NO";
				var data = {"cno":cno,"id":id};
				
				return $http({
					url:"/aboard2/boards/reply/delete",
					method: 'post',
					data: "reply="+ JSON.stringify(data),
					contentType:"application/json;charset=UTF-8",
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}).then(function(response){
					return response.data;
				});
			}
		
			
	};
	
}]);
// 유저 서비스
angular.module('myApp').factory('userStorage',['$http','$cookieStore','$rootScope',function($http,$cookieStore,$rootScope){
	
	return{
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
		},
		// 이메일인증
		emailToken: function(email,authToken){
			return $http({
				url:"/aboard2/users/findId/emailAuth",
				method:'POST',
				data:'mail='+ JSON.stringify({'email':email,'authToken': authToken}),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function(response){
				console.log(authToken);//TEST
				return "인증번호발송했습니다.";
			})
		},
		//아이디 찾기
		findId: function(find,emailToken,authToken){
			console.log(find);
			if(emailToken==authToken){
			return $http({
				url:"/aboard2/users/findId",
				method:'POST',
				data:'find='+JSON.stringify({'irum':find.irum, 'email':find.email}),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function(response){
				console.log(response);
				return response.data;
			});
			}
			if(emailToken!=$rootScope.authToken){
				alert('인증번호가 틀립니다');
			}
			
		},
		// 비밀번호 찾기
		findPwd: function(id){
			return $http({
				url:"/aboard2/users/findPwd",
				method:"POST",
				data:'id='+id,
				contentType:"application/json;charset=UTF-8",
				headers:{"Content-Type":'application/x-www-form-urlencoded'}
			}).then(function(response){
				return response.data;
			});
			
		},
		// 비밀번호 이메일
		pwdSend: function(id){
			return $http({
				url:"/aboard2/users/findPwd/emailAuth",
				method:"POST",
				data:'id='+id,
				contentType:"application/json;charset=UTF-8",
				headers:{"Content-Type":'application/x-www-form-urlencoded'}
			}).then(function(response){
				return '비밀번호를 발송했습니다.';
			});
		},
		// 회원정보
		userInfo: function(id){
			return $http.get("/aboard2/users/userInfo/"+id)
			.then(function(response){
				console.log(response.data);
				return response.data;
			});
		},
		// 회원정보수정
		updateUser: function(pwd,email,id){
			return $http({
				url:"/aboard2/users/updateUser",
				method:"POST",
				data:"update="+JSON.stringify({'pwd':pwd,'email':email,'id':id}),
				contentType:"application/json;charset=UTF-8",
				headers:{"Content-Type":'application/x-www-form-urlencoded'}
				
			}).then(function(response){
				 if(response.status==200){
					 return "정보수정되었습니다";
				 }
			});
			
			
		},
		// 알림
		noticeUser: function(id){
			return $http({
				url:"/aboard2/users/notice",
				method:"POST",
				data:"id="+id,
				contentType:"application.json;charset=UTF-8",
				headers:{"Content-Type":'application/x-www-form-urlencoded'}
			}).then(function(response){
				return response.data;
			});
			
		}
		
		
	}
}]);
// 로그인 서비스
angular.module('myApp').factory('AuthenticationService',
    ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
    function (Base64, $http, $cookieStore, $rootScope, $timeout) {
        var service = {};
        service.Login = function (id, pwd, callback) {
           
        	var loginUser = {"id":id, "pwd":pwd};
            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
          $timeout(function(){$http({
				url:"/aboard2/users/login",
				method:'POST',
				data: 'login='+JSON.stringify(loginUser),
				contentType:"application/json;charset=UTF-8",
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				
			}).then(function(response){
				console.log(response.status);
				if(response.status==200&&response.data==='아이디다름')
					response.message="회원정보가 없습니다";
				if(response.status==200&&response.data==='비밀번호다름')
					response.message="비밀번호가 다릅니다";
				callback(response);
			})
           
          , 1000});
        }
 
        service.SetCredentials = function (id, pwd) {
            var authdata = Base64.encode(id + ':' + pwd);
            $rootScope.userId = id;
            $rootScope.globals = {
                currentUser: {
                    id: id,
                    authdata: authdata
                }
            };
 
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
            $cookieStore.put('userId', $rootScope.userId);
        };
 
        service.ClearCredentials = function () {
            $rootScope.globals = null;
            $cookieStore.remove('globals');
            $cookieStore.remove('userId');
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