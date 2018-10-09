package ZTECDN;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import TOOLS.DBAcess;
import TOOLS.SetLog;
import TOOLS.getDate;
import TOOLS.getProperties;
import TOOLS.SftpManager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;
 


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import java.security.Provider;


// ZTE CDN log ftp 
public class ZTE_CDN_LOG_SFTP {
 public static	String  sftpServer = getProperties.getPropertie("ZTEsftpServer").trim();
 public static String sftpUser = getProperties.getPropertie("ZTEsftpUser").trim();
 public static String sftpPass = getProperties.getPropertie("ZTEsftpPass").trim();
 public static	String sftpPath = getProperties.getPropertie("ZTEsftpPath").trim();
 public static	String localPath = getProperties.getPropertie("ZTElocalPath").trim();
 public static	String sftPort =   getProperties.getPropertie("ZTEsftPort").trim();
 public static	String  ZTEMUST =  getProperties.getPropertie("ZTEMustHave").trim();
 public static	String  ZTEMDoNOT =  getProperties.getPropertie("ZTEDoNotHave").trim();
 
 public static	String driver = getProperties.getPropertie("maximodriver").trim();
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
			 GetFileAuto();
		 }
		 else {
			 GetFileManual(args[0]);
		 }		
	}	
	
	public static void GetFileAuto() throws Exception { 
		
		 
		
		String DBInfo =driver+"|"+url+"|"+user+"|"+passwd;
		
		System.out.println(sftpServer +"\t"+"sftpUser" +"\t"+ sftpPass+"\t"+sftpPath+"\t"+localPath+"\t"+  sftPort  +"\t"+ZTEMUST+"\t"+ZTEMDoNOT);
		while (true) {
			String today=getDate.getTodayEarlyOneHour();
	    	 
			ChannelSftp sftp=null;
			
			sftp=SftpManager.connect(sftpServer, sftpUser, sftpPass, Integer.parseInt(sftPort));	 
			
			SftpManager.downloadFileList(sftp, sftpPath+File.separator+today, localPath+File.separator+today,today,"cdn_zte_log_sftp",ZTEMUST,ZTEMDoNOT,DBInfo);
		 
			SftpManager.disconnect(sftp);			
			
			//int sleepTime = ;
			
			Thread.sleep(1000 * 60 * intervalTime);
	    	}
		
	}
	
	
