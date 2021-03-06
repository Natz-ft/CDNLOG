package algorithms;

import java.util.LinkedList;
import java.util.Queue;

public class searchQueue {

	// 使用二维数组表示有向图
	static String[] graphName = { "you", "alice", "bob", "claire", "anuj", "peggy", "thom", "jonny" };
	static String[][] graph = { 
			{ "you", "alice" }, 
			{ "you", "bob" }, 
			{ "you", "claire" }, 
			{ "bob", "anuj" },
			{ "bob", "peggy" }, 
			{ "alice", "peggy" }, 
			{ "claire", "jonny" }, 
			{ "claire", "thom" }};

	public static boolean Search(String name) {
		// 新建队列

		Queue<String> searchQueue = new LinkedList<>();

		Queue<String> searched = new LinkedList<>();

		for (int i = 0; i < graphName.length; i++) {
			searchQueue.add(graphName[i]);
		}
		while (!searchQueue.isEmpty()) {
			String person = searchQueue.poll();
			System.out.println("person\t" + person);
			if (!searched.contains(person)) {
				if (personIsSeller(person)) {
					System.out.println(person + " is a mango seller!");
					return true;
				} else {

					for (int i = 0; i < graph.length; i++) {
						if (graph[i][0].equals(person)) {
						//	System.out.println(person + "\tgraph[" + i + "][0]\t" + graph[i][0]);
							searchQueue.add(graph[i][1]);

						}

					}
					searched.add(person);
				}
			}
		}
		return false;
	}

	public static boolean personIsSeller(String name) {
		return name.charAt(name.length() - 1) == 'm';
	}

	public static void main(String[] args) {
		System.out.println(Search("you"));
	}
}