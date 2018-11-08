package zte;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAccess_new;
import TOOLS.SftpManager;

import com.jcraft.jsch.ChannelSftp;

public class test03 {
	static String DBInfo ="com.mysql.jdbc.Driver"+"|"+"jdbc:mysql://localhost/dirxu?useSSL=false&characterEncoding=utf8"+"|"+"root"+"|"+"PaTr@1234";
	static String[] dbinfo = DBInfo.split("\\|");
	public static void main(String[] args) throws Exception {
		   
		   List<List<String[]>> ListListFile = new ArrayList<List<String[]>>();
		       List<String[]> ListFile = new ArrayList<String[]>();
		    //   List<String> ListFileName = new ArrayList<String>();
		       
		      // List<List<String>> ListListFileName = new ArrayList<List<String>>();  
		       
		 DBAccess_new db = new DBAccess_new(dbinfo);
		 
		 String sql = "select localpath,fileName from cdn_zte_log_sftp   ";
		 
		 int i = 0;
		 if (db.createConn()) {
		 db.query(sql);
		
		 while (db.next()) {
			 
			 String fileName[]   = new String[2];
			
			 fileName[0] =db.getValue("localpath");//   db.getValue(1); 
			 fileName[1] =db.getValue("fileName"); 
			// ListFile.add(fileName);
			 
			 ListFile.add(fileName);
			// System.out.println("fileName[1]:\t"+fileName[1]);
			 i++;
		 
			 if (i==10)
			 {
				 
				
				 List<String[]> ListFileName_1= new ArrayList<String[]>();
				 
				 for (int m=0 ;m<ListFile.size();m++ ){
					 ListFileName_1.add(ListFile.get(m));
				  }					 
				 ListListFile.add(ListFileName_1);
				 ListFile.clear();				 
				 i=0;
			 }
			 
		 }
		 }
		 ListListFile.add(ListFile) ;
		 

		 
		 
 		db.closeRs();
 		db.closeConn();
		 for (int k = 0;k<ListListFile.size();k++){
			//System.out.println(k+"\t"+ ListListFileName.get(k).size());
			 
			  for (int j=0;j<ListListFile.get(k).size();j++)
			 {
				 
				 System.out.println(k+"\t"+j+"\t"+ListListFile.get(k).get(j)[0]+"\t"+ListListFile.get(k).get(j)[1]);
				 
			 }
			 
			 
		 }
	}	
}
