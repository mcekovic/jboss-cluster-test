package org.strangeforest.test.jboss.client;

import java.util.*;

import org.strangeforest.test.jboss.ejb.*;

public abstract class Tests {

	public static void testNode(RemoteTest test) {
		System.out.println();
		System.out.println(test);
		System.out.println("CurrentTime: " + test.getCurrentTime());
		System.out.println("ServerName: " + test.getServerName());
		System.out.println("NodeName: " + test.getNodeName());
		System.out.println("ServerHash: " + test.getServerHash());
		System.out.println("AppName: " + test.getAppName());
		testCluster(test);
	}

	public static void testCalculator(RemoteCalculator calc) {
		System.out.println();
		System.out.println(calc);
		calc.set(0);
		for (int i = 1; i < 100; i+=2) {
			calc.subtract(i);
			calc.add(i + 1);
		}
		System.out.println(calc.get());
		testCluster(calc);
	}

	private static void testCluster(NodeAware nodeAware) {
		Map<String, Integer> nodes = new TreeMap<>();
		for (int i = 0; i < 100; i++) {
			String node = nodeAware.getNodeName();
			Integer count = nodes.get(node);
			nodes.put(node, count != null ? ++count : 1);
		}
		System.out.println(nodes);
	}
}
