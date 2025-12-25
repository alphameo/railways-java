<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Locomotive - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Locomotive</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/locomotives" method="post">
        <input type="hidden" name="id" value="${locomotive.id}">

        <label for="number">Number:</label>
        <input type="text" id="number" name="number" value="${locomotive.number}" required>

        <label for="model">Model:</label>
        <input type="text" id="model" name="model" value="${locomotive.model}" required>

        <button type="submit">Update Locomotive</button>
    </form>
</body>
</html>