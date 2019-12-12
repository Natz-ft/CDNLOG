package TOOLS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class getProperties {
	
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(getPropertie("ZTElocalPath"));
		
	}
	
	public static String getPropertie(String col) {
		String Properties = "";
		
		Properties prop;
		prop = new Properties();		
		try {		 
			prop.load(new FileInputStream(getPath()));
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		  Properties = prop.getProperty(col);
        
		try {
			Properties= new String(Properties.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return Properties;
	}
	
	public static int getINTPropertie(String col){
		int Properties = 0;
		
		Properties prop;
		prop = new Properties();		
		try {		 
			prop.load(new FileInputStream(getPath()));
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		finally {
			
		}
		
		  Properties = Integer.parseInt(prop.getProperty(col)) ;
		
		return Properties;
	}
	
	
	
	public static String getPath() { // ��ȡ�����ļ�·��
		String path = "", path_2 = "", path_1 = "";

		try {

			getProperties g = new getProperties();
			Properties prop = new Properties();
			File directory = new File("");// ����Ϊ��

			//System.out.println("path:  "+ g.getClass().getResource("/").getPath());
			File f = new File(g.getClass().getResource("/").getPath());

			try {
				path_1 = directory.getCanonicalPath(); // java project
				path_2 = f.getPath();// web project
				// System.out.println("path_1" + path_1);
				// System.out.println("path_2" + path_2);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				// ��������·��
				path = path_1 + File.separator + "src" + File.separator 	+ "db.properties";
				
				prop.load(new FileInputStream(path));
				
				 //System.out.println("path" + path);
				 
			} catch (IOException e1) {
				// path+ File.separator +"db.properties"
				//System.out.println("path_2��" + path_2);

				try {
					// web����·��
					path = path_2 + File.separator + "db.properties";
					prop.load(new FileInputStream(path));
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}
		} catch (Exception e0) {
			File directory = new File("");// ����Ϊ��
			try {
				path = directory.getCanonicalPath();
				path = path + File.separator + "db.properties";				
				//System.out.println("db.properties:  "+path);
			}
			catch (Exception e01) {
				e01.printStackTrace();
			}
		}
	//	System.out.println("path:  "+path);
		return path;

	}

}

