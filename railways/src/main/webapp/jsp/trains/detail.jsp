<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Train Details</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <div class="details">
        <p><strong>ID:</strong> ${train.id}</p>
        <p><strong>Number:</strong> ${train.number}</p>
        <p><strong>Train Composition ID:</strong> ${train.trainCompositionId}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/trains/${train.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/trains/${train.id}/delete" onclick="return confirm('Are you sure you want to delete this train?')">Delete</a>
    </div>
</body>
</html>