<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Carriage - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Carriage</h1>


    <form action="${pageContext.request.contextPath}/carriages" method="post">
        Carriage ID: ${carriage.id}
        <input type="hidden" name="id" value="${carriage.id}">

        <label for="number">Number:</label>
        <input type="text" id="number" name="number" value="${carriage.number}" required>

        <label for="contentType">Content Type:</label>
        <select id="contentType" name="contentType">
            <option value="none" ${carriage.contentType == 'none' ? 'selected' : ''}>none</option>
            <option value="PASSENGER" ${carriage.contentType == 'PASSENGER' ? 'selected' : ''}>Passenger</option>
            <option value="CARGO" ${carriage.contentType == 'CARGO' ? 'selected' : ''}>Cargo</option>
        </select>

        <label for="capacity">Capacity:</label>
        <input type="number" id="capacity" name="capacity" value="${carriage.capacity}">

        <button type="submit">Update Carriage</button>
    </form>
</body>
</html>
