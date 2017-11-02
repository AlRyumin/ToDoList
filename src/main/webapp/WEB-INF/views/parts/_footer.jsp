<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />

<footer class="footer">
  <div class="container">
    <i class="fa fa-copyright"></i> <c:out value="${currentYear}" /> LaRyumin
  </div>
</footer>