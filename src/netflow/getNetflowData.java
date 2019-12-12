package netflow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import TOOLS.DBAcess;

public class getNetflowData {
	  public static String getPostData(String url,String json) throws ClientProtocolException, IOException{
		  String respContent = null;
		  
		    HttpPost httpPost = new HttpPost(url);
		    CloseableHttpClient client = HttpClients.createDefault();    
		    
		    String[] drivierNames = { "HOST", "HOSTV" };		    
		    StringEntity entity = new StringEntity(json, "utf-8");		    
		    entity.setContentEncoding("UTF-8");
		    entity.setContentType("application/json");
		    httpPost.setEntity(entity);
		  
		    HttpResponse resp = client.execute(httpPost);
		    if (resp.getStatusLine().getStatusCode() == 200)
		    {
		      HttpEntity he = resp.getEntity();
		      respContent = EntityUtils.toString(he, "UTF-8");
		    }
		    respContent = respContent.replace(":null", ":\"\"");
		     
		    respContent = respContent.replace("Src_servid,bps,maxbps\n", "");		    
		    respContent = respContent.replace("Src_ipid,bps,maxbps\n", "");
		    respContent = respContent.replace("Dst_servid,bps,maxbps\n", "");
		    respContent = respContent.replace("\n", "|");
		  return respContent;
	  }
	  
		public static List<String> GetSql(String str,String type,String vdate,String tableName){
			List<String> list = new ArrayList<String>();
			
			 String[] strs  =str.split("\\|");
		     for (int i=0;i<strs.length;i++){
		    	// System.out.println("i:"+i+"\t"+strs[i]);
		    	 String [] strss = strs[i].split(",");
		     for (int j=0;j<strss.length;j++){
		    	// System.out.println("j:"+j+"\t"+strss[j]);
		    	 }	
		      try {
		    	 String sql = "insert into "+tableName+" (main_col,vdate,sub_col,avg_data,max_data) values('"+type+"','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]+")";
		    	 System.out.println(sql);
		    	 list.add(sql);   
		      }
		      catch(Exception e)
		      {
		    	  e.printStackTrace();
		    	  System.out.println(type+"','"+vdate+"','"+strss[0]+"',"+strss[1]+","+strss[2]);
		      }
		    	
		    	
		     }
			
			return list;
			
		}
		public static void InToDB(List<String> list ) throws SQLException{
		    DBAcess db = new DBAcess();
		    if (db.createConnLocal())
		    {
		     
		     for (int i=0;i<list.size();i++)
		     {
		    	 db.insert(list.get(i));
		     }
		     
		    	 
		    }
		    db.closeRs();
		    db.closeStm();		    
		    db.closeConn();
		}
		
	  
	  
	  
	  
 public static void main(String[] args) throws Exception{
	                    
		  String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
	//		String json = "{\"time-range\":\""+"20181128000000"+"-"+"20181129000000"+"\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"缃戝唴-杈藉畞绉诲姩\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堢渷涓績锛塡",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堢渷涓績锛塡",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-闉嶅北\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-鏈邯\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-涓逛笢\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-澶ц繛\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-杈介槼\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-涓叴锛堜笅娌夛級-钀ュ彛\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-鏈濋槼\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-鎶氶『\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-闃滄柊\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-钁姦宀沑",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-閿﹀窞\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-鐩橀敠\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-娌堥槼\",\"鍐呭缃戠粶-浜掕仈缃戠數瑙�-鍗庝负锛堜笅娌夛級-閾佸箔\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"缃戝唴-杈藉畞绉诲姩\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
			 
        //String str =getPostData(url,json);
        //str = str.replace("Src_servid,bps,maxbps\n", "");
       // System.out.println(str);
      //  str = str.replace("\n", "|");
 
 }
	  
}
