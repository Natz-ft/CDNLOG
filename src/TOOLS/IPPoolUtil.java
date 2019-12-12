package TOOLS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IPPoolUtil {
	public static void main(String[] args) {
		// System.out.println(getNetMask("255.255.255.0"));

		// System.out.println(getPoolMax(getNetMask("255.255.255.128")));
		// System.out.println("a"+getEndIP("39.153.95.0",31).getStartIP());
		// System.out.println(IPRange_to_ipStart_and_IPEnd("39.96.0.0/14")[0]+"\t"+IPRange_to_ipStart_and_IPEnd("39.96.0.0/14")[1]);
		// System.out.println(getIp2long("139.99.8.58"));
		// ipIsInRange("221.180.170.1","60.206.12.0-60.206.12.255");

	
		String str = "7";
		 int i = 0;
		  i = Integer.parseInt(str);
		
		  System.out.println(i+"\t"+SumStrAscii(str));
		  
		  
			List<String> list = parseIpRange( "﻿7.22.5.1",  "﻿7.22.5.1");
		
	for (String ip:list){
			 System.out.println(ip);
		}
	}
	
	
	public static int SumStrAscii(String str){
		byte[] bytestr = str.getBytes();
		int sum = 0;
		for(int i=0;i<bytestr.length;i++){
			 System.out.println(bytestr[i]);
			sum += bytestr[i];
		}
		return sum;
	
	}
	
	
	
	

	/**
	 * 根据起始IP地址和子网掩码计算终止IP
	 */
	public static Nets getEndIP(String StartIP, int netmask) {
		return getEndIP(StartIP, getMask(netmask));
	}

	/**
	 * 根据起始IP地址和子网掩码计算终止IP
	 */
	public static Nets getEndIP(String StartIP, String netmask) {
		Nets nets = new Nets();

		if (netmask.equals("255.255.255.255")) {
			nets.setStartIP(StartIP);
			nets.setEndIP(StartIP);
		}

		// else if (netmask.equals("255.255.255.254")) { }
		else {

			String[] start = Negation(StartIP, netmask).split("\\.");

			int ip4_start = Integer.valueOf(start[3]);
			int ip4_end = Integer.valueOf(start[3]);

			nets.setStartIP(start[0] + "." + start[1] + "." + start[2] + "." + (Integer.valueOf(start[3])));
			// nets.setStartIP(start[0]+"."+start[1]+"."+start[2]+"."+
			// String.valueOf(ip4_start) ) ;

			nets.setEndIP(TaskOR(Negation(StartIP, netmask), netmask));

			nets.setNetMask(netmask);
		}
		return nets;
	}

	/**
	 * 根据掩码位计算掩码
	 */
	public static String getMask(int masks) {
		if (masks == 1)
			return "128.0.0.0";
		else if (masks == 2)
			return "192.0.0.0";
		else if (masks == 3)
			return "224.0.0.0";
		else if (masks == 4)
			return "240.0.0.0";
		else if (masks == 5)
			return "248.0.0.0";
		else if (masks == 6)
			return "252.0.0.0";
		else if (masks == 7)
			return "254.0.0.0";
		else if (masks == 8)
			return "255.0.0.0";
		else if (masks == 9)
			return "255.128.0.0";
		else if (masks == 10)
			return "255.192.0.0";
		else if (masks == 11)
			return "255.224.0.0";
		else if (masks == 12)
			return "255.240.0.0";
		else if (masks == 13)
			return "255.248.0.0";
		else if (masks == 14)
			return "255.252.0.0";
		else if (masks == 15)
			return "255.254.0.0";
		else if (masks == 16)
			return "255.255.0.0";
		else if (masks == 17)
			return "255.255.128.0";
		else if (masks == 18)
			return "255.255.192.0";
		else if (masks == 19)
			return "255.255.224.0";
		else if (masks == 20)
			return "255.255.240.0";
		else if (masks == 21)
			return "255.255.248.0";
		else if (masks == 22)
			return "255.255.252.0";
		else if (masks == 23)
			return "255.255.254.0";
		else if (masks == 24)
			return "255.255.255.0";
		else if (masks == 25)
			return "255.255.255.128";
		else if (masks == 26)
			return "255.255.255.192";
		else if (masks == 27)
			return "255.255.255.224";
		else if (masks == 28)
			return "255.255.255.240";
		else if (masks == 29)
			return "255.255.255.248";
		else if (masks == 30)
			return "255.255.255.252";
		else if (masks == 31)
			return "255.255.255.254";
		else if (masks == 32)
			return "255.255.255.255";
		return "";
	}

	/**
	 * temp1根据temp2取反
	 */
	private static String Negation(String StartIP, String netmask) {
		String[] temp1 = StartIP.trim().split("\\.");
		String[] temp2 = netmask.trim().split("\\.");
		int[] rets = new int[4];
		for (int i = 0; i < 4; i++) {

			rets[i] = Integer.parseInt(temp1[i]) & Integer.parseInt(temp2[i]);
			// System.out.println(i+"\t"+rets[i]+"\t"+Integer.parseInt(temp1[i])+"\t"+Integer.parseInt(temp2[i]));
		}

		return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
	}

	/**
	 * temp1根据temp2取或
	 */
	private static String TaskOR(String StartIP, String netmask) {
		String[] temp1 = StartIP.trim().split("\\.");
		String[] temp2 = netmask.trim().split("\\.");
		int[] rets = new int[4];
		for (int i = 0; i < 4; i++) {
			rets[i] = 255 - (Integer.parseInt(temp1[i]) ^ Integer.parseInt(temp2[i]));
			// System.out.println(i+"\t"+rets[i]+"\t"+Integer.parseInt(temp1[i])+"\t"+Integer.parseInt(temp2[i]));

		}
		// System.out.println("ss"+rets[0]+"."+rets[1]+"."+rets[2]+"."+(rets[3]));
		// return rets[0]+"."+rets[1]+"."+rets[2]+"."+(rets[3]-1);
		return rets[0] + "." + rets[1] + "." + rets[2] + "." + (rets[3]);
	}

	/**
	 * 计算子网大小
	 */
	public static int getPoolMax(int netmask) {
		if (netmask <= 0 || netmask >= 32) {
			return 0;
		}
		int bits = 32 - netmask;
		return (int) Math.pow(2, bits) - 2;
	}

	/*
	 * public static int getPoolMax(int maskBit) { if (maskBit <= 0 || maskBit
	 * >= 32) { return 0; }*
	 * 
	 * return (int) Math.pow(2, 32 - maskBit) - 2; }
	 * 
	 * /
	 * 
	 * 
	 * 
	 * /** 转换为验码位数
	 */
	public static int getNetMask(String netmarks) {
		StringBuffer sbf;
		String str;
		int inetmask = 0, count = 0;
		String[] ipList = netmarks.split("\\.");
		for (int n = 0; n < ipList.length; n++) {
			sbf = toBin(Integer.parseInt(ipList[n]));
			str = sbf.reverse().toString();
			count = 0;
			for (int i = 0; i < str.length(); i++) {
				i = str.indexOf('1', i);
				if (i == -1) {
					break;
				}
				count++;
			}
			inetmask += count;
		}
		return inetmask;
	}

	/**
	 * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
	 *
	 * @param netmarks
	 * @return
	 */
	/*
	 * public static int getNetMask(String netmarks) { StringBuilder sbf; String
	 * str; int inetmask = 0; int count = 0; String[] ipList =
	 * netmarks.split("\\."); for (int n = 0; n < ipList.length; n++) { sbf =
	 * toBin(Integer.parseInt(ipList[n])); str = sbf.reverse().toString(); count
	 * = 0; for (int i = 0; i < str.length(); i++) { i = str.indexOf('1', i); if
	 * (i == -1) { break; } count++; } inetmask += count; } return inetmask; }
	 */

	/**
	 * 计算子网大小
	 *
	 * @param netmask
	 *            掩码位
	 * @return
	 */

	private static StringBuffer toBin(int x) {
		StringBuffer result = new StringBuffer();
		result.append(x % 2);
		x /= 2;
		while (x > 0) {
			result.append(x % 2);
			x /= 2;
		}
		return result;
	}

	/*
	 * 
	 * private static StringBuilder toBin(int x) { StringBuilder result = new
	 * StringBuilder(); result.append(x % 2); x /= 2; while (x > 0) {
	 * result.append(x % 2); x /= 2; } return result; }
	 */

	public static boolean isIp(String ip) { // 判断 一个字符串是否是IP

		try {
			boolean b = false;

			ip = ip.trim();
			if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
				String s[] = ip.split("\\.");
				if (Integer.parseInt(s[0]) <= 255)
					if (Integer.parseInt(s[1]) <= 255)
						if (Integer.parseInt(s[2]) <= 255)
							if (Integer.parseInt(s[3]) <= 255)
								b = true;
			}
			return b;
		} catch (Exception e) {
			System.out.println("判断是否ip的字符：" + "\t" + ip);
			boolean b = false;
			return b;

		}
	}

	public static boolean ipIsInRange(String ip, String ipRange) {// 判断一个ip是否
																	// 在IP段
																	// check ip
																	// is in
																	// ipRange
		if (ipRange == null)
			throw new NullPointerException("IPrange is empty");
		if (ip == null)
			throw new NullPointerException("IP is empty");
		ipRange = ipRange.trim();
		ip = ip.trim();
		final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
		final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
		if (!ipRange.matches(REGX_IPB) || !ip.matches(REGX_IP))
			return false;
		int idx = ipRange.indexOf('-');

		String[] sips = ipRange.substring(0, idx).split("\\.");

		String[] sipe = ipRange.substring(idx + 1).split("\\.");

		String[] sipt = ip.split("\\.");

		long ips = 0L, ipe = 0L, ipt = 0L;

		for (int i = 0; i < 4; ++i) {
			ips = ips << 8 | Integer.parseInt(sips[i]);
			// System.out.println("ips ::"+ips);
			ipe = ipe << 8 | Integer.parseInt(sipe[i]);
			// System.out.println("ipe ::"+ipe);
			ipt = ipt << 8 | Integer.parseInt(sipt[i]);
		}

		// System.out.println("ips ::"+ips);
		// System.out.println("ipe ::"+ipe);

		if (ips > ipe) {
			long t = ips;
			ips = ipe;
			ipe = t;
		}
		return ips <= ipt && ipt <= ipe;
	}

	/*
	 * 鍒ゆ柇IP鏄惁鍦ㄦ寚瀹欼P娈靛唴锛屾柟娉曚簩 ipRange IP娈碉紙锟�?-'鍒嗛殧锟�?
	 * 
	 * @param ipRange
	 * 
	 * @param ip
	 * 
	 * @return boolean
	 */
	public static boolean ipInRange(String ip, String ipRange) {
		int idx = ipRange.indexOf('-');
		String beginIP = ipRange.substring(0, idx);
		String endIP = ipRange.substring(idx + 1);
		return getIp2long(beginIP) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIP);
	}

	public static long getIp2long(String ip) { // 转换ip地址长整型格式
		long ip2long = 0L;
		try {
			String[] ips = ip.split("\\.");

			for (int i = 0; i < 4; ++i) {
				ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ip);
		}
		return ip2long;

	}

	public static String[] IPRange_to_ipStart_and_IPEnd(String IPRange) {// 把一个ip地址段拆分成
																			// 起始ip，与结束ip的格式。
		String[] IPS = new String[2];
		IPRange = IPRange.trim();
		try {
			if (isIp(IPRange)) { // 如果 ip段是ip地址，那么起始ip，与结束ip都相同。
				IPS[0] = IPRange;
				IPS[1] = IPRange;
			} else if (IPRange.indexOf("-") > 0) { // ip段是短连接形式
				IPS[0] = IPRange.substring(0, IPRange.indexOf("-"));
				if (!isIp(IPS[0])) {
					IPS[0] = "ERROR";
				}
				int dot = IPRange.lastIndexOf(".");
				IPS[1] = IPRange.substring(0, dot) + "."
						+ IPRange.substring(IPRange.indexOf("-") + 1, IPRange.length());
				if (!isIp(IPS[1])) {
					IPS[0] = "ERROR";
				}

			} else if (IPRange.indexOf("/") > 0) // ip段是掩码形式
			{

				IPS[0] = IPRange.substring(0, IPRange.indexOf("/"));
				if (isIp(IPS[0])) {

					int mask = Integer.valueOf(IPRange.substring(IPRange.indexOf("/") + 1)).intValue();
					IPS[1] = IPPoolUtil.getEndIP(IPS[0], mask).getEndIP();
				} else {
					IPS[0] = "ERROR";
					IPS[1] = "ERROR";

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("IPRagne:\t" + IPRange);
		}
		return IPS;

	}

	/**
	 * 功能：判断一个IP是不是在一个网段下的 格式：isInRange("192.168.8.3", "192.168.9.10/22");
	 */
	public static boolean isInRange(String ip, String cidr) {
		String[] ips = ip.split("\\.");
		int ipAddr = (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16)
				| (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
		int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
		int mask = 0xFFFFFFFF << (32 - type);
		String cidrIp = cidr.replaceAll("/.*", "");
		String[] cidrIps = cidrIp.split("\\.");
		int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) | (Integer.parseInt(cidrIps[1]) << 16)
				| (Integer.parseInt(cidrIps[2]) << 8) | Integer.parseInt(cidrIps[3]);

		return (ipAddr & mask) == (cidrIpAddr & mask);
	}

	/**
	 * 功能：根据IP和位数返回该IP网段的所有IP 格式：parseIpMaskRange("192.192.192.1.", "23")
	 */
	public static List<String> parseIpMaskRange(String ip, String mask) {
		List<String> list = new ArrayList<>();
		if ("32".equals(mask)) {
			list.add(ip);
		} else {
			String startIp = getBeginIpStr(ip, mask);
			String endIp = getEndIpStr(ip, mask);
			if (!"31".equals(mask)) {
				String subStart = startIp.split("\\.")[0] + "." + startIp.split("\\.")[1] + "."
						+ startIp.split("\\.")[2] + ".";
				String subEnd = endIp.split("\\.")[0] + "." + endIp.split("\\.")[1] + "." + endIp.split("\\.")[2] + ".";
				startIp = subStart + (Integer.parseInt(startIp.split("\\.")[3]) + 1);
				endIp = subEnd + (Integer.parseInt(endIp.split("\\.")[3]) - 1);
			}
			list = parseIpRange(startIp, endIp);
		}
		return list;
	}

	/**
	 * 功能：根据位数返回IP总数 格式：parseIpMaskRange("192.192.192.1", "23")
	 */
	public static int getIpCount(String mask) {
		return BigDecimal.valueOf(Math.pow(2, 32 - Integer.parseInt(mask))).setScale(0, BigDecimal.ROUND_DOWN)
				.intValue();// IP总数，去小数点
	}

	/**
	 * 功能：根据位数返回IP总数 格式：isIP("192.192.192.1")
	 */
	public static boolean isIP(String str) {
		String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}
    /*
     * 根据起始ip和结束ip，生成全部ip地址列表 
     * 
     */
	public static List<String> parseIpRange(String ipfrom, String ipto) {
		
		ipfrom = ipfrom.trim();
		
		ipto = ipto.trim();
		
		List<String> ips = new ArrayList<String>();
		String[] ipfromd = ipfrom.split("\\.");
		String[] iptod = ipto.split("\\.");
		int[] int_ipf = new int[4];
		int[] int_ipt = new int[4];
		for (int i = 0; i < 4; i++) {
			 System.out.println(ipfromd[i]+"\t"+SumStrAscii(ipfromd[i]));
			int_ipf[i] = Integer.parseInt(ipfromd[i]);
			
			int_ipt[i] = Integer.parseInt(iptod[i]);
		}
		for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
			for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1] : 255); B++) {
				for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2] : 255); C++) {
					for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3] : 255); D++) {
						ips.add(A + "." + B + "." + C + "." + D);
					}
				}
			}
		}
		return ips;
	}

	/**
	 * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
	 *
	 * @param ip
	 * @return
	 */
	public static String getIpFromLong(Long ip) {
		String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
		String s2 = String.valueOf((ip & 16711680L) / 65536L);
		String s3 = String.valueOf((ip & 65280L) / 256L);
		String s4 = String.valueOf(ip & 255L);
		return s1 + "." + s2 + "." + s3 + "." + s4;
	}

	/**
	 * 把xx.xx.xx.xx类型的转为long类型的
	 *
	 * @param ip
	 * @return
	 */
	public static Long getIpFromString(String ip) {
		Long ipLong = 0L;
		String ipTemp = ip;
		ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
		ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
		ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
		ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
		ipLong = ipLong * 256 + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
		ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
		ipLong = ipLong * 256 + Long.parseLong(ipTemp);
		return ipLong;
	}

	/**
	 * 根据掩码位获取掩码
	 *
	 * @param maskBit
	 *            掩码位数，如"28"、"30"
	 * @return
	 */
	public static String getMaskByMaskBit(String maskBit) {
		return "".equals(maskBit) ? "error, maskBit is null !" : getMaskMap(maskBit);
	}

	/**
	 * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
	 *
	 * @param ip
	 *            给定的IP，如218.240.38.69
	 * @param maskBit
	 *            给定的掩码位，如30
	 * @return 起始IP的字符串表示
	 */
	public static String getBeginIpStr(String ip, String maskBit) {
		return getIpFromLong(getBeginIpLong(ip, maskBit));
	}

	/**
	 * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
	 *
	 * @param ip
	 *            给定的IP，如218.240.38.69
	 * @param maskBit
	 *            给定的掩码位，如30
	 * @return 起始IP的长整型表示
	 */
	public static Long getBeginIpLong(String ip, String maskBit) {
		return getIpFromString(ip) & getIpFromString(getMaskByMaskBit(maskBit));
	}

	/**
	 * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
	 *
	 * @param ip
	 *            给定的IP，如218.240.38.69
	 * @param maskBit
	 *            给定的掩码位，如30
	 * @return 终止IP的字符串表示
	 */
	public static String getEndIpStr(String ip, String maskBit) {
		return getIpFromLong(getEndIpLong(ip, maskBit));
	}

	/**
	 * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
	 *
	 * @param ip
	 *            给定的IP，如218.240.38.69
	 * @param maskBit
	 *            给定的掩码位，如30
	 * @return 终止IP的长整型表示
	 */
	public static Long getEndIpLong(String ip, String maskBit) {
		return getBeginIpLong(ip, maskBit) + ~getIpFromString(getMaskByMaskBit(maskBit));
	}

	public static String getMaskMap(String maskBit) {
		if ("1".equals(maskBit))
			return "128.0.0.0";
		if ("2".equals(maskBit))
			return "192.0.0.0";
		if ("3".equals(maskBit))
			return "224.0.0.0";
		if ("4".equals(maskBit))
			return "240.0.0.0";
		if ("5".equals(maskBit))
			return "248.0.0.0";
		if ("6".equals(maskBit))
			return "252.0.0.0";
		if ("7".equals(maskBit))
			return "254.0.0.0";
		if ("8".equals(maskBit))
			return "255.0.0.0";
		if ("9".equals(maskBit))
			return "255.128.0.0";
		if ("10".equals(maskBit))
			return "255.192.0.0";
		if ("11".equals(maskBit))
			return "255.224.0.0";
		if ("12".equals(maskBit))
			return "255.240.0.0";
		if ("13".equals(maskBit))
			return "255.248.0.0";
		if ("14".equals(maskBit))
			return "255.252.0.0";
		if ("15".equals(maskBit))
			return "255.254.0.0";
		if ("16".equals(maskBit))
			return "255.255.0.0";
		if ("17".equals(maskBit))
			return "255.255.128.0";
		if ("18".equals(maskBit))
			return "255.255.192.0";
		if ("19".equals(maskBit))
			return "255.255.224.0";
		if ("20".equals(maskBit))
			return "255.255.240.0";
		if ("21".equals(maskBit))
			return "255.255.248.0";
		if ("22".equals(maskBit))
			return "255.255.252.0";
		if ("23".equals(maskBit))
			return "255.255.254.0";
		if ("24".equals(maskBit))
			return "255.255.255.0";
		if ("25".equals(maskBit))
			return "255.255.255.128";
		if ("26".equals(maskBit))
			return "255.255.255.192";
		if ("27".equals(maskBit))
			return "255.255.255.224";
		if ("28".equals(maskBit))
			return "255.255.255.240";
		if ("29".equals(maskBit))
			return "255.255.255.248";
		if ("30".equals(maskBit))
			return "255.255.255.252";
		if ("31".equals(maskBit))
			return "255.255.255.254";
		if ("32".equals(maskBit))
			return "255.255.255.255";
		return "-1";
	}

	public static double ipToDouble(String ip) {
		String[] arr = ip.split("\\.");
		double d1 = Double.parseDouble(arr[0]);
		double d2 = Double.parseDouble(arr[1]);
		double d3 = Double.parseDouble(arr[2]);
		double d4 = Double.parseDouble(arr[3]);
		return d1 * Math.pow(256, 3) + d2 * Math.pow(256, 2) + d3 * 256 + d4;
	}

	public static class Nets {
		public String StartIP;
		public String EndIP;
		public String NetMask;

		public String getStartIP() {
			return StartIP;
		}

		public void setStartIP(String startIP) {
			StartIP = startIP;
		}

		public String getEndIP() {
			return EndIP;
		}

		public void setEndIP(String endIP) {
			EndIP = endIP;
		}

		public String getNetMask() {
			return NetMask;
		}

		public void setNetMask(String netMask) {
			NetMask = netMask;
		}

		public Nets() {
		}
	}
}
