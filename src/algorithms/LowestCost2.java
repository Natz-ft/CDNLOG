package algorithms;

import java.util.HashMap;
import java.util.Map;

public class LowestCost2{

    //不可达记为INF
    //不可记为Integer.MAX_VALUE,再加cost会变成负数，影响结果
    final static int INF = 999999;

    //下标与名称的映射
    static  Map<Integer,String> map = new HashMap<>();

    //用二位数组邻接矩阵表示有向加权图，下标表示边，值表示权
    //以行为边的起点，列为边的终点
    //INF表示不可达
    
//     加权路线图
    static int[][] graph = {
    		{INF,5,2,INF,INF,INF},  //  
            {INF,INF,INF,4,2,INF},// 
            {INF,8,INF,INF,7,INF}, //  
            {INF,INF,INF,INF,6,3}, //
            {INF,INF,INF,INF,INF,1},
            {INF,INF,INF,INF,INF,INF}
    };
//     开销，起点本身为无穷大，不知道的开销，设为无穷大
    static int[] costs = {
            INF, //start
            5, //A
            2, //B
            INF,
            INF,
            INF //end
    };
//  父节点数组，
    static int[] parents = {
            INF, //start 的起点
            0, //A的起点
            0, //B的起点
            -1 , //end的起点
            -1,
            -1
    };

    //记录是否处理过，处理过 记为1
    static int[] proceessed = {
            0,
            0,
            0,
            0,
            0,
            0
    };

    public static void main(String[] args) {
        map.put(0,"start");
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.put(4,"D");
        map.put(5,"end");
        
        //在未处理节点中找出开销最小的节点
        int node = findLowestCostNode(costs); 
        
        while (node != 0) { // 
            int cost = costs[node]; // 首次开销是2
//              遍历当前所有邻居 
            for (int i = 0; i < graph[node].length; i++) {
                
            	int newCost = cost + graph[node][i];
                 System.out.println(costs[i]);
                if (costs[i] > newCost){ // 如果当前节点前往该邻居更近
                    costs[i] = newCost;  //就更新该邻居的开销
                    parents[i] = node;   // 同时将该邻居的父节点，设置为当前节点
                }
            }
            proceessed[node] = 1; // 将当前节点标志标记为处理过
            node = findLowestCostNode(costs); // 找出接下来要处理的节点，并循环
        }

        //最终输出parents表，即可得出花费最少路线
        for (int i = 0; i < parents.length; i++) {
        	 System.out.print(i+":\t");
            System.out.print(map.get(i)+" ");
            System.out.println(map.get(parents[i]));

        }

    }

    //返回花费最少的节点（下标）
    public static int findLowestCostNode(int[] costs) {
        int lowCost = Integer.MAX_VALUE;
        int lowCostNode = 0;
        for (int i = 0; i < costs.length; i++) {
            int cost = costs[i];
            if (cost < lowCost && proceessed[i] == 0) {
                lowCost = cost;
                lowCostNode = i;
            }
        }
        return lowCostNode;
    }
    
}