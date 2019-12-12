package TOOLS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import excel.Common;
import excel.buglist;
import excel.portList;

public class DBAcess {
	private String drv = "oracle.jdbc.driver.OracleDriver";

	private String url = "";

	private String usr = "";

	private String pwd = "";

	private Connection conn = null;

	private Statement stm = null;
	private PreparedStatement pst = null;

	private ResultSet rs = null;

	public boolean createConn() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("localurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");
		// System.out.print(driver);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
		return b;
	}

	public boolean createConnIP() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("localurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");
		// System.out.print(driver);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
		return b;
	}

	public boolean createConnLocal(String driver, String url, String user,
			String passwd) { // web
		boolean b = false;

		// System.out.print(driver);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
		return b;
	}

	public boolean createConnLocal() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("localurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");

		// System.out.print("localurl:"+url);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		}
		// System.out.println("b:"+b);
		return b;
	}
	
	public boolean createHlw() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("hlwurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");

	 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		}
		// System.out.println("b:"+b);
		return b;
	}
	
	
	public boolean createConnLiujy() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("ljyull");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");

		// System.out.print("localurl:"+url);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		}
		// System.out.println("b:"+b);
		return b;
	}

	public boolean createConnLocalIP() { // web
		boolean b = false;
		Properties prop;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(getPath()));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driver = prop.getProperty("mysqldriver");
		String url = prop.getProperty("localipurl");
		String user = prop.getProperty("localuser");
		String passwd = prop.getProperty("localpassword");

		System.out.print("localipurl:" + url);

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
			b = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		}
		System.out.println("b:" + b);
		return b;
	}

	public static String getPath() { // ��ȡ�����ļ�·��
		String path = "", path_2 = "", path_1 = "";

		try {

			DBAcess g = new DBAcess();
			Properties prop = new Properties();
			File directory = new File("");// ����Ϊ��

			// System.out.println("path:  "+
			// g.getClass().getResource("/").getPath());
			File f = new File(g.getClass().getResource("/").getPath());

			try {
				path_1 = directory.getCanonicalPath(); // java project
				path_2 = f.getPath();// web project
				// System.out.println("path_1" + path_1);
				// System.out.println("path_2" + path_2);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				// ��������·��
				path = path_1 + File.separator + "src" + File.separator
						+ "db.properties";
				prop.load(new FileInputStream(path));

			} catch (IOException e1) {
				// path+ File.separator +"db.properties"
				// System.out.println("path_2��" + path_2);

				try {
					// web����·��
					path = path_2 + File.separator + "db.properties";
					prop.load(new FileInputStream(path));
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}
		} catch (Exception e0) {
			File directory = new File("");// ����Ϊ��
			try {
				path = directory.getCanonicalPath();
				path = path + File.separator + "db.properties";
				// System.out.println("db.properties:  "+path);
			} catch (Exception e01) {
				e01.printStackTrace();
			}
		}
		// System.out.println("path:  "+path);
		return path;

	}

	public void insert(String sql, buglist buglist) throws SQLException {

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, buglist.getIp()); // ip

			pst.setString(2, buglist.getPort()); // port
			pst.setString(3, buglist.getProtocol()); // protocol
			pst.setString(4, buglist.getService()); // service
			pst.setString(5, buglist.getBug_name()); // bug_name
			pst.setString(6, buglist.getBug_num()); // bug_num
			pst.setString(7, buglist.getBug_class()); // bug_class
			pst.setString(8, buglist.getBug_classify()); // bug_classify
			pst.setString(9, buglist.getApp_classify()); // app_classify
			pst.setString(10, buglist.getSys_classify()); // sys_classify
			pst.setString(11, buglist.getThreaten_classify());// threaten_classify
			pst.setString(12, buglist.getTime_classify());// time_classify
			pst.setString(13, buglist.getCVE_year());// CVE_year
			pst.setString(14, buglist.getDev_date());// dev_date
			pst.setString(15, buglist.getCve_num());// cve_num
			pst.setString(16, buglist.getCNNVD_num());// CNNVD_num
			pst.setString(17, buglist.getCNVD_num());// CNCVE_num
			pst.setString(18, buglist.getCNVD_num());// CNVD_num
			pst.setString(19, buglist.getDescribe());// describe
			pst.setString(20, buglist.getSolve());// solve
			pst.setString(21, buglist.getReturn_des());// return_des
			pst.setInt(22, buglist.getSort());// return_des

			boolean flag = pst.execute();
			if (!flag) {
				// System.out.println("Save data : IP. = " + buglist.getIp() +
				// " , Name = " + buglist.getBug_name() + " succeed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + buglist.getIp());
		}
	}

	public void insert(String sql, portList portList) throws SQLException {

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, portList.getPath()); // ip

			pst.setString(2, portList.getPort()); // port
			pst.setString(3, portList.getProtocol()); // protocol
			pst.setString(4, portList.getService()); // service
			pst.setInt(5, portList.getId()); // bug_name

			boolean flag = pst.execute();
			if (!flag) {
				// System.out.println("Save data : IP. = " + buglist.getIp() +
				// " , Name = " + buglist.getBug_name() + " succeed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + portList.getIp());
		}
	}

	public boolean insert(String sql) throws SQLException {
		// String cp_chinese = new String(str.getBytes("utf-8"),"utf-8");
		conn.setAutoCommit(false);
		boolean b = false;
		try {
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);

			int rows = pst.executeUpdate();
			conn.commit();
			if (rows > 0) {
				b = true;
			}
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate colume" + "\t" + sql);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return b;

	}

	public boolean List_insert(List<String> List_sql) throws SQLException {
		// String cp_chinese = new String(str.getBytes("utf-8"),"utf-8");
		boolean b = false;

		conn.setAutoCommit(false);
		stm = conn.createStatement();
		for (int i = 0; i < List_sql.size(); i++) {
			try {

				// System.out.println(List_sql.get(i));
				stm.addBatch(List_sql.get(i));

			} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
				System.out.println("Duplicate colume" + "\t" + List_sql.get(i));

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		//
		try {
			int rows[] = stm.executeBatch();
			b = true;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}

		return b;

	}

	/*
	 * public static void insertRelease() { // 批量 insert Long begin = new
	 * Date().getTime(); String sql =
	 * "INSERT INTO tb_big_data (count, create_time, random) VALUES (?, SYSDATE(), ?)"
	 * ; try { conn.setAutoCommit(false); PreparedStatement pst =
	 * conn.prepareStatement(sql); for (int i = 1; i <= 100; i++) { for (int k =
	 * 1; k <= 10000; k++) { pst.setLong(1, k * i); pst.setLong(2, k * i);
	 * pst.addBatch(); } pst.executeBatch(); conn.commit(); } pst.close();
	 * conn.close(); } catch (SQLException e) { e.printStackTrace(); } Long end
	 * = new Date().getTime(); System.out.println("cast : " + (end - begin) /
	 * 1000 + " s"); }
	 */

	public boolean truncate(String tableName) {
		String sql = "truncate table " + tableName;

		boolean b = false;
		try {
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);

			int rows = pst.executeUpdate();
			System.out.println(tableName + " is truncated !!! ");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return b;

	}

	// stmt.executeLargeUpdate(creatsql))

	public boolean createTable(String sql) {
		// String cp_chinese = new String(str.getBytes("utf-8"),"utf-8");
		boolean b = false;
		try {
			// stm = conn.createStatement();
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);

			int rows = pst.executeUpdate();
			b = true;

		} catch (MySQLSyntaxErrorException e1) {

		} catch (Exception e) {
			e.printStackTrace();

		}
		return b;

	}

	public boolean createIndex(String tableName, String IndexName,
			String colName) {
		String sql = "CREATE INDEX " + IndexName + " on " + tableName + "("
				+ colName + ")";
		boolean b = false;
		try {
			// stm = conn.createStatement();
			System.out.println("sql:\t" + sql);

			stm = conn.createStatement();

			boolean rows = stm.execute(sql);

			if (rows) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return b;

	}

	public boolean update(String sql) {
		// String cp_chinese = new String(str.getBytes("utf-8"),"utf-8");
		boolean b = false;
		try {
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);

			int rows = pst.executeUpdate();
			if (rows > 0) {
				b = true;

				try {

					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return b;

	}

	public void query(String sql) {
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		} catch (Exception e) {
		}
	}

	public boolean next() {
		boolean b = false;
		try {

			if (rs.next())
				b = true;
		} catch (Exception e) {
		}
		return b;
	}

	public String getValue(String field) {
		String value = null;
		try {
			if (rs != null)
				value = rs.getString(field);
		} catch (Exception e) {
		}
		return value;
	}

	public int getIntValue(String field) {
		int value = 0;
		try {
			if (rs != null)
				value = rs.getInt(field);
		} catch (Exception e) {
		}
		return value;
	}

	public int queryRow(String sql) {
		int num = 0;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				num++;
				// System.out.println("num++ " + num);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}

	public void closeStm() {
		try {
			if (stm != null)
				stm.close();
		} catch (SQLException e) {
		}
	}

	public void closeRs() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getDrv() {
		return drv;
	}

	public void setDrv(String drv) {
		this.drv = drv;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStm() {
		return stm;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

}
