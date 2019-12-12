package exam;

import java.util.Scanner;

/*
 55. 跳跃游戏
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
 
 * */
public class jump     
{ 
    public static void main(String[] args){   	
    int arr[]=new int[1000];  
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    for(int i=0;i<n;i++)  
        arr[i]=in.nextInt();
        
    if(Jump(n,arr,0)==1) System.out.println("true");
    if(Jump(n,arr,0)==0) System.out.println("false");
    }
    static int Jump(int n,int arr[],int x)  
    {    
        if(x>=n-1)  
            return 1; 
        for(int i=arr[x];i>0;i--)  
        {  
            if(Jump(n,arr,x+i)==1)  
                return 1;  
            else  
                arr[x+i]= -1;  
        }  
        return 0;  
    }  
}