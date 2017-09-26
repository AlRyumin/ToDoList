<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Home</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
    <div class="alert-danger notice">
      ${error}
    </div>
      <h1>Hello ${user.name}!</h1>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>
