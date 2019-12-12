package dns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zte.zte_cdn_log_unzip;
import zte.zte_cdn_log_unzip_thread;
import TOOLS.ReadFromFile;
import TOOLS.getDate;
import TOOLS.getProperties;

public class DNS_unzip_thread extends Thread {
	// public static List<String[]> fileNameList = new ArrayList<String[]>();
	public static String localPath = getProperties.getPropertie("DNSlocalPath").trim();
	public static String decompressPath = getProperties.getPropertie("decompressPath").trim();

	public static List<String[]> getFileList() {
		List<String[]> fileNameList = new ArrayList<String[]>();
		return fileNameList;
	}

	static String[] path_fileName = new String[2];

	/*
	 * zte_cdn_log_unzip_thread(String name){ super(name); }
	 */

	DNS_unzip_thread(String name) {
		super(name);

	}

	public void run() {

		// String [] pathfileName = getPath_fileName();
		String Name = getName();
		
		String[] paras = Name.split(",");
		String fileName =  paras[0];
		String vdate = paras[1];

		try {
			 
			DNS_unzip.decompressSingleFile(localPath+File.separator+vdate, fileName,decompressPath+vdate);
			
		} catch (Throwable e) {
			 

			e.printStackTrace();
		}
	}

	public static int UNZIP_Thread(String vdate) throws Exception {

		 
        List<String> list_sftp =  new ArrayList<String>();
        
        List<String> list_unzip =  new ArrayList<String>();
       
          
    	File file = new File(localPath+File.separator+"sftp_"+vdate+".log");
        
		if (ReadFromFile.judeFileExists(file)) {
			 
			 list_sftp =ReadFromFile.getFileByLines(localPath+File.separator+"sftp_"+vdate+".log");
		}
       
        
        file = new File(localPath+File.separator+"unzip_"+vdate+".log");
        
       
		
        if (ReadFromFile.judeFileExists(file)) {
			 
        	 list_unzip = ReadFromFile.getFileByLines(localPath+File.separator+"unzip_"+vdate+".log");
		}
        List<String> list =  new ArrayList<String>();
        // 去除已经解压缩过的文件
        for(String ftpName:list_sftp){
        	 boolean b = true;
        	for (String unzipName:list_unzip){
        		if (ftpName.equals(unzipName)){
        		    b =false;
        			break; 	 
        		}
        		
        	}
        	
        	if (b){
        		list.add(ftpName);
        	}
        	
        	
        }
        
       
        
		int i = list.size();
		if (i == 0) {
			return 0;
		}

		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();

		 for (int k = 0; k < list.size(); k++) {
			



			DNS_unzip_thread jt = new DNS_unzip_thread(list.get(k)+","+vdate);
			jt.start();
			int noThreads = currentGroup.activeCount();

			while (noThreads >= (5)) {
				Thread.sleep(5000);
				noThreads = currentGroup.activeCount();

				System.out.println("当前DNS解压缩线程组数：\t" + noThreads);

			}
			 
           
		}
		 
		return i;

	}

	public static void main(String[] args) throws Exception {
		// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次

		System.out
				.println("The HW_unzip_Thread Program  restarted  with no para");

		while (true) {
			String vtoday = getDate.getToday();
			
			

			int i = UNZIP_Thread( vtoday);
			if (i == 0) {
				System.out
						.println("The HW_unzip_Thread Program will restart in 10 Sec");
				Thread.sleep(1000 * 10);
			} else {
				Thread.sleep(1000);
			}
			System.out
					.println("----------------------------------------------------------------------------");
		}
	}
}
