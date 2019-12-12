package netflow;

import java.util.List;

import TOOLS.DBAcess;
import TOOLS.getDate;
import TOOLS.getProperties;

public class cdn_netflow_acc_hour {
	public static void main(String[] args) throws Exception {
		String yesterday = "";
		String today = "";
		if (args.length < 1) {
			yesterday = getDate.getYesterday();
			today = getDate.getToday();
		} else {
			yesterday = args[0];
		}
	    int endDate = getProperties.getINTPropertie("endDate"); 
	    
	    if ( Integer.parseInt(yesterday) >= endDate){
	    	System.out.println("小时报结束了");
	    	return ;
	    }
		
		  
		today= getDate.getHour() + "0000";
		yesterday = getDate.getEarlyOneHour() + "0000";
		/* String cache_url =  getProperties.getPropertie("cache_url").trim();
		 	 String cache_json = getProperties.getPropertie("cache_json").trim();			
		 
		 netflowInToDB.netFlowIntoDB(yesterday,today,cache_url,cache_json,"CACHE","cdn_netflow");
		  
		 String cache_hw_city_url =  getProperties.getPropertie("cache_hw_city_url").trim();
		 String cache_hw_city_json = getProperties.getPropertie("cache_hw_city_json").trim();	
	
		 netflowInToDB.netFlowIntoDB(yesterday,today,cache_hw_city_url,cache_hw_city_json,"CACHE_HW_CITY","cdn_netflow");
		    
		 
		 String WEB_CDN_url =  getProperties.getPropertie("WEB_CDN_url").trim();
		 String WEB_CDN_json = getProperties.getPropertie("WEB_CDN_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,WEB_CDN_url,WEB_CDN_json,"WEB_CDN","cdn_netflow");
		
		
		 
		 String OTT_url =  getProperties.getPropertie("OTT_url").trim();
		 String OTT_json = getProperties.getPropertie("OTT_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,OTT_url,OTT_json,"OTT","cdn_netflow");
		
		
		 
		 String WEB_CDN_HW_url =  getProperties.getPropertie("WEB_CDN_HW_url").trim();
		 String WEB_CDN_HW_json = getProperties.getPropertie("WEB_CDN_HW_json").trim();
		 
		 netflowInToDB.netFlowIntoDB(yesterday,today,WEB_CDN_HW_url,WEB_CDN_HW_json,"WEB_CDN_HW","cdn_netflow");
		 
		 */

		 String OTT_url =  getProperties.getPropertie("OTT_url").trim();
		 String OTT_json = getProperties.getPropertie("OTT_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,OTT_url,OTT_json,"OTT","cdn_netflow_hour",yesterday.substring(0, 10));
		 
		
	}

}
