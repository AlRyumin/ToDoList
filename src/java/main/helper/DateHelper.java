package main.helper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateHelper {

  public final static String DATE_FORMAT = "dd-MM-yyyy";

  public static Timestamp dateStringToTimeStamp(String date) {
    Timestamp timestamp = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
      Date parsedDate = (Date) dateFormat.parse(date);
      timestamp = new java.sql.Timestamp(parsedDate.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timestamp;
  }

  public static String timeStapmToString(Timestamp date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    String string = dateFormat.format(date);
    return string;
  }
}
