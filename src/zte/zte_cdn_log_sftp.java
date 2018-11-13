package zte;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import TOOLS.DBAccess_new;
import TOOLS.SftpManager;
import TOOLS.getDate;
import TOOLS.getProperties;

import com.jcraft.jsch.ChannelSftp;

public class zte_cdn_log_sftp {
	public static	String  sftpServer = getProperties.getPropertie("ZTEsftpServer").trim();
	 public static String sftpUser = getProperties.getPropertie("ZTEsftpUser").trim();
	 public static String sftpPass = getProperties.getPropertie("ZTEsftpPass").trim();
	 public static	String sftpPath = getProperties.getPropertie("ZTEsftpPath").trim();
	 public static	String localPath = getProperties.getPropertie("ZTElocalPath").trim();
	 public static	String sftPort =   getProperties.getPropertie("ZTEsftPort").trim();
	 public static	String  ZTEMUST =  getProperties.getPropertie("ZTEMustHave").trim();
	 public static	String  ZTEMDoNOT =  getProperties.getPropertie("ZTEDoNotHave").trim();
	 
	 public static	String driver = getProperties.getPropertie("mysqldriver").trim();
	 public static	String url = getProperties.getPropertie("localurl").trim();
	 public static	String user = getProperties.getPropertie("localuser").trim();
	 public static	String passwd = getProperties.getPropertie("localpassword").trim();
	 
	 public static int intervalTime = getProperties.getINTPropertie("SleepTime");
	 
		public static void main(String[] args) throws Exception {
			
			String yesterday ="",today="";
			
			KeyPairGenerator keyGen;
			
			 
			try {
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				Provider t[]=Security.getProviders();
				keyGen = KeyPairGenerator.getInstance("EC");
				keyGen.initialize(256);
				KeyPair keyPair = keyGen.generateKeyPair();
				} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			 if (args.length<1){
				 GetZTECdnLogfileAuto();
			 }
			 else {
				 System.out.println("args[0]\t"+args[0]);
				 GetZTECdnLogfileManual(args[0]);
				
			 }	
		 
				 
			  		
		}	
		
		public static void GetZTECdnLogfileAuto() throws Exception { 
			
			 	while (true) {
			 		String today=getDate.getToday();//  getTodayEarlyOneHour();
			 		GetZTECdnLogfileManual(getDate.getYesterday(today));
			 		GetZTECdnLogfileManual(today);
			 		
			 		 
					 
				
				Thread.sleep(1000 * 60 * intervalTime);
		    	}
			
		}
		
		
		public static void GetZTECdnLogfileManual(String today) throws Exception { 
			 System.out.println("FTPFile_date:\t"+today);
			String DBInfo =driver+"|"+url+"|"+user+"|"+passwd;
			String[] dbinfo = DBInfo.split("\\|");
			ChannelSftp sftp=null;
			sftp=SftpManager.connect(sftpServer, sftpUser, sftpPass, Integer.parseInt(sftPort));
			
 			List<String[]> filelist =  SftpManager.GetListFiles(sftp,sftpPath+File.separator+today);
			
			  String[] musts = ZTEMUST.split("\\|");
			  
			  String[] DoNots = ZTEMDoNOT.split("\\|");
			
			  DBAccess_new db = new DBAccess_new(dbinfo);
			  
		if (db.createConn()) {
			List<String> list_inserted = new ArrayList<String>();
            
			String sql1 = "select fileName from cdn_zte_log_sftp  ";
			
			db.query(sql1);
			
			while (db.next()){
		    	 list_inserted.add(db.getValue("fileName")) ;
		     }		    		 
		     

			w: for (int i = 0; i < filelist.size(); i++) {
				// 过滤 0，如果是目录，跳过循环
				if (!filelist.get(i)[0].equals("0")) {// dir is ignore 目录跳跳过 。
					continue w;
				}
				// 过滤 1 ，必须有的，如果没有 跳过循环
				for (int j = 0; j < musts.length; j++) {
					if (!filelist.get(i)[1].contains(musts[j])) {
						continue w;
					}
				}
				// 过滤 2 ，必须没有的，如果有 跳过循环
				for (int j = 0; j < DoNots.length; j++) {
					if (filelist.get(i)[1].contains(DoNots[j])) {
						continue w;
					}
				}
				
				// 过滤4，不下载已经下入库的文件 
				if (list_inserted.contains(filelist.get(i)[1])){
					continue w;	
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String vtime = df.format(new Date());
				
				// 下载
			    
				String savepath = localPath  + File.separator+ filelist.get(i)[2].replace(sftpPath, "");
				
				String sql = "insert into cdn_zte_log_sftp (fileName,vtime,path,localpath) values ('"+filelist.get(i)[1]+"','"+vtime+"','"+filelist.get(i)[2]+"' ,'"+savepath.replace("\\", "\\\\")+"')";
				
				if (SftpManager.download(sftp,	filelist.get(i)[2],	localPath  + File.separator+ filelist.get(i)[2].replace(sftpPath, ""),filelist.get(i)[1])){
					db.insert(sql);
					
					System.out.println(i + ":\t" + filelist.get(i)[2] + "\t"+ filelist.get(i)[1]);
	
				}
				
			}
		   
		}
		db.closeStm();
	     db.closeRs();
		db.closeConn();
		SftpManager.disconnect(sftp);
			
		}
		
		
		
}
