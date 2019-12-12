package exam;

import java.util.List;

public class binary {
	

	public static void main(String[] args) throws Exception {
		function2(function1(234));
		
		;
		 System.out.println(intToHex(15));
	}
	
	public static String function1(int n){
	      String result = Integer.toBinaryString(n);
	      //int r = Integer.parseInt(result);
	      //System.out.println(r);
	      System.out.println(result);
	      return result;
	  }
	//
	public static String function2(String str){
		String result = Integer.valueOf(str,2).toString();
	      System.out.println(result);
	      return result;
	  }
	
	
	// 十进制转十六进制
	private static String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;            
        }
        a = sb.reverse().toString();
        return a;
    }
	

}
