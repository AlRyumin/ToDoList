package main.helper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import static main.utils.Constants.DATE_FORMAT;

public class DateHelper {

  public static Date dateStringToSqlDate(String dateString)
  {
    Date sqlDate = null;
    try {
      SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
      java.util.Date date = format.parse(dateString);
      sqlDate = new Date(date.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sqlDate;
  }

  public static String sqlDateToString(Date sqlDate)
  {
    String date = "";
    try {
      DateFormat format = new SimpleDateFormat(DATE_FORMAT);
      date = format.format(sqlDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date getCurrentSqlDate(){

    return null;
  }

  public static String getCurrentDateString(){
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    String date = format.format(new java.util.Date());
    return date;
  }
}
