package dns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import TOOLS.DBAccess_new;
import TOOLS.DBAcess;
import TOOLS.FileWrite;
import TOOLS.ReadFromFile;
import TOOLS.cdnSetLog;
import TOOLS.getProperties;

public class DNS_unzip {

	public static String localPath = getProperties.getPropertie("DNSlocalPath").trim();
	  public static final int BUFFER = 1024;  
	    public static final String EXT = ".gz";  
	  
	    /** 
	     * ����ѹ�� 
	     *  
	     * @param data 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] compress(byte[] data) throws Exception {  
	        ByteArrayInputStream bais = new ByteArrayInputStream(data);  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	  
	        // ѹ��  
	        compress(bais, baos);  
	  
	        byte[] output = baos.toByteArray();  
	  
	        baos.flush();  
	        baos.close();  
	  
	        bais.close();  
	  
	        return output;  
	    }  
	  
	    /** 
	     * �ļ�ѹ�� 
	     *  
	     * @param file 
	     * @throws Exception 
	     */  
	    public static void compress(File file) throws Exception {  
	        compress(file, true);  
	    }  
	  
	    /** 
	     * �ļ�ѹ�� 
	     *  
	     * @param file 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ� 
	     * @throws Exception 
	     */  
	    public static void compress(File file, boolean delete) throws Exception {  
	        FileInputStream fis = new FileInputStream(file);  
	        FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);  
	  
	        compress(fis, fos);  
	  
	        fis.close();  
	        fos.flush();  
	        fos.close();  
	  
