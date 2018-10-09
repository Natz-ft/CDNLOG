package ZTECDN;

import java.sql.SQLException;
import java.util.Scanner;

import TOOLS.DBAcess;

public class ZTE_CDN_LOG_ACC_SERVICETYPE {
	public static void main(String[] args) throws Exception {
		
		//Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
		//System.out.println("请输入日志累计日期，格式是YYYYMMDD,不输入日期，直接敲回车，默认是当前日期的前一天");// 提示用户输入
		//String yesterday = scanner.nextLine();
		
		String yesterday = "";
		 if (args.length<1){
			 yesterday = TOOLS.getDate.getYesterday();
		 }
		 else 
		 {
			 yesterday = args[0];
		 }
		 
	 

		accumulative_day(yesterday, "as");
		accumulative_day(yesterday, "dd");
		accumulative_day(yesterday, "ly");
		accumulative_day(yesterday, "dl");
		accumulative_day(yesterday, "yk");
		accumulative_day(yesterday, "bx");
		
	}

	public static void accumulative_day(String day, String city ) throws SQLException {
		
		System.out.println("begein accumulative  file of "+city+"\t"+day) ;
		DBAcess db = new DBAcess();

		if (db.createConnLocal()) {
			 String sql = "insert into cdn_zte_acc_servicetype (city,vdate,ServiceType,nums,duration,volume)  SELECT '"+city+"','"+day+"', ServiceType, COUNT(*) NUUMS ,  (sum(duration) /(1000 * 60 * 60)) duration, 	sum(volume) / (1024 * 1024 * 1024 * 1024) volume  from bcs_cdn_024_"+ city	+ "_"+ day+" GROUP BY ServiceType";
			 System.out.println(sql);
					 db.insert(sql);
		 
			}
			db.closeRs();
			db.closeStm();
 

		db.closeConn();

	}

}
