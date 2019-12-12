package ZTECDN;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.getDate;
import TOOLS.getProperties;
import emai.Email;
import emai.EmailMessage;
import emai.MailSender;
import netflow.getNetflowData;

public class ZTE_CDN_LOG_SENDEMAIL_PIC_HOUR2 {

	public static void main(String[] args) throws Exception {

		String yesterday ,today = "";
	 

	    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = sdf.parse("2019-10-02");
	    
	    Date now =  new Date();
	    
	    if (!now.before(date)){
	    	return ;  
	    }
	    
	    yesterday = getDate.getEarlyOneHour() + "0000";
	    today= getDate.getHour() + "0000";
		
	   // System.out.println(today+"-"+yesterday);

		Email email = new Email();
		email.setSender(getProperties.getPropertie("SEND_USER").trim()); // SEND_USER

		email.setContent("");

		String emailSubject = getProperties.getPropertie("emailSubject_hour").trim();

		//System.out.println("emailSubject1:" + emailSubject);
		//emailSubject = new String(emailSubject.getBytes("ISO-8859-1"));
		System.out.println("emailSubject2:" + emailSubject);

		email.setTo( getProperties.getPropertie("receiverAddr_hour").trim());

		email.setSubject(emailSubject + "\t" + "--" + yesterday.substring(0, 8) + "-" + yesterday.substring(8, 10));

		String url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1517809785026";
		String json = "{\"time-range\":\"20190926090000-20190926100000\",\"portfilters\":[{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.1\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.10\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.12\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.14\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.15\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.17\"},{\"Input\":[\"Eth-Trunk49\",\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.2\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.20\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.21\"},{\"Input\":[\"Eth-Trunk50\",\"Eth-Trunk64\"],\"ExportIP\":\"211.137.0.22\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.23\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.24\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.25\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.26\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.27\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.28\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.29\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.30\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.31\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.32\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.33\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.34\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.35\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.4\"},{\"Input\":[\"Eth-Trunk36\",\"Eth-Trunk42\",\"Eth-Trunk30\",\"Eth-Trunk37\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.44\"},{\"Input\":[\"Eth-Trunk34\",\"Eth-Trunk37\",\"Eth-Trunk30\",\"Eth-Trunk36\",\"Eth-Trunk43\"],\"ExportIP\":\"211.137.0.45\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.5\"},{\"Input\":[\"Eth-Trunk36\"],\"ExportIP\":\"211.137.0.54\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.6\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.7\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.8\"},{\"Input\":[\"Eth-Trunk50\"],\"ExportIP\":\"211.137.0.9\"}],\"viewid\":\"1517809785026_Default\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-互联网电视-华为（下沉）\",\"内容网络-互联网电视-中兴（下沉）\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}},{\"Dst_servid\":{\"values\":[\"内容网络-互联网电视-中兴（下沉）-鞍山\",\"内容网络-互联网电视-中兴（下沉）-本溪\",\"内容网络-互联网电视-中兴（下沉）-丹东\",\"内容网络-互联网电视-中兴（下沉）-大连\",\">内容网络-互联网电视-中兴（下沉）-辽阳\",\"内容网络-互联网电视-中兴（下沉）-营口\",\"内容网络-互联网电视-华为（下沉）-朝阳\",\"内容网络-互联网电视-华为（下沉）-抚顺\",\"内容网络-互联网电视-华为（下沉）-阜新\",\"内容网络-互联网电视-华为（下沉）-葫芦岛\",\"内容网络->互联网电视-华为（下沉）-锦州\",\"内容网络-互联网电视-华为（下沉）-盘锦\",\"内容网络-互联网电视-华为（下沉）-沈阳\",\"内容网络-互联网电视-华为（下沉）-铁岭\",\"内容网络-互联网电视-中兴（下沉）-大连（新）\"],\"type\":\"noteq\"},\"Src_servid\":{\"values\":[\"内容网络-互联网电视-中兴（省中心）\",\"内容网络-互联网电视-华为（省中心）\"],\"type\":\"eq\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
        
		
		
		
		
		json = json.replace("20190926090000-20190926100000",  yesterday +"-"+today);
		
		
		
		String str = getNetflowData.getPostData(url, json);
		//System.out.println(str);

		String[] resArr = str.split("\\|");

		StringBuffer buffer = new StringBuffer(
				"<h1>OTT流量监控:</h1><table   border='1'><tr><th>城市</th><th align= 'center' >平均流量(GBps)</th><th>流量利用率(%)</th></tr>"); // 创建一个
																																	// StringBuffer
																																	// 对象

		String html = "";
		float sum = 0;
		float sumMax = 0;
		float sumAvg = 0;

		DecimalFormat df = new DecimalFormat(".##");

		for (String string : resArr) {

			String[] arr = string.split(",");
			
			

			arr[1] = String.valueOf(df.format(Float.parseFloat(arr[1]) / (1000 * 1000 * 1000)));

			sumAvg = sumAvg + Float.parseFloat(arr[1]);
			sumMax = sumMax + Float.parseFloat(arr[2])/(1000 * 1000 * 1000);
			
			sum = sum + Float.parseFloat(getOttRate(arr[0]));
			
			
			
			arr[2] = df.format(Long.parseLong(arr[2]) / (1000 * 1000 * 1000) / Float.parseFloat(getOttRate(arr[0])) * 100);

			

			

			html = "<tr> <th>" + arr[0] + "</th><td align= 'center' >" + arr[1] + "</td><td align= 'center' >" + arr[2]+ "</td></tr>";
			buffer.append(html);
		}
		
		//System.out.println("sumAvg\t"+sumAvg);
		//System.out.println("sumMax\t"+sumMax);
		
		//System.out.println("sum\t"+sum);
		
		html = "<tr> <th> 合计</th><td align= 'center' >" + df.format(sumAvg) + "</td><td align= 'center' >"
				+ df.format(sumMax / sum * 100) + "</td></tr>";

		buffer.append(html);

		buffer.append("</table>");

		/////////////////////////////////
		/////////////////////////////

		url = "http://211.137.42.227:2500/netflow/query/schemes/1490064897375_1490065007712";

		json = "{\"time-range\":\"20190926090000-20190926100000\",\"aggregation\":[\"Src_servid\"],\"filters\":[{\"Src_servid\":{\"values\":[\"内容网络-自建CDN-\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":0}";
        
