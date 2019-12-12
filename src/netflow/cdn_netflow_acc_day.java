package netflow;

import java.util.List;

import TOOLS.DBAcess;
import TOOLS.getDate;
import TOOLS.getProperties;

public class cdn_netflow_acc_day {
	public static void main(String[] args) throws Exception {
		String yesterday = "";
		String today = "";
		if (args.length < 1) {
			yesterday = getDate.getYesterday();
			today = getDate.getToday();
		} else {
			yesterday = args[0];
			today =  getDate.getTomorrow( yesterday);
		}
		
		yesterday = yesterday + "000000";
		today = today + "000000";
		 
		
		 String cache_url =  getProperties.getPropertie("cache_url").trim();
		 String cache_json = getProperties.getPropertie("cache_json").trim();		 
		 netflowInToDB.netFlowIntoDB(yesterday,today,cache_url,cache_json,"CACHE","cdn_netflow",yesterday.substring(0, 8));
		  
		 String cache_hw_city_url =  getProperties.getPropertie("cache_hw_city_url").trim();
		 String cache_hw_city_json = getProperties.getPropertie("cache_hw_city_json").trim();	
	
		 netflowInToDB.netFlowIntoDB(yesterday,today,cache_hw_city_url,cache_hw_city_json,"CACHE_HW_CITY","cdn_netflow",yesterday.substring(0, 8));
		 
		 
		 String cache_zte_city_url =  getProperties.getPropertie("cache_zte_city_url").trim();
		 String cache_zte_city_json = getProperties.getPropertie("cache_zte_city_json").trim();	
		 netflowInToDB.netFlowIntoDB(yesterday,today,cache_zte_city_url,cache_zte_city_json,"CACHE_ZTE_CITY","cdn_netflow",yesterday.substring(0, 8));
				 		   
		 
		 String WEB_CDN_url =  getProperties.getPropertie("WEB_CDN_url").trim();
		 String WEB_CDN_json = getProperties.getPropertie("WEB_CDN_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,WEB_CDN_url,WEB_CDN_json,"WEB-CDN","cdn_netflow",yesterday.substring(0, 8));
						
		 
		 String WEB_CDN_HW_url =  getProperties.getPropertie("WEB_CDN_HW_url").trim();
		 String WEB_CDN_HW_json = getProperties.getPropertie("WEB_CDN_HW_json").trim();
		 
		 netflowInToDB.netFlowIntoDB(yesterday,today,WEB_CDN_HW_url,WEB_CDN_HW_json,"WEB_CDN_HW","cdn_netflow",yesterday.substring(0, 8));
		 
		 

		 String WEB_CDN_ZTE_url =  getProperties.getPropertie("WEB_CDN_ZTE_url").trim();
		 String WEB_CDN_ZTE_json = getProperties.getPropertie("WEB_CDN_ZTE_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,WEB_CDN_ZTE_url,WEB_CDN_ZTE_json,"WEB_CDN_ZTE","cdn_netflow",yesterday.substring(0, 8));
		 
		 String OTT_url =  getProperties.getPropertie("OTT_url").trim();
		 String OTT_json = getProperties.getPropertie("OTT_json").trim();
		 netflowInToDB.netFlowIntoDB(yesterday,today,OTT_url,OTT_json,"OTT","cdn_netflow",yesterday.substring(0, 8));
		
		
		 
		 String OTT_DEVICE_url =  getProperties.getPropertie("OTT_DEVICE_url").trim();
		 String OTT_DEVICE_json = getProperties.getPropertie("OTT_DEVICE_json").trim();		
		 
		 netflowInToDB.netFlowIntoDB(yesterday,today,OTT_DEVICE_url,OTT_DEVICE_json,"OTT_DEVICE","cdn_netflow_ott_device",yesterday.substring(0, 8));
		
		 
		 
	}

}
