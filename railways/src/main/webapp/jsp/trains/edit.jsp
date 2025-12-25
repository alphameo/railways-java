<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Train - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Train</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/trains" method="post">
        <input type="hidden" name="id" value="${train.id}">

        <label for="number">Number:</label>
        <input type="text" id="number" name="number" value="${train.number}" required>

        <label for="trainCompositionId">Train Composition ID:</label>
        <input type="text" id="trainCompositionId" name="trainCompositionId" value="${train.trainCompositionId}" required>

        <button type="submit">Update Train</button>
    </form>
</body>
</html>