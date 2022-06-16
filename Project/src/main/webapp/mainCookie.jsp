
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<ul>
    <c:forEach var="item" items="${messages}">
        <li>${item}</li>
    </c:forEach>
</ul>
</body>
</html>
