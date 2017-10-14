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

      <c:if test="${category != null}">
        <div id="category">
          <form method="post" action="" class="form-inline">
            <div class="input-group mb-2 mr-sm-2 mb-sm-0">
              <div class="input-group-addon">Name:</div>
              <input type="text" name ="name" class="form-control" id="name" value="${category.name}">
            </div>

            <c:if test="${categories != null}">
              <div class="input-group-addon">Parent:</div>
              <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="parent" name="parent">
                <option value="0" <c:if test="${category.parentId == 0}">selected="selected"</c:if>>No Parent</option>
                <c:forEach var="parentCategory" items="${categories}">
                  <option value="<c:out value="${parentCategory.id}"/>"
                          <c:if test="${category.parentId == parentCategory.id}">selected="selected"</c:if>>
                    <c:out value="${parentCategory.name}"/>
                  </option>
                </c:forEach>
              </select>
            </c:if>
            <input type="hidden" name="id" value="${category.id}">
            <input type="submit" name="action" value="update" />
            <input type="submit" name="action" value="delete" />
          </form>
        </div>
      </c:if>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>