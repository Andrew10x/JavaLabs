
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="${pageContext.request.contextPath}/resources/showCalcPrice.css" rel="stylesheet">
</head>
<body>
<c:import url="/header.jsp" />
<div class="calcResBlock">
    <div>
        <div>Відстань: <%= request.getAttribute("distance") %> км</div>
        <div>Macа: <%= request.getAttribute("weight") %> кг</div>
        <div>Середня довжина: <%= request.getAttribute("evLength") %> см</div>
        <div>Оголошена вартість: <%= request.getAttribute("pCost") %> грн.</div>
    </div>
</div>
<h2>Вартість доставки складатиме: <%= request.getAttribute("price") %> грн.</h2>

</body>
</html>
