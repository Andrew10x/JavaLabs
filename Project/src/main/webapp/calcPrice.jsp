<%@ page import="model.CityModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/calcPrice.css" rel="stylesheet">
</head>
<body>
<% List<CityModel> cities = (List) request.getAttribute("cities"); %>
<h2>Розрахунок вартості доставки</h2>
<br />
<form id="calcPriceForm" class="calcPriceForm" method="post" action="${pageContext.request.contextPath}/">
    <div>
        <div>Маршрут</div>
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
        <div>Характеристика місць</div>
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
        <button class="calcButton" type="submit">Розрахувати вартість</button>
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


