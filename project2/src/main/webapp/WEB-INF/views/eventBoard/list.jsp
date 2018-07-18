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
		location.href= "/eventBoard/write"
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

        $('#term').autocomplete({"source":function(request,response){
				
               $.getJSON("/eventBoard/search",{"term":request.term},
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
		<form class="search" name="searchForm" method="GET" action="/eventBoard/searchPage">
       <label class="label-icon" for="term"><i class="small material-icons" style="color:#64b5f6;">subdirectory_arrow_right</i></label>
          
       	<i class="material-icons" id="clear" style="position: absolute; right: 150px;"></i>
          <input id="term" type="text" name="title" id="title">
        
         
         <button type="submit" class="btn waves-effect waves-light" id="searchBtn" style="height: 46px; width: 100px; background-color: #64b5f6;">검색</button>
      </form>
      </div>
      <div class="section-wrap">

		<table>
			
        <thead>
          <tr>
              <th>글번호</th>
              <th>제목</th>
              <th>글쓴이</th>
              <th>작성일</th>
              <th>조회수</th>
          </tr>
        </thead>

        <tbody>
        
          <c:forEach items="${eventBoard }" var="eventBoard">
            <tr>
            <td>${eventBoard.ebno }</td>
            <td><a href="/eventBoard/read?ebno=${eventBoard.ebno}">${eventBoard.title }</a></td>
            <td>${eventBoard.writer }</td>
            <td>${eventBoard.write_date }</td>
            <td>${eventBoard.read_cnt }</td>
            </tr>
            </c:forEach>
          
        
        </tbody>
       
      </table>
	
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