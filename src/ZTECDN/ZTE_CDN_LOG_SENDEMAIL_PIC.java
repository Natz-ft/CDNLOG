package ZTECDN;

import TOOLS.getDate;
import TOOLS.getProperties;
import emai.Email;
import emai.EmailMessage;
import emai.MailSender;

public class ZTE_CDN_LOG_SENDEMAIL_PIC {
	
	public static void main(String[] args) throws Exception {
	    String yesterday = "";
	    if (args.length < 1) {
	      yesterday = getDate.getYesterday();
	    } else {
	      yesterday = args[0];
	    }
		
		 
		Email email = new Email();
		email.setSender(getProperties.getPropertie("SEND_USER").trim()); //SEND_USER
		
		email.setContent("");
		
		String emailSubject = getProperties.getPropertie("emailSubject").trim();
		
		  System.out.println("emailSubject1:"+emailSubject);
		  emailSubject= new String(emailSubject.getBytes("ISO-8859-1"));
		  System.out.println("emailSubject2:"+emailSubject);
		  
		  //emailSubject= new String(emailSubject.getBytes("ISO-8859-1"),"GBK");
		 // System.out.println("emailSubject3:"+emailSubject);
	   email.setTo( getProperties.getPropertie("receiverAddr").trim());
		
		email.setSubject( emailSubject+"--"+yesterday);
		
		String pic_paht = getProperties.getPropertie("phantomjs_pic_path").trim(); //phantomjs_pic_path
		
		email.setImagePath(pic_paht+"download.jsp_"+yesterday+".png|"+pic_paht+"error.jsp_"+yesterday+".png");
		
		
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
