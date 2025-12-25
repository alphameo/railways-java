<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Carriage - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Carriage</h1>


    <form action="${pageContext.request.contextPath}/carriages" method="post">
        <label for="number">Number:</label>
        <input type="text" id="number" name="number" required>

        <label for="contentType">Content Type:</label>
        <select id="contentType" name="contentType">
            <option value="PASSENGER">Passenger</option>
            <option value="CARGO">Cargo</option>
            <option value="SPECIAL">Special</option>
        </select>

        <label for="capacity">Capacity:</label>
        <input type="number" id="capacity" name="capacity" required>

        <button type="submit">Add Carriage</button>
    </form>
</body>
</html>