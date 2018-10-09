package ZTECDN;

import TOOLS.DBAcess;
import TOOLS.getDate;
import java.io.PrintStream;

public class ZTE_CDN_LOG_ACC
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
  {
    System.out.println("begein accumulative  file of " + city + "\t" + day);
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      db.query("SELECT * from ( SELECT t.ContentID,t.ServiceType,sum(t.duration)/(1000*60*60) d,sum(t.volume)/(1024*1024*1024) v , count(*) c from bcs_cdn_024_" + 
        city + 
        "_" + 
        day + 
        " t where ServiceType <> 1 GROUP BY  t.ContentID,t.ServiceType ) a ");
      while (db.next())
      {
        String ContentID = db.getValue("ContentID");
        String ServiceType = db.getValue("ServiceType");
        String duration = db.getValue("d");
        
        duration = duration.substring(0, duration.indexOf("."));
        
        String volume = db.getValue("v");
        String nums = db.getValue("c");
        
        String sql = "insert into cdn_zte_accumulate  (CITY, VDATE, ContentID, ServiceType, duration , volume, nums) values('" + 
          city + 
          "','" + 
          day + 
          "','" + 
          ContentID + 
          "','" + 
          ServiceType + 
          "'," + 
          duration + 
          "," + 
          volume + 
          "," + 
          nums + ") ";
        try
        {
          db.insert(sql);
        }
        catch (Exception e)
        {
          System.out.println(ContentID + "is Duplicate");
        }
      }
      db.closeRs();
      db.closeStm();
      
      db.query("SELECT * from ( SELECT t.ContentID,t.ServiceType,sum(t.duration) /(1000*60*60) d,sum(t.volume) /(1024*1024*1024) v , count(*) c from bcs_cdn_024_" + 
        city + 
        "_" + 
        day + 
        " t where ServiceType = 1 GROUP BY  t.ContentID,t.ServiceType ) a order by d desc limit 200 ");
      while (db.next())
      {
        String ContentID = db.getValue("ContentID");
        String ServiceType = db.getValue("ServiceType");
        String duration = db.getValue("d");
        
        duration = duration.substring(0, duration.indexOf("."));
        
        String volume = db.getValue("v");
        String nums = db.getValue("c");
        
        String sql = "insert into cdn_zte_accumulate  (CITY, VDATE, ContentID, ServiceType, duration , volume, nums) values('" + 
          city + 
          "','" + 
          day + 
          "','" + 
          ContentID + 
          "','" + 
          ServiceType + 
          "'," + 
          duration + 
          "," + 
          volume + 
          "," + 
          nums + ") ";
        try
        {
          db.insert(sql);
        }
        catch (Exception e)
        {
          System.out.println(ContentID + "is Duplicate");
        }
      }
      db.closeRs();
      db.closeStm();
      
      db.query("SELECT * from ( SELECT t.ContentID,t.ServiceType,sum(t.duration) /(1000*60*60) d,sum(t.volume) /(1024*1024*1024) v , count(*) c from bcs_cdn_024_" + 
        city + 
        "_" + 
        day + 
        " t where ServiceType = 1 GROUP BY  t.ContentID,t.ServiceType ) a order by c desc limit 200 ");
      while (db.next())
      {
        String ContentID = db.getValue("ContentID");
        String ServiceType = db.getValue("ServiceType");
        String duration = db.getValue("d");
        
        duration = duration.substring(0, duration.indexOf("."));
        
        String volume = db.getValue("v");
        String nums = db.getValue("c");
        
        String sql = "insert into cdn_zte_accumulate  (CITY, VDATE, ContentID, ServiceType, duration , volume, nums) values('" + 
          city + 
          "','" + 
          day + 
          "','" + 
          ContentID + 
          "','" + 
          ServiceType + 
          "'," + 
          duration + 
          "," + 
          volume + 
          "," + 
          nums + ") ";
        try
        {
          db.insert(sql);
        }
        catch (Exception e)
        {
          System.out.println(ContentID + "is Duplicate");
        }
      }
      db.closeRs();
      db.closeStm();
      
      db.query("SELECT * from ( SELECT t.ContentID,t.ServiceType,sum(t.duration) /(1000*60*60) d,sum(t.volume) /(1024*1024*1024) v , count(*) c from bcs_cdn_024_" + 
        city + 
        "_" + 
        day + 
        " t where ServiceType = 1 GROUP BY  t.ContentID,t.ServiceType ) a order by v desc limit 200 ");
      while (db.next())
      {
        String ContentID = db.getValue("ContentID");
        String ServiceType = db.getValue("ServiceType");
        String duration = db.getValue("d");
        
        duration = duration.substring(0, duration.indexOf("."));
        
        String volume = db.getValue("v");
        String nums = db.getValue("c");
        
        String sql = "insert into cdn_zte_accumulate  (CITY, VDATE, ContentID, ServiceType, duration , volume, nums) values('" + 
          city + 
          "','" + 
          day + 
          "','" + 
          ContentID + 
          "','" + 
          ServiceType + 
          "'," + 
          duration + 
          "," + 
          volume + 
          "," + 
          nums + ") ";
        try
        {
          db.insert(sql);
        }
        catch (Exception e)
        {
          System.out.println(ContentID + "is Duplicate");
        }
      }
      db.closeRs();
      db.closeStm();
    }
    db.closeConn();
  }
}
