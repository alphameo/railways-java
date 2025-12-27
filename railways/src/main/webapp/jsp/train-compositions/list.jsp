<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Compositions - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Train Compositions</h1>


    <div class="add-button">
        <a href="${pageContext.request.contextPath}/train-compositions/create">Add New Train Composition</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Locomotive</th>
                <th>Carriages</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="trainComposition" items="${enrichedCompositions}">
                <tr>
                    <td>${trainComposition.id}</td>
                    <td>${trainComposition.locomotiveInfo}</td>
                    <td>${trainComposition.carriagesInfo}</td>
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