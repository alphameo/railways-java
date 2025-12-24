<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Stations - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Stations</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/trains">Trains</a>
        <a href="${pageContext.request.contextPath}/locomotives">Locomotives</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
        <a href="${pageContext.request.contextPath}/train-compositions">Train Compositions</a>
    </div>

    <div class="add-button">
        <a href="${pageContext.request.contextPath}/stations/create">Add New Station</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="station" items="${stations}">
                <tr>
                    <td>${station.id}</td>
                    <td>${station.name}</td>
                    <td>${station.location}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/stations/${station.id}">View</a>
                        <a href="${pageContext.request.contextPath}/stations/${station.id}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/stations/${station.id}/delete" onclick="return confirm('Are you sure you want to delete this station?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty stations}">
        <p>No stations found.</p>
    </c:if>
</body>
</html>