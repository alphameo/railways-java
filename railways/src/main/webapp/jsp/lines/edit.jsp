<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Line - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Line</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/lines" method="post">
        Line ID: ${line.id}

        <input type="hidden" name="id" value="${line.id}">

        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${line.name}" required>

        <label>Station Order:</label>
        <div id="stationOrder">
            <c:forEach var="stationId" items="${line.stationIdOrder}" varStatus="status">
                <div class="position">
                    <label>Position ${status.index + 1}:</label>
                    <select name="position${status.index}">
                        <option value="">-- Select Station --</option>
                        <c:forEach var="station" items="${stations}">
                            <option value="${station.id}" ${station.id == stationId ? 'selected' : ''}>${station.name} (${station.id})</option>
                        </c:forEach>
                    </select>
                    <button type="button" onclick="removePosition(${status.index})">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addPosition()">Add Position</button>
        <p>Note: Select stations for each position. Use Add/Remove to adjust the number of positions.</p>

        <button type="submit">Update Line</button>
    </form>

    <div id="stationOptions" style="display: none;">
        <c:forEach var="station" items="${stations}">
            <option value="${station.id}">${station.name} (${station.id})</option>
        </c:forEach>
    </div>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>loadPosCount(${line.stationIdOrder.size()})</script>
</body>
</html>
