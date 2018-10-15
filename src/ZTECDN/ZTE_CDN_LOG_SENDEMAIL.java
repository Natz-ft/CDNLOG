package ZTECDN;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import TOOLS.DBAcess;
import TOOLS.SendMailUtil;
import TOOLS.getProperties;

public class ZTE_CDN_LOG_SENDEMAIL {
	
	 //private static String PAGE = getProperties.getPropertie("PAGE").trim();
	
	public  static  String[] receiverAddrs = getProperties.getPropertie("receiverAddr").trim().split("\\|");
	
	//SELECT * from (SELECT TermialIP,ServerIP,ContentID,responsecode,count(*) s from bcs_cdn_024_as_20180923 where responsecode like '4%' GROUP BY TermialIP,ServerIP,ContentID,responsecode ) a ORDER BY s desc 
	
	  public static void main(String[] args) {
	     
			String yesterday = "";
			 if (args.length<1){
				 yesterday = TOOLS.getDate.getYesterday();
			 }
			 else 
			 {
				 yesterday = args[0];
			 }
	       
	       String message = ""; 
	       message= getMailMessage(yesterday);
	       
	       SendMailUtil se = new SendMailUtil();
	         
	       try {
			se.doSendHtmlEmail("CDN质量监控邮件---"+yesterday, message, receiverAddrs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	       
	    }
	  
	  public static String getMailMessage(String vdate){
		  String MailMessage = "";
			DBAcess db = new DBAcess();
			String sql = "select city,vdate,users,duration,volume,avg_rat,nums_all, nums_success,responsetime from cdn_zte_acc_day  where  vdate ='"+vdate+"'"; 	 
			 
		 
			if (db.createConnLocal()) {
				db.query(sql);
			 
				while (db.next()) {				 
					String city = db.getValue("city");
					String users = db.getValue("users");
					String duration = db.getValue("duration");
					String volume = db.getValue("volume");
					String avg_rat = db.getValue("avg_rat");
					String nums_all = db.getValue("nums_all");
					String nums_success = db.getValue("nums_success");
					String responsetime = db.getValue("responsetime");
					
					if (city.equals("as")){
						city = "鞍山";
					}
					else if(city.equals("bx")){
						city = "本溪";
					}
					else if(city.equals("dd")){
						city = "丹东";
					}
					else if(city.equals("dl")){
						city = "大连";
					}	
					else if(city.equals("ly")){
						city = "辽阳";
					}
					else if(city.equals("yk")){
						city = "营口";
					}
				    
					  float ft =Float.parseFloat(avg_rat)*8/1024;
					
					 BigDecimal   bd  =   new  BigDecimal((double)ft);  
					 bd   =  bd.setScale(2,4); 
					 ft   =  bd.floatValue(); 
					 
					 
					 float ft_success  =Float.parseFloat(nums_success)/+Float.parseFloat(nums_all)*100;
					 BigDecimal   bd_success  =   new  BigDecimal((double)ft_success); 
					 
					 bd_success   =  bd_success.setScale(4,4); 
					 ft_success = bd_success.floatValue(); 
					 
					 int nums_error =Integer.valueOf(nums_all) -  Integer.valueOf(nums_success);
					
					MailMessage = MailMessage+city+":\t"+" 平均下载速率（Mb ps）:"+ ft  + "&thinsp;&thinsp;&thinsp;&thinsp;"+"播放成功率："+ft_success+"%"+"&thinsp;&thinsp;&thinsp;&thinsp;"+ "播放错误次数："+ nums_error+ "&thinsp;&thinsp;&thinsp;&thinsp;"+   " 平均首字节响应时长（毫秒）:"+Float.parseFloat(responsetime)/Float.parseFloat(nums_all)+"<br>";
					
			}
				db.closeRs();
				db.closeStm();
				
				sql = "select   sum(users) users,sum(duration) duration, sum(volume) volume,sum(nums_all) nums_all, sum(nums_success) nums_success,sum(responsetime) responsetime from cdn_zte_acc_day  where  vdate ='"+vdate+"'";
				
				db.query(sql);
				 
				while (db.next()) {				 
					 
					String users = db.getValue("users");
					String duration = db.getValue("duration");
					String volume = db.getValue("volume");
					 
					String nums_all = db.getValue("nums_all");
					String nums_success = db.getValue("nums_success");
					String responsetime = db.getValue("responsetime");
					
					  float ft =Float.parseFloat(volume)*8*1024*1024/(Float.parseFloat(duration)*60*60);
						
					 BigDecimal   bd  =   new  BigDecimal((double)ft);  
					 bd   =  bd.setScale(2,4); 
					 ft   =  bd.floatValue(); 
					 
					 
					 float ft_success  =Float.parseFloat(nums_success)/+Float.parseFloat(nums_all)*100;
					 BigDecimal   bd_success  =   new  BigDecimal((double)ft_success); 
					 
					 bd_success   =  bd_success.setScale(4,4); 
					 ft_success = bd_success.floatValue(); 
					 int nums_error =Integer.valueOf(nums_all) -  Integer.valueOf(nums_success);
				 
					MailMessage = MailMessage+"中兴平面平均:"+"&thinsp;&thinsp;&thinsp;&thinsp;"+"平均下载速率（Mb ps）:"+ft+"&thinsp;&thinsp;&thinsp;&thinsp;"+"播放成功率："+ft_success+"%"+"&thinsp;&thinsp;&thinsp;&thinsp;"+"播放错误次数："+ nums_error+ "&thinsp;&thinsp;&thinsp;&thinsp;"+" 平均首字节响应时长（毫秒）:"+Float.parseFloat(responsetime)/Float.parseFloat(nums_all)+"<br>";
					
			}
				
				MailMessage = MailMessage +"=========================================================================================================="+"<br>";
				MailMessage = MailMessage +"地市"+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+"终端IP"+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+"服务器IP"+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+"节目ID"+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+"错误代码"+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+"错误次数"+"<br>";
				
				
				//sql = "select   sum(users) users,sum(duration) duration, sum(volume) volume,sum(nums_all) nums_all, sum(nums_success) nums_success,sum(responsetime) responsetime from cdn_zte_acc_day  where  vdate ='"+vdate+"'";
				sql ="SELECT city,TermialIP,ServerIP,ContentID,responsecode,nums from cdn_zte_acc_error where vdate   ='"+vdate+"' ORDER BY city  , nums desc ";
				db.query(sql);
				 
				while (db.next()) {				 
					 
					String city = db.getValue("city");
					String TermialIP = db.getValue("TermialIP");
					String ServerIP = db.getValue("ServerIP");
					 
					String ContentID = db.getValue("ContentID");
					String responsecode = db.getValue("responsecode");
					String nums = db.getValue("nums");
					
					if (city.equals("as")){
						city = "鞍山";
					}
					else if(city.equals("bx")){
						city = "本溪";
					}
					else if(city.equals("dd")){
						city = "丹东";
					}
					else if(city.equals("dl")){
						city = "大连";
					}	
					else if(city.equals("ly")){
						city = "辽阳";
					}
					else if(city.equals("yk")){
						city = "营口";
					}
					
				 
					MailMessage = MailMessage+city+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+TermialIP+"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+ ServerIP  +"&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+ContentID+  "&thinsp;"+  responsecode+ "&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;"+nums+  "&thinsp;"+  "<br>";
					
			}
		  
	  
			 		
				
				
		  
	  }
			db.closeRs();
			db.closeStm();
			
			
			
			
			
		db.closeConn();
		return MailMessage;

}
	  }
