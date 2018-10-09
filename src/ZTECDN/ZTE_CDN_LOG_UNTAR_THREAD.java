package ZTECDN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.getProperties;




public class ZTE_CDN_LOG_UNTAR_THREAD extends Thread { 
	
	public static int Threadnum = getProperties.getINTPropertie("ZTEThreadNum");
													 
	 public static int intervalTime = getProperties.getINTPropertie("SleepTime");  												
													
													
													
	public ZTE_CDN_LOG_UNTAR_THREAD(String name) {
		super(name);
	}

	public void run() {
		 

		String FILE = getName();
		String   filePath= FILE.substring(0,FILE.indexOf(","));
		String fileName = FILE.substring(FILE.indexOf(",")+1,FILE.length());
		//fileName = fileName.replace(".tar.gz", ".txt");

		try {				
		    ZTE_CDN_LOG_UNTAR.dearchivefile(filePath, fileName); 	 

		} catch (Throwable e) {
			//System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}
// String sql = "select fileName,path from cdn_zte_log_unzip where ( status='' or status is null ) order by fileName    ";
	public static int UNTAR_Thread() throws Exception {
		 

		int Thread_num = Threadnum  ;
		
		DBAcess db = new DBAcess();
		String sql ="select fileName,path from cdn_zte_log_unzip where ( status='' or status is null ) order by fileName    limit " 	+ String.valueOf(Thread_num);
		System.out.println(sql);
		int i = 0;
		if (db.createConnLocal()) {
			db.query(sql);
			ZTE_CDN_LOG_UNTAR_THREAD jt = null;
			while (db.next()) {
				i++;
				String fileName = db.getValue("fileName");
				String path = db.getValue("path");
				 

				jt = new ZTE_CDN_LOG_UNTAR_THREAD(path + "," + fileName);

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