public static void GetFileManual(String today) throws Exception { 
		
	String DBInfo =driver+"|"+url+"|"+user+"|"+passwd;
			 
	    	 
			ChannelSftp sftp=null;
			
			sftp=SftpManager.connect(sftpServer, sftpUser, sftpPass, Integer.parseInt(sftPort));	 
			
			SftpManager.downloadFileList(sftp, sftpPath+File.separator+today, localPath+File.separator+today,today,"cdn_zte_log_sftp",ZTEMUST,ZTEMDoNOT,DBInfo);
		 
			SftpManager.disconnect(sftp);			
			
			 
	    	 
		
	}
	

	/**
	 * ����JSch��ʵ��SFTP���ء��ϴ��ļ�(�û������뷽ʽ��½)
	 * 
	 * @param ip
	 *            ����IP
	 * @param user
	 *            ������½�û���
	 * @param psw
	 *            ������½����
	 * @param port
	 *            ����ssh2��½�˿ڣ����ȡĬ��ֵ(Ĭ��ֵ22)����-1
	 * 
	 */
	public static void sshSftp(String ip, String user, String psw, int port)
			throws Exception {
		System.out.println("��ʼ�û������뷽ʽ��½");
		Session session = null;

		JSch jsch = new JSch();
	 
		if (port <= 0) {
			// ���ӷ�����������Ĭ�϶˿�
			session = jsch.getSession(user, ip);
		} else {
			// ����ָ���Ķ˿����ӷ�����
			session = jsch.getSession(user, ip, port);
		}

		// ������������Ӳ��ϣ����׳��쳣
		if (session == null) {
			throw new Exception("session is null");
		}

		// ���õ�½����������
		session.setPassword(psw);// ��������
		// ���õ�һ�ε�½��ʱ����ʾ����ѡֵ��(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		 
		session.connect(30000);

		sftp_put(session, "aa.log");
		System.out.println("sftp�ɹ�");
	}

	private static void sftp_put(Session session, String uploadFileName)
			throws Exception {
		Channel channel = null;
		try {
			// ����sftpͨ��ͨ��
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			// ���������ָ�����ļ���
			sftp.cd("/root");

			// �г�������ָ�����ļ��б�
			Vector v = sftp.ls("/");
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.get(i));
			}

			// ���´���ʵ�ִӱ����ϴ�һ���ļ��������������Ҫʵ�����أ��Ի��������Ϳ�����
			OutputStream outstream = sftp.put(uploadFileName);
			InputStream instream = new FileInputStream(new File("C:\\aa.txt"));

			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
			}

			outstream.flush();
			outstream.close();
			instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}

	/*
	 * ��SFTP�����������ļ�
	 * 
	 * @param ftpHost SFTP IP��ַ
	 * 
	 * @param ftpUserName SFTP �û���
	 * 
	 * @param ftpPassword SFTP�û�������
	 * 
	 * @param ftpPort SFTP�˿�
	 * 
	 * @param ftpPath SFTP���������ļ�����·�� ��ʽ�� ftptest/aa
	 * 
	 * @param localPath ���ص����ص�λ�� ��ʽ��H:/download
	 * 
	 * @param fileName �ļ�����
	 */
	public static void downloadSftpFile(String ftpHost, String ftpUserName,
			String ftpPassword, int ftpPort, String ftpPath, String localPath,
			String fileName) throws JSchException {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
		session.setPassword(ftpPassword);
		session.setTimeout(100000);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp chSftp = (ChannelSftp) channel;

		String ftpFilePath = ftpPath + "/" + fileName;
		String localFilePath = localPath + File.separatorChar + fileName;

		try {
			chSftp.get(ftpFilePath, localFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			chSftp.quit();
			channel.disconnect();
			session.disconnect();
		}

	}

	public static void downloadByDirectory(String ftpHost, String ftpUserName,String ftpPassword, int ftpPort, String ftpPath, String localPath) throws JSchException, SftpException {
		
		File localFile=new File(localPath);// ��������Ŀ¼�ļ�
		localFile.mkdirs();// ��������Ŀ¼
		
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
		session.setPassword(ftpPassword);
		session.setTimeout(100000);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp chSftp = (ChannelSftp) channel;

		//String ftpFilePath = ftpPath + "/" + fileName;
		//String localFilePath = localPath + File.separatorChar + fileName;

		Vector fileList = null;
		List<String> fileNameList = new ArrayList<String>();
		fileList = chSftp.ls(ftpPath); // ����Ŀ¼�������ļ�����

		Iterator it = fileList.iterator();

		try {
            int delay=0;
			while (it.hasNext()) {
               // 
                Object its= it.next();
                String FtpLongName = its.toString() ;
              // String FtpNames = ((LsEntry) FtpLongName).getFilename();
              String FtpNames =  ((LsEntry)its).getFilename();
                                          
              
				if (".".equals(FtpNames) || "..".equals(FtpNames)) {
					continue;
				}
				
				if (FtpLongName.substring(0,1).equals("d")){
					
					System.out.println("Directory:" + FtpNames);
					downloadByDirectory(ftpHost, ftpUserName, ftpPassword, ftpPort,ftpPath+File.separator+FtpNames, localPath+File.separatorChar +FtpNames);
					continue;
				}
				
			     if (FtpNames.toLowerCase().contains("bcs_cdn_024_gj")) { // 甘井子 resorue center do not get
	             	 continue;
	              }
				
				/*Let machines hava a rest!*/
				delay  = delay +1;
				 if ((delay%1000)==0){
					 System.out.println("HAVE A REST!");
					 Thread.sleep(10000); 
				 }
				
				 //sftp get file
				chSftp.get(ftpPath + "/" + FtpNames, localPath + File.separatorChar + FtpNames);	
			}
		}		
	 
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			chSftp.quit();
			channel.disconnect();
			session.disconnect();
		}

	}
	
	
