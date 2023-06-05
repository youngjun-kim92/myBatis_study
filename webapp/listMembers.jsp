<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 출력창</title>
</head>
<body>
	<h2 align="center">회원정보</h2>
	<table align="center" border="1" width="800">
		<tr align="center" bgcolor="lightblue">
			<th>아이디</th><th>비밀번호</th><th>이름</th><th>이메일</th><th>가입일</th><th>수정</th><th>삭제</th>
		</tr>
		
		<c:choose>
		<%-- MemberServlet에 request.setAttribute("여기있는 이름") 가져오기--%>
			<c:when test="${empty membersList}">
				<tr>
					<td colspan="5" align="center">
						등록된 회원이 없습니다.
					</td>					
				</tr>				
			</c:when>
			<c:when test="${!empty membersList}">
				<c:forEach var="member" items="${membersList}">
					<tr align="center">
					<%-- member에다가 .을 찍어서 정보에 접근 --%>
						<td>${member.id}</td>
						<td>${member.pwd}</td>
						<td>${member.name}</td>
						<td>${member.email}</td>
						<td>${member.joinDate}</td>
						<td><a href="${contextPath}/member.do?action=modMember&id=${member.id}">수정</a></td>
						<td><a href="${contextPath}/member.do?action=delMember&id=${member.id}">삭제</a></td>			
					</tr>
				</c:forEach>				
			</c:when>			
		</c:choose>		
	</table>
	<p align="center"><a href="${contextPath}/member.do?action=memberForm">회원가입하기</a></p>
</body>
</html>