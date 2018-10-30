package TOOLS;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getDate
{
  private static final String A = "yyyy-MM-dd";
  private static final String B = "yyyy-MM-dd HH:mm:ss";
  private static final String C = "yyyy/MM/dd HH:mm:ss";
  private static final String exp = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";
  
  public static void main(String[] args)
    throws Exception
  {
    getTodayEarlyOneHour();
  }
  
  public static String getDateToString(Date date)
    throws Exception
  {
    SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
    String s = sft.format(date);
    return s;
  }
  
  public static String getDteToString(Date date, String format)
    throws Exception
  {
    SimpleDateFormat sft = new SimpleDateFormat(format);
    String s = sft.format(date);
    return s;
  }
  
  public static Date getStringToDate(String text)
    throws Exception
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = sdf.parse(text);
    return date;
  }
  
  public static Date gettringToDate(String text, String format)
    throws Exception
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = sdf.parse(text);
    return date;
  }
  
  public static long getDaysBetweenTwoDates(Date a, Date b)
    throws Exception
  {
    if (a.equals(b)) {
      return 0L;
    }
    if (!a.before(b))
    {
      Date temp = a;
      a = b;
      b = temp;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(a);
    long t1 = c.getTimeInMillis();
    c.setTime(b);
    long t2 = c.getTimeInMillis();
    
    long days = (t2 - t1) / 86400000L;
    return days;
  }
  
  public static Date getNextDasByNumber(Date currentDate, int distance)
    throws Exception
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(5, distance);
    Date date = calendar.getTime();
    return date;
  }
  
  public static boolean isDate(String date)
  {
    Pattern r = Pattern.compile("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
    boolean flag = r.matcher(date).matches();
    return flag;
  }
  
  public static String getDate1()
  {
    Date day = new Date();
    
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String vdate = df.format(Long.valueOf(System.currentTimeMillis())).toString();
    System.out.println(vdate);
    
    return vdate;
  }
  
  public static String getDate2()
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String vdate = "";
    vdate = df.format(Long.valueOf(System.currentTimeMillis())).toString();
    
    return vdate;
  }
  
  public static String getDate3()
  {
    Calendar c = Calendar.getInstance();
    
    int year = c.get(1);
    
    int month = c.get(2);
    
    int date = c.get(5);
    
    int hour = c.get(11);
    
    int minute = c.get(12);
    
    int second = c.get(13);
    
    System.out.println(year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second);
    String vdate = (year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second).toString();
    System.out.println(vdate);
    
    return vdate;
  }
  
  public static String getYesterday()
  {
    Date date = new Date();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    
    calendar.add(5, -1);
    
    date = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    String dateString = formatter.format(date);
    
    return dateString;
  }
  
  
  
  public static String getTomorrow()
  {
    Date date = new Date();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    
    calendar.add(5, 1);
    
    date = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    String dateString = formatter.format(date);
    
    return dateString;
  
  }
  
  public static String getYesterday(Date date)
  {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    
    calendar.add(5, -1);
    
    date = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    String dateString = formatter.format(date);
    
    return dateString;
  }
  
  public static String getTomorrow(Date date)
  {
	  Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    
	    calendar.add(5, 1);
	    
	    date = calendar.getTime();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    
	    String dateString = formatter.format(date);
	    
	    return dateString;
  
  }
  
  public static String getToday()
  {
    Date day = new Date();
    
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String vdate = df.format(Long.valueOf(System.currentTimeMillis())).toString();
    System.out.println(vdate);
    
    return vdate;
  }
  
  public static String getTodayEarlyOneHour()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(10, calendar.get(10) - 1);
    
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String vdate = df.format(calendar.getTime()).toString();
    
    return vdate;
  }
}
