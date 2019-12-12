package hw;

import java.util.ArrayList;
import java.util.List;

import zte.zte_cdn_log_unzip;
import zte.zte_cdn_log_unzip_thread;
import TOOLS.getProperties;

public class hw_cdn_log_unzip_thread extends Thread {
	// public static List<String[]> fileNameList = new ArrayList<String[]>();
	public static int intervalTime = getProperties.getINTPropertie("SleepTime");
	public static int Threadnum = getProperties.getINTPropertie("HWThreadNum");

	public static List<String[]> getFileList() {
		List<String[]> fileNameList = new ArrayList<String[]>();
		return fileNameList;
	}

	static String[] path_fileName = new String[2];

	/*
	 * zte_cdn_log_unzip_thread(String name){ super(name); }
	 */

	hw_cdn_log_unzip_thread(String name) {
		super(name);

	}

	public void run() {

		// String [] pathfileName = getPath_fileName();
		String FILE = getName();
		String filePath = FILE.substring(0, FILE.indexOf(","));
		String fileName = FILE.substring(FILE.indexOf(",") + 1, FILE.length());

		try {

			hw_cdn_log_unzip.decompressFile(filePath, fileName);
			// System.out.println("sss:"+filePath+"\t"+fileName);
		} catch (Throwable e) {
			// System.out.println("FILE:\t"+FILE);

			e.printStackTrace();
		}
	}

	public static int UNZIP_Thread() throws Exception {

		List<String[]> ListListFile = new ArrayList<String[]>();

		ListListFile = hw_cdn_log_unzip.getThreadFileList_new();
		
		int i = ListListFile.size();
		if (i == 0) {
			return 0;
		}

		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();

		for (int k = 0; k < ListListFile.size(); k++) {

			int noThreads = currentGroup.activeCount();

			while (noThreads >= (Threadnum*5)) {
				Thread.sleep(5000);
				noThreads = currentGroup.activeCount();

				System.out.println("当前华为解压缩线程组数：\t" + noThreads);

			}

			hw_cdn_log_unzip_thread jt = new hw_cdn_log_unzip_thread(ListListFile.get(k)[0] + "," + ListListFile.get(k)[1]);
			jt.start();

		}
		Thread.sleep(1000);
		return i;

	}

	public static void main(String[] args) throws Exception {
		// 如果没有参数，程序始终执行，如果有任何参数 程序 执行 一次

		System.out
				.println("The HW_unzip_Thread Program  restarted  with no para");

		while (true) {

			int i = UNZIP_Thread();
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
