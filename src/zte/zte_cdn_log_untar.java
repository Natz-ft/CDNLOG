package zte;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import TOOLS.DBAccess_new;
import TOOLS.DBAcess;
import TOOLS.SetLog;
import TOOLS.cdnSetLog;
import TOOLS.getProperties;

public class zte_cdn_log_untar {
	private static final String BASE_DIR = "";
	  private static final String PATH = File.separator;
	  private static final int BUFFER = 1024;
	  private static final String EXT = ".tar";
		public static String sftpServer = getProperties.getPropertie("ZTEsftpServer").trim();
		public static String sftpUser = getProperties.getPropertie("ZTEsftpUser").trim();
		public static String sftpPass = getProperties.getPropertie("ZTEsftpPass").trim();
		public static String sftpPath = getProperties.getPropertie("ZTEsftpPath").trim();
		public static String localPath = getProperties.getPropertie("ZTElocalPath").trim();
		public static String sftPort = getProperties.getPropertie("ZTEsftPort").trim();
		public static String ZTEMUST = getProperties.getPropertie("ZTEMustHave").trim();
		public static String ZTEMDoNOT = getProperties.getPropertie("ZTEDoNotHave").trim();

		public static String driver = getProperties.getPropertie("mysqldriver").trim();
		public static String url = getProperties.getPropertie("localurl").trim();
		public static String user = getProperties.getPropertie("localuser").trim();
		public static String passwd = getProperties.getPropertie("localpassword").trim();
		
		public static int  threadNum = getProperties.getINTPropertie("ZTEThreadNum"); 
		
		public static int intervalTime = getProperties.getINTPropertie("SleepTime");
	  
	  /** 
	   * 归档 
	   *  
	   * @param srcPath 
	   * @param destPath 
	   * @throws Exception 
	   */  
	  public static void archive(String srcPath, String destPath)  
	          throws Exception {  

	      File srcFile = new File(srcPath);  

	      archive(srcFile, destPath);  

	  }  

	  /** 
	   * 归档 
	   *  
	   * @param srcFile 
	   *            源路径 
	   * @param destPath 
	   *            目标路径 
	   * @throws Exception 
	   */  
	  public static void archive(File srcFile, File destFile) throws Exception {  

	      TarArchiveOutputStream taos = new TarArchiveOutputStream(  
	              new FileOutputStream(destFile));  

	      archive(srcFile, taos, BASE_DIR);  

	      taos.flush();  
	      taos.close();  
	  }  

	  /** 
	   * 归档 
	   *  
	   * @param srcFile 
	   * @throws Exception 
	   */  
	  public static void archive(File srcFile) throws Exception {  
	      String name = srcFile.getName();  
	      String basePath = srcFile.getParent();  
	      String destPath = basePath+File.separator + name + EXT;  
	      archive(srcFile, destPath);  
	  }  

	  /** 
	   * 归档文件 
	   *  
	   * @param srcFile 
	   * @param destPath 
	   * @throws Exception 
	   */  
	  public static void archive(File srcFile, String destPath) throws Exception {  
	      archive(srcFile, new File(destPath));  
	  }  

	  /** 
	   * 归档 
	   *  
	   * @param srcPath 
	   * @throws Exception 
	   */  
	  public static void archive(String srcPath) throws Exception {  
	      File srcFile = new File(srcPath);  

	      archive(srcFile);  
	  }  

	  /** 
	   * 归档 
	   *  
	   * @param srcFile 
	   *            源路径 
	   * @param taos 
	   *            TarArchiveOutputStream 
	   * @param basePath 
	   *            归档包内相对路径 
	   * @throws Exception 
	   */  
	  private static void archive(File srcFile, TarArchiveOutputStream taos,  
	          String basePath) throws Exception {  
	      if (srcFile.isDirectory()) {  
	          archiveDir(srcFile, taos, basePath);  
	      } else {  
	          archiveFile(srcFile, taos, basePath);  
	      }  
	  }  

	  /** 
	   * 目录归档 
	   *  
	   * @param dir 
	   * @param taos 
	   *            TarArchiveOutputStream 
	   * @param basePath 
	   * @throws Exception 
	   */  
	  private static void archiveDir(File dir, TarArchiveOutputStream taos,  
	          String basePath) throws Exception {  

	      File[] files = dir.listFiles();  

	      if (files.length < 1) {  
	          TarArchiveEntry entry = new TarArchiveEntry(basePath  
	                  + dir.getName() + PATH);  

	          taos.putArchiveEntry(entry);  
	          taos.closeArchiveEntry();  
	      }  

	      for (File file : files) {  

	          // 递归归档  
	          archive(file, taos, basePath + dir.getName() + PATH);  

	      }  
	  }  

