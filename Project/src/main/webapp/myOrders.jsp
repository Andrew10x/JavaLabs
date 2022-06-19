<%@ page import="model.OrderJoinedModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/myOrders.css" rel="stylesheet">
</head>
<body>
<% List<OrderJoinedModel> data = (List) request.getAttribute("data"); %>
<c:import url="/HeaderServ" />
<h2 class="moTitle">Мої замовлення</h2>
<div class="myOrdersBlock">
    <% for(OrderJoinedModel d: data) {%>
    <a href="${pageContext.request.contextPath}/Order?id=<%=d.getId()%>">
        <div class="myOrderItem">
            <div class="withBorder">Номер замовлення: <%=d.getId()%></div>
            <div class="withBorder">від: <%=d.getCityFrom()%></div>
            <div class="withBorder">до: <%=d.getCityTo()%></div>
            <div class="withBorder">Тип відправлення: <%=d.getTypeName()%></div>
            <div class="withBorder">Вартість: <%=d.getDeliveryCost()%></div>
            <div class="withBorder">Дата: <%=d.getDateOrd()%></div>
            <div>Статус: <%=d.getStatusName()%></div>
        </div>
    </a>
    <%}%>
</div>
</body>
</html>
