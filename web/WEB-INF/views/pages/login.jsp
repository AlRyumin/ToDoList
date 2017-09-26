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
        <div class="form-group">
          <label for="email">
            Email<span class="required">*</span>
          </label>
          <input class="form-control" type="text" id="email" name="email" required="required"/>
        </div>
        <div class="form-group">
          <label for="password">
            Password <span class="required">*</span>
          </label>
          <input class="form-control" type="password" name="password" id="password" required="required"/>
        </div>
        <div class="form-check">
          <label class="form-check-label">
            <input type="checkbox" name="remember" value="1" class="form-check-input">
            Remember me
          </label>
        </div>
        <button type="submit" class="btn btn-primary">Login</button>
      </form>
    </div>
    <jsp:include page="../parts/_footer.jsp"/>
  </body>
</html>
