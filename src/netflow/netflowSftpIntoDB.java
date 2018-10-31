package netflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.ReadFromFile;
import TOOLS.SetLog;
import TOOLS.getDate;

public class netflowSftpIntoDB {

	public static void main(String[] args) throws Exception {
		//importLog("F:/ftp/rb_nrmzl_20181030.txt");
	    String yesterday = "";
	    if (args.length < 1) {
	      yesterday = getDate.getYesterday();
	    } else {
	      yesterday = args[0];
	    }
		boolean b= netflow_sftp.ftpnetflowfile(yesterday);
		//System.out.print("b:"+b);
		if (b){
			
		    DBAcess db = new DBAcess();
		    if (db.createConnLocal())
		    {
		      String sql = "select fileName,localpath from cdn_netflow_log_sftp where fileName like '%"+yesterday+".txt'  ";
		     // System.out.print("sql:"+sql);
		      db.query(sql);
		      while (db.next())
		      {
		        String fileName = db.getValue("fileName");
		        String path = db.getValue("localpath");
		        ImportDB(path, fileName);
		      }
		      db.closeRs();
		      db.closeStm();
		    }
		    db.closeConn();	
		
		}
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
	      String table_name = "cdn_netflow_ftp";
	      
	      List<String> list_sql = new ArrayList();
	      for (int i = 0; i < fileList.size(); i++)
	      {
	        String[] logLine = ((String)fileList.get(i)).split("\\|");
	        
	      
	        
	        if (logLine.length >= 1) {
	         
	           
	            
	            String sql = "insert into " + 
	              table_name + 
	              " (vdate,IDC,cache,ott_center,ott_city,cdn_self,tt_direct,self_resource,other_resource,backbone,backbone_inter,third_other,backbone_tt,dtotal) values" + 
	              " ('"+logLine[0]+"',"+logLine[1]+","+logLine[2]+","+logLine[3]+","+logLine[4]+","+logLine[5]+","+logLine[6]+","+logLine[7]+","+logLine[8]+","+logLine[9]+","+logLine[10]+","+logLine[11]+","+logLine[12]+","+logLine[13]+")"; 
	           // System.out.println("sql:"+sql );
	            list_sql.add(sql);
	           
	        }
	      }
	      DBAcess db = new DBAcess();
	      if (db.createConnLocal())
	      {
	        b = db.List_insert(list_sql); 	        
	        System.out.println("Import DB:\t" + filePath + fileName);
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
	      return false;
	    }
	    return b;
	  }
	
	
	
	
	
	public static List<String> importLog(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> fileList = new ArrayList<String>();
		try {

			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {

				tempString = ReadFromFile.replaceBlank(tempString); // ȥ���ɼ��ַ�
				if (line == 2) {
					fileList.add(tempString);
				}
				System.out.println(line + tempString);
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

}
