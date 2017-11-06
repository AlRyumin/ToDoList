<%@page import="main.utils.Constants"%>
<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="${baseURL}">
      <i class="fa fa-tasks"></i>
      To Do List
    </a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <c:set var="cur_url" value="${requestScope['javax.servlet.forward.servlet_path']}"></c:set>
        <c:set var="is_home" value="${Constants.URL_HOME == cur_url || Constants.URL_HOME_FULL == cur_url || null == cur_url}"></c:set>
          <li class="nav-item <c:out value="${is_home ? 'active' : ''}"/>">
          <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_HOME}">Home</a>
        </li>

        <c:choose>
          <c:when test="${not empty sessionScope.USER_INFO}">
            <li class="nav-item <c:out value="${Constants.URL_TASK == cur_url ? 'active' : ''}"/>">
              <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_TASK}">Tasks</a>
            </li>
            <li class="nav-item <c:out value="${Constants.URL_CATEGORIES == cur_url ? 'active' : ''}"/>">
              <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_CATEGORIES}">Categories</a>
            </li>
            <li class="nav-item">
              <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_LOGOUT}">LogOut</a>
            </li>
          </c:when>
          <c:otherwise>
            <li class="nav-item <c:out value="${Constants.URL_LOGIN == cur_url ? 'active' : ''}"/>">
              <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_LOGIN}">Login</a>
            </li>
            <li class="nav-item <c:out value="${Constants.URL_REGISTER == cur_url ? 'active' : ''}"/>">
              <a class="nav-link waves-effect waves-light" href="${pageContext.request.contextPath}${Constants.URL_REGISTER}">Register</a>
            </li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>