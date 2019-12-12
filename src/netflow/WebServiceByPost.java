package netflow;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import TOOLS.DBAcess;
import TOOLS.getDate;


import TOOLS.getDate;

public class WebServiceByPost {
	public static String WebServiceByPost(String url,String string){
		String str= "";
		return str;
	} 
	public static void main(String[] args) throws Exception{
		
		String vdate = getDate.getYesterday();
		 
		String vtime ="";
		vtime = vdate+"000000";
		String vtime1 = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		 Date utilDate = sdf.parse(vdate);
		
		 vtime1= getDate.getTomorrow(utilDate)+"000000";
		 System.out.println(vtime1);
		
	}

}
