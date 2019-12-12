package haoliyuan;

import TOOLS.getProperties;
import emai.Email;
import emai.EmailMessage;
import emai.MailSender;

public class NoteByEmail {
public static void main(String[] args) throws Exception {
		
		 
		Email email = new Email();
		email.setSender(getProperties.getPropertie("SEND_USER").trim());
		
		email.setContent("互联网通报指标监控");
		
		email.setTo(getProperties.getPropertie("haoliyuan_receiverAddr").trim());
		
		email.setSubject("互联网通报指标监控--——"+TOOLS.getDate.getToday());
		
		email.setAttachPath(getProperties.getPropertie("excelFile").trim() );
		//email.setImagePath("F:\\pic\\A003.png|F:\\pic\\qq.jpg");
		
		
		EmailMessage mmss =new EmailMessage(email)  ;
		 
		 
		 mmss.setUsername(getProperties.getPropertie("SEND_USER").trim());
		 mmss.setPassword(getProperties.getPropertie("SEND_PWD").trim());
		 
		 
	 
		 MailSender ms = new MailSender();
		 
		 ms.sendEmail(mmss);
		 
		
		//System.out.println(mmss.getHost());
		
	}

}
