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
        <select id="trainCompositionId" name="trainCompositionId" required>
            <option value="">-- Select Composition --</option>
            <c:forEach var="comp" items="${compositions}">
                <option value="${comp.id}" data-summary="${comp.summary}">${comp.id}</option>
            </c:forEach>
        </select>

        <div class ="details" id="compositionSummary"></div>

        <button type="submit">Add Train</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/scripts.js">addListenerToTrainCompoSummary()</script>
</body>
</html>
