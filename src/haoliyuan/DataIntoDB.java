package haoliyuan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import TOOLS.CSVUtils;
import TOOLS.getDate;
import TOOLS.DBAccess_new;
import TOOLS.getProperties;

public class DataIntoDB {

	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("haoliyuanUrl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();

	public static String VixtelLocalPath = getProperties.getPropertie("VixtelLocalPath").trim();
	public static String PTCZZLocalPath = getProperties.getPropertie("PTCZZLocalPath").trim();

	public static String hlwzbjkLocalPath = getProperties.getPropertie("hlwzbjkLocalPath").trim();

	public static void VixtelIntoDB(String filePath, String vdate) throws SQLException {
		ArrayList<String[]> csvFileList = new ArrayList<String[]>();

		try {
			csvFileList = CSVUtils.readCsvByRow(filePath,true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	    catch(IOException e1){
			  
	    	return;
		  }
		String chinamobile_lost = ""; // 移动丢包
		String chinamobile_delay = "";// 移动延时
		String chinaunicom_lost = "";// 联通丢包
		String chinaunicom_delay = "";// 联通延时
		String chinatelecom_lost = "";// 电信丢包
		String chinatelecom_delay = "";// 电信延时

		for (int row = 0; row < csvFileList.size(); row++) {
			// 取得第row行第0列的数据
			String cell = csvFileList.get(row)[0];
			// System.out.println("------------>"+cell);

			cell = replaceBlank(cell);
		 
			if (cell.contains("移动丢包")) {
				chinamobile_lost = cell.replace("移动丢包（%）", "");
				 
			}
			if (cell.contains("移动时延")) {
				chinamobile_delay = cell.replace("移动时延（ms）", "");
			}
			if (cell.contains("联通丢包")) {
				chinaunicom_lost = cell.replace("联通丢包（%）", "");
			}
			if (cell.contains("联通时延")) {
				chinaunicom_delay = cell.replace("联通时延（ms）", "");
			}
			if (cell.contains("电信丢包")) {
				chinatelecom_lost = cell.replace("电信丢包（%）", "");
			}
			if (cell.contains("电信时延")) {
				chinatelecom_delay = cell.replace("电信时延（ms）", "");
			}

		}
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		if (db.createConn()) {
			String sql = "insert into t_vixtel_data (vdate,chinamobile_lost,chinamobile_delay,chinaunicom_lost,chinaunicom_delay,chinatelecom_lost,chinatelecom_delay) values('"
					+ vdate + "'," + chinamobile_lost + "," + chinamobile_delay + "," + chinaunicom_lost + ","
					+ chinaunicom_delay + "," + chinatelecom_lost + "," + chinatelecom_delay + ")";
			System.out.println(sql);
			db.insert(sql);
		}
		db.closeConn();

	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			// Pattern p = Pattern.compile("\t|\r|\n");
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/*
	 * // 延迟解析比率 ZipSecureFile.setMinInflateRatio(-1.0d); workbook = new
	 * XSSFWorkbook(file.getInputStream());
	 * 
	 */

	public static void PtczzIntoDB(String filePath, String vdate) throws SQLException {
		System.out.println("filePath:" + filePath);
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		List<Map<String, String>> list = null;
		String cellData = null;
		// filePath = "D:\\test.xlsx";
		String columns[] = { "指标名称", "指标监控值", };
		// String columns[] = {"name","age","score"};
		wb = readExcel(filePath);
		if (wb != null) {
			// 用来存放表中数据
			list = new ArrayList<Map<String, String>>();
			// 获取第一个sheet
			sheet = wb.getSheetAt(0);
			// 获取最大行数
			int rownum = sheet.getPhysicalNumberOfRows();
			// 获取第一行
			row = sheet.getRow(0);
			// 获取最大列数
			int colnum = row.getPhysicalNumberOfCells();
			for (int i = 1; i < rownum; i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				row = sheet.getRow(i);
				if (row != null) {
					/*
					 * for (int j=0;j<colnum;j++){ cellData = (String)
					 * getCellFormatValue(row.getCell(j)); map.put(columns[j],
					 * cellData); }
					 */
					cellData = (String) getCellFormatValue(row.getCell(3));
					map.put(columns[0], cellData);
					cellData = (String) getCellFormatValue(row.getCell(7));
					map.put(columns[1], cellData);

				} else {
					break;
				}
				list.add(map);
			}
		}
		// 遍历解析出来的list
		/*
		 * for (Map<String,String> map : list) { for (Entry<String,String> entry
		 * : map.entrySet()) {
		 * System.out.print(entry.getKey()+":"+entry.getValue()+","); }
		 * System.out.println(); }
		 */
		String province_lost = "";// CMNET省内链路拨测链路丢包率',
		String province_delay = "";// CMNET省内链路拨测链路时延,
		String province_fluctuates = "";// CMNET省内链路拨测链路抖动',
		String country_delay = "";// CMNET省际链路时延（普天）',
		String country_lost = "";// CMNET省际链路丢包率（普天）',
		String chinaunicom_delay = "";// CMNET省际链路丢包率（普天）',
		String chinaunicom_lost = "";// CMNET省际链路丢包率（普天）',
		String chinatelecom_delay = "";// CMNET省际链路时延（普天）',
		String chinatelecom_lost = "";// CMNET省际链路丢包率（普天）',
		String internet_delay = "";// 网间网络层时延（ms）',
		String internet_lost = "";// 网间网络层丢包率 (%)',
		String webpage_delay = "";// 网间网页主要展现时延 (s) ',
		String webpage_success = "";// 网间网页展现成功率 (%)'
		for (Map<String, String> map : list) {
			String str = "";
			for (Entry<String, String> entry : map.entrySet()) {
				// System.out.print(entry.getKey()+":"+entry.getValue()+",");

				String str_value = "";
				if (entry.getValue().equals("") || entry.getValue() == null) {
					str_value = "0";
					//
				} else {
					str_value = entry.getValue();
				}

				str = str + entry.getKey() + ":" + str_value;

			}
			str = replaceBlank(str);
			str = str.replace("指标名称:", "");
			str = str.replace("指标监控值", "");

			if (str.contains("CMNET省内链路拨测链路丢包率")) {
				province_lost = str.replace("CMNET省内链路拨测链路丢包率:", "");
			}
			if (str.contains("CMNET省内链路拨测链路时延")) {
				province_delay = str.replace("CMNET省内链路拨测链路时延:", "");
			}
			if (str.contains("CMNET省内链路拨测链路抖动")) {
				province_fluctuates = str.replace("CMNET省内链路拨测链路抖动:", "");
			}
			if (str.contains("CMNET省际链路时延（普天）")) {
				country_delay = str.replace("CMNET省际链路时延（普天）:", "");
			}
			if (str.contains("CMNET省际链路丢包率（普天）")) {
				country_lost = str.replace("CMNET省际链路丢包率（普天）:", "");
			}
			if (str.contains("CMNET省际链路时延（普天）")) {
				chinaunicom_delay = str.replace("CMNET省际链路时延（普天）:", "");
			}
			if (str.contains("CMNET省际链路丢包率（普天）")) {
				chinaunicom_lost = str.replace("CMNET省际链路丢包率（普天）:", "");
			}
			if (str.contains("CMNET省际链路时延（普天）")) {
				chinatelecom_delay = str.replace("CMNET省际链路时延（普天）:", "");
			}
			if (str.contains("CMNET省际链路丢包率（普天）")) {
				chinatelecom_lost = str.replace("CMNET省际链路丢包率（普天）:", "");
			}
			if (str.contains("网间网络层时延（ms）")) {
				internet_delay = str.replace("网间网络层时延（ms）:", "");
			}
			if (str.contains("网间网络层丢包率(%)")) {
				internet_lost = str.replace("网间网络层丢包率(%):", "");

			}
			if (str.contains("网间网页主要展现时延")) {
				webpage_delay = str.replace("网间网页主要展现时延(s):", "");

			}
			if (str.contains("网间网页展现成功率")) {
				webpage_success = str.replace("网间网页展现成功率(%):", "");

			}

			System.out.println(str);
			// System.out.println();
		}
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		if (db.createConn()) {
			String sql = "insert into t_ptczz_data (vdate,province_lost,province_delay,province_fluctuates,country_delay,country_lost,chinaunicom_delay,chinaunicom_lost,chinatelecom_delay,chinatelecom_lost,internet_delay,internet_lost,webpage_delay,webpage_success) values("
					+ vdate + "," + province_lost + "," + province_delay + "," + province_fluctuates + ","
					+ country_delay + "," + country_lost + "," + chinaunicom_delay + "," + chinaunicom_lost + ","
					+ chinatelecom_delay + "," + chinatelecom_lost + "," + internet_delay + "," + internet_lost + ","
					+ webpage_delay + "," + webpage_success + ")";
			System.out.println(sql);
			db.insert(sql);
		}
		db.closeConn();

	}

	// 读取excel
	// 延迟解析比率
	// ZipSecureFile.setMinInflateRatio(-1.0d);
	// workbook = new XSSFWorkbook(file.getInputStream());

	public static Workbook readExcel(String filePath) {
		Workbook wb = null;
		if (filePath == null) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				ZipSecureFile.setMinInflateRatio(-1.0d);
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	public static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: {
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					// 转换为日期格式YYYY-mm-dd
					cellValue = cell.getDateCellValue();
				} else {
					// 数字
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	public static void hlwzbjkIntoDB(String filePath, String vdate) throws SQLException {
		// String fileName = "hlwzbjkLocalPath"

		List<String> list = readFileByLines(filePath);
		// System.out.println(list.get(0));

		String[] datas = list.get(0).split("\\|");

		for (int i = 0; i < datas.length; i++) {
			if (datas[i].equals("") || datas[i] == null) {
				datas[i] = "0";
			}
		}

		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		if (db.createConn()) {
			String sql = "insert into t_hlwzbjk_data (vdate,country_rate,province_rate,province_evenness,country_evenness,city_rate,city_avg) "
					+ "values(" + vdate + "," + datas[0] + "," + datas[1] + "," + datas[2] + "," + datas[3] + ","
					+ datas[4] + "," + datas[5] + ")";
			System.out.println(sql);
			db.insert(sql);
		}
		db.closeConn();

	}

	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ʾ�к�
				// System.out.println("line " + line + ": " + tempString);
				list.add(tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	// VixtelIntoDB
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		String vdate = "";
		if (args.length < 1) {
			vdate = getDate.getToday();

		} else {
			vdate = args[0];
		}

		String lastWeekDay = getDate.getLastWeekday(vdate);

		// 飞思达，文件入库
		VixtelIntoDB(VixtelLocalPath + File.separator + "Provincial-link-" + lastWeekDay + ".csv", vdate);

		// 普天入库
		PtczzIntoDB(PTCZZLocalPath + File.separator + "PTCZZ_" + vdate + ".xlsx", vdate);

		// 直真入库
		// hlwzbjkIntoDB(hlwzbjkLocalPath+File.separator+"hlwzbjk_"+vdate+".txt",vdate)
		// ;

	}

}
