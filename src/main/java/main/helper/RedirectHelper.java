package main.helper;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectHelper {
  public static void continueWithError(HttpServletRequest request, HttpServletResponse response, String error, String path) throws ServletException, IOException{
    request.setAttribute("error", error);
    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(path);
    dispatcher.forward(request, response);
  }
}
