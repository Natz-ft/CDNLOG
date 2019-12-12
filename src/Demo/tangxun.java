package Demo;
import TOOLS.*;

public class tangxun {
	
	
	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
	
	  public static void main(String[] args)
			    throws Exception
			  {
			     
			    
			    boolean b = false;
				String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
				 System.out.println("DBInfo:\t"+DBInfo);
				String[] dbinfo = DBInfo.split("\\|");
				DBAccess_new db = new DBAccess_new(dbinfo);
			     if (db.createConn())
			    {
			    	 String tel = "";
			    	 db.query("SELECT str from abc ");
			 		
						while (db.next()) {
						 String str=	db.getValue("str");
						 if(str.length()>=11){
							 tel=str.substring(0,11);
						 }
						 else {
							   tel =str; 
						 }
						// System.out.println(str);	
					    System.out.println(str+"\t"+tel+"\t"+AccountValidatorUtil.isMobile(tel)); 
					    
					    db.update("update abc set tel ='"+tel+"',v_check='"+AccountValidatorUtil.isMobile(tel)+"' where str ='"+str+"'") ;
							
						}
			    }
			   
			    
			    
			    
			    
			    db.closeConn();  
			    
			    
			    
			    
			    
			  }
	
	 
}
