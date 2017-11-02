<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.db.model.Category" %>
<%@page import="main.db.model.TaskType" %>
<%@page import="main.db.model.TaskStatus" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Task Edit</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
      <h3>Edit task</h3>
      <form method="post" action="${pageContext.request.contextPath}${Constants.URL_TASK_EDIT}">
        <div class="form-group row">
          <label for="name" class="col-2 col-form-label">Task:</label>
          <div class="col-10">
            <input type="text" name="name" class="form-control" id="name" placeholder="Task" value='${task.name}'>
          </div>
        </div>

        <div class="form-group row">
          <label for="description" class="col-2 col-form-label">Description:</label>
          <div class="col-10">
            <textarea class="form-control" name="description" rows="3">${task.description}</textarea>
          </div>
        </div>

        <c:if test="${categories != null}">
          <div class="form-group row">
            <label for="category" class="col-2 col-form-label">Category:</label>
            <div class="col-10">
              <select id="select-category" name="category" >
                <c:forEach var="category" items="${categories}">
                  <option value="${category.id}" <c:if test="${task.category.id == category.id}">selected="selected"</c:if>>${category.name}</option>
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
                  <option value="${priority}" <c:if test="${task.priority.equals(priority)}">selected="selected"</c:if>>${priority}</option>
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
                  <option value="${type}" <c:if test="${task.type.equals(type)}">selected="selected"</c:if>>${type}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </c:if>

        <c:if test="${statuses != null}">
          <div class="form-group row">
            <label for="type" class="col-2 col-form-label">Type</label>
            <div class="col-10">
              <select class="custom-select mb-1 mr-sm-1 mb-sm-0" name="status">
                <c:forEach var="status" items="${statuses}">
                  <option value="${status}" <c:if test="${task.status.equals(status)}">selected="selected"</c:if>>${status}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </c:if>

        <div class="form-group row">
          <label for="due_date" class="col-2 col-form-label">Due date</label>
          <div class="col-3">
            <div class="input-group">
              <input type="text" id="datepicker" name="due_date" class="form-control" value='${task.dueDate}'>
              <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
            </div>
          </div>
        </div>

        <input type="hidden" name="id" value="${pageContext.request.getParameter("id")}"/>
        <button type="submit" class="btn btn-primary">Update</button>
      </form>
    </div>
  </body>
</html>