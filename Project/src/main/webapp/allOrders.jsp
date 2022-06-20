<%@ page import="model.OrderJoinedModel" %>
<%@ page import="java.util.List" %>
<%@ page import="model.StatusesModel" %>
<%@ page import="model.CityModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/myOrders.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/chooseOrders.css" rel="stylesheet">
</head>
<body>
<% List<OrderJoinedModel> data = (List) request.getAttribute("data"); %>
<% List<StatusesModel> statuses = (List) request.getAttribute("statuses"); %>
<% List<CityModel> cities = (List) request.getAttribute("cities"); %>
<c:import url="/header.jsp" />
<h2 class="moTitle">Замовлення</h2>
<form id="chooseOrdersForm" method="post" action="${pageContext.request.contextPath}/AllOrders">
    <div class="chooseOrdersBlock">
    <div>
        <label for="orderId">Id: </label>
        <input name="orderId" id="orderId" min="0"/>
    </div>
    <div>
        <label for="email">Email: </label>
        <input name="email" id="email"/>
    </div>
    <div>
        <label for="status">Статус: </label>
        <select name="status" id="status">
            <option value="">Оберіть статус</option>
            <% for(StatusesModel sm: statuses) {%>
            <option value="<%=sm.getStatusname()%>"><%=sm.getStatusname()%></option>
            <%}%>
        </select>
    </div>
    <div>
        <label for="date">Дата: </label>
        <input type="date" id="date" name="date"/>
    </div>
    <div>
        <label for="cityFrom">Від: </label>
        <select id="cityFrom" name="cityFrom" >
            <option value="">Оберіть місто</option>
            <% for(CityModel cm: cities) {%>
            <option value="<%=cm.getCityName()%>"><%=cm.getCityName()%></option>
            <%}%>
        </select>
    </div>
    <div>
        <label for="cityTo">До: </label>
        <select id="cityTo" name="cityTo">
            <option value="">Оберіть місто</option>
            <% for(CityModel cm: cities) {%>
            <option value="<%=cm.getCityName()%>"><%=cm.getCityName()%></option>
            <%}%>
        </select>
    </div>
    </div>
    <button type="submit" class="sendBtn">Знайти</button>
</form>

<div class="myOrdersBlock">
    <% for(OrderJoinedModel d: data) {%>
        <div class="allOrdersItem">
            <div class="withBorder">Номер замовлення: <%=d.getId()%></div>
            <div class="withBorder">від: <%=d.getCityFrom()%></div>
            <div class="withBorder">до: <%=d.getCityTo()%></div>
            <div class="withBorder">Тип відправлення: <%=d.getTypeName()%></div>
            <div class="withBorder">Вартість: <%=d.getDeliveryCost()%></div>
            <div class="withBorder">Дата: <%=d.getDateOrd()%></div>
            <div>
            <label for="<%=d.getId()%>">Статус: </label>
            <select id="<%=d.getId()%>" onchange="chf(this)">
            <%for(StatusesModel sm: statuses) {%>
                <%if(sm.getStatusid() == d.getStatusId()) {%>
                <option selected="selected" value="<%=sm.getStatusid()%>"><%=sm.getStatusname()%></option>
                <%} else {%>
                <option value="<%=sm.getStatusid()%>"><%=sm.getStatusname()%></option>
                <%}}%>
            </select>
            </div>
            <a href="${pageContext.request.contextPath}/Order?id=<%=d.getId()%>&print=true">
                <button>Переглянути</button>
            </a>
        </div>
    <%}%>
</div>

<script>
    function chf(sel) {
        const xhr = new XMLHttpRequest();
        const body = 'orderId=' + encodeURIComponent(sel.id) +
            '&statusId=' + encodeURIComponent(sel.value);

        xhr.open("POST", '${pageContext.request.contextPath}/ChangeStatus', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onload = function() {
            if(xhr.status !== 200)
                alert('Error to change status')
        };
        xhr.send(body);
    }
</script>
</body>
</html>

