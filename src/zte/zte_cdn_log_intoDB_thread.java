package zte;

import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.SetLog;
import TOOLS.cdnSetLog;
import TOOLS.getProperties;
import ZTECDN.ZTE_CDN_LOG_IntoDB;
import ZTECDN.ZTE_CDN_LOG_IntoDB_THREAD;

public class zte_cdn_log_intoDB_thread extends Thread {
	
	public static int Threadnum = getProperties.getINTPropertie("ZTEThreadNum");
	public static int intervalTime = getProperties.getINTPropertie("SleepTime");  

	public zte_cdn_log_intoDB_thread(String name) {
		super(name);
	}

	public void run() {

		String FILE = getName();
		String filePath = FILE.substring(0, FILE.indexOf(","));
		String fileName = FILE.substring(FILE.indexOf(",") + 1, FILE.length());
		fileName = fileName.replace(".tar.gz", ".txt");

		try {
		

				// System.out.println("FILE:\t"+FILE);
				// System.out.println("fileName:\t"+fileName);
				// System.out.println("filePath:\t"+filePath);

				if (zte_cdn_log_intoDB.ImportDB(filePath, fileName)) /*
																	 * data
																	 * import DB
																	 */
				{
					cdnSetLog.updateStatus("cdn_zte_log_untar",
							fileName.replace(".txt", ".tar.gz"), "YES");// ������־flag
				} else {
					cdnSetLog.updateStatus("cdn_zte_log_untar",
							fileName.replace(".txt", ".tar.gz"), "ERROR");
				 
			}
			 

		} catch (Throwable e) {
			// System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}
   
	//zte_cdn_log_intoDB.CreateTable(table_name); // create table and  index ;
	public static int IntoDB_Thread() throws Exception {

List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		
		ListListFile = zte_cdn_log_intoDB.getThreadFileList();
		 int i = ListListFile.size() ;
		 if (i==0){
			 return 0;
		 }
		 
		 for (int k = 0;k<ListListFile.size();k++){
			//System.out.println(k+"\t"+ ListListFileName.get(k).size());
			 zte_cdn_log_intoDB_thread jt = null;
			  for (int j=0;j<ListListFile.get(k).size();j++)
			 {    
				  String table_name = zte_cdn_log_intoDB.getTableName(ListListFile.get(k).get(j)[1]);				  
				  String table_name_tomorrow = zte_cdn_log_intoDB.getTableName_tomorrow(ListListFile.get(k).get(j)[1]);  ;//getTomorrow("20181231")
				 String table_name_yesterday = zte_cdn_log_intoDB.getTableName_yesterday(ListListFile.get(k).get(j)[1]);  ;//getTomorrow("20181231")
					 
				  
				  
				  
				  zte_cdn_log_intoDB.CreateTable(table_name);
				  zte_cdn_log_intoDB.CreateTable(table_name_tomorrow);
				  zte_cdn_log_intoDB.CreateTable(table_name_yesterday);
				  
				  jt = new zte_cdn_log_intoDB_thread(ListListFile.get(k).get(j)[0]+","+ListListFile.get(k).get(j)[1]);
				  jt.start();
								 
			 }
			 
			  jt.join();
			  Thread.sleep(1000 );
			 
		 }
		 
		 return i;
	}

	 

	public static void main(String[] args) throws Exception {
		// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次

		if ( args.length <1 ) {
			System.out.println("The intoDB Program  restarted  with no para");
			while (true) {

				int i = IntoDB_Thread();				
				if (i==0){
				System.out.println("The intoDB Program will restart in 5 min");
				 Thread.sleep(1000 * 60 * intervalTime);				 
				}
				else {
					 Thread.sleep(1000 );
				}
				System.out.println("----------------------------------------------------------------------------");
			}

		} else {
			boolean b = true;
			System.out.println("The intoDB Program  restarted  with para");
			while (b) {
			
				
				int i = IntoDB_Thread();
				System.out.println("The intoDB Program will restart in 1 min");
				
				
				
				System.out.println("----------------------------------------------------------------------------");
				Thread.sleep(1000);
				
				if (i == 0) {			 
					b = false;
				}
			}
		}
	}
	

}
