package exam;

import java.util.ArrayDeque;
import java.util.Queue;

public class TreeNode {
    //数据域
public int data; 
//左指针域
public TreeNode left;
//右指针域
public TreeNode right;
    //构造带有参数的构造方法
public TreeNode(int data) {
	this.data = data;
}
//重写toString方法
public String toString() {
	return "TreeNode [data=" + data + ", left=" + left + ", right=" + right
			+ "]";
}

// 获取最大深度
     public  int getMaxDepth(TreeNode root) {
         if (root == null)
             return 0;
         else {
             int left = getMaxDepth(root.left);
             int right = getMaxDepth(root.right);
             return 1 + Math.max(left, right);
         }
     }

  // 获取最大宽度
     public   int getMaxWidth(TreeNode root) {
         if (root == null)
             return 0;
 
         Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
         int maxWitdth = 1; // 最大宽度
         queue.add(root); // 入队
 
         while (true) {
             int len = queue.size(); // 当前层的节点个数
             if (len == 0)
                 break;
             while (len > 0) {// 如果当前层，还有节点
                 TreeNode t = queue.poll();
                 len--;
                 if (t.left != null)
                     queue.add(t.left); // 下一层节点入队
                 if (t.right != null)
                     queue.add(t.right);// 下一层节点入队
             }
             maxWitdth = Math.max(maxWitdth, queue.size());
         }
         return maxWitdth;
     }
     


}