package TOOLS;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class SendMailUtil {
    
    // 设置服务器
    private static String KEY_SMTP = getProperties.getPropertie("KEY_SMTP").trim();
    private static String VALUE_SMTP = getProperties.getPropertie("VALUE_SMTP").trim();
    // 服务器验证
    private static String KEY_PROPS =getProperties.getPropertie("KEY_PROPS").trim();
    private static boolean VALUE_PROPS = true;
    // 发件人用户名、密码
    private String SEND_USER = getProperties.getPropertie("SEND_USER").trim();
    private String SEND_UNAME = getProperties.getPropertie("SEND_UNAME").trim();
    private String SEND_PWD =  getProperties.getPropertie("SEND_PWD").trim();
    // 建立会话
    private MimeMessage message;
    private Session s;

    /*
     * 初始化方法
     */
    public SendMailUtil() {
        Properties props = System.getProperties();
        props.setProperty(KEY_SMTP, VALUE_SMTP);
        props.put(KEY_PROPS, "true");
        props.put("mail.smtp.auth", "true");
       // props.put("mail.smtp.port", "465");  
        
       // props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       // props.setProperty("mail.smtp.socketFactory.fallback", "false");
       // props.setProperty("mail.smtp.socketFactory.port", "465");
        
        
        
        s =  Session.getDefaultInstance(props, new Authenticator(){
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
              }});
        s.setDebug(true);
        message = new MimeMessage(s);
    }

    /**
     * 群发
     * @throws UnsupportedEncodingException 
     */
    public void doSendHtmlEmail(String headName, String sendHtml, String[] receiveUser) throws UnsupportedEncodingException {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(SEND_USER);
            message.setFrom(from);
            
            int receiverLen = receiveUser.length;
            //群发
            for (int i = 0; i < receiverLen; i++) {
                InternetAddress toAddr = new InternetAddress(receiveUser[i]);
                message.addRecipient(javax.mail.Message.RecipientType.TO,toAddr);
            }
            // 邮件标题
           // message.setSubject(headName);
            
            String encodedSubject = MimeUtility.encodeText(headName, MimeUtility.mimeCharset("gb2312"), null);				
           			
           // msg.setSubject(encodedSubject);            
            message.setSubject(encodedSubject);
           
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = s.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("send success!");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 单发 
     */
    public void doSendHtmlEmail(String headName, String sendHtml,    String receiveUser) {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(SEND_USER);
            message.setFrom(from);
            
            //单发
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            
            // 邮件标题
            message.setSubject(headName);
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = s.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("send success!");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SendMailUtil se = new SendMailUtil();
        //单发
        se.doSendHtmlEmail("这是自动发送的邮件", "自动发送邮件功能弄好了1111！！！！", "dirxu@126.com");
        //群发
        //String[] receiverAddr = {"415405800@qq.com","342097593@qq.com"};
        //se.doSendHtmlEmail("宝贝我爱你1", "ssssss", receiverAddr);
        
        
    }
}