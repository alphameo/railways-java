<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carriages - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Carriages</h1>


    <div class="add-button">
        <a href="${pageContext.request.contextPath}/carriages/create">Add New Carriage</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Number</th>
                <th>Content Type</th>
                <th>Capacity</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="carriage" items="${carriages}">
                <tr>
                    <td>${carriage.number}</td>
                    <td>${carriage.contentType}</td>
                    <td>${carriage.capacity}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/carriages/${carriage.id}">View</a>
                        <a href="${pageContext.request.contextPath}/carriages/${carriage.id}/edit">Edit</a>
                        <button onclick="delFrom('${carriage.id}', 'carriages')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty carriages}">
        <p>No carriages found.</p>
    </c:if>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
