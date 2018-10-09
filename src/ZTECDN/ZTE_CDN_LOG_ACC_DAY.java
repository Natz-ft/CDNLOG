package ZTECDN;

import TOOLS.DBAcess;
import TOOLS.getDate;
import java.io.PrintStream;
import java.sql.SQLException;

public class ZTE_CDN_LOG_ACC_DAY
{
  public static void main(String[] args)
    throws Exception
  {
    String yesterday = "";
    if (args.length < 1) {
      yesterday = getDate.getYesterday();
    } else {
      yesterday = args[0];
    }
    accumulative_day(yesterday, "as");
    accumulative_day(yesterday, "dd");
    accumulative_day(yesterday, "ly");
    accumulative_day(yesterday, "dl");
    accumulative_day(yesterday, "yk");
    accumulative_day(yesterday, "bx");
  }
  
  public static void accumulative_day(String day, String city)
    throws SQLException
  {
    System.out.println("begein accumulative  file of " + city + "\t" + day);
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      String sql = "insert into cdn_zte_acc_day (city,vdate,users,duration,volume,avg_duration,avg_rat,nums_all,nums_success,responsetime)  SELECT '" + city + "','" + day + "', count(DISTINCT userNumber) users, (sum(duration)/(1000*60*60)) duration ,sum(volume)/(1024*1024*1024*1024) volume , (sum(duration)/(1000*60))/count(DISTINCT userNumber) avg_duration ,(sum(volume)/(1024))/((sum(duration)/(1000))) avg_rat,count(*) ,sum(case  when responsecode like '2%' then 1  when responsecode like '3%' then 1 else 0   end ),sum(responsetime)  from bcs_cdn_024_" + city + "_" + day;
      System.out.println(sql);
      db.insert(sql);
      sql = "insert into cdn_zte_acc_error (city,vdate,TermialIP,ServerIP,ContentID,responsecode,nums) SELECT '" + city + "','" + day + "',a.* from (SELECT TermialIP,ServerIP,ContentID,responsecode,count(*) s from   bcs_cdn_024_" + city + "_" + day + " where responsecode like '4%' GROUP BY TermialIP,ServerIP,ContentID,responsecode ) a ORDER BY s desc limit 3";
      System.out.println(sql);
      db.insert(sql);
    }
    db.closeRs();
    db.closeStm();
    
    db.closeConn();
  }
}
