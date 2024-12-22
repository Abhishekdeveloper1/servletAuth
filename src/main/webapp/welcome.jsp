<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%
    
    model.User user = (model.User) session.getAttribute("user");
    if (user != null) {
%>
    <h1>Welcome, <%= user.getName() %>!</h1>
    <p>Email: <%= user.getEmail() %></p>
    <a href="logout">Logout</a>
    
    
<%
    } else {
%>
    <p>You are not logged in. Please <a href="login.jsp">log in</a>.</p>
<%
    }
%>
</body>
</html>