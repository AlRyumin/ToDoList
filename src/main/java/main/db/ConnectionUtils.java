/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.db;

import java.sql.Connection;
import java.sql.SQLException;
import static main.utils.Constants.*;

/**
 *
 * @author root
 */
public class ConnectionUtils {

  public static Connection getConnection()
          throws ClassNotFoundException, SQLException {

    if (USE_POSTGRES_DB)
      return PostGresConnUtils.getPostGresConnection();
    else
      return MySQLConnUtils.getMySQLConnection();
    // return OracleConnUtils.getOracleConnection);
    // return OracleConnUtils.getOracleConnection();
    // return SQLServerConnUtils_JTDS.getSQLServerConnection_JTDS();
    // return SQLServerConnUtils_SQLJDBC.getSQLServerConnection_SQLJDBC();
  }

  public static void closeQuietly(Connection conn) {
    try {
      conn.close();
    } catch (Exception e) {
    }
  }

  public static void rollbackQuietly(Connection conn) {
    try {
      conn.rollback();
    } catch (Exception e) {
    }
  }
}
