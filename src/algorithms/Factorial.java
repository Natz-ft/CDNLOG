package algorithms;

/**
 * 
 * @author Hanwen
 * @date 2017年8月29日 上午11:17:26
 * @version
 */
public class Factorial {
	public static void main(String[] args) {
		int k = 6;
		int i = factorial(k);
		System.out.println(i);
	}
	
	/**
	 * 递归算阶乘
	 * @param n (计算的数)
	 * @return result(计算的结果)
	 */
	private static int factorial(int n) {
		if(n == 1) {
			return 1;
		}else {
			int num = n * factorial(n-1);
			return num ;
		}
	}
}