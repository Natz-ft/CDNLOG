package hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import TOOLS.DBAccess_new;
import TOOLS.ReadFromFile;
import TOOLS.SetLog;
import TOOLS.getProperties;

public class hw_cdn_log_intoDB {
	 

	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
	public static String databaseName = getProperties.getPropertie("databaseName");
	public static int  threadNum = getProperties.getINTPropertie("HWThreadNum"); 
	
	 
	
  
  public static void main(String[] args)
    throws Exception
  {
   
	  ImportDB("F:/ftp/HMS_accessDownstream_425_20181107T165950Z+08.log", "HMS_accessDownstream_425_20181107T165950Z+08.log"   );
     // String str = "HMS_accessDownstream_425_20181107T165950Z+08.log";
            // str = str.substring(21);
      //System.out.println(str.substring(0,str.indexOf("_")));
      //System.out.println(str.substring(str.indexOf("_",21), str.indexOf("_",21)+9)  );
     // System.out.println(getTableName(str));
	  
	  // ImportDB();
      //Thread.sleep(1000L);
    
  }
  
  public static void ImportDB()
    throws Exception
  {
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
    if (db.createConn())
    {
      String sql = "select fileName,path from cdn_hw_log_untar where ( status='' or status is null ) order by fileName desc   ";
      db.query(sql);
      while (db.next())
      {
        String fileName = db.getValue("fileName").replace(".tar.gz", ".txt");
        String path = db.getValue("path");
        path = path + File.separator;
        
        String table_name = getTableName(fileName);
        
        CreateTable(table_name);
        if (ImportDB(path, fileName)) {
          SetLog.updateStatus("cdn_hw_log_untar", fileName.replace(".txt", ".tar.gz"), "YES");
        } else {
          SetLog.updateStatus("cdn_hw_log_untar", fileName.replace(".txt", ".tar.gz"), "ERROR");
        }
      }
      db.closeRs();
      db.closeStm();
    }
    db.closeConn();
  }
  
  public static void CreateTable()
  {
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
    if (db.createConn())
    {
      String sql = "SELECT \tCONCAT(\t\tSUBSTR(fileName, 1, 14),'_',SUBSTR(fileName, 20, 17)\t) TABLENAME FROM\tcdn_hw_log_untar WHERE\t(STATUS = '' OR STATUS IS NULL) GROUP BY\tCONCAT(\tSUBSTR(fileName, 1, 14),\t'_',\tSUBSTR(fileName, 29, 8)\t)   ";
      db.query(sql);
      while (db.next())
      {
        String TABLENAME = db.getValue("TABLENAME");
        
        TABLENAME = TABLENAME.replace(".", "_");
        TABLENAME = TABLENAME.replace("-", "_");
        
        TABLENAME = TABLENAME.toUpperCase();
        CreateTable(TABLENAME);
      }
      db.closeRs();
      db.closeStm();
    }
    db.closeConn();
  }
  
  public static boolean CreateTable(String table)
  {
    boolean b = false;
	String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
	String[] dbinfo = DBInfo.split("\\|");
	DBAccess_new db = new DBAccess_new(dbinfo);
    if (db.createConn())
    {
      String table_name = table.toUpperCase();
      
      String sql_table_name = "select count(*) num   from information_schema.tables t where t.table_schema='"+databaseName+"' and TABLE_NAME = '" + table_name + "'";
      
      db.query(sql_table_name);
      while (db.next())
      {
        int m = db.getIntValue("num");
        if (m == 0) {
          if (db.createTable("CREATE table " + table_name + " as SELECT * from cdn_hw_table where 1 <> 1 "))
          {
            System.out.println("Table:\t" + table_name + " CREATED! ");
            
            b = true;
            System.out.println("createIndex:\t" + db.createIndex(table_name, new StringBuilder("idx").append(table_name).toString(), "channel_id_public"));
          }
          else
          {
            System.out.println("Table:\t" + table_name + " already exists");
            b = true;
          }
        }
      }
    }
    db.closeRs();
    db.closeConn();
    
    return b;
  }
  
