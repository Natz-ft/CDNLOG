package netflow;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import TOOLS.DBAcess;
import TOOLS.getDate;
import TOOLS.getProperties;

public class netflowInToDB {
	
	public static void main(String[] args)
		    throws Exception
		  {
		    String yesterday = "";
		    String today = "";
		    if (args.length < 1) {
		      yesterday = getDate.getYesterday();
		      today = getDate.getToday();
		    } else {
		      yesterday = args[0];
		    }
		   // IntoDB_cache(yesterday+"000000",today+"000000");
		    //String str =  getProperties.getPropertie("cache_json").trim();
		   // System.out.println(str.replace("yesterday-today", "#############"));
		    
		  }
	
 
	
	public static void netFlowIntoDB(String yesterday,String today,String url,String json,String type,String tableName,String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		
		
					 
		 json=json.replace("yesterday-today", yesterday+"-"+today);		 
		 System.out.println(url);
		 System.out.println(json);
		 
		 String str = getNetflowData.getPostData(url,json);		
		  System.out.println(str);		  
		  
	      List<String> list= getNetflowData.GetSql(str,type, vdate,tableName);
	      getNetflowData.InToDB(list);
	      
		
	}
		
	

	
	
	public static void IntoDB_WEB_CDN_ZTE(String yesterday,String today) throws ParseException, ClientProtocolException, IOException, SQLException{
		 
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517618927648";
		
		// String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 //String json=     "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 String json = "{\"time-range\":\""+yesterday+"-"+today+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1528860205738\",\"aggregation\":[\"Dst_servid\"],\"filters\":[{\"Dst_servid\":{\"values\":[\"地市-\"],\"type\":\"match\"},\"Src_servid\":{\"values\":[\"内容网络-自建CDN-中兴\"],\"type\":\"match\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 
		 String str = getNetflowData.getPostData(url,json);
		
		  System.out.println(str);
		  
		    DBAcess db = new DBAcess();
		    if (db.createConnLocal())
		    {
		     
		     String[] strs  =str.split("\\|");
		     for (int i=0;i<strs.length;i++){
		    	 System.out.println("i:"+i+"\t"+strs[i]);
		    	 String [] strss = strs[i].split(",");
		     for (int j=0;j<strss.length;j++){
		    	// System.out.println("j:"+j+"\t"+strss[j]);
		    	 }		    	 
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('WEB_CDN_ZTE','"+yesterday.substring(0,8)+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();		  
		
	}
	
	
	

}
