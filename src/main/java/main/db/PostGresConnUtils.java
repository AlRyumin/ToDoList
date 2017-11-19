package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostGresConnUtils {

  public static Connection getPostGresConnection()
          throws ClassNotFoundException, SQLException {
    String hostName = "localhost";
    String dbName = "to_do_list";
    String userName = "root";
    String password = "root";
    return getPostGresConnection(hostName, dbName, userName, password);
  }

  public static Connection getPostGresConnection(String hostName, String dbName,
          String userName, String password) throws SQLException,
          ClassNotFoundException {

    Class.forName("org.postgresql.Driver");

//    String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String connectionURL = "jdbc:postgresql://" + hostName + ":5432/"  + dbName;

    Connection conn = DriverManager.getConnection(connectionURL, userName,
            password);
    return conn;
  }
}
