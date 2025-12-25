<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Train Composition - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Train Composition</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

    <form action="${pageContext.request.contextPath}/train-compositions" method="post">
        <input type="hidden" name="id" value="${trainComposition.id}">

        <label for="locomotiveId">Locomotive:</label>
        <select id="locomotiveId" name="locomotiveId" required>
            <option value="">-- Select Locomotive --</option>
            <c:forEach var="locomotive" items="${locomotives}">
                <option value="${locomotive.id}" ${locomotive.id == trainComposition.locomotiveId ? 'selected' : ''}>${locomotive.number} (${locomotive.model})</option>
            </c:forEach>
        </select>

        <label>Carriage Positions:</label>
        <div id="carriageOrder">
            <c:forEach var="carriageId" items="${trainComposition.carriageIds}" varStatus="status">
                <div class="position">
                    <label>Position ${status.index + 1}:</label>
                    <select name="position${status.index}">
                        <option value="">-- Select Carriage --</option>
                        <c:forEach var="carriage" items="${carriages}">
                            <option value="${carriage.id}" ${carriage.id == carriageId ? 'selected' : ''}>${carriage.number} (${carriage.contentType})</option>
                        </c:forEach>
                    </select>
                    <button type="button" onclick="removePosition(${status.index})">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addPosition()">Add Position</button>
        <p>Note: Select carriages for each position. Use Add/Remove to adjust the number of positions.</p>

        <button type="submit">Update Train Composition</button>
    </form>

    <div id="carriageOptions" style="display: none;">
        <c:forEach var="carriage" items="${carriages}">
            <option value="${carriage.id}">${carriage.number} (${carriage.contentType})</option>
        </c:forEach>
    </div>

    <script>
        let positionCount = ${trainComposition.carriageIds.size()};

        function addPosition() {
            const container = document.getElementById('carriageOrder');
            const div = document.createElement('div');
            div.className = 'position';
            const options = document.getElementById('carriageOptions').innerHTML;
            div.innerHTML = `
                <label>Position ${positionCount + 1}:</label>
                <select name="position${positionCount}">
                    <option value="">-- Select Carriage --</option>
                    ${options}
                </select>
                <button type="button" onclick="removePosition(${positionCount})">Remove</button>
            `;
            container.appendChild(div);
            positionCount++;
        }

        function removePosition(index) {
            const container = document.getElementById('carriageOrder');
            const positions = container.querySelectorAll('.position');
            if (positions.length > 0) {
                positions[index].remove();
                // Renumber the remaining positions
                const remaining = container.querySelectorAll('.position');
                remaining.forEach((pos, i) => {
                    pos.querySelector('label').textContent = `Position ${i + 1}:`;
                    pos.querySelector('select').name = `position${i}`;
                    pos.querySelector('button').onclick = () => removePosition(i);
                });
                positionCount--;
            }
        }
    </script>
</body>
</html>