<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lines - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Lines</h1>


    <div class="add-button">
        <a href="${pageContext.request.contextPath}/lines/create">Add New Line</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Station ID Order</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="line" items="${lines}">
                <tr>
                    <td>${line.id}</td>
                    <td>${line.name}</td>
                    <td>${line.stationIdOrder}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/lines/${line.id}">View</a>
                        <a href="${pageContext.request.contextPath}/lines/${line.id}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/lines/${line.id}/delete" onclick="return confirm('Are you sure you want to delete this line?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty lines}">
        <p>No lines found.</p>
    </c:if>
</body>
</html>