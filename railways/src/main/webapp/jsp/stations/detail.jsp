<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Station Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
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
    <h1>Station Details</h1>


    <div class="details">
        <p><strong>ID:</strong> ${station.id}</p>
        <p><strong>Name:</strong> ${station.name}</p>
        <p><strong>Location:</strong> ${station.location}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/stations/${station.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/stations/${station.id}/delete" onclick="return confirm('Are you sure you want to delete this station?')">Delete</a>
    </div>
</body>
</html>