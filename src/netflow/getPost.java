package netflow;

import java.util.ArrayList;
import java.util.List;

public class getPost {
	
	/*

commandsn string 64 必填字符 指令流水号， 指令唯一标识， 由时间戳（ 秒） +自增序列组成
objectType int 4 必填	整型 处置对象类型：1： 域名， 例如： aaa.com;2： URL， 例如：http://aaa.com/news.mpg
object string 128 必填	字符 如： aaa.com 或者例如：http://aaa.com/news.mpgs
effectiveDate string 19 必填字符 指令生效时间， 本地时间,采用	yyyy-MM-dd HH:mm:ss 格式。
expiredDate string 19 必填	字符 指令失效时间， 本地时间,采用yyyy-MM-dd HH:mm:ss 格式。
 
*
*/
	//新增接口
	public static String createNewDomain(String url, String commandsn,String objectType,String object,String effectiveDate,String expiredate ) {
		String strReturn = "";
		
		strReturn = url+commandsn+objectType+object+effectiveDate+expiredate;
				
		return strReturn;
	}
	//删除接口
	public static String deletDomain(String url, String commandsn,String object) {
		String strReturn = "";
		
		strReturn = url+commandsn+object;
				
		return strReturn;
	}
	//查询接口
	
	public static List<String[]> queryDomain(String URL){
		  
		 
		String [] a = {"a1","a2","a3","a4","a5"};
		String [] b = {"b1","b2","b3","b4","b5"};
		List<String[]> list=new ArrayList<String[]>();
		
	  list.add(a);
	  list.add(b);
	  return list;
	}
	
	

}
