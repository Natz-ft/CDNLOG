package emai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** *
 * @Description: 处理邮件的工具类 * 
 * @author huhao * 
 * @date Dec 26, 2013 5:32:38 PM */
public class EmailUtils {
	private static Log logger = LogFactory.getLog(EmailUtils.class);

	// huhao19920306@163.com 通过@之后的是163还是qq，动态的判断邮件服务器pop.163.com
	// 994822422@qq.com
	
	public static String getMailServerHost(String emailAddress) {
		if (emailAddress == null || "".equals(emailAddress.trim())) {
			throw new RuntimeException("邮件账户错误！");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("smtp.");
		emailAddress = emailAddress.substring(emailAddress.lastIndexOf("@") + 1);
		sb.append(emailAddress);
		return sb.toString().trim();
	}

	// 验证邮箱地址
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			// String check =
			// "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			String check = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			logger.error("邮箱地址错误！");
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
		System.out.println(checkEmail("1@163.ke.com"));
	}
}