	        if (delete) {  
	            file.delete();  
	        }  
	    }  
	  
	    /** 
	     * ����ѹ�� 
	     *  
	     * @param is 
	     * @param os 
	     * @throws Exception 
	     */  
	    public static void compress(InputStream is, OutputStream os)  
	            throws Exception {  
	  
	        GZIPOutputStream gos = new GZIPOutputStream(os);  
	  
	        int count;  
	        byte data[] = new byte[BUFFER];  
	        while ((count = is.read(data, 0, BUFFER)) != -1) {  
	            gos.write(data, 0, count);  
	        }  
	  
	        gos.finish();  
	  
	        gos.flush();  
	        gos.close();  
	    }  
	  
	    /** 
	     * �ļ�ѹ�� 
	     *  
	     * @param path 
	     * @throws Exception 
	     */  
	    public static void compress(String path) throws Exception {  
	        compress(path, true);  
	    }  
	  
	    /** 
	     * �ļ�ѹ�� 
	     *  
	     * @param path 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ� 
	     * @throws Exception 
	     */  
	    public static void compress(String path, boolean delete) throws Exception {  
	        File file = new File(path);  
	        compress(file, delete);  
	    }  
	  
	    /** 
	     * �ļ�ѹ�� 
	     *  
	     * @param path 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ� 
	     * @throws Exception 
	     */  
	    public static void compress(String inputFileName, String outputFileName)  
	            throws Exception {  
	        FileInputStream inputFile = new FileInputStream(inputFileName);  
	        FileOutputStream outputFile = new FileOutputStream(outputFileName);  
	        compress(inputFile, outputFile);  
	        inputFile.close();  
	        outputFile.flush();  
	        outputFile.close();  
	    }  
	  
	    /** 
	     * ���ݽ�ѹ�� 
	     *  
	     * @param data 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] decompress(byte[] data) throws Exception {  
	        ByteArrayInputStream bais = new ByteArrayInputStream(data);  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	  
	        // ��ѹ��  
	  
	        decompress(bais, baos);  
	  
	        data = baos.toByteArray();  
	  
	        baos.flush();  
	        baos.close();  
	  
	        bais.close();  
	  
	        return data;  
	    }  
	  
	    /** 
	     * �ļ���ѹ�� 
	     *  
	     * @param file 
	     * @throws Exception 
	     */  
	    public static void decompress(File file) throws Exception {  
	        decompress(file, true, null);  
	    }  
	  
	    /** 
	     * �ļ���ѹ�� 
	     *  
	     * @param file 
	     *                ��Ҫ��ѹ���ļ� 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ� 
	     * @param outPath            
	     *            ��ѹ�ļ������·�� 
	     * @throws Exception 
	     */  
	    public static void decompress(File file, boolean delete, String outPath)  
	            throws Exception {  
	        FileInputStream fis = new FileInputStream(file);  
	        FileOutputStream fos = null;  
	        if (outPath == null || outPath=="") {  
	            fos = new FileOutputStream(file.getPath().replace(EXT, ""));  
	        } else {  
	            File files = new File(outPath);  
	            //�ж��ļ��Ƿ���ڣ������ڣ��򴴽�  
	           // FileUtil.mkDir(files);//�˴������˵ݹ鴴���ļ��У�û��д�������Ϻܶ�               
	            files.mkdirs();// ��������Ŀ¼
	            
	            //�ļ�����������У���Ҫָ���ļ���ѹ����ļ�����������ļ���ԭ����  
	            
	            fos = new FileOutputStream(outPath + File.separator + file.getName().replace(EXT, ""));  
	        }  
	  
	        decompress(fis, fos);  
	        fis.close();  
	        fos.flush();  
	        fos.close();  
	  
	        if (delete) {  
	            file.delete();  
	        }  
	    }  
	  
	    /** 
	     * �ļ���ѹ�� 
	     *  
	     * @param file 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ� 
	     * @throws Exception 
	     */  
	    public static void decompress(String inputFileName, String outputFileName)  
	            throws Exception {  
	        FileInputStream inputFile = new FileInputStream(inputFileName);  
	        FileOutputStream outputFile = new FileOutputStream(outputFileName);  
	        decompress(inputFile, outputFile);  
	        inputFile.close();  
	        outputFile.flush();  
	        outputFile.close();  
	    }  
	  
	    /** 
	     * ���ݽ�ѹ�� 
	     *  
	     * @param is 
	     * @param os 
	     * @throws Exception 
	     */  
	    public static void decompress(InputStream is, OutputStream os)  
	            throws Exception {  
	        GZIPInputStream gis = new GZIPInputStream(is);  
	        //GZIPInputStream gis = new GZIPInputStream(new BufferedInputStream(is));  
	        int count;  
	        byte data[] = new byte[BUFFER];  
	        while ((count = gis.read(data, 0, BUFFER)) != -1) {  
	            os.write(data, 0, count);  
	        }  
	  
	        gis.close();  
	    }  
	  
	    /** 
	     * �ļ���ѹ�� 
	     *  
	     * @param path 
	     * @throws Exception 
	     */  
	    public static void decompress(String path) throws Exception {  
	        decompress(path, true, null);  
	    }  
	  
	    /** 
	     * �ļ���ѹ��(��ѹ�����ļ�) 
	     *  
	     * @param path 
	     *            ��Ҫ��ѹ���ļ�·���������ļ����ƣ� 
	     * @param delete 
	     *            �Ƿ�ɾ��ԭʼ�ļ���true��ɾ����false�������� 
	     * @param outPath 
	     *            ��ѹ���ļ������·��������ò�����ֵΪ null���������ѹ�ļ�����ǰ�ļ��� 
	     * @throws Exception 
	     */  
	    public static void decompress(String path, boolean delete, String outPath)  
	            throws Exception {  
	        File file = new File(path);  
	        decompress(file, delete, outPath);  
	    } 
	    
	    public static void decompressFiles(String path)
	            throws Exception { 
	    	// String path = "F:\\ftp\\ss\\";
	    	long startTime = System.currentTimeMillis();// ��¼��ʼʱ��  
	         
	        File file = new File(path);  
	        String files[] = file.list();  
	        
	        Thread.sleep(2000);  
	        int num = 0;  
	        List list=new ArrayList();  
	        for (int x = 0; x < files.length; x++) {  
	            try {  
	                //���ý�ѹ����  
	            	if (0==cdnSetLog.Checklog("cdn_hw_log_unzip", files[x])) {
	                
	            	decompress(path + file.separator + files[x], false,path); //  false：Do not delte source ZIP file ; true delete source zipfile 
	           
	            	cdnSetLog.setlog("cdn_hw_log_untar",files[x],path.replace("\\", "\\\\")); //д���ݿ���־
	                
	                System.out.println(""+files[x] );
	            	}
	            } catch (Exception e) {  
	               // list.add(files[x]);  
	                continue;  
	            }  
	            ++num;  
	           
	        }  
	  
	      
	           
	        long endTime = System.currentTimeMillis();// ��¼����ʱ��  
	        float excTime = (float) (endTime - startTime) / 1000;  
	        System.out.println("ִ��ʱ�䣺" + excTime + "s");  
	        System.out.println("*****��ɣ���*****");  
	           
	    } 
	    
		public static boolean  decompressSingleFile(String path, String fileName) {
				 
			boolean b = false ;
			try {
			 	
					decompress(path + File.separatorChar  + fileName, false, path); 
	               
					System.out.println("unzip file:\t" + fileName);
					b = true;
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return b;

		}
		
		public static boolean  decompressSingleFile(String path, String fileName,String desPath) {
			 
			boolean b = false ;
			try {
			 	
					decompress(path + File.separatorChar  + fileName, false, desPath ); 
					       //    System.out.println(localPath + File.separator+ "unzip_" + desPath.substring(desPath.length()-8) + ".log"+"\t"+fileName);
					
					Thread.sleep(1000);
					
					 	if (ReadFromFile.checkFileExists(desPath+ File.separator + fileName.replace(".gz", ""))) {
						FileWrite.WriteFileByBuffered(localPath + File.separator+ "unzip_" + desPath.substring(desPath.length()-8) + ".log", fileName, "1");
						System.out.println("unzip file:\t" + fileName);
						b = true;
						 
					}
					
					
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return b;

		}
		
		 public static void decompress() throws Exception { // ͨ����־���ѹ���ļ����н�ѹ���� 
				DBAcess db = new DBAcess();

				if (db.createConnLocal()) {
					String sql = "select fileName,localpath from cdn_hw_log_sftp where ( status='' or status is null )  order by fileName    ";
					db.query(sql);
				while (db.next()) {
					String fileName = db.getValue("fileName");
					String path = db.getValue("localpath");

					if (decompressSingleFile(path, fileName)) {
						cdnSetLog.updateStatus("cdn_hw_log_sftp", fileName,"YES");
					}
					else
					{
						cdnSetLog.updateStatus("cdn_hw_log_sftp", fileName,"ERROR");
					}
					;
				}
					db.closeRs();
					db.closeStm();
				}
				db.closeConn();	        
		    }  
		 
		 
		 public static void decompressFile(String path,String fileName) throws Exception { // ͨ����־���ѹ���ļ����н�ѹ���� 
				 
			         cdnSetLog.updateStatus("cdn_hw_log_sftp", fileName,"ING");

					if (decompressSingleFile(path, fileName)) {
						cdnSetLog.updateStatus("cdn_hw_log_sftp", fileName,"YES");
					}
					else
					{
						cdnSetLog.updateStatus("cdn_hw_log_sftp", fileName,"ERROR");
					}
					 
				 
					     
		    }
	
	
	
	
	
   /*
    * 获取list文件
    * 
    * 
    * */
	public static List<List<String[]>> getThreadFileList() {
	 
	 
		List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		List<String[]> ListFile = new ArrayList<String[]>();		 
		return ListListFile;
	}
	
	
	public static List<String[]> getThreadFileList_new() {
	  
		List<String[]> ListListFile = new ArrayList<String[]>();
 
		return ListListFile;
	}
	
	
	
    public static void main(String[] args) throws Exception { 
		String path = "F:/ftp/20190416/";
		String fileName = "240_221180135008_20190416020832_1.gz";

		if (decompressSingleFile(path, fileName,"F:/ftp/unzip/20190416")) {
			 
		} else {
			 System.out.println("error");
		}
    	
    	
    	
    	
    	//TarUtils.dearchive();
    	//LogIotoDB.ImportDB();
	  	
    	//while (true) { 
    		//decompress() ;
    	  //Thread.sleep(60000);  
    	//}
        
    } 

}
