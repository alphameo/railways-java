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
        Train ID: ${train.id}

        <input type="hidden" name="id" value="${train.id}">

        <label for="number">Number:</label>
        <input type="text" id="number" name="number" value="${train.number}" required>

        <label for="trainCompositionId">Train Composition ID:</label>
        <select id="trainCompositionId" name="trainCompositionId" required>
            <option value="">-- Select Composition --</option>
            <c:forEach var="comp" items="${compositions}">
                <option value="${comp.id}" data-summary="${comp.summary}" ${comp.id == train.trainCompositionId ? 'selected' : ''}>${comp.id}</option>
            </c:forEach>
        </select>
        <div class="details" id="compositionSummary">No data</div>

        <button type="submit">Update Train</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>
        addListenerToTrainCompoSummary();
        updateSummary();
    </script>
</body>
</html>
