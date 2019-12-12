package emai;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store; // MailReceives

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
 
import com.sun.mail.pop3.POP3Message;
 
/**
 * 邮件的收取类
 */
public class ReceiveMailHandler {
	StringBuffer TextBuffer = new StringBuffer();//存放邮件内容，无html格式内容
	StringBuffer bodytext = new StringBuffer();//存放邮件内容，有html格式内容
    
	/**
	 * 获取session会话的方法
	 * @return
	 * @throws Exception
	 */
    private Session getSessionMail() throws Exception {
    	Properties properties = System.getProperties();
    	properties.put("mail.smtp.host", Config.MAIL_HOST);
    	properties.put("mail.smtp.auth", Config.MAIL_AUTH);
    	Session sessionMail = Session.getDefaultInstance(properties, null);
	    return sessionMail;
    }
    
    /**
	 * 接收邮件
	 * @param 邮箱的用户名和密码
	 * @return 无
	 */
	public void receiveMail(String userName,String passWord) {
    	Store store = null;
    	Folder folder = null;
    	int messageCount = 0;
    	URLName urln = null;
		try{
			//进行用户邮箱连接
			urln = new URLName(Config.MAIL_TYPE, Config.MAIL_HOST, Config.MAIL_PORT, null,userName,passWord);   
			store = getSessionMail().getStore(urln);   
			store.connect();
			//获得邮箱内的邮件夹Folder对象，以"只读"打开
			folder = store.getFolder("INBOX");//打开收件箱
			folder.open(Folder.READ_ONLY);//设置只读
			//获得邮件夹Folder内的所有邮件个数
			messageCount = folder.getMessageCount();// 获取所有邮件个数
	        //获取新邮件处理
			System.out.println("============>>邮件总数："+messageCount);
			if(messageCount > 0){
				Message[] messages = folder.getMessages(1,messageCount);//读取最近的一封邮件
			 
				for(int i = 0;i < messages.length;i++) {	
					TextBuffer.setLength(0); // 初始化（清空）邮件正文信息
					bodytext.setLength(0); // 初始化（清空）邮件正文信息
					String content = getMailContent((Part)messages[i]);//获取内容
					  // Multipart mp = (Multipart)messages.getContent();  
					String     content2 =getMailContent2((Part)messages[i]);//获取内容
					
				    if (isContainAttach((Part)messages[i])) {
	                	saveAttachMent((Part)messages[i],Config.MAIL_ATTACH_PATH);
	                } 
					System.out.println("=====================>>开始显示邮件内容<<=====================");
		            System.out.println("发送人: " + getFrom(messages[i]));
		            System.out.println("主题: " + getSubject(messages[i]));
		            System.out.println("内容: " + content);
		            System.out.println("内容2: " + content2);
		            System.out.println("发送时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((MimeMessage) messages[i]).getSentDate()));
	                System.out.println("是否有附件: " + (isContainAttach((Part)messages[i]) ? "有附件" : "无附件"));
		            System.out.println("=====================>>结束显示邮件内容<<=====================");
		            
		            ((POP3Message) messages[i]).invalidate(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(folder != null && folder.isOpen()){
				try {
					folder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			if(store.isConnected()){
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
    }
	
	  /**
		 * 查找内容引入相关邮件，中兴审批
		 * @param 邮箱的用户名和密码,邮件主题关键字
		 * @return 无
		 */
		public String receiveMail2(String userName,String passWord,String subject) {
	    	Store store = null;
	    	Folder folder = null;
	    	int messageCount = 0;
	    	URLName urln = null;
	    	String last_content = ""; 
			try{
				//进行用户邮箱连接
				urln = new URLName(Config.MAIL_TYPE, Config.MAIL_HOST, Config.MAIL_PORT, null,userName,passWord);   
				store = getSessionMail().getStore(urln);   
				store.connect();
				//获得邮箱内的邮件夹Folder对象，以"只读"打开
				folder = store.getFolder("INBOX");//打开收件箱
				folder.open(Folder.READ_ONLY);//设置只读
				//获得邮件夹Folder内的所有邮件个数
				messageCount = folder.getMessageCount();// 获取所有邮件个数
		        //获取新邮件处理
				System.out.println("============>>邮件总数："+messageCount);
				if(messageCount > 0){
					Message[] messages = folder.getMessages(1,messageCount);//读取最近的一封邮件
				   
					 SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
					 Date emaildata = simFormat.parse("2019.01.01");
					 
					
					for(int i = 0;i < messages.length;i++) {	
						TextBuffer.setLength(0); // 初始化（清空）邮件正文信息
						bodytext.setLength(0); // 初始化（清空）邮件正文信息
						//String content = getMailContent((Part)messages[i]);//获取内容
						  // Multipart mp = (Multipart)messages.getContent();  
						      
						//保存附件代码，先注释掉了
						
/*					    if (isContainAttach((Part)messages[i])) {
		                	saveAttachMent((Part)messages[i],Config.MAIL_ATTACH_PATH);
		                }*/ 
						
						
						
						
						//System.out.println("=====================>>开始显示相关邮件内容<<=====================");
						//date emaildata = new date();
						
						
						 
						if (getSubject(messages[i]).contains(subject)) {// 判断是否有中兴回复邮件
							
							  
							 Date fromDate = ((MimeMessage) messages[i]).getSentDate();// 取邮件发送时间
							
							 
							 if (   fromDate.after(emaildata)  ) // 取最新的邮件
							 {
								 emaildata =fromDate;
							 }
							 else 
							 {
								 continue;
							 }							 
							
			           /* System.out.println("发送人: " + getFrom(messages[i]));
			            System.out.println("主题: " + getSubject(messages[i]));
			            System.out.println("内容: " + content);
			            System.out.println("内容2: " + content2);			           
			            System.out.println("发送时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((MimeMessage) messages[i]).getSentDate()));
		                System.out.println("是否有附件: " + (isContainAttach((Part)messages[i]) ? "有附件" : "无附件"));
	*/	                
		                last_content = getMailContent2((Part)messages[i]);//获取内容
		               // System.out.println("=====================>>结束显示相关邮件内容<<=====================");
						
						}
			            ((POP3Message) messages[i]).invalidate(true);
					}
					
				
				}
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}finally{
				if(folder != null && folder.isOpen()){
					try {
						folder.close(true);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
				if(store.isConnected()){
					try {
						store.close();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}				
			}
			//System.out.println("last_content:"+last_content);
			return last_content;
	    }
		
		
		/**
	     * 根据中兴邮件内容返回，更新数据库信息
	     * @param 邮件内容
	     * @return 发件人的地址
	     */
	
	   public boolean update_flow_status_zte(String content){
		   boolean b =false ;
		   String[] nets = content.split(";");//拆分有多少条记录
	       for (int i=0;i<nets.length;i++)
	       {
	    	   String[] status = nets[i].split("\\|");
	    	   if (status[1].equals("ok"))
	    	   {
	    		   //update flow stastus
	    	   }
	    	   else {
	    		   //update reject 
	    	   }
	       }
	       
	       
	       return b;
	   }	
	   /**
	     * 根据集团邮件内容返回，更新数据库信息
	     * @param 邮件内容
	     * @return 发件人的地址
	     */
	
	   public boolean update_flow_status_group(String content){
		   boolean b =false ;
		   String[] nets = content.split(";");//拆分有多少条记录
	       for (int i=0;i<nets.length;i++)
	       {
	    	   String[] status = nets[i].split("\\|");
	    	   if (status[1].equals("ok"))
	    	   {
	    		   //update flow stastus
	    	   }
	    	   else {
	    		   //update reject 
	    	   }
	       }
	       
	       
	       return b;
	   }	
	
	
   
	/**
     * 获得发件人的地址
     * @param message：Message
     * @return 发件人的地址
     */
	private String getFrom(Message message) throws Exception {  
        InternetAddress[] address = (InternetAddress[]) ((MimeMessage) message).getFrom();    
        String from = address[0].getAddress();    
        if (from == null){
        	from = "";
        }
        return from;    
    }
	
	 /**
     * 获得邮件主题   
     * @param message：Message
     * @return 邮件主题  
     */
	private String getSubject(Message message) throws Exception {
    	String subject = "";
    	if(((MimeMessage) message).getSubject() != null){
    		subject = MimeUtility.decodeText(((MimeMessage) message).getSubject());// 将邮件主题解码  
    	}
    	return subject;    
    }
	
    /**
     * 获取邮件内容
     * @param part：Part
     */
	private String getMailContent(Part part) throws Exception {    
	
		
		List<String> list=new ArrayList<String>();
		
		//判断邮件类型,不同类型操作不同
		if (part.isMimeType("text/plain")) {  
			//System.out.println("part.isMimeType:text/plain");
            bodytext.append((String) part.getContent());  
            String cotent = ((String) part.getContent()).replace("\n", ";");
            list.add(replaceBlank(cotent));
            
        } else if (part.isMimeType("text/html")) { 
        	//System.out.println("part.isMimeType:text/html");
            bodytext.append((String) part.getContent()); 
            //list.add((String) part.getContent());
            
        } else if (part.isMimeType("multipart/*")) {  
        	//System.out.println("part.isMimeType:multipart/*");
            Multipart multipart = (Multipart) part.getContent();    
            int counts = multipart.getCount();    
            for (int i = 0; i < counts; i++) {    
                getMailContent(multipart.getBodyPart(i));    
            }    
        } else if (part.isMimeType("message/rfc822")) {
        	//System.out.println("part.isMimeType:message/rfc822");
            getMailContent((Part) part.getContent()); 
            list.add((String) part.getContent());
            
        } else {
        	//System.out.println("part.isMimeType:!!!!!!!!!!!!");
        }    
		  
		 String cotent = (bodytext.toString()).replace("\n", ";");
		 cotent =(replaceBlank(cotent));
         
        return cotent;
    }
	
	private String getMailContent2(Part part) throws Exception {    
		StringBuffer bodytext = new StringBuffer();//存放邮件内容
		
		//List<String> list=new ArrayList<String>();
		 
		
		//判断邮件类型,不同类型操作不同
		if (part.isMimeType("text/plain")) {  
			//System.out.println("part.isMimeType:text/plain");
            bodytext.append((String) part.getContent());  
            String cotent = ((String) part.getContent()).replace("\n", ";");
            TextBuffer.append(replaceBlank(cotent));
            
        } else if (part.isMimeType("text/html")) { 
        	//System.out.println("part.isMimeType:text/html");
            bodytext.append((String) part.getContent()); 
            String cotent = ((String) part.getContent()).replace("\n", ";");
            //TextBuffer.append(replaceBlank(cotent));
            
        } else if (part.isMimeType("multipart/*")) {  
        	//System.out.println("part.isMimeType:multipart/*");
            Multipart multipart = (Multipart) part.getContent();    
            int counts = multipart.getCount();    
            for (int i = 0; i < counts; i++) {    
                getMailContent2(multipart.getBodyPart(i));    
            }    
        } else if (part.isMimeType("message/rfc822")) {
        	//System.out.println("part.isMimeType:message/rfc822");
            getMailContent((Part) part.getContent()); 
            String cotent = ((String) part.getContent()).replace("\n", ";");
            TextBuffer.append(replaceBlank(cotent));
            
        } else {
        	//System.out.println("part.isMimeType:!!!!!!!!!!!!");
        }    
		 
		//System.out.println("TextBuffer:"+TextBuffer.toString());
        return TextBuffer.toString();
    }
	
	
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");

			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}	
    
    /**
     * 判断此邮件是否包含附件 
     * @param part：Part
     * @return 是否包含附件
     */
	private boolean isContainAttach(Part part) throws Exception { 
        boolean attachflag = false;    
        if (part.isMimeType("multipart/*")) {    
            Multipart mp = (Multipart) part.getContent();    
            for (int i = 0; i < mp.getCount(); i++) {    
                BodyPart mpart = mp.getBodyPart(i);    
                String disposition = mpart.getDisposition();    
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))    
                    attachflag = true;    
                else if (mpart.isMimeType("multipart/*")) {    
                    attachflag = isContainAttach((Part) mpart);    
                } else {    
                    String contype = mpart.getContentType();    
                    if (contype.toLowerCase().indexOf("application") != -1)    
                        attachflag = true;    
                    if (contype.toLowerCase().indexOf("name") != -1)    
                        attachflag = true;    
                }    
            }    
        } else if (part.isMimeType("message/rfc822")) {    
            attachflag = isContainAttach((Part) part.getContent());    
        }    
        return attachflag;    
    }	
	
	 /**
     * 保存附件
     * @param part：Part
     * @param filePath：邮件附件存放路径
     */
	private void saveAttachMent(Part part,String filePath) throws Exception {    
    	String fileName = "";
    	//保存附件到服务器本地
        if (part.isMimeType("multipart/*")) {    
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
            	BodyPart mpart = mp.getBodyPart(i);    
                String disposition = mpart.getDisposition();   
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {   
                	fileName = mpart.getFileName();    
                	if (fileName != null) {
                    	fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mpart.getInputStream(),filePath);  
                    }  
                } else if (mpart.isMimeType("multipart/*")) {    
                	saveAttachMent(mpart,filePath);
                } else {
                	fileName = mpart.getFileName();    
                	if (fileName != null) {
                    	fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mpart.getInputStream(),filePath);
                    }    
                }    
            }    
        } else if (part.isMimeType("message/rfc822")) {    
            saveAttachMent((Part) part.getContent(),filePath);    
        }
        
    }
    
    /**
     * 保存附件到指定目录里 
     * @param fileName：附件名称
     * @param in：文件输入流
     * @param filePath：邮件附件存放基路径
     */
    private void saveFile(String fileName, InputStream in,String filePath) throws Exception {    
    	File storefile = new File(filePath);   
        if(!storefile.exists()){
        	storefile.mkdirs();
    	}
        BufferedOutputStream bos = null;   
        BufferedInputStream bis = null;   
        try {   
            bos = new BufferedOutputStream(new FileOutputStream(filePath + "\\" + fileName));   
            bis = new BufferedInputStream(in);   
            int c;   
            while ((c = bis.read()) != -1) {   
                bos.write(c);   
                bos.flush();   
            }   
        } catch (Exception e) {   
        	throw e;   
        } finally {
        	if(bos != null){
        		bos.close();
        	}
            if(bis != null){
            	bis.close();
            }
        }   
    } 
}