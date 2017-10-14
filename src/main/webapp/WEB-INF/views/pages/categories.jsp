<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Home</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
      <c:if test="${error != null}">
        <div class="notice alert-danger">
          ${error}
        </div>
      </c:if>
      <form class="form-inline" method="post" action="${pageContext.request.contextPath}${Constants.URL_CATEGORIES}">
        <h3 class="input-group mb-2 mr-sm-2 mb-sm-0">Create new: </h3>
        <div class="input-group mb-2 mr-sm-2 mb-sm-0">
          <div class="input-group-addon">Name:</div>
          <input type="text" name ="name" class="form-control" id="name" placeholder="Category Name">
        </div>
        <c:if test="${nodes != null}">
          <div class="input-group-addon">Parent:</div>
          <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="parent" name="parent">
            <option value="0" selected="selected">No Parent</option>
            <c:forEach var="node" items="${nodes}">
              <option value="<c:out value="${node.category.id}"/>"><c:out value="${node.category.name}"/></option>
            </c:forEach>
          </select>
        </c:if>
        <button type="submit" class="btn btn-primary">Add</button>
      </form>
      <c:if test="${nodes != null}">
        <div id="categories">
          <c:forEach var="node" items="${nodes}">
            <div  class="category category-level-${node.level}">
              <form method="post" action="${pageContext.request.contextPath}${Constants.URL_CATEGORY}">
                <div class="row">
                  <div class="col-md-9">
                    <div class="title">
                      <c:if test="${node.level > 0}">-</c:if>
                      <c:out value="${node.category.name}"/>
                    </div>
                  </div>
                  <input type="hidden" name="id" value="<c:out value="${node.category.id}"/>">
                  <div class="col-md-3">
                    <button type="submit" class="btn btn-primary">Edit</button>
                  </div>
                </div>
              </form>
            </div>
          </c:forEach>
        </div>
      </c:if>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>