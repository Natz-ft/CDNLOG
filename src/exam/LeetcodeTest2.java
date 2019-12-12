package exam;
/*
给定一个非负整数数组，你最初位于数组的第一个位置。
数组中的每个元素代表你在该位置可以跳跃的最大长度。
判断你是否能够到达最后一个位置。
示例 1:
输入: [2,3,1,1,4]
输出: true
解释: 从位置 0 到 1 跳 1 步, 然后跳 3 步到达最后一个位置。
示例 2:
输入: [3,2,1,0,4]
输出: false
解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。


分析：
指针i从倒数第二位往前遍历，n代表步数，初始为1，
若nums[i]大于或等于n,说明从此位置可以跳到最后一位，并从此位置截断，此位置便为最后一位
若nums[i]小于n,说明从此位置不能跳到最后一位，则n++,向前遍历查看其它位置是否能跳到最后一位，
遍历到最后一位时步数大于1，说明跳跃游戏会失败，返回false,否则返回true.


	 
	 * */
	
public class LeetcodeTest2 {
	public static void main(String[] args) {
		Solution2 So = new Solution2();
		int[] nums = {2,3,1,1,4};
		System.out.println(So.jump(nums));
	}
}
class Solution2 {
    public int jump(int[] nums) {
    	int i=0;
        int res = 0;
        while(i<nums.length-1){
        	int steps = nums[i];
        	//最后一步是个特例，不需要寻找下一个位置，如果能在此时跳到最后一个位置，这一步就是最优的。
        	if(steps>=nums.length-1-i){
        		res++;
        		break;
        	}
        	//跳到下一个最优的地方
        	int max = nums[i+1];//记录最大步数
        	int index = i+1;//记录索引
        	for(int j=index; j<=i+steps && j<nums.length-1; j++){
        		if(nums[j]+j-index >= max){
        			max = nums[j];
        			index = j;
        		}
        	}
        	res++;
        	i = index;
        }
        return res;
    }
}
 
 

