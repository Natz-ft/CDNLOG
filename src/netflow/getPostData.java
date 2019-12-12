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
public class getPostData {
	
	  public static String getPostDataWithcookie(String url,String cookie,String Content_Length ,String isPanda) throws ClientProtocolException, IOException{
		  String respContent = null;
		  
		    HttpPost httpPost = new HttpPost(url);
		    CloseableHttpClient client = HttpClients.createDefault();    
		    
		   // String[] drivierNames = { "HOST", "HOSTV" };		    
		    StringEntity entity = new StringEntity("goodsId=169&goodsNum=1&addressId=&invoiceFlag=&addressFlag=1", "utf-8");		    
		    entity.setContentEncoding("UTF-8");
		    entity.setContentType("application/json");
		    httpPost.setEntity(entity);
		    

		    
		     //设置头部信息
		     httpPost.setHeader("Host","m.chinagoldcoin.net");
		     httpPost.setHeader("Connection", "keep-alive");
		     httpPost.setHeader("Accept","application/json");
		     httpPost.setHeader("Content-Length", "60");
		     httpPost.setHeader("Origin","http://m.chinagoldcoin.net");
		     httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.1021.400 QQBrowser/9.0.2524.400");
		     httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		     httpPost.setHeader("Referer","http://m.chinagoldcoin.net/member/order/order.html?goodsId=169&goodsNum=1&isPanda=0&addressFlag=1");
		     httpPost.setHeader("Accept-Encoding","gzip, deflate");
		     httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-us;q=0.6,en;q=0.5;q=0.4");
		     httpPost.setHeader("Cookie","JSESSIONID=137D1C5C4FB04912C92C51E48A441B99; loginState=1; Hm_lvt_79551d2592621515873edbfb6d98c7c6=1559442270; Hm_lpvt_79551d2592621515873edbfb6d98c7c6=1559442393");
		      
		    
		    
		    
		    
		  
		    HttpResponse resp = client.execute(httpPost);
		    if (resp.getStatusLine().getStatusCode() == 200)
		    {
		      HttpEntity he = resp.getEntity();
		      respContent = EntityUtils.toString(he, "UTF-8");
		    }
		     
		  return respContent;
	  }
	  
	  public static void main(String[] args) throws Exception{
	  System.out.println(getPostDataWithcookie("http://m.chinagoldcoin.net/order/orderAdd","","",""));
	  }
}