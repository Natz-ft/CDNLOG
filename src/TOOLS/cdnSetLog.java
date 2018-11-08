package TOOLS;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class cdnSetLog {
	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
	
	public static void main(String[] args) throws Exception {
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		//String vdate = df.format(new Date());
		//System.out.println(Checklog("t_cdn_ftplog","c.txt.gz"));// new Date()Ϊ��ȡ��ǰϵͳʱ��
		Properties prop;
		prop = new Properties();		
		try {		 
			prop.load(new FileInputStream(DBAcess.getPath()));
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("localurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");
		
		updateStatus("t_cdn_ftplog_zte","c.txt.gz",driver,url,user,passwd);
	}
	
	public static void setlog(String table,String fileName,String path,String driver,String url ,String user,String passwd) throws SQLException{
 
		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);

			if (db.createConn()) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String vtime = df.format(new Date());
				String sql = "insert into "+table+" (fileName,vtime,path) values ('"+fileName+"','"+vtime+"','"+path+"')";
				db.insert(sql);
			}
			db.closeConn();
		}
	
	public static void setlog(String table,String fileName,String path) throws SQLException{
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		 
		//DBAcess db = new DBAcess();

		if (db.createConn()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path) values ('"+fileName+"','"+vtime+"','"+path+"')";
			db.insert(sql);
		}
		db.closeConn();
	}
	
	public static boolean  setlog(String table,String fileName,String path,String localPath,String driver,String url ,String user,String passwd) throws SQLException{
		 
		 boolean b = false;
			String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
			String[] dbinfo = DBInfo.split("\\|");
			DBAccess_new db = new DBAccess_new(dbinfo);

		if (db.createConn()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path,localpath) values ('"+fileName+"','"+vtime+"','"+path+"' ,'"+localPath+"'    )";
			b =db.insert(sql);
		}
		db.closeConn();
		return b;
	}
	
	public static void setlog(String table,String fileName,String path,String localPath) throws SQLException{
		 
		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);

		if (db.createConn()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path,localpath) values ('"+fileName+"','"+vtime+"','"+path+"' ,'"+localPath+"'    )";
			db.insert(sql);
		}
		db.closeConn();
	}
	
	public static int Checklog(String table,String fileName,String driver,String url ,String user,String passwd ){
		 
		int num=0;
		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);

		if (db.createConn()) {
			String sql = "select count(*) i  from "+table+" where fileName='"+fileName+"'";
			db.query(sql);
			while(db.next()){
				num = db.getIntValue("i");
			}
			db.closeRs();
			db.closeStm();
		}
		db.closeConn();
		return num;
	}
	public static int Checklog(String table,String fileName ){
		 
		int num=0;
		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);

		if (db.createConn()) {
			String sql = "select count(*) i  from "+table+" where fileName='"+fileName+"'";
			db.query(sql);
			while(db.next()){
				num = db.getIntValue("i");
			}
			db.closeRs();
			db.closeStm();
		}
		db.closeConn();
		return num;
	}
	
	public static void updateStatus(String table,String fileName,String driver,String url ,String user,String passwd){
		 
		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);

		if (db.createConn()) {			 
			 
			String sql = "update  "+table+" set status = 'YES' WHERE fileName = '"+fileName+"'";
			db.update(sql);;
		}
		db.closeConn();
	}
		
	public static void updateStatus(String table,String fileName,String status ){		 
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		
		//DBAcess db = new DBAcess();

		if (db.createConn()) {			 
			 
			String sql = "update  "+table+" set status = '"+status+"' WHERE fileName = '"+fileName+"'";
			db.update(sql);;
		}
		db.closeConn();
	}	
	

}
