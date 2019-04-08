package emai;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EmailMessage implements Serializable {
	private Email email; // 邮件
	// private HttpServletRequest request;
	private String emailAccount; // 发件人
	private String receiver; // 收件人
	private String protocol; // 使用协议
	private String host; // 邮箱服务器
	private String auth; // 是否请求认证
	private String username; // 用户名
	private String password; // 密码
	private String cc; // 抄送
	private String bcc; // 暗送
	private String subject; // 主题
	private String charset; // 字符编码
	private boolean receipt; // 回执
	private String content; // 文本
	private Map<String, String> map = new HashMap<String, String>(); // 发送邮件处理图片所需的内容
	private List<String> attacPaths; // 发送邮件附件

	/** * @return protocol */
	public String getProtocol() {
		return "smtp";
	}

	/** * @param protocol */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/** * @return host */
	public String getHost() {
		return EmailUtils.getMailServerHost(email.getSender());
	}

	/** * @param host */
	public void setHost(String host) {
		this.host = host;
	}

	/** * @return auth */
	public String getAuth() {
		return "true";
	}

	/** * @param auth */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/** * @return username */
	public String getUsername() {
		return this.username;
	}

	/** * @param username */
	public void setUsername(String username) {
		this.username = username;
	}

	/** * @return password */
	public String getPassword() {
		return this.password;
	}

	/** * @param password */
	public void setPassword(String password) {
		this.password = password;
	}

	/** * @return cc */
	public String getCc() {
		return email.getCc();
	}

	/** * @param cc */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/** * @return bcc */
	public String getBcc() {
		return email.getBcc();
	}

	/** * @param bcc */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/** * @return subject */
	public String getSubject() {
		return email.getSubject();
	}

	/** * @param subject */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** * @return charset */
	public String getCharset() {
		return "UTF-8";
	}

	/** * @param charset */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public boolean isReceipt() {
		return email.isReplySign();
	}

	public void setReceipt(boolean receipt) {
		this.receipt = receipt;
	}

	/** * @return content */
	public String getContent() {
		if (map == null || map.size() == 0) {
			return email.getContent();
		}
		return content;
	}

	/** * @param content */
	public void setContent(String content) {
		this.content = content;
	}

	/** * @return emailAccount */
	public String getEmailAccount() {
		return email.getSender();
	}

	/** * @param emailAccount */
	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	/** * @return receiver */
	public String getReceiver() {
		return email.getTo();
	}

	/** * @param receiver */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Map<String, String> getMap() {
		// 如果图片路径为null,直接返回null
		if (email.getImagePath() == null
				|| "".equals(email.getImagePath().trim())) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(email.getContent());
		// 处理邮件中图片和附件的路径
		System.out.println(email.getImagePath());
		for (String realImgPath : email.getImagePath().split("\\|")) {
			String cid = genPK();
			System.out.println("cid:"+cid);
			// String cid=System.currentTimeMillis()+"";
			map.put(realImgPath, cid);
			sb.append("<br/><img src='cid:").append(cid).append("'")
					.append("/>");
		}
		// 设置文本
		setContent(sb.toString());
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<String> getAttacPaths() {
		// 如果附件路径为null,直接返回null
		if (email.getAttachPath() == null
				|| "".equals(email.getAttachPath().trim())) {
			return null;
		}
		List<String> attachList = new ArrayList<String>();
		for (String realAttachPath : email.getAttachPath().split("\\|")) {
			attachList.add(realAttachPath);
		}
		return attachList;
	}

	public EmailMessage(Email email) {
		super();
		this.email = email;
	}

	public static String genPK() {
		return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}

	 
	
	
}
