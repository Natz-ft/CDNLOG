package algorithms.test;

import java.util.LinkedList;
import java.util.Queue;

public class test03 {
	 public static void main(String[] args) {
		 Queue<String> searchQueue = new LinkedList<>();
		 
		 searchQueue.add("a");
		 searchQueue.add("b");
		 searchQueue.add("c");
		 searchQueue.add("d");
		 searchQueue.add("e");
		 
			while (!searchQueue.isEmpty()) {
				String person = searchQueue.poll();
			   System.out.println(person)	;
			}
		 
		 
	 }
}
