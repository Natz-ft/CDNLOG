package zte;

import TOOLS.DBAccess_new;
import TOOLS.getProperties;

public class test111 {
 // BCS-CDN-024-AS0202_OTTCACHE_20181111T211001Z
	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
	public static void main(String[] args) throws Exception {
		
		boolean b = false;
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
	    if (db.createConn())
	    {
	       
	      
	      String sql_table_name = "CREATE VIEW  NewView AS SELECT aaa.str1 FROM aaa ";
	      db.createTable(sql_table_name);}
	    db.closeConn();
		
	}
}
