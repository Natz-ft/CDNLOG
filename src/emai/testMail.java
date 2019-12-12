package emai;

import java.util.ArrayList;
import java.util.List;

public class testMail {
	public static void main(String[] args) throws Exception {
		
		System.out.println("sss");
		Email email = new Email();
		email.setSender("xuyangs@ln.chinamobile.com");
		email.setContent("www.baidu.com|ok<br>www.163.com|<br>www.sina.com|ok<br>");
		email.setTo("dirxu@126.com");
		email.setSubject("10002");
		email.setAttachPath("f:/student.txt|f:/hello.js");
		//email.setImagePath("F:\\pic\\A003.png|F:\\pic\\qq.jpg");
		
		
		EmailMessage mmss =new EmailMessage(email)  ;
		 mmss.setEmailAccount("xuyangs@ln.chinamobile.com");
		 mmss.setReceiver("dirxu@126.com");
		 
		 mmss.setUsername("xuyangs@ln.chinamobile.com");
		 mmss.setPassword("");
		 mmss.setSubject("dirxu happy！");
		 mmss.setContent("text,text");
		 
		 //List<String> attacPaths = new ArrayList<String>();		 
		 //attacPaths.add("c:/student.txt");
		 // mmss.setAttacPaths(attacPaths);
		 
		 MailSender ms = new MailSender();
		 
		 ms.sendEmail(mmss);
		 
		
		//System.out.println(mmss.getHost());
		
	}

}
