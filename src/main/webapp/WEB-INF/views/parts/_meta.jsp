<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.utils.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cur_url" value="${requestScope['javax.servlet.forward.servlet_path']}"></c:set>

  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/src/css/style.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/src/css/botosrap/bootstrap.min.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/src/css/jquery/jquery-ui.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/src/css/selectize.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/src/css/font-awesome/font-awesome.min.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/src/css/daterangepicker/daterangepicker.min.css" />" />

<script src='<c:url value="/src/js/jquery/jquery-3.2.1.min.js" />'></script>
<script src='<c:url value="/src/js/jquery/jquery-ui.min.js" />'></script>
<script src='<c:url value="/src/js/bootsrap/bootstrap.min.js" />'></script>
<script src='<c:url value="/src/js/selectize.min.js" />'></script>
<script src='<c:url value="/src/js/moment.min.js" />'></script>
<script src='<c:url value="/src/js/daterangepicker/jquery.daterangepicker.min.js" />'></script>

<script src='<c:url value="/src/js/main.js" />'></script>

<c:if test="${Constants.URL_TASK == cur_url}" >
  <script src='<c:url value="/src/js/pages/show_tasks.js" />'></script>
</c:if>
