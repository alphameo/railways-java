<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Carriage - Railways Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; padding: 5px 10px; background: #f0f0f0; }
        .nav a:hover { background: #e0e0e0; }
        form { max-width: 400px; }
        label { display: block; margin-top: 10px; }
        input, select { width: 100%; padding: 5px; margin-top: 5px; }
        button { margin-top: 20px; padding: 10px 20px; background: #007bff; color: white; border: none; }
        button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h1>Edit Carriage</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/trains">Trains</a>
        <a href="${pageContext.request.contextPath}/stations">Stations</a>
        <a href="${pageContext.request.contextPath}/locomotives">Locomotives</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
        <a href="${pageContext.request.contextPath}/train-compositions">Train Compositions</a>
    </div>

    <form action="${pageContext.request.contextPath}/carriages" method="post">
        <input type="hidden" name="id" value="${carriage.id}">

        <label for="number">Number:</label>
        <input type="text" id="number" name="number" value="${carriage.number}" required>

        <label for="contentType">Content Type:</label>
        <select id="contentType" name="contentType">
            <option value="PASSENGER" ${carriage.contentType == 'PASSENGER' ? 'selected' : ''}>Passenger</option>
            <option value="CARGO" ${carriage.contentType == 'CARGO' ? 'selected' : ''}>Cargo</option>
            <option value="SPECIAL" ${carriage.contentType == 'SPECIAL' ? 'selected' : ''}>Special</option>
        </select>

        <label for="capacity">Capacity:</label>
        <input type="number" id="capacity" name="capacity" value="${carriage.capacity}" required>

        <button type="submit">Update Carriage</button>
    </form>
</body>
</html>