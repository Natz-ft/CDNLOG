package netflow;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import TOOLS.DBAcess;
import TOOLS.getDate;

public class netflowInToDB20190124 {
	
	public static void main(String[] args)
		    throws Exception
		  {
		    String yesterday = "";
		    if (args.length < 1) {
		      yesterday = getDate.getYesterday();
		    } else {
		      yesterday = args[0];
		    }
		    
		    //IntoDB_CDN(yesterday);
		    //IntoDB_cache(yesterday);
		    //IntoDB_WEB_CDN_HW(yesterday);
		    IntoDB_OTT(yesterday);
		    //IntoDB_cache_hw_city(yesterday);
		  }
	
	public static void IntoDB_cache(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1490065007712";
		
		 String json = "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\",\"Eth-Trunk36\",\"Eth-Trunk42\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk43\",\"Eth-Trunk34\",\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065093209\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"缓存\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
				 //"{\"time-range\":\""+vtime+"-"+vtime1+"\",    \"portfilters\":[{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"}],\"viewid\":\"1490065093209\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"缓存\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		             //{\"time-range\":\"20181010000000-2018101100\",\"portfilters\":[{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"}],\"viewid\":\"1490065093209\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"缓存\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"day\",\"topN\":0}
		 
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('CACHE','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();
		  
		
	}
	
	public static void IntoDB_cache_hw_city(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517618927648";
		
		 String json = "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk64\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1543825700520\",\"aggregation\":[\"Dst_servid\"],\"filters\":[{\"Dst_servid\":{\"values\":[\"地市-\"],\"type\":\"match\"},\"Src_servid\":{\"values\":[\"内容网络-缓存-华为-下沉大文件\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
				     //"{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"}],\"viewid\":\"1490065093209\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"缓存\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		             //{\"time-range\":\"20181010000000-2018101100\",\"portfilters\":[{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"}],\"viewid\":\"1490065093209\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"缓存\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"day\",\"topN\":0}
		 
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('CACHE_HW_CITY','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();
		  
		
	}
	
	
	
	
	public static void IntoDB_CDN(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		String s0 = "000000";
		vtime = vdate+s0;
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1490065007712";
		
	    // String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		  String json  =  "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 
		  System.out.println(json);
		 String str = getNetflowData.getPostData(url,json);
		
		 // System.out.println(str);
		  
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('WEB-CDN','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();
		  
		
	}
	

	public static void IntoDB_OTT(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 //String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
			// String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 
	   //String json = "{\"time-range\":\"20190101000000-20190102000000\",\"portfilters\":[{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk34\",\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-中兴（下沉）-鞍山\",\"内容网络-互联网电视-中兴（下沉）-本溪\",\"内容网络-互联网电视-中兴（下沉）-丹东\",\"内容网络-互联网电视-中兴（下沉）-大连\",\"内容网络-互联网电视-中兴（下沉）-辽阳\",\"内容网络-互联网电视-中兴（下沉）-营口\",\"内容网络-互联网电视-华为（下沉）-朝阳\",\"内容网络-互联网电视-华为（下沉）-抚顺\",\"内容网络-互联网电视-华为（下沉）-阜新\",\"内容网络-互联网电视-华为（下沉）-葫芦岛\",\"内容网络-互联网电视-华为（下沉）-锦州\",\"内容网络-互联网电视-华为（下沉）-盘锦\",\"内容网络-互联网电视-华为（下沉）-沈阳\",\"内容网络-互联网电视-华为（下沉）-铁岭\",\"内容网络-互联网电视-中兴（下沉）-大连（新）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 String json = "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk34\",\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-中兴（下沉）-鞍山\",\"内容网络-互联网电视-中兴（下沉）-本溪\",\"内容网络-互联网电视-中兴（下沉）-丹东\",\"内容网络-互联网电视-中兴（下沉）-大连\",\"内容网络-互联网电视-中兴（下沉）-辽阳\",\"内容网络-互联网电视-中兴（下沉）-营口\",\"内容网络-互联网电视-华为（下沉）-朝阳\",\"内容网络-互联网电视-华为（下沉）-抚顺\",\"内容网络-互联网电视-华为（下沉）-阜新\",\"内容网络-互联网电视-华为（下沉）-葫芦岛\",\"内容网络-互联网电视-华为（下沉）-锦州\",\"内容网络-互联网电视-华为（下沉）-盘锦\",\"内容网络-互联网电视-华为（下沉）-沈阳\",\"内容网络-互联网电视-华为（下沉）-铁岭\",\"内容网络-互联网电视-中兴（下沉）-大连（新）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('OTT','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();		
	}
	
	
	public static void IntoDB_OTT_CITY(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
		
		// String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 //String json=     "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 String json = " {\"time-range\":\"20190101000000-20190102000000\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk34\",\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-中兴（下沉）-鞍山\",\"内容网络-互联网电视-中兴（下沉）-本溪\",\"内容网络-互联网电视-中兴（下沉）-丹东\",\"内容网络-互联网电视-中兴（下沉）-大连\",\"内容网络-互联网电视-中兴（下沉）-辽阳\",\"内容网络-互联网电视-中兴（下沉）-营口\",\"内容网络-互联网电视-华为（下沉）-朝阳\",\"内容网络-互联网电视-华为（下沉）-抚顺\",\"内容网络-互联网电视-华为（下沉）-阜新\",\"内容网络-互联网电视-华为（下沉）-葫芦岛\",\"内容网络-互联网电视-华为（下沉）-锦州\",\"内容网络-互联网电视-华为（下沉）-盘锦\",\"内容网络-互联网电视-华为（下沉）-沈阳\",\"内容网络-互联网电视-华为（下沉）-铁岭\",\"内容网络-互联网电视-中兴（下沉）-大连（新）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 //System.out.println(json);
		
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('OTT_CITY','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 //System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();
		  
		
	}
	
	public static void IntoDB_WEB_CDN_HW(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
		
		// String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 //String json=     "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 String json="{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1528859770050\",\"aggregation\":[\"Dst_servid\"],\"filters\":[{\"Dst_servid\":{\"values\":[\"地市-\"],\"type\":\"match\"},\"Src_servid\":{\"values\":[\"内容网络-自建CDN-华为\"],\"type\":\"match\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
			
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('WEB_CDN_HW','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();		  
		
	}
	
	
	public static void IntoDB_WEB_CDN_ZTE(String vdate) throws ParseException, ClientProtocolException, IOException, SQLException{
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		
		 String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517618927648";
		
		// String json = "{\"time-range\":\""+vdate+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1490065142525\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 //String json=     "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 String json = "{\"time-range\":\""+vtime+"-"+vtime1+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1528860205738\",\"aggregation\":[\"Dst_servid\"],\"filters\":[{\"Dst_servid\":{\"values\":[\"地市-\"],\"type\":\"match\"},\"Src_servid\":{\"values\":[\"内容网络-自建CDN-中兴\"],\"type\":\"match\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
		 
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
		    	 String sql = "insert into cdn_netflow (main_col,vdate,sub_col,avg_data,max_data) values('WEB_CDN_ZTE','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 db.insert(sql);	     
		     }
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();		  
		
	}
	
	
	

}