		json = json.replace("20190926090000-20190926100000",  yesterday +"-"+today);
		
		
		System.out.println("json:\t"+json);
		
		str = getNetflowData.getPostData(url, json);

		resArr = str.split("\\|");

		buffer.append(
				"<h1>自建CDN流量监控:</h1><table   border='1'><tr><th>种类</th><th align= 'center' >平均流量(GBps)</th><th>流量利用率(%)</th></tr>"); // 创建一个
																																		// StringBuffer
																																		// 对象

		sum = 0;
		sumMax = 0;
		sumAvg = 0;

		for (String string : resArr) {

			String[] arr = string.split(",");

			arr[1] = String.valueOf(df.format(Float.parseFloat(arr[1]) / (1000 * 1000 * 1000)));

			if (arr[0].contains("扁平化")) {
				continue;
			}

			sumAvg = sumAvg + Float.parseFloat(arr[1]);
			sumMax = sumMax + Float.parseFloat(arr[2]) / (1000*1000*1000);
			
			sum = sum + Float.parseFloat(getOttRate(arr[0]));
			
			//System.out.println("sum:\t"+sum);
			//System.out.println("sumMax:\t"+sumMax);

			arr[2] = df.format(Long.parseLong(arr[2]) / (1000 * 1000 * 1000) / Float.parseFloat(getOttRate(arr[0])) * 100);

			

			if (Math.abs(Float.parseFloat(getOttRate(arr[0]))) < 0.001) {
				continue;
			}

			

			html = "<tr> <th>" + arr[0] + "</th><td align= 'center' >" + arr[1] + "</td><td align= 'center' >" + arr[2]
					+ "</td></tr>";
			buffer.append(html);
		}
		//System.out.println("---------"+sumMax+"\t"+sum);

		html = "<tr> <th> 合计</th><td align= 'center' >" + df.format(sumAvg) + "</td><td align= 'center' >"
				+ df.format(sumMax / sum * 100) + "</td></tr>";

		buffer.append(html);

		/////////////////////////////////
		/////////////////////////////

		buffer.append("</table>");

		url = "http://211.137.42.227:2500/netflow/query/schemes/1551059526504_1551059601818";

		json = "{\"time-range\":\"20190926090000-20190926100000\",\"aggregation\":[\"Src_ipid\"],\"filters\":[{\"Src_ipid\":{\"values\":[\"LNAS-PS-CDN-OTT\",\"LNBX-PS-CDN-OTT\",\"LNCY-PS-OTT-HMS\",\"LNDD-PS-CDN-OTT\",\"LNDL-PS-CDN-OTT0\",\"LNDL-PS-CDN-OTT1\",\"LNDL-PS-CDN-OTT2\",\"LNDL-PS-CDN-OTT3\",\"LNDL-PS-CDN-OTT4\",\"LNDL-PS-CDN-OTT5\",\"LNFS-PS-OTT-HMS\",\"LNFX-PS-OTT-HMS\",\"LNHLD-PS-OTT-HMS\",\"LNJZ-PS-CDN-OTT\",\"LNLY-PS-CDN-OTT\",\"LNPJ-PS-OTT-HMS\",\"LNSY-PS-OTT-HMS\",\"LNTL-PS-OTT-HMS\",\"LNYK-PS-CDN-OTT\",\"LNSY-PS-OTT-Origin\",\"LNDL-PS-CDN-OTTC\"],\"type\":\"match\"},\"Dst_netid\":{\"values\":[\"网内-辽宁移动\"],\"type\":\"eq\"}}],\"timetype\":\"custom-time\",\"topN\":1000}";
        
