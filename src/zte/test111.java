package zte;

public class test111 {
 // BCS-CDN-024-AS0202_OTTCACHE_20181111T211001Z
	public static void main(String[] args) throws Exception {
		
		String str = "HMS_accessDownstream_426_20181112T011004Z+08.log";
		System.out.println(str.indexOf("_",21));
		System.out.println(str.substring(str.indexOf("T")-1,str.indexOf("T") ) );
		
	}
}
