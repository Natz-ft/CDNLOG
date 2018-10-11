package emai;
//首先准备一个Email对象，用于封装前台传递的数据。（如果你不需要前台的交互，可以不用这个对象）
import java.io.Serializable;

public class Email implements Serializable {
	private String id;
	private String sender;// 发送人
	private String to;// 发给谁
	private String cc; // 抄送人 多个
	private String bcc; // 暗送人 多个
	private String subject; // 主题
	private String sendDate; // 发送时间
	private boolean replySign; // 要求回执
	private boolean containAttach; // 是否包含附件
	private String imagePath; // 邮件图片的路劲 （多个，使用"|"拼接）
	private String attachPath; // 邮件附件的路径 （多个，使用"|"拼接）
	private String content; // 邮件的文本内容
	private boolean isRead; // 是否已读

	public Email() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public boolean isReplySign() {
		return replySign;
	}

	public void setReplySign(boolean replySign) {
		this.replySign = replySign;
	}

	public boolean isContainAttach() {
		return containAttach;
	}

	public void setContainAttach(boolean containAttach) {
		this.containAttach = containAttach;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
