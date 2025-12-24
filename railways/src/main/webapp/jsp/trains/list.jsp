<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trains - Railways Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; padding: 5px 10px; background: #f0f0f0; }
        .nav a:hover { background: #e0e0e0; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .actions { white-space: nowrap; }
        .actions a { margin-right: 5px; text-decoration: none; padding: 2px 5px; background: #007bff; color: white; }
        .actions a:hover { background: #0056b3; }
        .add-button { margin-bottom: 10px; }
        .add-button a { text-decoration: none; padding: 5px 10px; background: #28a745; color: white; }
        .add-button a:hover { background: #218838; }
    </style>
</head>
<body>
    <h1>Trains</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/stations">Stations</a>
        <a href="${pageContext.request.contextPath}/locomotives">Locomotives</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
        <a href="${pageContext.request.contextPath}/train-compositions">Train Compositions</a>
    </div>

    <div class="add-button">
        <a href="${pageContext.request.contextPath}/trains/create">Add New Train</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Number</th>
                <th>Train Composition ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="train" items="${trains}">
                <tr>
                    <td>${train.id}</td>
                    <td>${train.number}</td>
                    <td>${train.trainCompositionId}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/trains/${train.id}">View</a>
                        <a href="${pageContext.request.contextPath}/trains/${train.id}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/trains/${train.id}/delete" onclick="return confirm('Are you sure you want to delete this train?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty trains}">
        <p>No trains found.</p>
    </c:if>
</body>
</html>