package emai;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * * @decrisption 实现邮件的发送 * @author huhao * @version Nov 19, 2013 11:47:47 AM * @since
 * JDK1.6.0_10 spring3.2.0
 */
public class MailSender {
	private MimeMessage msg;
	/** * @param msg */
	private EmailMessage emailMessage;

	public MailSender() {
		super();
	}

	/**
	 * * * 发送邮件,可发送多个图片和附件 * @author huhao * @date Nov 19, 2013 10:25:44 AM * @param
	 * ms * @throws EmailSendException * @throws UnsupportedEncodingException *
	 */
	public void sendEmail(EmailMessage ms) {
		try {
			// 得到环境对象
			getMimeMessage(ms);
			msg.setFrom(new InternetAddress(ms.getEmailAccount()));
			String[] receiverArray = ms.getReceiver().trim().split(",|，");
			// 收件人InternetAddress数组
			InternetAddress[] receiverAddressArray = new InternetAddress[receiverArray.length];
			for (int i = 0; i < receiverArray.length; i++) {
				receiverAddressArray[i] = new InternetAddress(
						receiverArray[i].trim());
			}
			msg.setRecipients(MimeMessage.RecipientType.TO,
					receiverAddressArray); // 设置收件人
			// 暗送
			if (ms.getBcc() != null && !"".equals(ms.getBcc().trim())) {
				String[] bccArray = ms.getBcc().trim().split(",|，");
				InternetAddress[] bccAddressArray = new InternetAddress[bccArray.length];
				for (int i = 0; i < bccArray.length; i++) {
					bccAddressArray[i] = new InternetAddress(bccArray[i].trim());
				}
				msg.setRecipients(MimeMessage.RecipientType.BCC,
						bccAddressArray);
			}
			// 抄送
			if (ms.getCc() != null && !"".equals(ms.getCc().trim())) {
				String[] ccArray = ms.getCc().trim().split(",|，");
				InternetAddress[] ccAddressArray = new InternetAddress[ccArray.length];
				for (int i = 0; i < ccArray.length; i++) {
					ccAddressArray[i] = new InternetAddress(ccArray[i].trim());
				}
				msg.setRecipients(MimeMessage.RecipientType.CC, ccAddressArray);
			}
			if (ms.isReceipt()) {
				// 是否要求回执
				msg.setHeader("Disposition-Notification-To", "1");
				msg.addHeader("Disposition-Notification-To", "1");
			}
			msg.setSubject(ms.getSubject());
			// 主题
			MimeMultipart mmp = new MimeMultipart();
			Map<String, String> map = ms.getMap();
			// 得到邮件内容
			if (map == null) {
				mmp.addBodyPart(getTextPart(ms.getContent(), ms.getCharset()));
			}
			// 得到所有的图片
			if (map != null) {
				mmp.addBodyPart(getTextPart(ms.getContent(), ms.getCharset()));
				List<MimeBodyPart> imageParts = getImageParts(map);
				for (MimeBodyPart imagePart : imageParts) {
					mmp.addBodyPart(imagePart);
				}
			}
			// 得到所有的附件
			List<String> attachPaths = ms.getAttacPaths();
			if (attachPaths != null) {
				List<MimeBodyPart> attchParts = getAttachmentParts(attachPaths);
				for (MimeBodyPart attchPart : attchParts) {
					mmp.addBodyPart(attchPart);
				}
			}
			msg.setSentDate(new Date());
			mmp.setSubType("mixed");
			msg.setContent(mmp);
			msg.saveChanges();// 保存修改
			// 发送邮件
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** * * 得到邮件的环境对象 * @author huhao * @date Nov 19, 2013 10:13:57 AM */
	public void getMimeMessage(EmailMessage ms) {
		Properties props = new Properties();
		// 设置一些参数：发送时需要设置
		props.setProperty("mail.transport.protocol", ms.getProtocol());
		props.setProperty("mail.host", ms.getHost());
		props.setProperty("mail.smtp.auth", ms.getAuth());// 请求认证
		final String username = ms.getUsername();
		final String password = ms.getPassword();
		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		// 代表环境的对象
		msg = new MimeMessage(session);
		// 得到了代表邮件的对象
	}

	/**
	 * * * 得到文本部分 * @author huhao * @date Nov 19, 2013 11:05:16 AM * @param
	 * content 文本内容(html代码) * @param charset 字符编码 * @return * @throws
	 * MessagingException *
	 */
	public MimeBodyPart getTextPart(String content, String charset)
			throws MessagingException {
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(content, "text/html;charset=" + charset);
		return textPart;
	}

	/**
	 * * * 得到图片部分 * @author huhao * @date Nov 19, 2013 11:18:19 AM * @param map
	 * 代表的是文件路径和它所在的img标签的cid * @return * @throws MessagingException *
	 */
	public List<MimeBodyPart> getImageParts(Map<String, String> map)
			throws MessagingException {
		List<MimeBodyPart> imageParts = new ArrayList<MimeBodyPart>();
		for (Entry<String, String> e : map.entrySet()) {
			MimeBodyPart imagePart = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource(e.getKey()));
			// jaf会自动探测文件的MIME类型
			imagePart.setDataHandler(dh);
			imagePart.setContentID(e.getValue());
			imageParts.add(imagePart);
		}
		return imageParts;
	}

	/**
	 * * * 得到附件部分 * @author huhao * @date Nov 19, 2013 11:22:36 AM * @param
	 * sources 多个附件的路径 * @return * @throws MessagingException * @throws
	 * UnsupportedEncodingException *
	 */
	public List<MimeBodyPart> getAttachmentParts(List<String> sources)
			throws MessagingException, UnsupportedEncodingException {
		List<MimeBodyPart> attachmentParts = new ArrayList<MimeBodyPart>();
		for (String source : sources) {
			MimeBodyPart attachmentPart = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource(source));// jaf会自动探测文件的MIME类型
			String name = dh.getName();
			attachmentPart.setDataHandler(dh);
			attachmentPart.setFileName(MimeUtility.encodeText(name
					.substring(name.lastIndexOf("$^%") + 3)));
			attachmentParts.add(attachmentPart);
		}
		return attachmentParts;
	}
}
