package netflow;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class getNetflowData {
	  public static String getPostData(String url,String json) throws ClientProtocolException, IOException{
		  String respContent = null;
		  
		    HttpPost httpPost = new HttpPost(url);
		    CloseableHttpClient client = HttpClients.createDefault();
		  
		    
		    //JSONObject jsonParam = new JSONObject();
			
	 //		String json = "{\"time-range\":\"20181010000000-20181011000000\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"day\",\"topN\":0}";
	 
		    
		    String[] drivierNames = { "HOST", "HOSTV" };
		    
		   // jsonParam.put("userName", "businessAdmin");
		    
		    StringEntity entity = new StringEntity(json, "utf-8");
		    
		    entity.setContentEncoding("UTF-8");
		    entity.setContentType("application/json");
		    httpPost.setEntity(entity);
		   // System.out.println();
		    HttpResponse resp = client.execute(httpPost);
		    if (resp.getStatusLine().getStatusCode() == 200)
		    {
		      HttpEntity he = resp.getEntity();
		      respContent = EntityUtils.toString(he, "UTF-8");
		    }
		    respContent = respContent.replace(":null", ":\"\"");
		     
		    respContent = respContent.replace("Src_servid,bps,maxbps\n", "");
		    
		    respContent = respContent.replace("Dst_servid,bps,maxbps\n", "");
		    respContent = respContent.replace("\n", "|");
		  return respContent;
	  }
	  
 public static void main(String[] args) throws Exception{
		  
		  String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
			String json = "{\"time-range\":\"20181010000000-20181011000000\",\"portfilters\":[{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\",\"Eth-Trunk34\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"day\",\"topN\":0}";
			 
        String str =getPostData(url,json);
        //str = str.replace("Src_servid,bps,maxbps\n", "");
        System.out.println(str);
      //  str = str.replace("\n", "|");
 
 }
	  
}
