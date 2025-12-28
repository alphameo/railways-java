<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Line - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Line</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/lines" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>

        <label>Station Order:</label>
        <div id="stationOrder">
            <div class="position">
                <label>Position 1:</label>
                <select name="position0">
                    <option value="">-- Select Station --</option>
                    <c:forEach var="station" items="${stations}">
                        <option value="${station.id}">${station.name} (${station.id})</option>
                    </c:forEach>
                </select>
                <button type="button" onclick="removePosition(0)">Remove</button>
            </div>
        </div>
        <button type="button" onclick="addPosition()">Add Position</button>
        <p>Note: Select stations for each position. Use Add/Remove to adjust the number of positions.</p>

        <button type="submit">Add Line</button>
    </form>

    <div id="stationOptions" style="display: none;">
        <c:forEach var="station" items="${stations}">
            <option value="${station.id}">${station.name} (${station.id})</option>
        </c:forEach>
    </div>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>loadPosCount(1)</script>
</body>
</html>
