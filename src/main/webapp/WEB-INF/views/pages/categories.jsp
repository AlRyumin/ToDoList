<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="main.helper.category.CategoryNode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.utils.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
  public void printNodes(TreeMap<Integer, CategoryNode> nodes, JspWriter out, HttpServletRequest request)
  {
    try {
      out.print("<ul>");
      for (Map.Entry<Integer, CategoryNode> node : nodes.entrySet()) {
        CategoryNode val = node.getValue();
        out.print("<li>");
        out.print("<form method='post' action='" + request.getContextPath() + Constants.URL_CATEGORY + "'>");
        out.print("<div class='title'>");
        if (val.hasChildren())
          out.print("<i class='fa fa-chevron-down'></i>");
        out.print(val.getCategory().getName());
        out.print("<button type='submit' class='btn btn-primary'><i class='fa fa-edit'></i></button>  ");
        out.print("</div>");
        out.print("<input type='hidden' name='id' value='" + val.getCategory().getId() + "'>");
        out.print("</form>");
        if (val.hasChildren())
          printNodes(val.getChildren(), out, request);
        out.print("</li>");
      }
      out.print("</ul>");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Categories</title>
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
        <c:if test="${listNodes != null}">
          <div class="input-group-addon">Parent:</div>
          <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="parent" name="parent">
            <option value="0" selected="selected">No Parent</option>
            <c:forEach var="node" items="${listNodes}">
              <option value="<c:out value="${node.category.id}"/>"><c:out value="${node.category.name}"/></option>
            </c:forEach>
          </select>
        </c:if>
        <button type="submit" class="btn btn-primary">Add</button>
      </form>
      <div id="categories">
        <c:if test="${nodes != null}">
          <%
            TreeMap<Integer, CategoryNode> nodes = (TreeMap<Integer, CategoryNode>) request.getAttribute("nodes");
            printNodes(nodes, out, request);
          %>
          <ul>
            <%--
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
            --%>
          </ul>
        </div>
      </c:if>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>