/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.filter;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import main.db.ConnectionUtils;
import main.utils.Utils;

/**
 *
 * @author root
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
          Connection conn = null;
      try {
        // Create a Connection.
        conn = ConnectionUtils.getConnection();
        // Set outo commit to false.
        conn.setAutoCommit(false);

        // Store Connection object in attribute of request.
        Utils.setConnection(request, conn);

        // Allow request to go forward
        // (Go to the next filter or target)
        chain.doFilter(request, response);

        // Invoke the commit() method to complete the transaction with the DB.
        conn.commit();
      } catch (Exception e) {
        e.printStackTrace();
        ConnectionUtils.rollbackQuietly(conn);
        throw new ServletException();
      } finally {
        ConnectionUtils.closeQuietly(conn);
      }
  }

  @Override
  public void destroy() {

  }

}
