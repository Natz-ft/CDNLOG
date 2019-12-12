package ZTECDN;//ZTE_PHANTOMJS_PIC

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import TOOLS.DBAcess;
import TOOLS.getProperties;
import TOOLS.getDate;

public class ZTE_PHANTOMJS_PIC_DNS {
	

	
public  static  String[] PAGE = getProperties.getPropertie("phantomjs_PAGE_dns").trim().split("\\|");
public  static  String  phantomjs_js = getProperties.getPropertie("phantomjs_js_path").trim();
public  static  String  phantomjs_pic_path = getProperties.getPropertie("phantomjs_pic_path").trim();


public  static  String[] dns_ip = getProperties.getPropertie("dns_ip").trim().split("\\|");


	
    public static void main(String[] args) {
    	 
    
    	 String vdate  = "";
		 if (args.length < 1) {
	   			vdate = getDate.getYesterday();
	   	    } else {
	   	    	vdate = args[0];
	   	    }
		 
		/* for (String ip :dns_ip ) {
			 makePic(ip,vdate);
			 //System.out.println(ip);
		 }*/
		 
		 makePic(vdate);
        
        
        
    }
    
    
     public static void makePic(String vdate  ){
    		
    	 Runtime run = Runtime.getRuntime(); 
    	 
    		
    		for (int i=0;i<PAGE.length;i++){
     		
     		String url =  getProperties.getPropertie("url").trim();
    		   
    
    		     
    		 
    		String  phantomjs_path =  getProperties.getPropertie("phantomjs_path").trim();
     		
     		
     	try {
     		if (PAGE[i].equals("dns_bus.jsp")) {
     		 for (String ip :dns_ip ) {
     		
             // run.exec("cmd /k shutdown -s -t 3600");
     		 System.out.println("phantomjs:"+phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" " +vdate);
             Process process = run.exec(phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+"?ip="+ip+  " "+PAGE[i]+" " +phantomjs_pic_path+" " +ip+"_"+vdate);
             
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
     		 }
     		}
     		else {
     			
     			 System.out.println("phantomjs:"+phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" " +vdate);
                 Process process = run.exec(phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" "+vdate);
                // Process process = run.exec(phantomjs_path+"phantomjs "+phantomjs_js+ " "+  url+ PAGE[i]+" "+PAGE[i]+" " +phantomjs_pic_path+" "+vdate);
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
     			
     		}
              

         } catch (Exception e) {
             e.printStackTrace();
         }
      
     	}  	 
    	 
     }
    
    
}
