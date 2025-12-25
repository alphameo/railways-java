<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Locomotive - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Locomotive</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/locomotives" method="post">
        <label for="number">Number:</label>
        <input type="text" id="number" name="number" required>

        <label for="model">Model:</label>
        <input type="text" id="model" name="model" required>

        <button type="submit">Add Locomotive</button>
    </form>
</body>
</html>