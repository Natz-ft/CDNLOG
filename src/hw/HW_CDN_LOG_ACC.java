package hw;

import java.sql.SQLException;

import TOOLS.DBAccess_new;
import TOOLS.getDate;
import TOOLS.getProperties;

public class HW_CDN_LOG_ACC {
	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
	
	  public static void main(String[] args)
			    throws Exception
			  {
			    String yesterday = "";
			    if (args.length < 1) {
			      yesterday = getDate.getYesterday(getDate.getYesterday());
			    } else {
			      yesterday = args[0];
			    }
			    
			    boolean b = false;
				String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
				String[] dbinfo = DBInfo.split("\\|");
				DBAccess_new db = new DBAccess_new(dbinfo);
			     if (db.createConn())
			    {
			       
			      
			      String sql_create_table = "CREATE table  hms_accessdownstream_sy_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table); 
			     
			      sql_create_table = "CREATE table  hms_accessdownstream_ln_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_fs_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_jz_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_tl_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_fx_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			     
			      sql_create_table = "CREATE table  hms_accessdownstream_pj_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      db.createTable(sql_create_table);
			      System.out.println(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_cy_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      db.createTable(sql_create_table);
			      System.out.println(sql_create_table);
			      
			      sql_create_table = "CREATE table  hms_accessdownstream_hl_"+yesterday+" AS SELECT * FROM cdn_hw_table ";
			      System.out.println(sql_create_table);
			      db.createTable(sql_create_table);
			    }
			   
			    
			    
			    
			    
			    db.closeConn();  
			    
			    accumulative_day(yesterday);
			    
			    
			    
			  }
	  public static void accumulative_day(String day) throws SQLException{
		  String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
			String[] dbinfo = DBInfo.split("\\|");
			DBAccess_new db = new DBAccess_new(dbinfo);
		    if (db.createConn())
		    {
		    	String sql = "select TABLE_NAME from information_schema.tables t where   TABLE_NAME like 'hms_accessdownstream%"+day+"' " +
		    			"and (TABLE_NAME not like '%cy%' and " +
		    			"TABLE_NAME not like '%fs%' and " +
		    			"TABLE_NAME not like '%hl%' and " +
		    			"TABLE_NAME not like '%jz%' and " +
		    			"TABLE_NAME not like '%ln%' and " +
		    			"TABLE_NAME not like '%pj%' and " +
		    			"TABLE_NAME not like '%sy%' and " +
		    			"TABLE_NAME not like '%tl%' and " +
		    			"TABLE_NAME not like '%fx%'  )";
		    	 System.out.println(sql);
		    	
		    	db.query(sql);
		    	while (db.next()){
		    	  String  device_name = db.getValue("TABLE_NAME");
		    	  
			 
		    	  String table_name = hw_cdn_log_intoDB.getTableName(device_name) ;
		    	  
		    	  String sql_1 = "insert into "+table_name +" (client_port,channel_id_public,ServiceType,http_code,file_size,Fileduration) select client_port,channel_id_public,ServiceType,http_code,sum(file_size),sum(Fileduration)  from "+device_name +" group by client_port,channel_id_public,ServiceType,http_code ";
		    	  System.out.println(sql_1);
		    	  db.insert(sql_1);
		    			  
		    	
		    	}
		    	db.closeRs();
		    	db.closeStm();
		    }
		    db.closeConn();
	  }
	

}
