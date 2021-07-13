<!-- 작성자 : 김태훈 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">  
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<style type="text/css">
body {
  padding-top: 0;
  font-size: 12px;
  color: #777;
  background: #f9f9f9;
  font-family: 'Open Sans',sans-serif;
  margin-top:20px;
}

.bg-white {
  background-color: #fff;
}

.friend-list {
  list-style: none;
margin-left: -40px;
}

.friend-list li {
  border-bottom: 1px solid #eee;
}

.friend-list li a img {
  float: left;
  width: 45px;
  height: 45px;
  margin-right: 10px;
}

 .friend-list li a {
  position: relative;
  display: block;
  padding: 10px;
  transition: all .2s ease;
  -webkit-transition: all .2s ease;
  -moz-transition: all .2s ease;
  -ms-transition: all .2s ease;
  -o-transition: all .2s ease;
}

.friend-list li.active a {
  background-color: #f1f5fc;
}

.friend-list li a .friend-name, 
.friend-list li a .friend-name:hover {
  color: #777;
}

.friend-list li a .last-message {
  font-size: 15px;
  width: 65%;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.friend-list li a .time {
  position: absolute;
  top: 10px;
  right: 8px;
}

small, .small {
  font-size: 85%;
}

.friend-list li a .chat-alert {
  position: absolute;
  right: 8px;
  top: 27px;
  font-size: 10px;
  padding: 3px 5px;
}

.chat-message {
  padding: 60px 20px 115px;
}

.chat {
    list-style: none;
    margin: 0;
}

.chat-message{
    background: #f9f9f9;  
}

.chat li img {
  width: 45px;
  height: 45px;
  border-radius: 50em;
  -moz-border-radius: 50em;
  -webkit-border-radius: 50em;
}

img {
  max-width: 100%;
}

.chat-body {
  padding-bottom: 20px;
}

.chat li.left .chat-body {
  margin-left: 70px;
  background-color: #fff;
}

.chat li .chat-body {
  position: relative;
  font-size: 11px;
  padding: 10px;
  border: 1px solid #f1f5fc;
  box-shadow: 0 1px 1px rgba(0,0,0,.05);
  -moz-box-shadow: 0 1px 1px rgba(0,0,0,.05);
  -webkit-box-shadow: 0 1px 1px rgba(0,0,0,.05);
}

.chat li .chat-body .header {
  padding-bottom: 5px;
  border-bottom: 1px solid #f1f5fc;
}

.chat li .chat-body p {
  margin: 0;
}

.chat li.left .chat-body:before {
  position: absolute;
  top: 10px;
  left: -8px;
  display: inline-block;
  background: #fff;
  width: 16px;
  height: 16px;
  border-top: 1px solid #f1f5fc;
  border-left: 1px solid #f1f5fc;
  content: '';
  transform: rotate(-45deg);
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -ms-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
}

.chat li.right .chat-body:before {
  position: absolute;
  top: 10px;
  right: -8px;
  display: inline-block;
  background: #fff;
  width: 16px;
  height: 16px;
  border-top: 1px solid #f1f5fc;
  border-right: 1px solid #f1f5fc;
  content: '';
  transform: rotate(45deg);
  -webkit-transform: rotate(45deg);
  -moz-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  -o-transform: rotate(45deg);
}

.chat li {
  margin: 15px 0;
}

.chat li.right .chat-body {
  margin-right: 70px;
  background-color: #fff;
}

.chat-box {
/*
  position: fixed;
  bottom: 0;
  left: 444px;
  right: 0;
*/
  padding: 15px;
  border-top: 1px solid #eee;
  transition: all .5s ease;
  -webkit-transition: all .5s ease;
  -moz-transition: all .5s ease;
  -ms-transition: all .5s ease;
  -o-transition: all .5s ease;
}

.primary-font {
  color: #3c8dbc;
}

a:hover, a:active, a:focus {
  text-decoration: none;
  outline: 0;
}
</style>
</head>
<body>
<div class="container bootstrap snippets bootdey">
    <div class="row">
		<div class="col-md-4 bg-white ">
            
            <!-- =============================================================== -->
            <!-- member list -->
            <ul class="friend-list">
            
	            <c:forEach var="crl" items="${chattingRoomList}">
	            	<c:if test="${crl.sellerId == userId}">
		                <li class="active bounceInDown">
		                	<a class="clearfix" type="button" onclick="showPopup();" target="_blank">
		                		<img src="https://bootdey.com/img/Content/user_1.jpg" alt="" class="img-circle">
		                		<div class="friend-name">	
		                			<strong>${crl.buyer}</strong>
		                		</div>
		                		<c:if test="${crl.lastChatterId == userId}">
			                		<div class="last-message text-muted">
			                			${crl.lastContent}
			                		</div>
		                		</c:if>
		                		<c:if test="${crl.lastChatterId != userId}">
			                		<div class="last-message text-muted">${crl.lastContent}</div>
		                		</c:if>
		                		<small class="time text-muted">${crl.createDate}</small>
		                		<small class="chat-alert label label-danger">1</small>
		                	</a>
		                </li>
		                <br>
	                </c:if>
	                
	                <c:if test="${crl.buyerId == userId}">
		                <li class="active bounceInDown">
		                	<a class="clearfix" type="button" onclick="showPopup();" target="_blank">
		                		<img src="https://bootdey.com/img/Content/user_1.jpg" alt="" class="img-circle">
		                		<div class="friend-name">	
		                			<strong>${crl.seller}</strong>
		                		</div>
		                		<c:if test="${crl.lastChatterId == userId}">
			                		<div class="last-message text-muted">
			                			${crl.lastContent}
			                		</div>
		                		</c:if>
		                		<c:if test="${crl.lastChatterId != userId}">
			                		<div class="last-message text-muted">${crl.lastContent}</div>
		                		</c:if>
		                		<small class="time text-muted">${crl.createDate}</small>
		                		<small class="chat-alert label label-danger">1</small>
		                	</a>
		                </li>
		                <br>
	                </c:if>
	                
	            </c:forEach>  
    
            </ul>
		</div>
               
	</div>
</div>
</body>
<script>
//채팅방 팝업창
function showPopup() { 
	  window.open("${pageContext.request.contextPath}/users/getChattingRoomOne?directTradeProductRegistrationId=45&userId=3", "b", "width=400, height=600, left=600, top=200"); 
}
</script>
</html>