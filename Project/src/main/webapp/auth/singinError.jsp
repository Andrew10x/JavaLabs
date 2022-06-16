
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/singinError.css" rel="stylesheet">
</head>
<body>
<div class="errorBlock">
    <div>Неправильний email або пароль.</div>
    <div>Спробувати ще раз: <a href = "${pageContext.request.contextPath}/auth/singin.jsp" >Вхід</a ></div >
    <div>Перейти на <a href = "${pageContext.request.contextPath}/" >головну сротінку</a ></div>
</div>
</body>
</html>
