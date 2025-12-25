<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Station - Railways Management System</title>
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
        button { margin-top: 20px; padding: 10px 20px; background: #28a745; color: white; border: none; }
        button:hover { background: #218838; }
    </style>
</head>
<body>
    <h1>Add New Station</h1>


    <form action="${pageContext.request.contextPath}/stations" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>

        <label for="location">Location:</label>
        <input type="text" id="location" name="location" required>

        <button type="submit">Add Station</button>
    </form>
</body>
</html>