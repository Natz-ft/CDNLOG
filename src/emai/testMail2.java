package emai;



import  emai.ReceiveMailHandler;
 
public class testMail2 {
 
	public static void main(String[] args) {
		try {
		String contetn=	new ReceiveMailHandler().receiveMail2("13604218538@139.com", "PaTr@1234","TEST");
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
