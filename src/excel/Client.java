package excel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

 
/**
12  * @author Hongten
13  * @created 2014-5-18
14  */
public class Client {

     public static void main(String[] args)  {
        // SaveData2DB saveData2DB = new SaveData2DB();
        // saveData2DB.save();
         //System.out.println("end");
    	 
    	 //ExcelIntoDB("F://39.134.196.1.xls");;
     try {
       String path="F:\\tmp\\402";
    	// String path= "F:/集团组巡/test";
    	 
    	 File file=new File(path);
     File[] tempList = file.listFiles();
   
     System.out.println("该目录下对象个数："+tempList.length);
     for (int i = 0; i < tempList.length; i++) {
      if (tempList[i].isFile()) {
    	  String fileName = tempList[i].getName(); 
       System.out.println("文     件："+tempList[i]);
       
       String inputStr = tempList[i].toString();
       
       if (fileName.contains("index")){continue;}
       if (fileName.contains("zip")){continue;}
       
     
    
       String regexString="\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
       Pattern p = Pattern.compile(regexString);
       String parentString=fileName;
       Matcher m = p.matcher(parentString);
       boolean result = m.find();
       while(result) {
        
         // ExcelIntoDB(inputStr); // 扫描结果入库
    	   
    	  ExcelIntoDB_port(inputStr); // 端口入库
    	   
    	   result = m.find();
       }
       
       
       
      }
      if (tempList[i].isDirectory()) {
      // System.out.println("文件夹："+tempList[i]);
      }
     }
     }
     catch (Exception e){
    	 e.printStackTrace();
    	  
     }
     System.out.println("end");
    }
     
    public static void ExcelIntoDB(String filePath) throws IOException, SQLException{
    	 SaveData2DB saveData2DB = new SaveData2DB(filePath);
         saveData2DB.save();
        
    	
    }
    
    
    
    public static void ExcelIntoDB_port(String filePath) throws IOException, SQLException{
   	 SaveData2DB saveData2DB = new SaveData2DB(filePath);
        saveData2DB.save_port();
       
   	
   }
    
    
    
    
    }