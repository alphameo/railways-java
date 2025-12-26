<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Train Composition - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Train Composition</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/train-compositions" method="post">
        <label for="locomotiveId">Locomotive:</label>
        <select id="locomotiveId" name="locomotiveId" required>
            <option value="">-- Select Locomotive --</option>
            <c:forEach var="locomotive" items="${locomotives}">
                <option value="${locomotive.id}">${locomotive.number} (${locomotive.id})</option>
            </c:forEach>
        </select>

        <label>Carriage Positions:</label>
        <div id="carriageOrder">
            <div class="position">
                <label>Position 1:</label>
                <select name="position0">
                    <option value="">-- Select Carriage --</option>
                    <c:forEach var="carriage" items="${carriages}">
                        <option value="${carriage.id}">${carriage.number} (${carriage.id}) - ${carriage.contentType}</option>
                    </c:forEach>
                </select>
                <button type="button" onclick="removeCompositionPosition(0)">Remove</button>
            </div>
        </div>
        <button type="button" onclick="addCompositionPosition()">Add Position</button>
        <p>Note: Select carriages for each position. Use Add/Remove to adjust the number of positions.</p>

        <button type="submit">Add Train Composition</button>
    </form>

    <div id="carriageOptions" style="display: none;">
        <c:forEach var="carriage" items="${carriages}">
            <option value="${carriage.id}">${carriage.number} (${carriage.id}) - ${carriage.contentType}</option>
        </c:forEach>
    </div>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>loadPosCount(${trainComposition.carriageIds.size()})</script>
</body>
</html>
