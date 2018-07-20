<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!-- Compiled and minified JavaScript -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/boardList.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/cookie/jquery.cookie.js"></script> 
<script>
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var list = "${list}";
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(function() {
	$("#write").on("click", function() {
		location.href= "/board/write"
	});
})
var msg = "${msg}";
	if(msg==='WRITE'){
		alert("작성성공");
		
	}
	if(msg==='UPDATE'){
		alert("수정성공");
	}
var cookieList = "${cookieList}";
console.log(cookieList);
$.each(JSON.parse(cookieList),function(key,value){
	$(function(){
		$('#'+value).text("favorite");
	});
	console.log(value);
});


	$(function(){
	$(".likeBtn").on('click',function(){
		var data = $(this).attr('value');
		var seller = $("[name=seller]").attr('value');
		$.ajax({
			url:"/like/count",
			type:"post",
			data: {'data':data,'seller':seller},
			dataType:'text',
			success:
				function(){
				$('#'+data).text("favorite");
				$('#'+data+'lk').html("좋아요를 누르셨습니다");
				
			},
			error:
				function(request){
				alert("판매자는 추천할 수 없습니다.");
			}
			
		});

	});
	});

	$(function(){

        $('#term').autocomplete({"source":function(request,response){
				
               $.getJSON("/board/search",{"term":request.term},
            		  function(result){
            	 
            	   return response($.map(result, function(item){
            		  return item.value;
            		   
            	   }));
            	  
            	   
               })
            		   
                 
     
             	

        }});

});
	$("#clear").on('click',function(){
		$("#term").val('');
	});
	
	$(function(){
		$("#term").focus(function(){
			$("#clear").text("close");
		
	});
	});
	$(function(){
		$("#clear").on('click',function(){
			$("#term").val('');
		});
	});
	
	
</script>
</head>
<body>
	<div class="form-wrap">
		<form class="search" name="searchForm" method="GET" action="/board/searchPage">
       <label class="label-icon" for="term"><i class="small material-icons" style="color:#64b5f6;">subdirectory_arrow_right</i></label>
          
       	<i class="material-icons" id="clear" style="position: absolute; right: 150px;"></i>
          <input id="term" type="text" name="product_name" id="product_name">
        
         
         <button type="submit" class="btn waves-effect waves-light" id="searchBtn" style="height: 46px; width: 100px; background-color: #64b5f6;">검색</button>
      </form>
      </div>
      <div class="section-wrap">
		<c:forEach items="${product}" var="product" >
		
		<div class="group">
		
	<div class="photo"><a href="/board/read?product_id=${product.product_id}"><img src="/product/${product.photo_url}" style="width:200px; height: 200px;"></a></div>
			<div class="product_id"><span>상품번호</span>${product.product_id }번</div>
			<div class="product_name"><span>상품명</span><a href="/board/read?product_id=${product.product_id}">${product.product_name}</a></div>
			<div class="seller" id="${product.seller }"><span>판매자</span>${product.seller }</div>
			<div class="price"><span>가격</span>${product.price}원</div>
			<div class="lk" id="${product.product_id}lk"><span>좋아요횟수</span>${product.like_cnt}번</div>
			
			<input type="hidden" name="seller" value="${product.seller }">
			<div class="like"><i class="material-icons" id="${product.product_id}">favorite_border</i><button type="button" value="${product.product_id}" class="likeBtn">좋아요</button></div>
			
			</div>
		</c:forEach>
	<div class="pagination">
		
			<ul>
			<c:if test="${paging.prev }">
				<li><a href="list?page=${paging.startPage -1 }">&laquo;</a></li>
			</c:if>
			
			<c:forEach begin="${paging.startPage }"
			end ="${paging.endPage }" var="idx">
				<li
					<c:out value="${paging.cri.page == idx?'class = active':''}"/>>
					<a href="list?page=${idx }">${idx }</a>
				</li>
			</c:forEach>
			
			<c:if test="${paging.next && paging.endPage > 0 }">
				<li><a href="list?page=${paging.endPage +1}">&raquo;</a></li>
				</c:if>
		</ul>
	
		
		</div>
		<button class="btn waves-effect waves-light" type="button" id="write">글쓰기
    		<i class="material-icons right">send</i>
  		</button>
  		
  		</div>
</html>