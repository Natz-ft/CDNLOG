package algorithms.test;

import java.util.Stack;
// 栈 例子
public class test02 {
    public static void main(String[] args) {
        Stack<Character> stack = new Stack<>();
        // 元素入栈
        stack.push('a');
        stack.push('b');
        stack.push('c');
        stack.push('d');
        
        System.out.println("栈顶元素\t"+stack.peek());

        try {
            // 栈空时会抛出 EmptyStackException 异常进入catch块，从而结束打印
            while(stack.peek() != null) {
                // 出栈栈顶元素，并打印出栈元素
                System.out.println(stack.pop());
            }
        } catch(Exception e) {
         //   e.printStackTrace();
        	System.out.println("栈空");
        }
    }
}