	  /** 
	   * 数据归档 
	   *  
	   * @param data 
	   *            待归档数据 
	   * @param path 
	   *            归档数据的当前路径 
	   * @param name 
	   *            归档文件名 
	   * @param taos 
	   *            TarArchiveOutputStream 
	   * @throws Exception 
	   */  
	  private static void archiveFile(File file, TarArchiveOutputStream taos,  
	          String dir) throws Exception {  

	      /** 
	       * 归档内文件名定义 
	       *  
	       * <pre> 
	       * 如果有多级目录，那么这里就需要给出包含目录的文件名 
	       * 如果用WinRAR打开归档包，中文名将显示为乱码 
	       * </pre> 
	       */  
	      TarArchiveEntry entry = new TarArchiveEntry(dir + file.getName()); 
	       //如果打包不需要文件夹，就用 new TarArchiveEntry(file.getName())

	      entry.setSize(file.length());  

	      taos.putArchiveEntry(entry);  

	      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(  
	              file));  
	      int count;  
	      byte data[] = new byte[BUFFER];  
	      while ((count = bis.read(data, 0, BUFFER)) != -1) {  
	          taos.write(data, 0, count);  
	      }  

	      bis.close();  

	      taos.closeArchiveEntry();  
	  }  

	  /** 
	   * 解归档 
	   *  
	   * @param srcFile 
	   * @throws Exception 
	   */  
	  public static void dearchive(File srcFile) throws Exception {  
	      String basePath = srcFile.getParent();  
	      dearchive(srcFile, basePath);  
	  }  

	  /** 
	   * 解归档 
	   *  
	   * @param srcFile 
	   * @param destFile 
	   * @throws Exception 
	   */  
	  public static void dearchive(File srcFile, File destFile) throws Exception {  

	      TarArchiveInputStream tais = new TarArchiveInputStream(  
	              new FileInputStream(srcFile));  
	      dearchive(destFile, tais);  

	      tais.close();  

	  }  

	  /** 
	   * 解归档 
	   *  
	   * @param srcFile 
	   * @param destPath 
	   * @throws Exception 
	   */  
	  public static void dearchive(File srcFile, String destPath)  
	          throws Exception {  
	      dearchive(srcFile, new File(destPath));  

	  }  

	  /** 
	   * 文件 解归档 
	   *  
	   * @param destFile 
	   *            目标文件 
	   * @param tais 
	   *            ZipInputStream 
	   * @throws Exception 
	   */  
	  private static void dearchive(File destFile, TarArchiveInputStream tais)  
	          throws Exception {  

	      TarArchiveEntry entry = null;  
	      while ((entry = tais.getNextTarEntry()) != null) {  

	          // 文件  
	          String dir = destFile.getPath() + File.separator + entry.getName();  

	          File dirFile = new File(dir);  

	          // 文件检查  
	          fileProber(dirFile);  

	          if (entry.isDirectory()) {  
	              dirFile.mkdirs();  
	          } else {  
	              dearchiveFile(dirFile, tais);  
	          }  

	      }  
	  }  

	  /** 
	   * 文件 解归档 
	   *  
	   * @param srcPath 
	   *            源文件路径 
	   *  
	   * @throws Exception 
	   */  
	  public static void dearchive(String srcPath) throws Exception {  
	      File srcFile = new File(srcPath);  

	      dearchive(srcFile);  
	  }  

	  /** 
	   * 文件 解归档 
	   *  
	   * @param srcPath 
	   *            源文件路径 
	   * @param destPath 
	   *            目标文件路径 
	   * @throws Exception 
	   */  
	  public static void dearchive_old(String srcPath, String destPath)  
	          throws Exception {  

	      File srcFile = new File(srcPath);  
	      dearchive(srcFile, destPath);  
	  }  
	  
	  public static boolean dearchive(String srcPath, String destPath)
	  {
	    boolean b = false;
	    try
	    {
	      File srcFile = new File(srcPath);
	      dearchive(srcFile, destPath);
	      b = true;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return b;
	  } 
	  
	  
	  

	  /** 
	   * 文件解归档 
	   *  
	   * @param destFile 
	   *            目标文件 
	   * @param tais 
	   *            TarArchiveInputStream 
	   * @throws Exception 
	   */  
	  private static void dearchiveFile(File destFile, TarArchiveInputStream tais)  
	          throws Exception {  

	      BufferedOutputStream bos = new BufferedOutputStream(  
	              new FileOutputStream(destFile));  

	      int count;  
	      byte data[] = new byte[BUFFER];  
	      while ((count = tais.read(data, 0, BUFFER)) != -1) {  
	          bos.write(data, 0, count);  
	      }  

	      bos.close();  
	  }  

	  /** 
	   * 文件探针 
	   *  
	   * <pre> 
	   * 当父目录不存在时，创建目录！ 
	   * </pre> 
	   *  
	   * @param dirFile 
	   */  
	  private static void fileProber(File dirFile) {  

	      File parentFile = dirFile.getParentFile();  
	      if (!parentFile.exists()) {  

	          // 递归寻找上级目录  
	          fileProber(parentFile);  

	          parentFile.mkdir();  
	      }  

	  }  
	  
	  public static void dearchive()
	  {
	    DBAcess db = new DBAcess();
	    if (db.createConnLocal())
	    {
	      String sql = "select  fileName,path  from cdn_zte_log_unzip where ( status='' or status is null )  order by fileName desc   limit 3 ";
	      db.query(sql);
	      while (db.next())
	      {
	        String fileName = db.getValue("fileName");
	        String path = db.getValue("path");
	        try
	        {
	          System.out.println("UNTAR FILEL:\t" + path + File.separator + fileName.replace(".gz", ""));
	          if (dearchive(path + File.separator + fileName.replace(".gz", ""), path.replace("\\", "\\\\")))
	          {
	        	  cdnSetLog.setlog("cdn_zte_log_untar", fileName, path.replace("\\", "\\\\"));
	        	  cdnSetLog.updateStatus("cdn_zte_log_unzip", fileName, "YES");
	          }
	          else
	          {
	        	  cdnSetLog.updateStatus("cdn_zte_log_unzip", fileName, "ERROR");
	          }
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }
	      db.closeRs();
	      db.closeStm();
	    }
	    db.closeConn();
	  }
	  
	  public static void dearchivefile(String path, String fileName)
	  {
	    try
	    { 
	    	cdnSetLog.updateStatus("cdn_zte_log_unzip", fileName, "ING");
	      System.out.println("UNTAR FILEL:\t" + path + File.separator + fileName.replace(".gz", ""));
	      if (dearchive(path + File.separator + fileName.replace(".gz", ""), path.replace("\\", "\\\\")))
	      {
	    	cdnSetLog.setlog("cdn_zte_log_untar", fileName, path.replace("\\", "\\\\"));
	    	cdnSetLog.updateStatus("cdn_zte_log_unzip", fileName, "YES");
	      }
	      else
	      {
	    	  cdnSetLog.updateStatus("cdn_zte_log_unzip", fileName, "ERROR");
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  
	  public static List<List<String[]>> getThreadFileList() {
			String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
			String[] dbinfo = DBInfo.split("\\|");
			DBAccess_new db = new DBAccess_new(dbinfo);
			List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
			List<String[]> ListFile = new ArrayList<String[]>();
			 

			

			String sql = "select path,fileName from cdn_zte_log_unzip where status is null or status = ''    ";

			int i = 0;
			if (db.createConn()) {
				db.query(sql);
				while (db.next()) {
					String fileName[] = new String[2];
					fileName[0] = db.getValue("path");// db.getValue(1);
					fileName[1] = db.getValue("fileName");
					ListFile.add(fileName);				 
					i++;
					if (i == threadNum) {
						List<String[]> ListFileName_1 = new ArrayList<String[]>();

						for (int m = 0; m < ListFile.size(); m++) {
							ListFileName_1.add(ListFile.get(m));
						}
						ListListFile.add(ListFileName_1);
						ListFile.clear();
						i = 0;
					}

				}
			}
			 if (ListFile.size()>0) {
			ListListFile.add(ListFile);}
			 
			db.closeRs();
			db.closeConn();
			return ListListFile;
		}
	  
	  
	  public static void main(String[] args)
	    throws Exception
	  {
	    for (;;)
	    {
	      dearchive();
	      Thread.sleep(10000L);
	    }
	  }

}
