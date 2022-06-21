<%@ page import="model.OrderJoinedModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/printOrder.css" rel="stylesheet">
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<c:import url="/header.jsp" />


<h2 class="printTitle">Рахунок на оплату номер: <%= request.getAttribute("orderId") %></h2>
    <div class="printOrder">
        <div>
           Постачальник:
        </div>
        <div>
            <b>ТОВ Fast Delivery</b> <br>
             п/р UA 2532820912340000056789 (застар. 12340000056789) у банку АКЦІОНЕРНИЙ БАНК
             ПІВДЕННИЙ, М.ОДЕСА, МФО 328209.
            <div>код за ЄДРПОУ 1236544, ІПН 12254489</div>
        </div>
        <div>
            Покупець:
        </div>
        <div>
            <%= request.getAttribute("userName") %>
        </div>
        <div>
            Договір:
        </div>
        <div>
            постачання послуг
        </div>
        <div>
            Послуга:
        </div>
        <div>
            перевезення вантажу
        </div>
        <div>
            Сума:
        </div>
        <div>
            <%= request.getAttribute("price") %> гривень 00 копійок
        </div>
        <div>
            Виписав(ла):
        </div>
        <div class="pibBordBottom"></div>
        <div>
            Підпис:
        </div>
        <div class="singBordBottom"></div>
    </div>
</body>
</html>