		json = json.replace("20190926090000-20190926100000",  yesterday +"-"+today);
		
		
		str = getNetflowData.getPostData(url, json);

		resArr = str.split("\\|");

		List<String> listLow = new ArrayList<String>();

		List<String> listHigh = new ArrayList<String>();

		for (String string : resArr) {

			String[] arr = string.split(",");

			arr[2] = String.valueOf(df.format(Float.parseFloat(arr[2]) / (1000 * 1000 * 1000)));
			
			if (Math.abs(Float.parseFloat(getDevice(arr[0]))) < 0.001) {
				continue;
			}
			
			
			

			float BandwithLimit = Float.parseFloat(getDevice(arr[0]));

			if (Float.parseFloat(arr[2]) / BandwithLimit < 0.15) {
				listLow.add(arr[0] + "," + df.format(Float.parseFloat(arr[2]) / BandwithLimit * 100));
			} else if (Float.parseFloat(arr[2]) / BandwithLimit > 0.6) {
				listHigh.add(arr[0] + "," + df.format(Float.parseFloat(arr[2]) / BandwithLimit * 100 ));
			}

		}
		 
		
		
		buffer.append("<h1>高负载设备清单:</h1><table border='1'><tr><th>设备名称</th><th align= 'center' >利用率</th></tr>"); // 创建一个
	    
		for (String string : listHigh) {

			String[] arr = string.split(",");
			
			html = "<tr><td>"+arr[0]+"</td><td align= 'center' >"+arr[1]+"</td></tr>";
			buffer.append(html);
			
		}
		
		buffer.append("</table>");	
	 
		
		
		buffer.append("<h1>低负载设备清单:</h1><table border='1'><tr><th>设备名称</th><th align= 'center' >利用率</th></tr>"); // 创建一个
		    
		for (String string : listLow) {

			String[] arr = string.split(",");
		
			
			html = "<tr><td>"+arr[0]+"</td><td align= 'center' >"+arr[1]+"</td></tr>";
			buffer.append(html);
			
		}
		
		buffer.append("</table>");
//		
//		
//		
//		buffer.append("<h4>高负载设备清单:</h4><table border='1'><tr><th>设备名称</th><th align= 'center' >利用率</th></tr>"); // 创建一个
//	    
//		for (String string : listHigh) {
//
//			String[] arr = string.split(",");
//			
//			html = "<tr><td>"+arr[0]+"</td>td align= 'center' >"+arr[1]+"</td></tr>";
//			buffer.append(html);
//			
//		}
//		
//		buffer.append("</table>");
//		
//		
		

		email.setContent(buffer.toString());

		EmailMessage mmss = new EmailMessage(email);
		mmss.setEmailAccount("dirxu@126.com");

		mmss.setUsername(getProperties.getPropertie("SEND_USER").trim());
		mmss.setPassword(getProperties.getPropertie("SEND_PWD").trim());// SEND_PWD

		MailSender ms = new MailSender();

		ms.sendEmail(mmss);

	}

	public static String getOttRate(String colume) {

		String result = "";
		DBAcess db = new DBAcess();
		if (db.createConnLocal()) {

			String sql = "SELECT IFNULL(sum(BandwithLimit),0)  flow from resource_of_device  where NETFLOWNAME = '"
					+ colume + "'";

			db.query(sql);
			while (db.next()) {
				result = db.getValue("flow");

			}

		}
		db.closeRs();
		db.closeStm();
		db.closeConn();

		return result;

	}

	public static String getDevice(String colume) {

		String result = "";
		DBAcess db = new DBAcess();
		if (db.createConnLocal()) {

			String sql = "SELECT IFNULL(sum(BandwithLimit),0)  flow from resource_of_device   where device_name = '"
					+ colume + "'";

			db.query(sql);
			while (db.next()) {
				result = db.getValue("flow");

			}

		}
		db.closeRs();
		db.closeStm();
		db.closeConn();

		return result;

	}

}
