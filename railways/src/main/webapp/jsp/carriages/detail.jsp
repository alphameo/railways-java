<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carriage Details - Railways Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; padding: 5px 10px; background: #f0f0f0; }
        .nav a:hover { background: #e0e0e0; }
        .details { border: 1px solid #ddd; padding: 20px; max-width: 400px; }
        .actions { margin-top: 20px; }
        .actions a { margin-right: 10px; text-decoration: none; padding: 5px 10px; background: #007bff; color: white; }
        .actions a:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h1>Carriage Details</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/trains">Trains</a>
        <a href="${pageContext.request.contextPath}/stations">Stations</a>
        <a href="${pageContext.request.contextPath}/locomotives">Locomotives</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
        <a href="${pageContext.request.contextPath}/train-compositions">Train Compositions</a>
    </div>

    <div class="details">
        <p><strong>ID:</strong> ${carriage.id}</p>
        <p><strong>Number:</strong> ${carriage.number}</p>
        <p><strong>Content Type:</strong> ${carriage.contentType}</p>
        <p><strong>Capacity:</strong> ${carriage.capacity}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/carriages/${carriage.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/carriages" onclick="return confirm('Are you sure you want to delete this carriage?')">Delete</a>
    </div>
</body>
</html>