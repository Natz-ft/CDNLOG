package ip;

import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.IPPoolUtil;
import TOOLS.ReadFromFile;
import TOOLS.FileWrite;

public class manyIpFind {
	public static void main(String[] args) throws Exception {
		List<String> list = ReadFromFile.getFileByLines("e:/aaa.txt");

//		DBAcess db = new DBAcess();
//		System.out.println("---start");
//		if (db.createHlw()) {
//
//			for (String ip : list) {
//				String res = searchIp( db,ip);
//				FileWrite.WriteFileByBuffered("e:/ip", res, "1");
//			}
//		}
//		System.out.println("---end");
//		db.closeConn();
		
		List<ipEntiy> listIp = getList();
		
		for (String ip : list) {
			 long lip = IPPoolUtil.getIp2long(ip);
			// System.out.println(lip);
			 int i =  BinarySearch (listIp,lip) ;
			 if (i>0){
				 System.out.println(ip+"\t"+listIp.get(i).getProvince()+"\t"+listIp.get(i).getIcp());
			 }
			 else {
				 System.out.println(ip+"\t"+"未登记");
			 }	 
			
		}
		
		
	}
	
	
	
	public static List<ipEntiy> getList() {

		List<ipEntiy> list = new ArrayList<ipEntiy>();
		DBAcess db = new DBAcess();

		if (db.createHlw()) {
			String sql = "SELECT  PROVINCE,icp,IPSTART_INT, IPEND_INT from vinca_ip_icp_check  order by  IPSTART_INT";
			db.query(sql);
			while (db.next()) {
				ipEntiy ip = new ipEntiy();

				ip.setIcp(db.getValue("icp"));
				ip.setProvince(db.getValue("PROVINCE"));
				ip.setIPSTART_INT(Long.parseLong(db.getValue("IPSTART_INT")));
				ip.setIPEND_INT(Long.parseLong(db.getValue("IPEND_INT")));

				list.add(ip);
			}
			db.closeRs();
			db.closeStm();
		}
		db.closeConn();
		return list;
	}
	
	
	
	

	public static String  searchIp(DBAcess db ,String ip) {
		long ipl = IPPoolUtil.getIp2long(ip);
	

		String province = "";
		String icp = "";
		if (db.createHlw()) {

			String sql = "SELECT PROVINCE,icp from vinca_ip_icp_check where  IPSTART_INT <= " + ipl
					+ " and IPEND_INT >= " + ipl + " LIMIT 1 ";

			db.query(sql);

			

			while (db.next()) {
				province = db.getValue("PROVINCE");
				icp = db.getValue("icp");
			}

			if (province.length() == 0) {
				province = "未登记";

			}
			if (icp.length() == 0) {
				icp = "未登记";

			}

			System.out.println(ip + "\t" + province + "\t" + icp);

		}
		db.closeRs();
		db.closeStm();
		 return ip + "\t" + province + "\t" + icp;
	}
	
	public  static int BinarySearch(List<ipEntiy> list, long  lip) {
		int low = 0;
		int high = list.size() - 1;
		
		while(low <= high) {
			int mid = (low + high) / 2;
			
			if( list.get(mid).getIPSTART_INT() <=lip &&  lip<= list.get(mid).getIPEND_INT()       ) {
				return mid;
			}else if(lip >  list.get(mid).getIPEND_INT() ) {
				low = mid + 1;
			}else {
				high = mid - 1; 
			}
		}
		return 0;
	}
	
	
	
	
	
	public static class ipEntiy {
		String icp;
		String province;
		long IPSTART_INT;
		long IPEND_INT;
		public String getIcp() {
			return icp;
		}
		public void setIcp(String icp) {
			this.icp = icp;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public long getIPSTART_INT() {
			return IPSTART_INT;
		}
		public void setIPSTART_INT(long iPSTART_INT) {
			IPSTART_INT = iPSTART_INT;
		}
		public long getIPEND_INT() {
			return IPEND_INT;
		}
		public void setIPEND_INT(long iPEND_INT) {
			IPEND_INT = iPEND_INT;
		}
		
		
	}

}
