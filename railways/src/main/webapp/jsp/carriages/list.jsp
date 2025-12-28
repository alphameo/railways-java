<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carriages - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Carriages</h1>


    <div class="add-button">
        <a href="${pageContext.request.contextPath}/carriages/create">Add New Carriage</a>
    </div>

    <div class="page-size-selector">
        <label for="pageSize">Items per page: </label>
        <select id="pageSize" onchange="changePageSize(this.value)">
            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
            <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
            <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
        </select>
    </div>

    <table>
        <thead>
            <tr>
                <th>Number</th>
                <th>Content Type</th>
                <th>Capacity</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="carriage" items="${carriages}">
                <tr>
                    <td>${carriage.number}</td>
                    <td>${carriage.contentType}</td>
                    <td>${carriage.capacity}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/carriages/${carriage.id}">View</a>
                        <a href="${pageContext.request.contextPath}/carriages/${carriage.id}/edit">Edit</a>
                        <button onclick="delFrom('${carriage.id}', 'carriages')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty carriages}">
        <p>No carriages found.</p>
    </c:if>

    <div class="pagination">
        <p>Showing ${currentPage * pageSize + 1} to ${currentPage * pageSize + carriages.size()} of ${totalElements} entries</p>
        <c:if test="${currentPage > 0}">
            <a href="?page=${currentPage - 1}&size=${pageSize}">Previous</a>
        </c:if>
        <c:forEach var="i" begin="${currentPage - 2 > 0 ? currentPage - 2 : 0}" end="${currentPage + 2 < totalPages - 1 ? currentPage + 2 : totalPages - 1}">
            <c:if test="${i >= 0 && i < totalPages}">
                <a href="?page=${i}&size=${pageSize}" ${i == currentPage ? 'class="current"' : ''}>${i + 1}</a>
            </c:if>
        </c:forEach>
        <c:if test="${currentPage < totalPages - 1}">
            <a href="?page=${currentPage + 1}&size=${pageSize}">Next</a>
        </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>
        function changePageSize(size) {
            window.location.href = '?page=0&size=' + size;
        }
    </script>
</body>
</html>
