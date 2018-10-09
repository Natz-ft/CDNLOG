package TOOLS;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;




public class SetLog {
	
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
		String driver = prop.getProperty("maximodriver");
		String url = prop.getProperty("localurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");
		
		updateStatus("t_cdn_ftplog_zte","c.txt.gz",driver,url,user,passwd);
	}
	
	public static void setlog(String table,String fileName,String path,String driver,String url ,String user,String passwd) throws SQLException{
 
		 
			DBAcess db = new DBAcess();

			if (db.createConnLocal( driver, url , user, passwd)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String vtime = df.format(new Date());
				String sql = "insert into "+table+" (fileName,vtime,path) values ('"+fileName+"','"+vtime+"','"+path+"')";
				db.insert(sql);
			}
			db.closeConn();
		}
	
	public static void setlog(String table,String fileName,String path) throws SQLException{
		 
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path) values ('"+fileName+"','"+vtime+"','"+path+"')";
			db.insert(sql);
		}
		db.closeConn();
	}
	
	public static boolean  setlog(String table,String fileName,String path,String localPath,String driver,String url ,String user,String passwd) throws SQLException{
		 
		 boolean b = false;
		DBAcess db = new DBAcess();

		if (db.createConnLocal( driver, url , user, passwd)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path,localpath) values ('"+fileName+"','"+vtime+"','"+path+"' ,'"+localPath+"'    )";
			b =db.insert(sql);
		}
		db.closeConn();
		return b;
	}
	
	public static void setlog(String table,String fileName,String path,String localPath) throws SQLException{
		 
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String vtime = df.format(new Date());
			String sql = "insert into "+table+" (fileName,vtime,path,localpath) values ('"+fileName+"','"+vtime+"','"+path+"' ,'"+localPath+"'    )";
			db.insert(sql);
		}
		db.closeConn();
	}
	
	public static int Checklog(String table,String fileName,String driver,String url ,String user,String passwd ){
		 
		int num=0;
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal(driver,url,user,passwd)) {
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
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal()) {
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
		 
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal( driver, url , user, passwd)) {			 
			 
			String sql = "update  "+table+" set status = 'YES' WHERE fileName = '"+fileName+"'";
			db.update(sql);;
		}
		db.closeConn();
	}
		
	public static void updateStatus(String table,String fileName,String status ){		 
		 
		DBAcess db = new DBAcess();

		if (db.createConnLocal()) {			 
			 
			String sql = "update  "+table+" set status = '"+status+"' WHERE fileName = '"+fileName+"'";
			db.update(sql);;
		}
		db.closeConn();
	}	
	
	
	 

}
