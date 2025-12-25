<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locomotive Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Locomotive Details</h1>


    <div class="details">
        <p><strong>ID:</strong> ${locomotive.id}</p>
        <p><strong>Number:</strong> ${locomotive.number}</p>
        <p><strong>Model:</strong> ${locomotive.model}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/locomotives/${locomotive.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/locomotives/${locomotive.id}/delete" onclick="return confirm('Are you sure you want to delete this locomotive?')">Delete</a>
    </div>
</body>
</html>