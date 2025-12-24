<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Compositions - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Train Compositions</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/trains">Trains</a>
        <a href="${pageContext.request.contextPath}/stations">Stations</a>
        <a href="${pageContext.request.contextPath}/locomotives">Locomotives</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
    </div>

    <div class="add-button">
        <a href="${pageContext.request.contextPath}/train-compositions/create">Add New Train Composition</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Locomotive ID</th>
                <th>Carriage IDs</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="trainComposition" items="${trainCompositions}">
                <tr>
                    <td>${trainComposition.id}</td>
                    <td>${trainComposition.locomotiveId}</td>
                    <td>${trainComposition.carriageIds}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/train-compositions/${trainComposition.id}">View</a>
                        <a href="${pageContext.request.contextPath}/train-compositions/${trainComposition.id}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/train-compositions/${trainComposition.id}/delete" onclick="return confirm('Are you sure you want to delete this train composition?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty trainCompositions}">
        <p>No train compositions found.</p>
    </c:if>
</body>
</html>