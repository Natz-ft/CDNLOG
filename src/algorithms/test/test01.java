package algorithms.test;

/**
 * 把一个长方形，分割成尽可能大的若干正方形，已知长方形长，宽，求正方形的边长，利用递归
 * @author dirxu
 * @date 2019年8月22日 
 * @version
 */
public class test01 {
	public static void main(String[] args) {
		 
		int i = factorial(640,1680);
		System.out.println(i);
	}
	
	/**
	 * 假定，m  
	 * @param n (计算的数)
	 * @return result(计算的结果)
	 */
	private static int factorial(int m,int n ) {
		if(m == n) {
			return m ;
		}else {
			  if (m>n){
				  int tmp = m;
				    m =n;
				    n=tmp;
			  }
			  
			  while (n-m>0){
				  n = n-m;
			  }
			  
			  return factorial(m,n);
			  
		}
	}
}