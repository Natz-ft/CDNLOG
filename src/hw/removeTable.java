package hw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import TOOLS.DBAccess_new;
import TOOLS.getDate;
import TOOLS.getProperties;

public class removeTable {
	public static String driver = getProperties.getPropertie("mysqldriver")
			.trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword")
			.trim();

	public static void main(String[] args) throws Exception {
		String yesterday = "";
		if (args.length < 1) {
			Date date=new Date();//取时间
		 	   Calendar calendar = new GregorianCalendar();
			   calendar.setTime(date);
			   // calendar.add(calendar.DATE,1); tomorrow
			   calendar.add(calendar.DATE,-30);// yesterday 把日期往后增加一天.整数往后推,负数往前移动
			   
			   date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
			   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			   ////SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			    yesterday = formatter.format(date);		
		} else {
			yesterday = args[0];
		}

		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		if (db.createConn()) {
			String sql = "select TABLE_NAME from information_schema.tables t where   TABLE_NAME like 'hms_accessdownstream%"+ yesterday + "' or TABLE_NAME like 'bcs_cdn_024%"+yesterday+"'";
			System.out.println(sql);

			db.query(sql);
			while (db.next()) {
				String device_name = db.getValue("TABLE_NAME");
				//System.out.println(device_name);
				db.createTable("drop table " + device_name);

			}
			db.closeRs();
			db.closeStm();
		}
		db.closeConn();
	}
}
