
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/singUp.css" rel="stylesheet">
</head>
<body>
<c:import url="/HeaderServ" />
<h2 class="authTitle">Реєстрація</h2>
<form id="singUpForm" class="authForm" method="post" action="${pageContext.request.contextPath}/SingUpServ">
    <div class="formBlock">
        <label for="pib">ПІБ: </label>
        <input id="pib" name="pib"/>
    </div>
    <div class="formBlock">
        <label for="phoneNumber">Номер телефону: </label>
        <input id="phoneNumber" name="phoneNumber"/>
    </div>
    <div class="formBlock">
        <label for="login">Email: </label>
        <input id="login" name="login"/>
    </div>
    <div class="formBlock">
        <label for="password">Пароль: </label>
        <input id="password" name="password" type="password"/>
    </div>
    <div class="formBlock">
        <label for="password2">Повторіть пароль: </label>
        <input id="password2" name="password" type="password"/>
    </div>
    <div class="authButtonDiv">
        <button type="submit" class="authButton">Зареєструватися</button>
    </div>
</form>
<script>
    const form = document.querySelector('#singUpForm');
    const pib = document.querySelector('#pib');
    const phoneNumber = document.querySelector('#phoneNumber');
    const login = document.querySelector('#login');
    const password = document.querySelector('#password')
    const password2 = document.querySelector('#password2');
    form.addEventListener('submit', function(evt) {
        evt.preventDefault();
        if(!pib.value) {
            alert('Введіть ПІБ');
            return;
        }
        if(!phoneNumber.value) {
            alert('Введіть номер телефону');
            return;
        }
        if(!login.value) {
            alert('Введіть login');
            return;
        }
        if(!password.value) {
            alert('Введіть пароль')
            return;
        }
        if(!password2.value) {
            alert('Повторіть пароль');
            return;
        }
        if(password.value !== password2.value) {
            alert('Паролі не співпадають');
            return;
        }
        this.submit();
    });
</script>
</body>
</html>
