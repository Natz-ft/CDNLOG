package ZTECDN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.SetLog;
import TOOLS.getProperties;

public class ZTE_CDN_LOG_IntoDB_THREAD extends Thread {

	public static int Threadnum = getProperties.getINTPropertie("ZTEThreadNum");
	public static int intervalTime = getProperties.getINTPropertie("SleepTime");  

	public ZTE_CDN_LOG_IntoDB_THREAD(String name) {
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

				if (ZTE_CDN_LOG_IntoDB.ImportDB(filePath, fileName)) /*
																	 * data
																	 * import DB
																	 */
				{
					SetLog.updateStatus("cdn_zte_log_untar",
							fileName.replace(".txt", ".tar.gz"), "YES");// ������־flag
				} else {
					SetLog.updateStatus("cdn_zte_log_untar",
							fileName.replace(".txt", ".tar.gz"), "ERROR");
				 
			}
			 

		} catch (Throwable e) {
			// System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}

	public static int IntoDB_Thread() throws Exception {

		int Thread_num = Threadnum  ;
		
		DBAcess db = new DBAcess();
		String sql = "select fileName,path from cdn_zte_log_untar where ( status='' or status is null ) limit " 	+ String.valueOf(Thread_num);
		System.out.println(sql);
		int i = 0;
		if (db.createConnLocal()) {
			db.query(sql);
			ZTE_CDN_LOG_IntoDB_THREAD jt = null;
			while (db.next()) {
				i++;
				String fileName = db.getValue("fileName");
				String path = db.getValue("path");
				String table_name = ZTE_CDN_LOG_IntoDB.getTableName(fileName);
				ZTE_CDN_LOG_IntoDB.CreateTable(table_name); // create table and  index ;

				jt = new ZTE_CDN_LOG_IntoDB_THREAD(path + "," + fileName);

				jt.start();

			}
			if (i > 0) {
				jt.join();
			}

			db.closeRs();
			db.closeStm();

		}
		db.closeConn();
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
