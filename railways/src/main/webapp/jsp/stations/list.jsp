<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Stations - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Stations</h1>


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
                        <button onclick="delFrom('${station.id}', 'stations')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty stations}">
        <p>No stations found.</p>
    </c:if>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
