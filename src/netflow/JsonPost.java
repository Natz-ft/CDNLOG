package netflow;

import java.io.IOException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 
 
public class JsonPost {
	
	public static void main(String[] args) {
		String url = "http://159.138.1.196:9200/gspoc/idealmoney_rt_result/_search";
		
		//Post方式提交Json字符串，按照指定币种，指定时间点查询
		String json1 = "{\"query\":{\"bool\":{\"must\":[{\"match_phrase\":{\"imtype\":\"LTCUS\"}},{\"match_phrase\":{\"rtdatetime\":1521164922000}}]}}}";
		//Post方式提交json字符串，按照指定的币种和时间正序的方式获取前N条数据
		String json2 = "{\"query\":{\"match\":{\"imtype\":\"LTCUS\"}},\"sort\":[{\"rtdatetime\":{\"order\":\"desc\"}}],\"size\":3}";
		//Post方式提交Json字符串，按照指定币种，指定时间段查询	
		String json3 = "{\"query\":{\"bool\":{\"must\":[{\"match_phrase\":{\"imtype\":\"LTCUS\"}},{\"range\":{\"rtdatetime\":{\"gte\":1521164922000,\"lte\":1521164932000}}}]}}}";
		
		String json = json2;
		
		System.out.println(JsonPost.HttpPostWithJson(url, json));
		
	}
	
	public static String HttpPostWithJson(String url, String json) {
		String returnValue = "这是默认返回值，接口调用失败";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try{
			//第一步：创建HttpClient对象
		 httpClient = HttpClients.createDefault();
		 	
		 	//第二步：创建httpPost对象
	        HttpPost httpPost = new HttpPost(url);
	        
	        //第三步：给httpPost设置JSON格式的参数
	        StringEntity requestEntity = new StringEntity(json,"utf-8");
	        requestEntity.setContentEncoding("UTF-8");    	        
	        httpPost.setHeader("Content-type", "application/json");
	        httpPost.setEntity(requestEntity);
	       
	       //第四步：发送HttpPost请求，获取返回值
	       returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
	      
		}
		 catch(Exception e)
		{
			 e.printStackTrace();
		}
		
		finally {
	       try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
		 //第五步：处理返回值
	     return returnValue;
	}
	
	public static String createNewDomain(String url, String commandsn,String objectType,String object,String effectiveDate,String expiredate ) {
		String strReturn = "";
		
		strReturn = url+commandsn+objectType+object+effectiveDate+expiredate;
				
		return strReturn;
	}
	
 
 
}
