package TOOLS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

  
public class FtpMain {  
	public static void main(String[] args) throws FileNotFoundException {
	    //ftp服务器IP地址
        String ftpHost = "221.180.176.174";  
        //ftp服务器端口
        int ftpPort = 21;  
        //ftp服务器用户名
        String ftpUserName = "vixtelftp";
        //ftp服务器密码
        String ftpPassword = "LNwg!@#$1qaz";  
        //ftp服务器路径
        String ftpPath = "/"; 
       //本地路径
        String localPath = "F://FTP";  
        //文件名
        String fileName = "Provincial-link-20190108.csv";  
        
        /*  服务器IP:221.180.176.174
  端口：21
  账号：vixtelftp
  密码：LNwg!@#$1qaz
         * */
        //下载
        //将ftp根目录下的文件下载至E盘
        try {
         FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);  
        }
        catch(NullPointerException e){
        	System.out.println("ERROR!!!	There is not  "+fileName);
        }
        //上传
        //将E盘的文件上传至ftp根目录
        /*FileInputStream in=new FileInputStream(new File("e:/" + fileName)); 
        FtpUtil.uploadFile(ftpHost, ftpPort, ftpUserName, ftpPassword, "ftpdir/", "/", fileName, in);*/
        
        //删除
        //删除ftp根目录下的文件
        // FtpUtil.deleteFile(ftpHost, ftpPort, ftpUserName, ftpPassword, "ftpdir/", "2.docx");
        
        
	}
} 

