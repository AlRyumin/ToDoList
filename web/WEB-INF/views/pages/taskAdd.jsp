<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Add Task</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
      <h3>Create task</h3>
      <form method="post" action="${pageContext.request.contextPath}${Constants.URL_TASK_ADD}">
        <div class="form-group row">
          <label for="name" class="col-2 col-form-label">Task:</label>
          <div class="col-10">
            <input type="text" name="name" class="form-control" id="name" placeholder="Task">
          </div>
        </div>

        <div class="form-group row">
          <label for="description" class="col-2 col-form-label">Description:</label>
          <div class="col-10">
            <textarea class="form-control" name="description" rows="3"></textarea>
          </div>
        </div>

        <c:if test="${categories != null}">
          <div class="form-group row">
            <label for="category" class="col-2 col-form-label">Category:</label>
            <div class="col-10">
              <select id="select-category" name="category" >
                <c:forEach var="category" items="${categories}">
                  <option value="${category.id}">${category.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </c:if>

        <c:if test="${priorities != null}">
          <div class="form-group row">
            <label for="priority" class="col-2 col-form-label">Priority:</label>
            <div class="col-10">
              <select class="custom-select mb-2 mr-sm-2 mb-sm-0" name="priority">
                <c:forEach var="priority" items="${priorities}">
                  <option value="${priority}">${priority}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </c:if>

        <c:if test="${types != null}">
          <div class="form-group row">
            <label for="type" class="col-2 col-form-label">Type</label>
            <div class="col-10">
              <select class="custom-select mb-1 mr-sm-1 mb-sm-0" name="type">
                <c:forEach var="type" items="${types}">
                  <option value="${type}">${type}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </c:if>

        <div class="form-group row">
          <label for="due_date" class="col-2 col-form-label">Due date</label>
          <div class="col-3">
            <div class="input-group">
              <input type="text" id="datepicker" name="due_date" class="form-control">
              <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
            </div>
          </div>
        </div>
        <button type="submit" class="btn btn-primary">Add</button>
      </form>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>