<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Details - Railways Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Train Details</h1>

    <jsp:include page="/WEB-INF/jspf/navbar.jspf"/>

     <div class="details">
         <p><strong>ID:</strong> ${train.id}</p>
         <p><strong>Number:</strong> ${train.number}</p>
         <p><strong>Train Composition ID:</strong> ${train.trainCompositionId}</p>
     </div>

     <c:if test="${not empty composition}">
         <div class="details">
             <p><strong>Locomotive ID:</strong> ${composition.locomotiveId}</p>
             <p><strong>Carriage IDs:</strong></p>
             <ul>
                 <c:forEach var="carriageId" items="${composition.carriageIds}">
                     <li>${carriageId}</li>
                 </c:forEach>
             </ul>
         </div>
      </c:if>

      <c:if test="${not empty schedule}">
          <h2>Schedule</h2>
          <table>
              <thead>
                  <tr>
                      <th>Order</th>
                      <th>Station</th>
                      <th>Arrival Time</th>
                      <th>Departure Time</th>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach var="entry" items="${schedule}">
                      <tr>
                          <td>${entry.order}</td>
                          <td>${entry.station}</td>
                          <td>${entry.arrival}</td>
                          <td>${entry.departure}</td>
                      </tr>
                  </c:forEach>
              </tbody>
          </table>
      </c:if>

      <div class="actions">
        <a href="${pageContext.request.contextPath}/trains/${train.id}/edit">Edit</a>
        <a href="${pageContext.request.contextPath}/trains/${train.id}/delete" onclick="return confirm('Are you sure you want to delete this train?')">Delete</a>
    </div>
</body>
</html>