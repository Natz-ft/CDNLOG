package excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {

	  public static ArrayList<String> strings = new ArrayList<>();
	    public static boolean finished = false;
	    public static ArrayList<String> validateIp = new ArrayList<>();

 public static void main(String[] args) {
  String path="f:/";
  File file=new File(path);
  File[] tempList = file.listFiles();
  System.out.println("该目录下对象个数："+tempList.length);
  for (int i = 0; i < tempList.length; i++) {
   if (tempList[i].isFile()) {
   // System.out.println("文     件："+tempList[i]);
    
    String inputStr = tempList[i].toString();
 
    String regexString="\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    Pattern p = Pattern.compile(regexString);
    String parentString=inputStr;
    Matcher m = p.matcher(parentString);
    boolean result = m.find();
    while(result) {
     System.out.println("IP:"+m.group());
     result = m.find();
    }
    
    
    
   }
   if (tempList[i].isDirectory()) {
    System.out.println("文件夹："+tempList[i]);
   }
  }
 }
 
  

 
}
