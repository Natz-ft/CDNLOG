package dns;

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
import TOOLS.FileWrite;
import TOOLS.ReadFromFile;
import TOOLS.SftpManager;
import TOOLS.getDate;
import TOOLS.getProperties;

import com.jcraft.jsch.ChannelSftp;

public class DNS_ftp {
	public static String sftpServer = getProperties.getPropertie(
			"DNSsftpServer").trim();
	public static String sftpUser = getProperties.getPropertie("DNSsftpUser")
			.trim();
	public static String sftpPass = getProperties.getPropertie("DNSsftpPass")
			.trim();
	public static String sftpPath = getProperties.getPropertie("DNSsftpPath")
			.trim();
	public static String localPath = getProperties.getPropertie("DNSlocalPath")
			.trim();
	public static String sftPort = getProperties.getPropertie("DNSsftPort")
			.trim();

	public static void main(String[] args) throws Exception {

		String yesterday = "", today = "";

		KeyPairGenerator keyGen;

		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Provider t[] = Security.getProviders();
			keyGen = KeyPairGenerator.getInstance("EC");
			keyGen.initialize(256);
			KeyPair keyPair = keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (args.length < 1) {
			DNSLogfileAuto();
		} else {
			System.out.println("args[0]\t" + args[0]);
			DNSLogfileManual(args[0]);

		}

	}

	public static List<String> readSftpLog(String fileName) {
		List<String> list = new ArrayList<String>();

		File file = new File(fileName);

		if (ReadFromFile.judeFileExists(file)) {
			list = ReadFromFile.getFileByLines(fileName);
		}

		return list;
	}

	public static void DNSLogfileAuto() throws Exception {

		while (true) {
			String today = getDate.getToday(); // getTodayEarlyOneHour();

			DNSLogfileManual(today);

			System.out.println("The HW_SFTP Program will restart in 10 Sec ");
			Thread.sleep(1000 * 10);
		}

	}

	public static void DNSLogfileManual(String today) throws Exception {
		System.out.println("FTPFile_date:\t" + today);
		today = today.trim();

		ChannelSftp sftp = null;
		sftp = SftpManager.connect(sftpServer, sftpUser, sftpPass,		Integer.parseInt(sftPort));

		List<String[]> filelist = SftpManager.DNSGetListFiles(sftp, sftpPath,	today);

		List<String> list_inserted = readSftpLog(localPath + File.separator+ "sftp_" + today + ".log");

		w: for (int i = 0; i < filelist.size(); i++) {
		
			// 过滤 0，如果是目录，跳过循环
			if (!filelist.get(i)[0].equals("0")) {// dir is ignore 目录跳跳过 。
				continue w;
			}

			// 过滤4，不下载已经下入库的文件
			if (list_inserted.contains(filelist.get(i)[1])) {
				continue w;
			}		 
			 

			String savePath = 	localPath + File.separator	+ filelist.get(i)[2].replace(sftpPath, "");	 
			// 下载
			if (SftpManager.download(sftp,filelist.get(i)[2],savePath ,filelist.get(i)[1])) {
              
				//写日志文件
				if (ReadFromFile.checkFileExists(savePath+ File.separator + filelist.get(i)[1])) {
					
					FileWrite.WriteFileByBuffered(localPath + File.separator + "sftp_" + today + ".log", filelist.get(i)[1], "1");

					System.out.println(i + ":\t" + filelist.get(i)[2] + "\t"+ filelist.get(i)[1]);
				}
			}

		}
		filelist.clear();
		list_inserted.clear();
		SftpManager.disconnect(sftp);
	}

}
