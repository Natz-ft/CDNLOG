package zte;

import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.getProperties;
import ZTECDN.ZTE_CDN_LOG_UNTAR;
import ZTECDN.ZTE_CDN_LOG_UNTAR_THREAD;

public class zte_cdn_log_untar_thread extends Thread {
	
	public static int Threadnum = getProperties.getINTPropertie("ZTEThreadNum");
	 
	 public static int intervalTime = getProperties.getINTPropertie("SleepTime");  												
													
													
													
	public zte_cdn_log_untar_thread(String name) {
		super(name);
	}

	public void run() {
		 

		String FILE = getName();
		String   filePath= FILE.substring(0,FILE.indexOf(","));
		String fileName = FILE.substring(FILE.indexOf(",")+1,FILE.length());
		//fileName = fileName.replace(".tar.gz", ".txt");

		try {				
			zte_cdn_log_untar.dearchivefile(filePath, fileName); 	 

		} catch (Throwable e) {
			//System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}
//String sql = "select fileName,path from cdn_zte_log_unzip where ( status='' or status is null ) order by fileName    ";
	public static int UNTAR_Thread() throws Exception {
		 

List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		
		ListListFile = zte_cdn_log_untar.getThreadFileList();
		 int i = ListListFile.size() ;
		 if (i==0){
			 return 0;
		 }
		 
		 for (int k = 0;k<ListListFile.size();k++){
			//System.out.println(k+"\t"+ ListListFileName.get(k).size());
			 zte_cdn_log_untar_thread jt = null;
			  for (int j=0;j<ListListFile.get(k).size();j++)
			 {
				  jt = new zte_cdn_log_untar_thread(ListListFile.get(k).get(j)[0]+","+ListListFile.get(k).get(j)[1]);
				  jt.start();
								 
			 }
			 
			  jt.join();
			  Thread.sleep(1000 );
			 
		 }
		 
		 return i;
	}

	public static void main(String[] args) throws Exception {

		// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次

		 
			System.out.println("The UNTAR_Thread Program  restarted  with no para");
			
			while (true) {

				int i = UNTAR_Thread();				
				if (i==0){
				System.out.println("The UNTAR_Thread Program will restart in 5 min");
				 Thread.sleep(1000 * 60 * intervalTime);				 
				}
				else {
					 Thread.sleep(1000 );
				}
				System.out.println("----------------------------------------------------------------------------");
			}
		 

		 
	}

}
