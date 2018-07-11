<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 100%;
		height: 100%;
		text-align: center;
	}
	tr,td{
		border: 2px solid black;
	}

</style>
</head>
<body>
	<table>

<c:forEach items="${map}" var="b">
		<tr>
		<td>${b.CART_ID }</td><td><a href="/cart/delete?cart_id=${b.CART_ID }">삭제</a></td>
		<td>상품이미지</td><td><img src="/product/${b.PHOTO_URL}" style="width: 100px; height: 100px;"></td>
		<td>상품명</td><td>${b.PRODUCT_NAME}</td>
		<td>수량</td><td>${b.AMOUNT}</td>
		<td>가격</td><td>${b.PRICE}</td></tr>
		
</c:forEach>		
	
	
	</table>


</body>
</html>