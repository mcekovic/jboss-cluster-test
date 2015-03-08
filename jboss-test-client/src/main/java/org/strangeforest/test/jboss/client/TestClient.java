package org.strangeforest.test.jboss.client;

import java.util.*;
import javax.naming.*;

import org.strangeforest.test.jboss.ejb.*;

import static com.google.common.base.Strings.*;

public class TestClient {

	public static final String REMOTE_TEST_NAME = "jboss-test-ear/jboss-test-ejb//RemoteTestBean!org.strangeforest.test.jboss.ejb.RemoteTest";
	public static final String REMOTE_CALCULATOR_NAME = "jboss-test-ear/jboss-test-ejb//RemoteCalculatorBean!org.strangeforest.test.jboss.ejb.RemoteCalculator";

	public static void main(String[] args) throws NamingException {
		String port = System.getProperty("jboss-as.port", "4447");

		InitialContext ctx = getInitialContext(port);
		RemoteTest test = (RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_NAME);
		testNode(test);
		testCluster(test);

		RemoteCalculator calc = (RemoteCalculator)ctx.lookup("ejb:" + REMOTE_CALCULATOR_NAME + "?stateful");
		testCalculator(calc);
		testCluster(calc);

		InitialContext ctx2 = getInitialContextViaFactory(port);
		traverse(ctx2, "java:");
		RemoteTest test2 = (RemoteTest)ctx2.lookup("java:" + REMOTE_TEST_NAME);
		testNode(test2);
		testCluster(test2);

		RemoteCalculator calc2 = (RemoteCalculator)ctx2.lookup("java:" + REMOTE_CALCULATOR_NAME);
		testCalculator(calc2);
		testCluster(calc2);
	}

	private static void testNode(RemoteTest test2) {
		System.out.println(test2.getCurrentTime());
		System.out.println(test2.getServerName());
		System.out.println(test2.getNodeName());
		System.out.println(test2.getServerHash());
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

	private static void testCalculator(RemoteCalculator calc) {
		calc.set(0);
		for (int i = 1; i < 100; i+=2) {
			calc.subtract(i);
			calc.add(i + 1);
		}
		System.out.println(calc.get());
	}

	private static InitialContext getInitialContext(String port) throws NamingException {
		Properties props = new Properties();
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put("remote.connection.default.port", port);
		return new InitialContext(props);
	}

	private static InitialContext getInitialContextViaFactory(String port) throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL,"remote://localhost:" + port);
		props.put(Context.SECURITY_PRINCIPAL, "test");
		props.put(Context.SECURITY_CREDENTIALS, "test123.");
		return new InitialContext(props);
	}

	private static void traverse(Context ctx, String path) throws NamingException {
		if (isNullOrEmpty(path))
			traverse(ctx, null, 0);
		else {
			System.out.println(path);
			traverse(ctx, path, 1);
		}
	}

	private static void traverse(Context ctx, String path, int depth) throws NamingException {
		for (NamingEnumeration<Binding> e = ctx.listBindings(path); e.hasMore() && depth < 10; ) {
			Binding next = e.next();
			String name = next.getName();
			System.out.print(repeat(" ", depth * 2));
			System.out.printf("%1$s[%2$s]%n", name, next.getClassName());
			Object obj = next.getObject();
			if (obj instanceof Context)
				traverse((Context)obj, isNullOrEmpty(path) ? name : path + "/" + name, depth+1);
		}
	}
}
