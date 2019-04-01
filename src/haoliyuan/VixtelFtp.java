package haoliyuan;
// 飞思达ftp下载程序 for HaoLiyuan
import TOOLS.FtpUtil;
import TOOLS.getDate;
import TOOLS.getProperties;
import TOOLS.FtpUtil;

public class VixtelFtp {
	public static	String  VixtelFtpServer = getProperties.getPropertie("VixtelFtpServer").trim();
	 public static String VixtelFtpUser = getProperties.getPropertie("VixtelFtpUser").trim();
	 public static String VixtelFtpPass = getProperties.getPropertie("VixtelFtpPass").trim();
	 public static	String VixtelFtpPath = getProperties.getPropertie("VixtelFtpPath").trim();
	 public static	String VixtelLocalPath = getProperties.getPropertie("VixtelLocalPath").trim();
	 public static	String VixtelFtPort =   getProperties.getPropertie("VixtelFtPort").trim();
	 
	 
	 public static	String driver = getProperties.getPropertie("mysqldriver").trim();
	 public static	String url = getProperties.getPropertie("localurl").trim();
	 public static	String user = getProperties.getPropertie("localuser").trim();
	 public static	String passwd = getProperties.getPropertie("localpassword").trim();
	 
	 
		public static void main(String[] args) {
			String today=getDate.getLastWeekday(getDate.getToday());
			
			String fileName = "Provincial-link-"+today+".csv" ;
			
			//System.out.println(fileName);
			//fileName = "Provincial-link-20190218.csv"; 
					 
		    try {
		         FtpUtil.downloadFtpFile(VixtelFtpServer, VixtelFtpUser, VixtelFtpPass, Integer.parseInt(VixtelFtPort), VixtelFtpPath, VixtelLocalPath, fileName);  
		         System.out.println("SUCCESS!!!	Download file "+fileName);
		    }
		        catch(NullPointerException e){
		        	System.out.println("ERROR!!!	There is not  "+fileName);
		        }
		}
	 
	 

}
