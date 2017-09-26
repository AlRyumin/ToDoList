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
      <form class="form-inline" method="post" action="${pageContext.request.contextPath}/categories">
        <div class="input-group mb-2 mr-sm-2 mb-sm-0">
          <div class="input-group-addon">Name:</div>
          <input type="text" name ="name" class="form-control" id="inlineFormInputGroup" placeholder="Category Name">
        </div>
        <input type="hidden" name="action" value="add">
        <button type="submit" class="btn btn-primary">Add</button>
      </form>
      <c:if test="categories">
        Categories
      </c:if>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>