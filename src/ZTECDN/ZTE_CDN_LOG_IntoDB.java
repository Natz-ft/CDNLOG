package ZTECDN;

import TOOLS.DBAcess;
import TOOLS.SetLog;
import TOOLS.getProperties;
import TOOLS.ReadFromFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.IOException;



public class ZTE_CDN_LOG_IntoDB
{
  public static int duration = getProperties.getINTPropertie("ZTEDuration");
  
  public static void main(String[] args)
    throws Exception
  {
    for (;;)
    {
      ImportDB();
      Thread.sleep(1000L);
    }
  }
  
  public static void ImportDB()
    throws Exception
  {
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      String sql = "select fileName,path from cdn_zte_log_untar where ( status='' or status is null ) order by fileName desc   ";
      db.query(sql);
      while (db.next())
      {
        String fileName = db.getValue("fileName").replace(".tar.gz", ".txt");
        String path = db.getValue("path");
        path = path + File.separator;
        
        String table_name = getTableName(fileName);
        
        CreateTable(table_name);
        if (ImportDB(path, fileName)) {
          SetLog.updateStatus("cdn_zte_log_untar", fileName.replace(".txt", ".tar.gz"), "YES");
        } else {
          SetLog.updateStatus("cdn_zte_log_untar", fileName.replace(".txt", ".tar.gz"), "ERROR");
        }
      }
      db.closeRs();
      db.closeStm();
    }
    db.closeConn();
  }
  
  public static void CreateTable()
  {
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      String sql = "SELECT \tCONCAT(\t\tSUBSTR(fileName, 1, 14),'_',SUBSTR(fileName, 20, 17)\t) TABLENAME FROM\tcdn_zte_log_untar WHERE\t(STATUS = '' OR STATUS IS NULL) GROUP BY\tCONCAT(\tSUBSTR(fileName, 1, 14),\t'_',\tSUBSTR(fileName, 29, 8)\t)   ";
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
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      String table_name = table.toUpperCase();
      
      String sql_table_name = "select count(*) num   from information_schema.tables t where t.table_schema='dirxu' and TABLE_NAME = '" + table_name + "'";
      
      db.query(sql_table_name);
      while (db.next())
      {
        int m = db.getIntValue("num");
        if (m == 0) {
          if (db.createTable("CREATE table " + table_name + " as SELECT * from cdn_zte_table where 1 <> 1 "))
          {
            System.out.println("Table:\t" + table_name + " CREATED! ");
            
            b = true;
            System.out.println("createIndex:\t" + db.createIndex(table_name, new StringBuilder("idx").append(table_name).toString(), "ContentID"));
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
          if ((logLine[6].contains("accountinfo"))||(table_name.contains("WEBCACHE")))
          {
            String cellPhone = getUserId(logLine[6]);
            
            String sql = "insert into " + 
              table_name + 
              " (filename,TermialIP,ServerIP,DomainName,RelativeURL,BeginTime,EndTime,duration,volume,ServiceType,ContentID,userNumber,responsetime,responsecode,FirstRespTime) values" + 
              " ('" + fileName + "','" + logLine[0] + "', '" + logLine[2] + "','"+ logLine[5] +"','" + logLine[6] + "', '" + 
              logLine[8] + "','" + logLine[9] + "','" + logLine[13] + 
              "','" + logLine[14] + "','" + logLine[23] + "','" + 
              logLine[28] + "','" + cellPhone + "','" + logLine[32] + "','" + logLine[37] + "','" + logLine[43] + "' )";
            //System.out.println("insert_sql:"+sql);
            list_sql.add(sql);
          }
        }
      }
      DBAcess db = new DBAcess();
      if (db.createConnLocal())
      {
        b = db.List_insert(list_sql);
        
        String sql = "insert into cdn_zte_log_DB (fileName,records) values ('" + fileName + "'," + fileList.size() + ") ";
        if (b) {
          b = db.insert(sql);
        }
        System.out.println("Import DB:\t" + filePath + fileName);
        db.closeConn();
        b = true;
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
    String table_name = fileName.substring(0, 36);
    
    table_name = table_name.replace("-", "_");
    table_name = table_name.replace(".", "_");
    
    //table_name = table_name.substring(0, 14) + table_name.substring(27, 36);
    table_name = table_name.substring(0, 14) + table_name.substring(18, 36);
    table_name = table_name.toUpperCase();
    return table_name;
  }
}
