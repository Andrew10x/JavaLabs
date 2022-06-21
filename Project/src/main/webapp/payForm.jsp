<%@ page import="java.util.Map" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/calcPrice.css" rel="stylesheet">
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<c:import url="/header.jsp" />
<h2>Оплата замовлення</h2>
<br />

<%if(!((boolean) request.getAttribute("success"))) {%>
<form id="payForm" class="payForm" method="post" action="${pageContext.request.contextPath}/PayOrder">
    <div>
        <div>
            <div class="marg">
                <label for="orderId">Номер замовлення</label>
                <input name="orderId" id="orderId" value="<%=request.getAttribute("orderId")%>" readonly="readonly">
            </div>
            <div class="marg">
                <label for="cardNumber1">Номер картки</label>
                <input name="cardNumber1" id="cardNumber1" type="number" autocomplete="off" class="cardNumber margLeft5" min="0" max="9999"/>
                <input name="cardNumber2" id="cardNumber2" type="number" autocomplete="off" class="cardNumber" min="0" max="9999"/>
                <input name="cardNumber3" id="cardNumber3" type="number" autocomplete="off" class="cardNumber" min="0" max="9999"/>
                <input name="cardNumber4" id="cardNumber4" type="number" autocomplete="off" class="cardNumber" min="0" max="9999"/>
            </div>
            <div class="marg">
                <label for="month">Місяць/рік</label>
                <input class="margLeft5" name="month" id="month" type="number" autocomplete="off" min="1" max="12"/>
                /
                <input name="year" id="year" type="number" autocomplete="off" min="0"/>
                <label for="cvc" class="cvcL">cvc</label>
                <input class="margLeft5" name="cvc" id="cvc" type="number" autocomplete="off" min="000"/>
            </div>
        </div>
        <div class="marg">
            <label for="pib">Ім'я Прізвище</label>
            <input class="margLeft5" name="pib" id="pib" placeholder="Латинськими літерами" autocomplete="off"/>
        </div>
        <button class="calcButton" type="submit">Оплатити замовлення</button>
    </div>
</form>
<%} else {%>
<h2>Замовлення оплачено</h2>
<%}%>

<script>
    const form = document.querySelector('#payForm');
    const cn1 = document.querySelector('#cardNumber1');
    const cn2 = document.querySelector('#cardNumber2');
    const cn3 = document.querySelector('#cardNumber3');
    const cn4 = document.querySelector('#cardNumber4');
    const month = document.querySelector('#month');
    const year = document.querySelector('#year');
    const cvc = document.querySelector('#cvc');
    const pib = document.querySelector('#pib');
    if(form !== null)
    form.addEventListener('submit', function(evt) {
        evt.preventDefault();
        if(!cn1.value || cn1.value.length !== 4 || !cn2.value || cn2.value.length !== 4 ||
            !cn3.value || cn3.value.length !== 4 || !cn4.value || cn4.value.length !== 4) {
            alert('Неправильний номер картки');
            return;
        }
        if(!month.value || !year.value) {
            alert('Введіть Рік та Місяць')
            return;
        }
        if(!cvc.value || cvc.value.length !== 3) {
            alert('Введіть  cvc')
            return;
        }
        if(!pib.value) {
            alert("Введіть Ім'я та Прізвище");
            return;
        }

        this.submit();
    });
</script>
</body>
</html>
