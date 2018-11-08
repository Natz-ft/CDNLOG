package netflow;

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

public class netflow_sftp {
	public static String sftpServer = getProperties.getPropertie(
			"netFlowsftpServer").trim();
	public static String sftpUser = getProperties.getPropertie(
			"netFlowsftpUser").trim();
	public static String sftpPass = getProperties.getPropertie(
			"netFlowsftpPass").trim();
	public static String sftpPath = getProperties.getPropertie(
			"netFlowsftpPath").trim();
	public static String localPath = getProperties.getPropertie(
			"netFlowlocalPath").trim();
	public static String sftPort = getProperties.getPropertie("netFlowsftPort")
			.trim();
	public static String netFlowMUST = getProperties.getPropertie(
			"netFlowustHave").trim();
	public static String netFlowMDoNOT = getProperties.getPropertie(
			"netFlowDoNotHave").trim();

	public static String driver = getProperties.getPropertie("mysqldriver")
			.trim();
	public static String url = getProperties.getPropertie("localurl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword")
			.trim();

	// public static int intervalTime =
	// getProperties.getINTPropertie("SleepTime");

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
		// GetFileAuto();

	}

	public static boolean ftpnetflowfile(String vdate) throws Exception {
		boolean b = false;
		// String yesterday ="",today="";

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
		b = GetFileAuto(vdate);

		return b;
	}

	public static boolean GetFileAuto(String vdate) throws Exception {

		boolean b = false;

		String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;

		System.out.println(sftpServer + "\t" + "sftpUser" + "\t" + sftpPass
				+ "\t" + sftpPath + "\t" + localPath + "\t" + sftPort + "\t"
				+ netFlowMUST + "\t" + netFlowMDoNOT);

		// String today=getDate.getYesterday();
		String today = vdate;
		try {
			ChannelSftp sftp = null;

			sftp = SftpManager.connect(sftpServer, sftpUser, sftpPass,
					Integer.parseInt(sftPort));

			SftpManager.downloadFileList_netflow(sftp, sftpPath, localPath,
					today, "cdn_netflow_log_sftp", netFlowMUST, netFlowMDoNOT,
					DBInfo);

			SftpManager.disconnect(sftp);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}

		// int sleepTime = ;

		return b;

	}

}
