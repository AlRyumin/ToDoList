<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="header">
  <div class="container">
    <div class="logo">
      To Do List
    </div>
  </div>
  <div class="container">
    <jsp:include page="_menu.jsp"/>
  </div>
</header>
<c:if test="${error != null}">
  <div class="notice alert-danger">
    ${error}
  </div>
</c:if>