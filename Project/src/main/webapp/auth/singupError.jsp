
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/singinError.css" rel="stylesheet">
</head>
<body>
<div class="errorBlock">
    <div>Даний email вже використовується</div>
    <div>Увійти: <a href = "${pageContext.request.contextPath}/auth/singin.jsp" >Вхід</a ></div >
    <div>Зареєструватися з іншим email: <a href = "${pageContext.request.contextPath}/auth/singup.jsp" >Реєстрація</a ></div >
    <div>Перейти на <a href = "${pageContext.request.contextPath}/" >головну сротінку</a ></div>
</div>
</body>
</html>
