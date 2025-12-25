<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Train - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Train</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/trains" method="post">
        <label for="number">Number:</label>
        <input type="text" id="number" name="number" required>

        <label for="trainCompositionId">Train Composition ID:</label>
        <input type="text" id="trainCompositionId" name="trainCompositionId" required>

        <button type="submit">Add Train</button>
    </form>
</body>
</html>