package TOOLS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
 
/**
 * 
 * @author Administrator
 * @date 
 */
public class SftpManager {
	private static final Logger logger = LoggerFactory.getLogger(SftpManager.class);
	public static final String SFTP_PROTOCAL = "sftp";
	public static List<String[]> fileList3 = new ArrayList<String[]>();
	public static List<List<String[]>> fileList4 = new ArrayList<List<String[]>>();
	/**
	 * 
	 * @param host
	 *            IP地址
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param port
	 *            端口号
	 * @return
	 * @throws Exception
	 */
	public static ChannelSftp connect(String host, String username, String password, int port) throws Exception {
		Channel channel = null;
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		Session session = createSession(jsch, host, username, port);
		// 设置登陆主机的密码
		session.setPassword(password);
		// 设置登陆超时时间
		session.connect(15000);
		logger.info("Session connected to " + host + ".");
		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel(SFTP_PROTOCAL);
			channel.connect(1000);
			logger.info("Channel created to " + host + ".");
			sftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			logger.error("exception when channel create.", e);
		}
		return sftp;
	}
 
	/*
	 * Private/public key authorization (加密秘钥方式登陆)
	 * 
	 * @param username
	 *            主机登陆用户名(user account)
	 * @param host
	 *            主机IP(server host)
	 * @param port
	 *            主机ssh登陆端口(ssh port), 如果port<=0, 取默认值22
	 * @param privateKey
	 *            秘钥文件路径(the path of key file.)
	 * @param passphrase
	 *            密钥的密码(the password of key file.)
	 * @return
	 * @throws Exception
	 */
	public static ChannelSftp connect(String username, String host, int port, String privateKey, String passphrase)
			throws Exception {
		Channel channel = null;
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		// 设置密钥和密码 ,支持密钥的方式登陆
		if (StringUtils.isNotEmpty(privateKey)) {
			if (StringUtils.isNotEmpty(passphrase)) {
				// 设置带口令的密钥
				jsch.addIdentity(privateKey, passphrase);
			} else {
				// 设置不带口令的密钥
				jsch.addIdentity(privateKey);
			}
		}
		Session session = createSession(jsch, host, username, port);
		// 设置登陆超时时间
		session.connect(15000);
		logger.info("Session connected to " + host + ".");
		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel(SFTP_PROTOCAL);
			channel.connect(1000);
			logger.info("Channel created to " + host + ".");
			sftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			logger.error("exception when channel create.", e);
		}
		return sftp;
	}
 
	public static void upload(ChannelSftp sftp, String srcFile, String dest) {
		try {
			File file = new File(srcFile);
			if (file.isDirectory()) {
				sftp.cd(srcFile);
				for (String fileName : file.list()) {
					sftp.put(srcFile + SystemUtils.FILE_SEPARATOR + fileName, dest);
				}
			}
			sftp.put(srcFile, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static void upload(ChannelSftp sftp, List<String> fileList, String destPath) throws SftpException {
		try {
			sftp.cd(destPath);
		} catch (Exception e) {
			sftp.mkdir(destPath);
		}
		for (String srcFile : fileList) {
			upload(sftp, srcFile, destPath);
		}
	}
 
	/**
	 * 
	 * @param sftpsftp
	 * @param srcPath
	 *            服务器上源文件的路径, 必须是目录
	 * @param saveFile
	 *            下载后文件的存储路径, 若为目录, 则文件名将与目标服务器上的文件名相同
	 * @param srcfile
	 *            目标服务器上的文件, 不能为目录
	 */
/*	public static void download(ChannelSftp sftp, String srcPath, String saveFile, String srcfile) {
		try {
			sftp.cd(srcPath);
			File file = new File(saveFile);
			file.mkdirs();//
			if (file.isDirectory()) {
				sftp.get(srcfile, new FileOutputStream(file + SystemUtils.FILE_SEPARATOR + srcfile));
			} else {
				sftp.get(srcfile, new FileOutputStream(file));
			}
		} catch (Exception e) {
			logger.error("download file: {} error", srcPath + SystemUtils.FILE_SEPARATOR + srcfile, e);
		}
	}*/
	
	public static boolean download (ChannelSftp sftp, String srcPath, String saveFile, String srcfile) {
		boolean b =false ;
		
		try {
			sftp.cd(srcPath);
			File file = new File(saveFile);
			file.mkdirs();//
			if (file.isDirectory()) {
				sftp.get(srcfile, new FileOutputStream(file + SystemUtils.FILE_SEPARATOR + srcfile));
			} else {
				sftp.get(srcfile, new FileOutputStream(file));
				
			}
			b =true;
		} catch (Exception e) {
			logger.error("download file: {} error", srcPath + SystemUtils.FILE_SEPARATOR + srcfile, e);
		}
		return b ;
	}
	
	
	
 
	/**
	 * 使用sftp下载目标服务器上某个目录下指定类型的文件, 得到的文件名与 sftp服务器上的相同
	 * 
	 * @param sftp
	 * @param srcPath
	 *            sftp服务器上源目录的路径, 必须是目录
	 * @param savePath
	 *            下载后文件存储的目录路径, 一定是目录, 如果不存在则自动创建
	 * @param fileTypes
	 *            指定类型的文件, 文件的后缀名组成的字符串数组
	 */
	public static void download(ChannelSftp sftp, String srcPath, String savePath, String... fileTypes) {
		List<String> fileList = new ArrayList<String>();
		try {
			sftp.cd(srcPath);
			createDir(savePath);
			if (fileTypes.length == 0) {
				// 列出服务器目录下所有的文件列表
				fileList = listFiles(sftp, srcPath, "*");
				downloadFileList(sftp, srcPath, savePath, fileList);
				return;
			}
			for (String type : fileTypes) {
				fileList = listFiles(sftp, srcPath, "*" + type);
				parseAndUpdateDB(sftp, srcPath, savePath, fileList);
			}
		} catch (Exception e) {
			logger.error(
					"download all file in path = '" + srcPath + "' and type in " + Arrays.asList(fileTypes) + " error",
					e);
		}
	}
 
	private static File createDir(String savePath) throws Exception {
		File localPath = new File(savePath);
		if (!localPath.exists() && !localPath.isFile()) {
			if (!localPath.mkdir()) {
				throw new Exception(localPath + " directory can not create.");
			}
		}
		return localPath;
	}
 
	/**
	 * sftp下载目标服务器上srcPath目录下所有指定的文件.<br/>
	 * 若本地存储路径下存在与下载重名的文件,仍继续下载并覆盖该文件.<br/>
	 * 
	 * @param sftp
	 * @param srcPath
	 * @param savePath
	 *            文件下载到本地存储的路径,必须是目录
	 * @param fileList
	 *            指定的要下载的文件名列表
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public static void downloadFileList(ChannelSftp sftp, String srcPath, String savePath, List<String> fileList)
		// throws SftpException, FileNotFoundException 
			
			{
		try {
		sftp.cd(srcPath);
		for (String srcFile : fileList) {
			
			//logger.info("srcFile: " + srcFile);
			String localPath = savePath + SystemUtils.FILE_SEPARATOR + srcFile;
			
			sftp.get(srcFile, localPath);}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/* dowload by file */ 
	public static void downloadFileList(ChannelSftp sftp, String srcPath, String savePath,String vdate,String log_TableName,String must,String DoNot,String DBInfo)
 
		{
		//System.out.println(DBInfo);
		//System.out.println(must);
		//System.out.println(DoNot);
		
		String[] DBInfoS = DBInfo.split("\\|");
	 	
		String driver =DBInfoS[0];
		String url =   DBInfoS[1];
		String user =  DBInfoS[2];
		String passwd= DBInfoS[3];
		 
		
		
		File localFile=new File(savePath);//  
		localFile.mkdirs();//  
		
		  String[] musts = must.toUpperCase().split("\\|");
		  
		  String[] DoNots = DoNot.toUpperCase().split("\\|");
		  ////////////////////////////////
			
		  for (int j=0;j<DBInfoS.length;j++)
			 {
				  System.out.println("DBInfoS["+j+"]:\t"+DBInfoS[j]);
			 }
		  
		  for (int j=0;j<DoNots.length;j++)
			 {
			  System.out.println("DoNots["+j+"]:\t"+DoNots[j]);
			 }
		  
		  for (int j=0;j<musts.length;j++)
			 {
			  System.out.println("musts["+j+"]:\t"+musts[j]);
			 }
		
	try {
		System.out.println("srcPath:\t"+srcPath);
	   
		sftp.cd(srcPath);
	 
		
		//logger.info("srcFile: " + srcFile);
		String localPath = savePath ;
		
		List<String[]> fileList =listFiles(sftp,srcPath); //get file List dirxu：
		
			w :for (int i = 0; i < fileList.size(); i++) {
				
				
				
				if (fileList.get(i)[0].equals("0")) // if is dir Recurrence(digui) get
				
				{
					if (!fileList.get(i)[1].contains(vdate)) {
						continue w;
					}

				 
					
					 for (int j=0;j<musts.length;j++)
					 {
						 if ( !fileList.get(i)[1].toUpperCase().contains(musts[j]) ){
							 
							// System.out.println("MUST:\t"+fileList.get(i)[1]+"\t"+musts[j]);
							 continue w; 
						 }
					 }
					 
					 for (int j=0;j<DoNots.length;j++)
					 {
						 if ( fileList.get(i)[1].toUpperCase().contains(DoNots[j]) ){
							// System.out.println("DoNots:\t"+fileList.get(i)[1]+"\t"+DoNots[j]);
							 continue w; 
						 }
					 }
					
				 
					if  ( SetLog.setlog(log_TableName, fileList.get(i)[1],	srcPath.replace("\\", "\\\\"),localPath.replace("\\", "\\\\"), driver,url,user,passwd  ));
					{
						 System.out.println("It is getting:\t"+srcPath+File.separator+fileList.get(i)[1]+"\t"+localPath	);
						 sftp.get(fileList.get(i)[1], localPath);
							
					}
				
				 
					 
				} else {
					downloadFileList(sftp, srcPath + "/" + fileList.get(i)[1],localPath + File.separator + fileList.get(i)[1],vdate,log_TableName,must,DoNot,DBInfo);
				}
			}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}

	public static boolean  downloadFileList_netflow(ChannelSftp sftp, String srcPath, String savePath,String vdate,String log_TableName,String must,String DoNot,String DBInfo)
	 
	{
		boolean b = false;
	//System.out.println(DBInfo);
	//System.out.println(must);
	//System.out.println(DoNot);
	
	String[] DBInfoS = DBInfo.split("\\|");
 	
	String driver =DBInfoS[0];
	String url =   DBInfoS[1];
	String user =  DBInfoS[2];
	String passwd= DBInfoS[3];
	 
	
	
	File localFile=new File(savePath);//  
	localFile.mkdirs();//  
	
	  String[] musts = must.toLowerCase().split("\\|");
	  
	  String[] DoNots = DoNot.toLowerCase().split("\\|");
	  ////////////////////////////////
		
	  for (int j=0;j<DBInfoS.length;j++)
		 {
			  System.out.println("DBInfoS["+j+"]:\t"+DBInfoS[j]);
		 }
	  
	  for (int j=0;j<DoNots.length;j++)
		 {
		  System.out.println("DoNots["+j+"]:\t"+DoNots[j]);
		 }
	  
	  for (int j=0;j<musts.length;j++)
		 {
		  System.out.println("musts["+j+"]:\t"+musts[j]);
		 }
	
try {
	System.out.println("srcPath:\t"+srcPath);
   
	sftp.cd(srcPath);
 
	
	//logger.info("srcFile: " + srcFile);
	String localPath = savePath ;
	
	List<String[]> fileList =listFiles(sftp,srcPath); //get file List dirxu：
	
		w :for (int i = 0; i < fileList.size(); i++) {
			
			
			
			if (fileList.get(i)[0].equals("0")) // if is dir Recurrence(digui) get
			
			{
				if (!fileList.get(i)[1].contains(vdate)) {
					continue w;
				}

			 
				
				 for (int j=0;j<musts.length;j++)
				 {
					 if ( !fileList.get(i)[1].toLowerCase().contains(musts[j]) ){
						 
						 
						 continue w; 
					 }
				 }
				 
				 for (int j=0;j<DoNots.length;j++)
				 {
					 if ( fileList.get(i)[1].toLowerCase().contains(DoNots[j]) ){
						// System.out.println("DoNots:\t"+fileList.get(i)[1]+"\t"+DoNots[j]);
						 continue w; 
					 }
				 }
				
			 
				if  ( SetLog.setlog(log_TableName, fileList.get(i)[1],	srcPath.replace("\\", "\\\\"),localPath.replace("\\", "\\\\"), driver,url,user,passwd  ));
				{
					 System.out.println("It is getting:\t"+srcPath+File.separator+fileList.get(i)[1]+"\t"+localPath	);
					 sftp.get(fileList.get(i)[1], localPath);
						
				}
			
			 
				 
			} else {
				downloadFileList(sftp, srcPath + "/" + fileList.get(i)[1],localPath + File.separator + fileList.get(i)[1],vdate,log_TableName,must,DoNot,DBInfo);
			}
		}
	 b = true;
}
catch (Exception e)
{
	e.printStackTrace();
	b = false;
}
return b;
}
	
	
 
	/* sftp下载目标服务器上所有指定的文件, 并解析文件的内容.<br/>
	 * 若本地存储路径下存在与下载重名的文件, 则忽略(不下载)该文件.<br/>
	 * 
	 * @param sftp
	 * @param srcPath
	 *            sftp上源文件的目录
	 * @param savePath
	 *            文件下载到本地存储的路径,必须是目录
	 * @param fileList
	 *            指定的要下载的文件列表
	 * @throws FileNotFoundException
	 * @throws SftpException
	 */
	private static void parseAndUpdateDB(ChannelSftp sftp, String srcPath, String savePath, List<String> fileList)
			throws FileNotFoundException, SftpException {
		sftp.cd(srcPath);
		for (String srcFile : fileList) {
			String localPath = savePath + SystemUtils.FILE_SEPARATOR + srcFile;
			File localFile = new File(localPath);
			// savePath路径下已有文件与下载文件重名, 忽略这个文件
			if (localFile.exists() && localFile.isFile()) {
				continue;
			}
			logger.info("start downloading file: [" + srcFile + "], parseAndUpdate to DB");
			sftp.get(srcFile, localPath);
			// updateDB(localFile);
		}
	}
 
	/**
	 * 获取srcPath路径下以regex格式指定的文件列表
	 * 
	 * @param sftp
	 * @param srcPath
	 *            sftp服务器上的目录
	 * @param regex
	 *            需要匹配的文件名
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> listFiles(ChannelSftp sftp, String srcPath, String regex) throws SftpException {
		
		List<String> fileList = new ArrayList<String>();		
		sftp.cd(srcPath); // 如果srcPath不是目录则会抛出异常
		
		if ("".equals(regex) || regex == null) {
			regex = "*";
		}
		
		Vector<LsEntry> sftpFile = sftp.ls(regex);
		String fileName = null;
		
		for (LsEntry lsEntry : sftpFile) {
			fileName = lsEntry.getFilename();
			fileList.add(fileName);
		}
		return fileList;
	}
	
	public static List<String[]> listFiles(ChannelSftp sftp, String srcPath) throws SftpException {
		
		List<String[]> fileList = new ArrayList<String[]>();
		
		
		
		sftp.cd(srcPath); // 如果srcPath不是目录则会抛出异常
		 
		Vector<LsEntry> sftpFile = sftp.ls(srcPath);		 
		
		for (LsEntry lsEntry : sftpFile) {
		 
		String isDir = "0"; 
		String FILENAME[]   = new String[2];
			 Object its= lsEntry  ;
             String FtpLongName = its.toString() ;             
             
             String  fileName = lsEntry.getFilename();
			
			if (".".equals(fileName) || "..".equals(fileName)) {
				continue;
			}
			
			if (FtpLongName.substring(0,1).equals("d")){
				isDir = "1";			 
			}
			
			FILENAME[0] = isDir;
			FILENAME[1] = fileName;
			
			fileList.add(FILENAME);	
			
		}
		
/*		for (int i=0;i<fileList.size();i++){
			System.out.println(i+"\t"+fileList.get(i)[0] + "\t" +fileList.get(i)[1] );
		}*/
		
		return fileList;
	}
	
	public static List<String[]> GetListFiles(ChannelSftp sftp, String srcPath,String filename) throws SftpException {
		List<String[]> fileList4 = new ArrayList<String[]>();
		
		filename = filename.trim();
		
		fileList4 = GetListFiles(sftp, srcPath);
		System.out.println("filename:\t"+filename);
		System.out.println("fileListSize:\t"+fileList4.size());
		if (!filename.equals("")) {
			for (int i = 0; i < fileList4.size(); i++) {
 
				if (!fileList4.get(i)[1].contains(filename)) {
					//System.out.println("filename:\t"+fileList4.get(i)[1]);
					fileList4.remove(i);
					i=i-1;
				}
			}
		}
		 
		System.out.println("fileListSize:\t"+fileList4.size());
		return fileList4;
	}
	
	public static List<String[]> GetListFiles(ChannelSftp sftp, String srcPath) throws SftpException {
		
		List<List<String[]>> fileList0 = new ArrayList<List<String[]>>();
		
		List<String[]> fileList2 = new ArrayList<String[]>();
		//System.out.println("srcPath"+"\t"+srcPath);
		sftp.cd(srcPath); // 如果srcPath不是目录则会抛出异常
		 
		Vector<LsEntry> sftpFile = sftp.ls(srcPath);		 
		
		for (LsEntry lsEntry : sftpFile) {
		 
		String isDir = "0"; 
		String FILENAME[]   = new String[4];
			 Object its= lsEntry  ;
             String FtpLongName = its.toString() ;         
            
             String Longname = lsEntry.getLongname();          
             
             String  fileName = lsEntry.getFilename();
            // String fileSize = lsEntry.
			
			if (".".equals(fileName) || "..".equals(fileName)) {
				continue;
			}
			
			if (FtpLongName.substring(0,1).equals("d")){
				isDir = "1";	
				//System.out.print("dir:"+srcPath+File.separator+fileName);
				  GetListFiles(sftp, srcPath+"/"+fileName);
			}
			
			
			String[] dirs = Longname.substring(Longname.indexOf("wisg")+4).trim().split(" ");
			
		
			
			
			FILENAME[0] = isDir;
			FILENAME[1] = fileName;
			FILENAME[2] =  srcPath;
			FILENAME[3] =  dirs[0];
			
			//fileList2.add(FILENAME);// .add(FILENAME);	
			//System.out.println(srcPath+"/"+fileName);
			//fileList2.add(FILENAME);
			
			fileList3.add(FILENAME) ;
			
			
			//for (int i=0;i<fileList.size();i++){
				
			//	fileList.add(fileList2.get(i));
			//}
			
		}
		
/*		for (int i=0;i<fileList.size();i++){
			System.out.println(i+"\t"+fileList.get(i)[0] + "\t" +fileList.get(i)[1] );
		}*/
		
		return fileList3;
	}
	
	
	
 
	/**
	 * 删除文件
	 * 
	 * @param dirPath
	 *            要删除文件所在目录
	 * @param file
	 *            要删除的文件
	 * @param sftp
	 * @throws SftpException
	 */
	public static void delete(String dirPath, String file, ChannelSftp sftp) throws SftpException {
		String now = sftp.pwd();
		sftp.cd(dirPath);
		sftp.rm(file);
		sftp.cd(now);
	}
 
	/**
	 * Disconnect with server
	 * 
	 * @param sftp
	 */
	public static void disconnect(ChannelSftp sftp) {
		try {
			if (sftp != null) {
				if (sftp.isConnected()) {
					sftp.disconnect();
				} else if (sftp.isClosed()) {
					logger.info("sftp is closed already");
				}
				if (null != sftp.getSession()) {
					sftp.getSession().disconnect();
				}
			}
		} catch (JSchException e) {
			// Ignore
		}
	}
 
	private static Session createSession(JSch jsch, String host, String username, int port) throws Exception {
		Session session = null;
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(username, host);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(username, host, port);
		}
		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception(host + "session is null");
		}
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		return session;
	}
 
	public static void main(String[] args) throws Exception {	 	
		
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
		
		
		ChannelSftp sftp=null;
		
		sftp=connect("221.180.170.54", "ftpclient", "iCache9200@huawei", 39200);	 
		
		downloadFileList(sftp, "/opt/linshi/02/", "f:\\ftp","20180916","cdn_zte_log_sftp","","","");
	 
		disconnect(sftp);
		
	}
 
}