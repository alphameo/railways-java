<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trains - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Trains</h1>


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