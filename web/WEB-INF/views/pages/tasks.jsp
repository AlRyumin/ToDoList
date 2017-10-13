<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Tasks</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
      <a class="button" href="${pageContext.request.contextPath}${Constants.URL_TASK_ADD}">Add New</a>
      <c:if test="${tasks != null}">
        <c:forEach var="task" items="${tasks}">
          <div id="task-${task.id}">
            ${task.name}
            ${task.description}
            ${task.category.name}
            ${task.priority}
            ${task.type}
            ${task.status}
            ${task.dueDate}
          </div>
        </c:forEach>
      </c:if>

    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>