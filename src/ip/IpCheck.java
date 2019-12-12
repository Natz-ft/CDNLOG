package ip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import TOOLS.DBAcess;
import TOOLS.SetLog;

public class IpCheck {

	public static List<String[]> getIcpList() {
		DBAcess db = new DBAcess();
		List<String[]> list = new ArrayList<String[]>();
		if (db.createConnLiujy()) {
			String sql = " select * from vinca_ip_icp_check order by IPSTART_INT ";
			db.query(sql);
			// String [] arr =new String[3];

			while (db.next()) {
				String ID = db.getValue("ID");
				String ipStart = db.getValue("IPSTART_INT");
				String ipEnd = db.getValue("IPEND_INT");
				String province = db.getValue("PROVINCE");
				String busi = db.getValue("BUSI");

				String[] arr = new String[] { ID, ipStart, ipEnd, province, busi };
				list.add(arr);

			}
			db.closeRs();
			db.closeStm();
		}
		db.closeConn();
		return list;

	}

	// 检查vinca_ip_icp_check 表数据有没有重复交叉数据
	public static void check1(List<String[]> list) throws Exception {
		DBAcess db = new DBAcess();
		if (db.createConnLiujy()) {
		for (String[] arr : list) {
			String str = "select * from vinca_ip_icp_check WHERE ID <>'" + arr[0] + "' AND ( IPSTART_INT BETWEEN "
					+ arr[1] + " AND " + arr[2] + " or  IPEND_INT BETWEEN " + arr[1] + " AND " + arr[2] + ")";

			int count = db.queryRow(str);
			if (count > 0) {
				System.out.println(arr[0]);
			}
		}}

		db.closeConn();
	}

	// 核对 icp表的记录，在集团省表中有登记。

	public static void check2(List<String[]> list) throws Exception {
		DBAcess db = new DBAcess();
		if (db.createConnLiujy()) {
		for (String[] arr : list) {
			
			if (arr[4]==null||arr[4].length()==0){
				arr[4] = "%";
			}
			
			String str = "select * from vinca_ip_pro_info  WHERE  IPSTART_INT <= " + arr[1] + " and IPEND_INT >= "
					+ arr[2] + " and (PROVINCE = '" + arr[3] + "' or PROVINCE = '其他' ) and BUSI like '" + arr[4] + "'";
			
			int count = db.queryRow(str);
			if (count == 0) {
				System.out.println(str);
			}
		}
		}
		db.closeConn();
	}

	public static void main(String[] args) throws Exception {
		List<String[]> list = getIcpList();

		check2(list);
	}
}
