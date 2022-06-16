
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/auth.css" rel="stylesheet">
</head>
<body>
<c:import url="/HeaderServ" />
<h2 class="authTitle">Вхід</h2>
<form id="singInForm" class="authForm" method="post" action="${pageContext.request.contextPath}/SingInServ">
    <div class="formBlock">
        <label for="login">Email: </label>
        <input id="login" name="login" placeholder="enter email"/>
    </div>
    <div class="formBlock">
        <label for="password">Пароль: </label>
        <input class="password" id="password" name="password" type="password" placeholder="enter password"/>
    </div>
    <div class="authButtonDiv">
        <button type="submit" class="authButton">Увійти</button>
    </div>
</form>
<script>
    const form = document.querySelector('#singInForm');
    const login = document.querySelector('#login');
    const password = document.querySelector('#password')
    form.addEventListener('submit', function(evt) {
        evt.preventDefault();
        if(!login.value) {
            alert('Введіть login');
            return;
        }
        if(!password.value) {
            alert('Введіть пароль')
            return;
        }
        this.submit();
    });
</script>
</body>
</html>


