<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.db.model.TaskType" %>
<%@page import="main.db.model.TaskStatus" %>
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
      <div id="tasks">
        <div>
          <a class="btn btn-primary" href="${pageContext.request.contextPath}${Constants.URL_TASK_ADD}">Add New</a>
        </div>
        <div class="filters">
          <h3 class="d-md-none">Filters <i class="fa fa-chevron-down"></i></h3>
          <div class="filters-content">
            <form class="form-inline" method="get" action="${pageContext.request.contextPath}${Constants.URL_TASK}">
              <h3 class="input-group mb-2 mr-sm-2 mb-sm-0 d-none d-md-block">Filters:</h3>
                <c:if test="${categories != null}">
                <div class="mb-2 mr-sm-2 mb-sm-0">
                  <label for="category">Category</label>
                  <select id="select-category" name="category">
                    <option value="" <c:if test="${currentCategory == null}">selected="selected"</c:if>>All</option>
                    <c:forEach var="category" items="${categories}">
                      <option value="${category.id}" <c:if test="${currentCategory.equals(category.id.toString())}">selected="selected"</c:if>>${category.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>

              <c:if test="${priorities != null}">
                <div class="mb-2 mr-sm-2 mb-sm-0">
                  <label for="priority">Priority</label>
                  <select id="select-priority" name="priority" class="custom-select">
                    <option value="" <c:if test="${currentPriority.isEmpty()}">selected="selected"</c:if>>All</option>
                    <c:forEach var="priority" items="${priorities}">
                      <option value="${priority}" <c:if test="${currentPriority.equalsIgnoreCase(priority)}">selected="selected"</c:if>>${priority}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>

              <c:if test="${types != null}">
                <div class="mb-2 mr-sm-2 mb-sm-0">
                  <label for="type">Type</label>
                  <select id="select-type" name="type" class="custom-select">
                    <option value="" <c:if test="${currentType.isEmpty()}">selected="selected"</c:if>>All</option>
                    <c:forEach var="type" items="${types}">
                      <option value="${type}"<c:if test="${currentType.equalsIgnoreCase(type)}">selected="selected"</c:if>>${type}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>

              <c:if test="${statuses != null}">
                <div class="mb-2 mr-sm-2 mb-sm-0">
                  <label for="type">Status</label>
                  <select id="select-status" name="status" class="custom-select">
                    <option value="" <c:if test="${currentStatus.isEmpty()}">selected="selected"</c:if>>All</option>
                    <c:forEach var="status" items="${statuses}">
                      <option value="${status}"<c:if test="${currentStatus.equalsIgnoreCase(status)}">selected="selected"</c:if>>${status}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>

              <div class="mb-2 mr-sm-2 mb-sm-0">
                <label for="type">Due Date</label>
                <div class="input-group">
                  <input type="text" id="task-datepicker" name="due_date" class="form-control" value="${due_date}">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                </div>
              </div>
              <button class="btn btn-secondary">Apply</button>
            </form>
          </div>
        </div>
        <c:if test="${tasks != null}">
          <form>
            <c:forEach var="task" items="${tasks}">
              <div id="task-${task.id}" class="task<c:if test="${task.status.equals(TaskStatus.DONE)}"> done</c:if>">
                  <div class="main-content">
                    <div class="row">
                      <div class="col-md-10">
                        <div class="title">
                        ${task.name}
                      </div>
                    </div>
                    <div class="col-md-2">
                      <a class="btn complete" href="${pageContext.request.contextPath}${Constants.URL_TASK_COMPLETE}?id=${task.id}"><i class="fa fa-check-square-o"></i></a>
                      <span class="btn details" href="#"><i class="fa fa-info-circle"></i></span>
                      <a class="btn" href="${pageContext.request.contextPath}${Constants.URL_TASK_EDIT}?id=${task.id}"><i class="fa fa-pencil-square-o"></i></a>
                    </div>
                  </div>
                </div>
                <div class="details-content">
                  <div class="row">
                    <div class="col-md-8">
                      <div class="description">
                        ${task.description}
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="row">
                        <div class="col-sm-4">
                          <div class="cap">
                            Category:
                          </div>
                        </div>
                        <div class="col-sm-8">
                          ${task.category.name}
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-4">
                          <div class="cap">
                            Priority:
                          </div>
                        </div>
                        <div class="col-sm-8">
                          ${task.priority}
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-4">
                          <div class="cap">
                            Type:
                          </div>
                        </div>
                        <div class="col-sm-8">
                          ${task.type}
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-4">
                          <div class="cap">
                            Due Date:
                          </div>
                        </div>
                        <div class="col-sm-8">
                          ${task.dueDate}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </form>
        </c:if>

      </div>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>