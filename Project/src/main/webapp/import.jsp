
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <link href="resources/st.scss" rel="stylesheet">
</head>
<body>
<c:url value="title.jsp" var="importUrl">
    <c:param name="pageName" value="Import template page"/>
</c:url>
<h2>Main page</h2>
<c:import url="${importUrl}" />
</body>
</html>
