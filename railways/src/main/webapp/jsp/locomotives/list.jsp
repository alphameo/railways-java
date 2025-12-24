<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locomotives - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Locomotives</h1>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/carriages">Carriages</a>
        <a href="${pageContext.request.contextPath}/trains">Trains</a>
        <a href="${pageContext.request.contextPath}/stations">Stations</a>
        <a href="${pageContext.request.contextPath}/lines">Lines</a>
        <a href="${pageContext.request.contextPath}/train-compositions">Train Compositions</a>
    </div>

    <div class="add-button">
        <a href="${pageContext.request.contextPath}/locomotives/create">Add New Locomotive</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Number</th>
                <th>Model</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="locomotive" items="${locomotives}">
                <tr>
                    <td>${locomotive.id}</td>
                    <td>${locomotive.number}</td>
                    <td>${locomotive.model}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/locomotives/${locomotive.id}">View</a>
                        <a href="${pageContext.request.contextPath}/locomotives/${locomotive.id}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/locomotives/${locomotive.id}/delete" onclick="return confirm('Are you sure you want to delete this locomotive?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty locomotives}">
        <p>No locomotives found.</p>
    </c:if>
</body>
</html>