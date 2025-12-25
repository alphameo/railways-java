<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Composition Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Train Composition Details</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <div class="details">
        <p><strong>ID:</strong> ${trainComposition.id}</p>
        <p><strong>Locomotive ID:</strong> ${trainComposition.locomotiveId}</p>
        <p><strong>Carriage IDs:</strong> ${trainComposition.carriageIds}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/train-compositions/${trainComposition.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/train-compositions/${trainComposition.id}/delete" onclick="return confirm('Are you sure you want to delete this train composition?')">Delete</a>
    </div>
</body>
</html>