public static void downloadByDate(String ftpHost, String ftpUserName,String ftpPassword, int ftpPort, String ftpPath, String localPath,String vdate) throws Exception {
		
	System.out.println(ftpHost+"\t"+ftpUserName+"\t"+ftpPassword+"\t"+"\t"+ftpPort+"\t"+ftpPath	+"\t"+localPath+"\t"+vdate);	
	
	
		File localFile=new File(localPath);// ��������Ŀ¼�ļ�
		localFile.mkdirs();// ��������Ŀ¼
		
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
		
		  //如果服务器连接不上，则抛出异常  
        if (session == null) {  
            throw new Exception("session is null");  
        }  
		
		session.setPassword(ftpPassword);
		
		session.setTimeout(900000000);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		//session.setConfig("kex","diffie-hellman-group1-sha1");
		session.setConfig(config);		 
         try {
		 session.connect();
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp chSftp = (ChannelSftp) channel;

		//String ftpFilePath = ftpPath + "/" + fileName;
		//String localFilePath = localPath + File.separatorChar + fileName;

		Vector fileList = null;
		List<String> fileNameList = new ArrayList<String>();
		fileList = chSftp.ls(ftpPath); // ����Ŀ¼�������ļ�����

		Iterator it = fileList.iterator();

		try {
			int delay =0 ;
			while (it.hasNext()) {
               // 
                Object its= it.next();
                String FtpLongName = its.toString() ;
              // String FtpNames = ((LsEntry) FtpLongName).getFilename();
              String FtpNames =  ((LsEntry)its).getFilename();
              
              if (FtpNames.toLowerCase().contains("bcs_cdn_024_gj")) { // 甘井子 resorue center do not get
             	 continue;
              } 
                 
             if (!FtpNames.contains(vdate)) { // fileName contain date  文件名必须包含日期
            	 continue;
             }
              
             if (!FtpNames.contains("OTTCACHE")) { // fileName contain date  文件名必须包含日期
            	 continue;
             } 
				if (".".equals(FtpNames) || "..".equals(FtpNames)) { // 父目录，当前目录 文件信息不处理。
					continue;
				}
				
				if (FtpLongName.substring(0,1).equals("d")){
					
					System.out.println("FileName:\t" + FtpNames);
					downloadByDirectory(ftpHost, ftpUserName, ftpPassword, ftpPort,ftpPath+"/"+FtpNames, localPath+File.separatorChar +FtpNames);
					continue;
				}	
				
				Properties prop;
				prop = new Properties();		
				try {		 
					prop.load(new FileInputStream(DBAcess.getPath()));
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			
				
				if (SetLog.Checklog("cdn_zte_log_sftp", FtpNames,driver,url,user,passwd) == 0) {
					
					 delay  = delay +1;
					 if ((delay%1000)==0){
						 System.out.println("HAVE A REST!");
						 Thread.sleep(10000); 
					 }
					

					 System.out.println("正在下载文件:\t" + ftpPath + "/" + FtpNames);	
				      
				    	  chSftp.get(ftpPath + "/" + FtpNames, localPath + File.separatorChar + FtpNames); 				       

					     SetLog.setlog("cdn_zte_log_sftp", FtpNames,	ftpPath.replace("\\", "\\\\"),localPath.replace("\\", "\\\\"), driver,url,user,passwd  ); // д���ݿ���־
				}
				else {
					// System.out.println( ftpPath + "/" + FtpNames+"\t 文件已经下载过了");
				}
			}		

		} 		
	 
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			chSftp.quit();
			channel.disconnect();
			session.disconnect();
			 System.out.println("------FINISHED---ONE MINUTES AGAIN------");
		}

	}
	

		
		
	 



}
