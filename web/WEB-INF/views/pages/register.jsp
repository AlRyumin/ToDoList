<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../parts/_meta.jsp"></jsp:include>
      <title>Register</title>
    </head>
    <body>
    <jsp:include page="../parts/_header.jsp"></jsp:include>
      <div class="container">

        <h1>Register</h1>

        <div class="notice alert-danger">
        ${errorRegister}
      </div>
    </div>
    <div class="container content">
      <form method="POST" action="${pageContext.request.contextPath}/register">
        <div>
          Name: <br>
          <input type="text" name="name" placeholder="name"/>
        </div>
        <div>
          Email: <br>
          <input type="email" name="email" placeholder="email"/>
        </div>
        <div>
          Password: <br>
          <input type="password" name="password" placeholder="password"/>
        </div>
        <div>
          Repeat password: <br>
          <input type="password" name="password_repeat" placeholder="repeat password"/>
        </div>
        <br>
        <button type="submit">Sign Up</button>
      </form>
    </div>
    <jsp:include page="../parts/_footer.jsp"></jsp:include>
  </body>
</html>
