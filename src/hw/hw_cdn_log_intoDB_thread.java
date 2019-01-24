package hw;

import java.util.ArrayList;
import java.util.List;

 
import TOOLS.cdnSetLog;
import TOOLS.getProperties;

public class hw_cdn_log_intoDB_thread extends Thread {
	public static int Threadnum = getProperties.getINTPropertie("HWThreadNum");
	public static int intervalTime = getProperties.getINTPropertie("SleepTime");  

	public hw_cdn_log_intoDB_thread(String name) {
		super(name);
	}

	public void run() {

		String FILE = getName();
		String filePath = FILE.substring(0, FILE.indexOf(","));
		String fileName = FILE.substring(FILE.indexOf(",") + 1, FILE.length());
		fileName = fileName.replace(".gz", "");

		try {
		

				// System.out.println("FILE:\t"+FILE);
				// System.out.println("fileName:\t"+fileName);
				// System.out.println("filePath:\t"+filePath);
			
			//System.out.println(fileName);
			
			cdnSetLog.updateStatus("cdn_hw_log_unzip", fileName + ".gz","ING");// 
			
			if (hw_cdn_log_intoDB.ImportDB_new(filePath, fileName)) {
				cdnSetLog.updateStatus("cdn_hw_log_unzip", fileName + ".gz",
						"YES");// ������־flag
			} else {
				cdnSetLog.updateStatus("cdn_hw_log_unzip", fileName + ".gz",
						"ERROR");

			}
			 

		} catch (Throwable e) {
			// System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}
   
	 
	public static int IntoDB_Thread(String arg) throws Exception {

List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		
		ListListFile = hw_cdn_log_intoDB.getThreadFileList(arg);
		 int i = ListListFile.size() ;
		 if (i==0){
			 return 0;
		 }
		 
		 for (int k = 0;k<ListListFile.size();k++){
			//System.out.println(k+"\t"+ ListListFileName.get(k).size());
			 hw_cdn_log_intoDB_thread jt = null;
			  for (int j=0;j<ListListFile.get(k).size();j++)
			 {    
				  String table_name = hw_cdn_log_intoDB.getTableName(ListListFile.get(k).get(j)[1]);
				  String table_name_tomorrow = hw_cdn_log_intoDB.getTableName_tomorrow(ListListFile.get(k).get(j)[1]);
				  String table_name_yesterday = hw_cdn_log_intoDB.getTableName_getYesterday(ListListFile.get(k).get(j)[1]);
				  
				  hw_cdn_log_intoDB.CreateTable(table_name);
				  hw_cdn_log_intoDB.CreateTable(table_name_tomorrow);
				  hw_cdn_log_intoDB.CreateTable(table_name_yesterday);
				  jt = new hw_cdn_log_intoDB_thread(ListListFile.get(k).get(j)[0]+","+ListListFile.get(k).get(j)[1]);
				  jt.start();
								 
			 }
			 
			  jt.join();
			  //Thread.sleep(1000 );
			 
		 }
		 
		 return i;
	}
	
	public static int IntoDB_Thread_new(String arg) throws Exception {

 
List<String[]> ListListFile = new ArrayList<String[]>();
		ListListFile = hw_cdn_log_intoDB.getThreadFileList_new(arg);
		 int i = ListListFile.size() ;
		 if (i==0){
			 return 0;
		 }
		 ThreadGroup currentGroup =Thread.currentThread().getThreadGroup();
		// Thread curretThread =  Thread.currentThread();
		 
		 for (int k = 0;k<ListListFile.size();k++){
			 hw_cdn_log_intoDB_thread jt = null;
			// int ThreadCuNo = curretThread.activeCount();
			
			 int noThreads = currentGroup.activeCount();
			 
			 while(noThreads>= (Threadnum)) {
				 Thread.sleep(5000 );
				 noThreads = currentGroup.activeCount();
				 //ThreadCuNo = curretThread.activeCount();
				 
				// System.out.println("当前华为入库线程组数：\t"+noThreads);
				 
			 }  
				  String table_name = hw_cdn_log_intoDB.getTableName_HW(ListListFile.get(k)[1]);
				   
				  hw_cdn_log_intoDB.CreateTable(table_name);
				  
				    jt = new hw_cdn_log_intoDB_thread(ListListFile.get(k)[0]+","+ListListFile.get(k)[1]);
				  jt.start();
								 
			 }	 
		 
		 return i;
	}
	
	
	

	 

	public static void main(String[] args) throws Exception {	
	 
		if (args.length < 1) {
			// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次
			System.out.println("The HW_intoDB Program  restarted  with no para");
			while (true) {
				//int i = IntoDB_Thread("%");
				int i = IntoDB_Thread_new("%");
				
				if (i == 0) {
					System.out.println("The HW_intoDB Program will restart in 10 Sec ");
					Thread.sleep(1000 * 10);
				} else {
					Thread.sleep(1000);
				}
				System.out.println("----------------------------------------------------------------------------");
			}
		} else {
			System.out.println("The HW_intoDB Program  restarted  with   para:'"+args[0]+"'");
			//IntoDB_Thread(args[0]);
			  IntoDB_Thread_new("%");

		}
			
			
			
			
			
			
			

		 
	}

}
