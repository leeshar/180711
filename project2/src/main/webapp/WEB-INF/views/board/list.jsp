<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script>
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
	
	
	$(function(){
		
	$("button").on('click',function(){
		var vl = $(this).attr('value');
		alert(vl);
		$('#'+vl).text("favorite");	
	});
	
	});
</script>
<style>
	.pagination {
		text-align: center;
		margin-top: 20px;
		margin-bottom: 20px;
	}
	div.group{
		width: 30%;
		overflow: hidden;
		float: left;
		padding-left: 90px;
		
	}
	div.pagination{
		position: absolute;
		left: 640px;
		bottom: -550px;
	}
	#write{
		position: absolute;
		right: 120px;
		bottom: -520px;
	}
	.product_name,.price,.product_id{
		border: 1px solid black;
		font-family: sans-serif;
	}
	.price>span{
		padding-right: 50px;
	}
	.product_name>span{
		padding-right: 30px;
		
	}
	.product_id>span{
		padding-right: 30px;
	}
</style>
</head>
<body>

		
		
		<c:forEach items="${product}" var="product">
		
		<div class="group">
		
	<div class="photo"><a href="/board/read?product_id=${product.product_id}"><img src="/product/${product.photo_url}" style="width:200px; height: 200px;"></a></div>
			<div class="product_id"><span>상품번호</span>${product.product_id }번</div>
			<div class="product_name"><span>상품명</span><a href="/board/read?product_id=${product.product_id}">${product.product_name}</a></div>
			
			<div class="price"><span>가격</span>${product.price}원</div>
			
			
			<div class="like"><i class="material-icons" id="${product.product_id}">favorite_border</i><button type="button" value="${product.product_id}" >좋아요</button></div>
			
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
</html>