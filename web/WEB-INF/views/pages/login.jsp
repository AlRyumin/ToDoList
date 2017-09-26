<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"/>
    <title>Login</title>
  </head>
  <body>
    <jsp:include page="../parts/_header.jsp"/>
    <div class="container content">
      <h1>Login</h1>
      <div class="alert-danger notice">
        ${errorLogin}
      </div>
      <form method="post" action="">
        <div>
          Email: <br>
          <input type="text" name="email"/>
        </div>
        <div>
          Password <br>
          <input type="password" name="password"/>
        </div>
        <br>
        <button type="submit">Login</button>
      </form>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>
