package exam;

public class struckTree {

public static void main(String[] args) {
	int preorder[] = {3,9,20,15,7};
	int inorder[] = {9,3,15,20,7};
	
	// preorder = [3,9,20,15,7]
	//	inorder = [9,3,15,20,7]
	
	struckTree tree = new struckTree();
	
	TreeNode treeNode = tree.rebuildBinaryTree(preorder, inorder);
	System.out.println(treeNode);
	System.out.println(treeNode.getMaxDepth( treeNode));
	
	System.out.println(treeNode.getMaxWidth( treeNode));
	
}

	
	
	
	public TreeNode rebuildBinaryTree(int preorder[], int inorder[]) {
		if (preorder == null || inorder == null) { //如果前序或者中序有一个是空直接返回
			return null;
		}
	// 定义构建二叉树的核心算法
		TreeNode root = rebuildBinaryTreeCore(preorder, 0, preorder.length - 1,
				inorder, 0, inorder.length - 1);
		return root;
	}
	// 构建二叉树的核心算法
	public TreeNode rebuildBinaryTreeCore(int preorder[], int startPreorder,
			int endPreorder, int inorder[], int startInorder, int endInorder) {
		if (startPreorder > endPreorder || startInorder > endInorder) { //停止递归的条件
			return null;
		}
		TreeNode root = new TreeNode(preorder[startPreorder]);
		for (int i = startInorder; i <= endInorder; i++) {
			if (preorder[startPreorder] == inorder[i]) {
				// 其中（i - startInorder）为中序排序中左子树结点的个数
				//左子树
				root.left = rebuildBinaryTreeCore(preorder, startPreorder + 1,
						startPreorder + (i - startInorder), inorder,
						startInorder, i - 1);
				//右子树
				root.right = rebuildBinaryTreeCore(preorder, (i - startInorder)
						+ startPreorder + 1, endPreorder, inorder, i + 1,
						endInorder);
	 
			}
		}
		return root;
	}
	 
}
