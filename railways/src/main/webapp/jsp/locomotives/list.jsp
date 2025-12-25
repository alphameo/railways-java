<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locomotives - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Locomotives</h1>


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
                        <button onclick="delFrom('${locomotive.id}', 'locomotives')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty locomotives}">
        <p>No locomotives found.</p>
    </c:if>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
