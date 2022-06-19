<%@ page import="model.CityModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/calcPrice.css" rel="stylesheet">
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<% List<CityModel> cities = (List) request.getAttribute("cities"); %>
<c:import url="/HeaderServ" />
<h2>Створити замовлення</h2>
<br />
<form id="makeOrderForm" class="makeOrderForm" method="post" action="${pageContext.request.contextPath}/MakeOrder">
    <div>
        <div class="boldDiv">Отримувач</div>
        <div>
            <div class="marg">
                <label for="pibRec">ПІБ отримувача</label>
                <input name="pibRec" id="pibRec" />
            </div>
            <div class="marg">
                <label for="telRec">Номер телефону отримувача</label>
                <input name="telRec" id="telRec" />
            </div>
        </div>
        <div class="marg">
            <label for="addressRec">Адреса доставки</label>
            <input name="addressRec" id="addressRec" />
        </div>
        <div class="marg boldDiv">Маршрут</div>
        <div class="block">
            <div class="leftBlock">
                <label for="cityFrom">місто-відправник</label>
                <select name="cityFrom" id="cityFrom">
                    <% for (CityModel city: cities) {%>
                    <option value="<%= city.getCityId()%>"><%= city.getCityName()%></option>
                    <% } %>
                </select>
            </div>
            <div>
                <label for="cityTo">місто-одержувач</label>
                <select name="cityTo" id="cityTo">
                    <% for (CityModel city: cities) {%>
                    <option value="<%= city.getCityId()%>"><%= city.getCityName()%></option>
                    <% } %>
                </select>
            </div>
        </div>
    </div>
    <div class="marg">
        <label for="pType">Вид відправлення</label>
        <select name="pType" id="pType">
            <option value="1">Посилки</option>
            <option value="2">Вантажі</option>
        </select>
    </div>
    <div class="marg">
        <div class="boldDiv">Характеристика місць</div>
        <div>
            <div class="marg block">
                <label class="weight" for="pCost">Оголошена вартість</label>
                <input type="number" class="inputNumb" min="10" name="pCost" id="pCost"/>
                <div class="metrics">грн.</div>
            </div>
            <div class="block">
                <label class="weight" for="weight">Маса</label>
                <input  type="number" class="inputNumb" min="0" name="weight" id="weight"/>
                <div class="metrics">кг</div>
            </div>
            <div class="block">
                <div class="sizeBlock">
                    <label for="length">Довжина</label>
                    <input type="number" class="inputNumb" name="length" id="length" min="1"/>
                </div>
                <div class="sizeBlock">
                    <label for="width">Ширина</label>
                    <input type="number" class="inputNumb" min="1" name="width" id="width"/>
                </div>
                <div>
                    <label for="height">Висота</label>
                    <input type="number" class="inputNumb" min="1" name="height" id="height"/>
                </div>
                <div class="metrics">см</div>
            </div>
        </div>
        <button class="calcButton" type="submit">Створити замовлення</button>
    </div>
</form>

<script>
    const form = document.querySelector('#calcPriceForm');
    const weight = document.querySelector('#pCost');
    form.addEventListener('submit', function(evt) {
        evt.preventDefault();
        if(!weight.value) {
            alert('Поле имя не заполнено');
            return;
        }
        this.submit();
    });
</script>
</body>



