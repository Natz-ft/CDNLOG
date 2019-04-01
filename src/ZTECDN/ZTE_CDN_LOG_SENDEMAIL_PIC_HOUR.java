package ZTECDN;

import TOOLS.getDate;
import TOOLS.getProperties;
import emai.Email;
import emai.EmailMessage;
import emai.MailSender;

public class ZTE_CDN_LOG_SENDEMAIL_PIC_HOUR {
	public  static  String[] PAGE = getProperties.getPropertie("phantomjs_PAGE_hour").trim().split("\\|");
	public static void main(String[] args) throws Exception {
	    String yesterday = "";
	    if (args.length < 1) {
	      yesterday = getDate.getEarlyOneHour();
	    		  
	    } else {
	      yesterday = args[0];
	    }
	 // 判断结束时间
	    int endDate = getProperties.getINTPropertie("endDate"); 
	    
	    if ( Integer.parseInt(yesterday) >=endDate){
	    	System.out.println("小时报结束了");
	    	return ;
	    }
		
		 
		Email email = new Email();
		email.setSender(getProperties.getPropertie("SEND_USER").trim()); //SEND_USER
		
		email.setContent("");
		
		String emailSubject = getProperties.getPropertie("emailSubject_hour").trim();
		
		  System.out.println("emailSubject1:"+emailSubject);
		  emailSubject= new String(emailSubject.getBytes("ISO-8859-1"));
		  System.out.println("emailSubject2:"+emailSubject);
		  
		  //emailSubject= new String(emailSubject.getBytes("ISO-8859-1"),"GBK");
		 // System.out.println("emailSubject3:"+emailSubject);
	   email.setTo( getProperties.getPropertie("receiverAddr_hour").trim());
		
		email.setSubject( emailSubject+"--"+ yesterday.substring(0,8)+"-"+yesterday.substring(8,10));
		
		String pic_paht = getProperties.getPropertie("phantomjs_pic_path_hour").trim(); //phantomjs_pic_path
		
		String page_path = "";
		
		for (int i=0;i<PAGE.length;i++){
			
			page_path = page_path+ pic_paht+PAGE[i]+"_"+yesterday+".png";
			if (i < (PAGE.length -1)){
				page_path = page_path +"|";
			}			
		}
		
		//email.setImagePath(pic_paht+"download.jsp_"+yesterday+".png|"+pic_paht+"error.jsp_"+yesterday+".png");
		
		email.setImagePath(page_path);
		
		
		EmailMessage mmss =new EmailMessage(email)  ;
		 mmss.setEmailAccount("dirxu@126.com");
		 
		 
		 mmss.setUsername(getProperties.getPropertie("SEND_USER").trim());
		 mmss.setPassword(getProperties.getPropertie("SEND_PWD").trim());//SEND_PWD
		 
		 
		 MailSender ms = new MailSender();
		 
		 ms.sendEmail(mmss);
		 
		
		 
		
	}
	
	public static String getIamge(){
		
		String pic = "";
		return pic ;
	}

}
