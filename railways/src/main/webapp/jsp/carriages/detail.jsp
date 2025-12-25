<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carriage Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Carriage Details</h1>


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