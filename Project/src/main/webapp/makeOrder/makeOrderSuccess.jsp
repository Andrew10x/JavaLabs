
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/makeOrderSuccess.css" rel="stylesheet">
</head>
<body>
<div class="successBlock">
    <div>Ваше замовлення <b><%=request.getAttribute("id")%></b> створено і очікує підтвердження менеджером.
        Після того, як замовлення буде підтверджено, ви зможете його оплатити</div>
    <div>Перейти на сторінку: <a href = "${pageContext.request.contextPath}/myOrders.jsp" >мої замовлення</a ></div >
    <div>Перейти на <a href = "${pageContext.request.contextPath}/" >головну сротінку</a ></div>
</div>
</body>
</html>
