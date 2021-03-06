package zte;

import java.util.ArrayList;
import java.util.List;

import TOOLS.getProperties;
import ZTECDN.ZTE_CDN_LOG_UNZIP;
import ZTECDN.ZTE_CDN_LOG_UNZIP_THREAD;

public class zte_cdn_log_unzip_thread  extends Thread {
	//public static List<String[]> fileNameList = new ArrayList<String[]>();
	 public static int intervalTime = getProperties.getINTPropertie("SleepTime");  
	
	public static List<String[]> getFileList (){
		 List<String[]> fileNameList = new ArrayList<String[]>();
		 return fileNameList;
	}
	
	static String [] path_fileName = new String[2];

 

 
	
/*	zte_cdn_log_unzip_thread(String name){
		super(name);
	}*/
	
	 zte_cdn_log_unzip_thread(String name){
		super(name);
		
	}
	


	public void run() {
		 
		   // String [] pathfileName = getPath_fileName();
			String FILE = getName();
			String   filePath= FILE.substring(0,FILE.indexOf(","));
			String fileName = FILE.substring(FILE.indexOf(",")+1,FILE.length());

		try {			 			
				
			zte_cdn_log_unzip.decompressFile(filePath, fileName);			
              //System.out.println("sss:"+filePath+"\t"+fileName);
		} catch (Throwable e) {
			//System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}




	public static int  UNZIP_Thread() throws Exception {
		 
		List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		
		ListListFile = zte_cdn_log_unzip.getThreadFileList();
		 int i = ListListFile.size() ;
		 if (i==0){
			 return 0;
		 }
		 
		 for (int k = 0;k<ListListFile.size();k++){
			//System.out.println(k+"\t"+ ListListFileName.get(k).size());
			 zte_cdn_log_unzip_thread jt = null;
			  for (int j=0;j<ListListFile.get(k).size();j++)
			 {
				  jt = new zte_cdn_log_unzip_thread(ListListFile.get(k).get(j)[0]+","+ListListFile.get(k).get(j)[1]);
				  jt.start();
								 
			 }
			 
			  jt.join();
			  Thread.sleep(1000 );
			 
		 }
		 
		 return i;
	
	}
	
	public static void main(String[] args) throws Exception {
		// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次   

		System.out.println("The UNZIP_Thread Program  restarted  with no para");
		 
		while (true) {

			int i =  UNZIP_Thread();
			if (i==0){
			System.out.println("The UNZIP_Thread Program will restart in 5 min");
			 Thread.sleep(1000 * 60 * intervalTime);				 
			}
			else {
				 Thread.sleep(1000 );
			}
			System.out.println("----------------------------------------------------------------------------");
		}
	}
	
}
