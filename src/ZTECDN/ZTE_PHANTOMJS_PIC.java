package ZTECDN;//ZTE_PHANTOMJS_PIC

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import TOOLS.DBAcess;
import TOOLS.getProperties;
import TOOLS.getDate;

public class ZTE_PHANTOMJS_PIC {
	

	
public  static  String[] PAGE = getProperties.getPropertie("phantomjs_PAGE").trim().split("\\|");
public  static  String  phantomjs_js = getProperties.getPropertie("phantomjs_js_path").trim();
public  static  String  phantomjs_pic_path = getProperties.getPropertie("phantomjs_pic_path").trim();


	
    public static void main(String[] args) {
    	Runtime run = Runtime.getRuntime();
    	  DBAcess db = new DBAcess();
    	for (int i=0;i<PAGE.length;i++){
    		String url =  getProperties.getPropertie("url").trim();
   		   String vdate  =  getDate.getYesterday();
   		 
   		String  phantomjs_path =  getProperties.getPropertie("phantomjs_path").trim();
    		
    		
    	try {
            // run.exec("cmd /k shutdown -s -t 3600");
    		System.out.println("phantomjs:"+phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" " +vdate);
            Process process = run.exec(phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" " +vdate);
            
            InputStream in = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("GBK")));
            //BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK") ) );

            String line;
            while((line = reader.readLine())!= null){
                System.out.println(line);
            }
            process.waitFor();
            in.close();
           
            reader.close();
            process.destroy();
            
          
            if (db.createConnLocal())
            {
               
             
              String vtime = getDate.getDate2();
             
              String pic_path =phantomjs_pic_path.replace("\\", "\\\\");
              String sql = "insert into cdn_pic_day (pic_name,pic_path,vtime) values('"+PAGE[i]+"_"+vdate+".png','"+pic_path+"','"+vtime+"')";
               
              System.out.println(sql);
              db.insert(sql);
            }        
            
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	}
    	  db.closeConn();
        
        
        
    }
}
