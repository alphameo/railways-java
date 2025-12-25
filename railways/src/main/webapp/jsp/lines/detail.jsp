<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Line Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Line Details</h1>


    <div class="details">
        <p><strong>ID:</strong> ${line.id}</p>
        <p><strong>Name:</strong> ${line.name}</p>
        <p><strong>Station ID Order:</strong> ${line.stationIdOrder}</p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/lines/${line.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/lines/${line.id}/delete" onclick="return confirm('Are you sure you want to delete this line?')">Delete</a>
    </div>
</body>
</html>