<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Station - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; padding: 5px 10px; background: #f0f0f0; }
        .nav a:hover { background: #e0e0e0; }
        form { max-width: 400px; }
        label { display: block; margin-top: 10px; }
        input { width: 100%; padding: 5px; margin-top: 5px; }
        button { margin-top: 20px; padding: 10px 20px; background: #007bff; color: white; border: none; }
        button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h1>Edit Station</h1>


    <form action="${pageContext.request.contextPath}/stations" method="post">
        Station ID: ${station.id}

        <input type="hidden" name="id" value="${station.id}">

        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${station.name}" required>

        <label for="location">Location:</label>
        <input type="text" id="location" name="location" value="${station.location}" required>

        <button type="submit">Update Station</button>
    </form>
</body>
</html>
