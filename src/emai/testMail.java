package emai;

public class testMail {
	public static void main(String[] args) throws Exception {
		
		System.out.println("sss");
		Email email = new Email();
		email.setSender("dirxu@126.com");
		email.setContent("adadfasfdsafdasf");
		email.setTo("dirxu@126.com|13604218538@139.com");
		email.setSubject("dirxu happy1");
		email.setImagePath("F:\\pic\\A003.png|F:\\pic\\qq.jpg");
		
		
		EmailMessage mmss =new EmailMessage(email)  ;
		 mmss.setEmailAccount("dirxu@126.com");
		 mmss.setReceiver("13604218538@139.com");
		 
		 mmss.setUsername("dirxu@126.com");
		 mmss.setPassword("PaTr@1234");
		 mmss.setSubject("dirxu happy！");
		 
		 MailSender ms = new MailSender();
		 
		 ms.sendEmail(mmss);
		 
		
		//System.out.println(mmss.getHost());
		
	}

}