  public static boolean ImportDB(String filePath, String fileName)
  {
    boolean b = false;
    
    String filePath2 = filePath.replace("\\", "\\\\");
    try
    {
      List<String> fileList = importLog(filePath + File.separator + fileName);
      if (fileList.size() == 0) {
        return false;
      }
      String table_name = getTableName(fileName);
      
      List<String> list_sql = new ArrayList();
      for (int i = 0; i < fileList.size(); i++)
      {
        String[] logLine = ((String)fileList.get(i)).split("\\|");
        if (logLine.length >= 1) {
          if (logLine[15].contains("accountinfo"))
          {
            String cellPhone = getUserId(logLine[15]);
            String table_name_day = table_name.substring(0, 24)+logLine[24].substring(0, 8);
          
            
            String sql = "insert into " + 
            		table_name_day + 
              " (filename,client_port,server_port2,domain,URL,startTime,endTime,Fileduration,file_size,ServiceType,channel_id_public,userNumber,responsetime,http_code,transfer_time) values" + 
              " ('" + fileName + "','" + logLine[9] + "', '" + logLine[4] + "','"+ logLine[11] +"','" + logLine[15] + "', '" + 
              logLine[23] + "','" + logLine[24] + "','" + logLine[21] + 
              "','" + logLine[20] + "','" + logLine[13] + "','" + 
              logLine[19] + "','" + cellPhone + "','" + logLine[30] + "','" + logLine[26] + "','" + logLine[32] + "' )";
            //System.out.println("insert_sql:"+sql);
            list_sql.add(sql);
          }
        }
      }
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
			if (db.createConn()) {
				String sql = "insert into cdn_hw_log_DB (fileName,records) values ('"+ fileName + "'," + fileList.size() + ") ";
				b = db.insert(sql);
				System.out.println("Import DB:\t" + filePath + File.separator+ fileName);

				if (b) {
					b = db.List_insert(list_sql);
				}

				db.closeConn();

			}
      else
      {
        return false;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return b;
  }
  
  /* Error */
  public static List<String> importLog(String fileName)
  {
	File file = new File(fileName);
	BufferedReader reader = null;
	List<String> fileList = new ArrayList<String>();// �½�һ��list�ļ�����log�ļ��е�ÿ�����ݴ洢��list�С�
	try {

		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
		while ((tempString = reader.readLine()) != null) {

			tempString = ReadFromFile.replaceBlank(tempString); // ȥ���ɼ��ַ�
			fileList.add(tempString);

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
	return fileList;
  }
  
  public static String getCellphone(String str)
  {
    String telphone = "";
    
    Pattern pattern = Pattern.compile("((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}");
    
    Matcher matcher = pattern.matcher(str);
    
    int i = 0;
    while (matcher.find()) {
      telphone = telphone + "_" + matcher.group();
    }
    return telphone.replaceFirst("_", "");
  }
  
  public static String getUserId(String str)
  {
    String UserId = "";
    try
    {
      UserId = str.substring(str.indexOf("accountinfo"));
      
      UserId = UserId.substring(UserId.indexOf(",", 2) + 1);
      UserId = UserId.substring(0, UserId.indexOf(","));
    }
    catch (Exception e)
    {
      UserId = "";
    }
    return UserId;
  }
  
  public static String getTableName(String fileName)
  {
	  
	  String table_name = "";
	  String deviceID ="";
	  String vdate =fileName.substring(fileName.indexOf("_",21), fileName.indexOf("_",21)+9);
	  deviceID = fileName.substring(21);
	  deviceID = deviceID.substring(0,deviceID.indexOf("_"));
      
	  String city = getcityName(deviceID);
	  
	  table_name = fileName.substring(0,21)+city+vdate; 
     
    return table_name;
  }
  
  
  public static String getTableName_tomorrow(String fileName)
  {
	  
	  String table_name = "";
	  String deviceID ="";
	  String vdate =fileName.substring(fileName.indexOf("_",21)+1, fileName.indexOf("_",21)+9);
	  deviceID = fileName.substring(21);
	  deviceID = deviceID.substring(0,deviceID.indexOf("_"));
      
	  String city = getcityName(deviceID);
	  //System.out.println("vdate:"+vdate);
	  table_name = fileName.substring(0,21)+city+"_"+TOOLS.getDate.getTomorrow(vdate); 
     
    return table_name;
  }
  // TOOLS.getDate.
  
  public static String getTableName_getYesterday(String fileName)
  {
	  
	  String table_name = "";
	  String deviceID ="";
	  String vdate =fileName.substring(fileName.indexOf("_",21)+1, fileName.indexOf("_",21)+9);
	  deviceID = fileName.substring(21);
	  deviceID = deviceID.substring(0,deviceID.indexOf("_"));
      
	  String city = getcityName(deviceID);
	  
	  table_name = fileName.substring(0,21)+city+"_"+TOOLS.getDate.getYesterday(vdate); 
     
    return table_name;
  }
  
  
  public static String getcityName(String deviceID){
	  String city = deviceID ;
	  String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
    if (db.createConn())
    {
    	db.query("select city from cdn_hw_device where device_id = '"+deviceID+"'");
    	while (db.next()) {
    		city = db.getValue("city");
    		
    	}
    	
    }
    db.closeRs();
    db.closeStm();
    db.closeConn();
	  
	  
	  return city ;
  }
  
  
  public static List<List<String[]>> getThreadFileList() {
		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
		String[] dbinfo = DBInfo.split("\\|");
		DBAccess_new db = new DBAccess_new(dbinfo);
		List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		List<String[]> ListFile = new ArrayList<String[]>();
		 

		

		String sql = "select path,fileName from cdn_hw_log_unzip where status is null or status = '' order by vtime ";

		int i = 0;
		if (db.createConn()) {
			db.query(sql);
			while (db.next()) {
				String fileName[] = new String[2];
				fileName[0] = db.getValue("path");// db.getValue(1);
				fileName[1] = db.getValue("fileName");
				ListFile.add(fileName);				 
				i++;
				if (i == threadNum) {
					List<String[]> ListFileName_1 = new ArrayList<String[]>();

					for (int m = 0; m < ListFile.size(); m++) {
						ListFileName_1.add(ListFile.get(m));
					}
					ListListFile.add(ListFileName_1);
					ListFile.clear();
					i = 0;
				}

			}
		}
		 if (ListFile.size()>0) {
		ListListFile.add(ListFile);}
		 
		db.closeRs();
		db.closeConn();
		return ListListFile;
	}
  
}
