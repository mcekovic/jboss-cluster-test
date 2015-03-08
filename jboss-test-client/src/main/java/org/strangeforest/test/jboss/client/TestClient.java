package org.strangeforest.test.jboss.client;

import javax.naming.*;

import org.strangeforest.test.jboss.ejb.*;

import static org.strangeforest.test.jboss.client.JNDIUtil.*;
import static org.strangeforest.test.jboss.client.Tests.*;

public class TestClient {

	public static final String REMOTE_TEST_NAME = "jboss-test-ear/jboss-test-ejb//RemoteTestBean!org.strangeforest.test.jboss.ejb.RemoteTest";
	public static final String REMOTE_CALCULATOR_NAME = "jboss-test-ear/jboss-test-ejb//RemoteCalculatorBean!org.strangeforest.test.jboss.ejb.RemoteCalculator";

	public static void main(String[] args) throws NamingException {
		String port = System.getProperty("jboss-as.port", "4447");

		// JBoss Remoting
		InitialContext ctx = getInitialContext();
		testNode((RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx.lookup("ejb:" + REMOTE_CALCULATOR_NAME + "?stateful"));

		// Remote Naming
		InitialContext ctx2 = getRemoteInitialContext(port);
		traverse(ctx2, "java:");
		testNode((RemoteTest)ctx2.lookup("java:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx2.lookup("java:" + REMOTE_CALCULATOR_NAME));

		// Remote Naming Alt
		InitialContext ctx3 = getRemoteInitialContextAlt(port);
		traverse(ctx3, "java:");
		testNode((RemoteTest)ctx3.lookup("java:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx3.lookup("java:" + REMOTE_CALCULATOR_NAME));
	}
}
