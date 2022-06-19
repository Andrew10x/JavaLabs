<%@ page import="model.OrderJoinedModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/calcPrice.css" rel="stylesheet">
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<c:import url="/HeaderServ" />
<% OrderJoinedModel ojm = (OrderJoinedModel) request.getAttribute("ojm"); %>
<form id="orderForm" class="orderForm" method="post" action="${pageContext.request.contextPath}/">
    <div>
        <div class="boldDiv">Отримувач</div>
        <div>
            <div class="marg">
                <label for="pibRec">ПІБ отримувача</label>
                <input name="pibRec" id="pibRec" readonly="readonly" value="<%=ojm.getRecipientName()%>"/>
            </div>
            <div class="marg">
                <label for="telRec">Номер телефону отримувача</label>
                <input name="telRec" id="telRec" readonly="readonly" value="<%=ojm.getPhone()%>"/>
            </div>
        </div>
        <div class="marg">
            <label for="addressRec">Адреса доставки</label>
            <input name="addressRec" id="addressRec" readonly="readonly" value="<%=ojm.getAddress()%>"/>
        </div>
        <div class="boldDiv">Маршрут</div>
        <div class="block">
            <div class="leftBlock">
                <label for="cityFrom">місто-відправник</label>
                <input id="cityFrom" readonly="readonly" value="<%=ojm.getCityFrom()%>"/>
            </div>
            <div>
                <label for="cityTo">місто-одержувач</label>
                <input id="cityTo" readonly="readonly" value="<%=ojm.getCityTo()%>>"/>
            </div>
        </div>
    </div>
    <div class="marg">
        <label for="pType">Вид відправлення</label>
        <input readonly="readonly" id="pType" value="<%=ojm.getTypeName()%>"/>
    </div>
    <div class="marg">
        <div class="boldDiv">Характеристика місць</div>
        <div>
            <div class="marg block">
                <label class="weight" for="pCost">Оголошена вартість</label>
                <input type="number" class="inputNumb" id="pCost" readonly="readonly" value="<%=ojm.getSumInsured()%>"/>
                <div class="metrics">грн.</div>
            </div>
            <div class="block">
                <label class="weight" for="weight">Маса</label>
                <input  readonly="readonly" type="number" class="inputNumb" id="weight" value="<%=ojm.getWeightOrd()%>"/>
                <div class="metrics">кг</div>
            </div>
            <div class="block">
                <div class="sizeBlock">
                    <label for="length">Довжина</label>
                    <input readonly="readonly" type="number" class="inputNumb" id="length" value="<%=ojm.getLengthOrd()%>"/>
                </div>
                <div class="sizeBlock">
                    <label for="width">Ширина</label>
                    <input readonly="readonly" type="number" class="inputNumb" id="width" value="<%=ojm.getWidthOrd()%>"/>
                </div>
                <div>
                    <label for="height">Висота</label>
                    <input readonly="readonly" type="number" class="inputNumb" id="height" value="<%=ojm.getHeightOrd()%>"/>
                </div>
                <div class="metrics">см</div>
            </div>
        </div>
        <div class="marg block resCost">
            <div>Вартість доставки: <%=(int) ojm.getDeliveryCost()%> грн.</div>
        </div>
        <button class="calcButton" type="submit">Оплатити</button>
    </div>
</form>
</body>
</html>
