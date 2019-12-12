package haoliyuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import TOOLS.CSVUtils;
import TOOLS.DBAccess_new;
import TOOLS.getProperties;

/*
 * 直真三个 csv 文件，每日入库
 * 
 * */
public class ZzIntoDbEveryDay {

	public static String hlwzbjkLocalPath = getProperties.getPropertie("hlwzbjkLocalPath").trim();

	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("haoliyuanUrl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();

	public static void main(String[] args) throws SQLException {
		
		// CMNET城域网链路忙时带宽利用率
		readCsvFile("CMNET_CYW_MS");

		// CMNET省内中继链路忙时带宽利用率

		readCsvFile("CMNET_SN_ZJ");

		//CMNET省网、双跨城域网上联骨干网链路忙时带宽利用率
		readCsvFile("CMNET_SW_SK");
	}

	public static void readCsvFile(String fileType) throws SQLException {
		String today = TOOLS.getDate.getToday();
           
		     
		for (int i = 1; i < 10; i++) {
			// CMNET城域网链路忙时带宽利用率 CMNET_CYW_MS
			try {
				String fileName = fileType + "_liuhe_DAY_" + today + "03000" + String.valueOf(i) + ".csv";
				System.out.println(hlwzbjkLocalPath + File.separator + fileName);
				ArrayList<String[]> csvFileList = CSVUtils.readCsvByRow(hlwzbjkLocalPath + File.separator + fileName,false);

				String tableName = "t_" + fileType;
				intoDB(tableName, csvFileList, today);
				break;
			} catch (FileNotFoundException e) {

				continue;
			} catch (IOException e1) {

				continue;
			}
		}
	}

	public static void intoDB(String tablename, ArrayList<String[]> csvFileList, String vdate) throws SQLException {

		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		if (db.createConn()) {
			for (String[] arr : csvFileList) {
				String sql = "insert into " + tablename
						+ " (vdate,device_name,total,ifname,org_time,maxifInbandwidth_rate,ifSpeed,maxifOutbandwidth_rate,portalias,dev_ip) "
						+ "values( '" + vdate + "','" + arr[0] + "'," + arr[1] + ",'" + arr[2] + "','" + arr[3] + "',"
						+ arr[4] + "," + arr[5] + "," + arr[6] + ",'" + arr[7] + "','" + arr[8] + "')";
				System.out.println(sql);
				db.insert(sql);
			}
		}
		db.closeConn();

	}

}
