package TOOLS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class DBAccess_new {
	private String drv = "";

	private String url = "";

	private String usr = "";

	private String pwd = "";

	private Connection conn = null;

	private Statement stm = null;
	private PreparedStatement pst = null;

	private ResultSet rs = null;

	public DBAccess_new(String dbinfo[]) {
		this.drv = dbinfo[0];
		this.url = dbinfo[1];
		this.usr = dbinfo[2];
		this.pwd = dbinfo[3];

	}

	public boolean createConn() { // web
		boolean b = false;
		//System.out.println(drv+"|"+url+"|"+usr+"|"+pwd);
		try {
			Class.forName(drv);
			conn = DriverManager.getConnection(url, usr, pwd);
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return b;
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
			b = false;

		} catch (Exception e) {
			e.printStackTrace();
			b = false;

		}

		return b;

	}
	
	
	////////////////////////////////////
	
	public boolean truncate(String tableName) {
		String sql = "truncate table " + tableName;

		boolean b = false;
		try {
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);
			
			System.out.println(tableName + " is truncated !!! ");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return b;

	}
	/////////////////////////////////////////////
	
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
	
	//////////////////////////////////////////
	
	public boolean createIndex(String sql) {
		 
		boolean b = false;
		try {
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
	
	public boolean createIndex(String tableName, String IndexName, String colName) {
		String sql = "CREATE INDEX " + IndexName + " on " + tableName + "("
				+ colName + ")";
		boolean b = false;
		try {
			// stm = conn.createStatement();
			System.out.println("sql:\t" + sql);

			stm = conn.createStatement();

			boolean rows = stm.execute(sql);
			
				b = true;
				
		} catch (Exception e) {
			e.printStackTrace();
			 b = false;

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
	
	
	
	////////////////////
	public boolean update(String sql) {
		// String cp_chinese = new String(str.getBytes("utf-8"),"utf-8");
		boolean b = false;
		try {
			stm = conn.createStatement();

			pst = conn.prepareStatement(sql);

			int rows = pst.executeUpdate();
			if (rows > 0) {
				b = true;
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
	
	
	public static void main(String[] args) throws Exception {
		String[] DBinfo = {
				"com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost/dirxu?useSSL=false&characterEncoding=utf8",
				"root", "PaTr@1234" };
		DBAccess_new db = new DBAccess_new(DBinfo);
		if(db.createConn()){
			
			db.insert("insert into abc (str) values('sss')");
			
		}
		db.closeConn();
	}
}
