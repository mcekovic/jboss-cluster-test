package org.strangeforest.test.jboss.client;

import javax.naming.*;

import org.strangeforest.test.jboss.ejb.*;

import static org.strangeforest.test.jboss.client.JNDIUtil.*;
import static org.strangeforest.test.jboss.client.Tests.*;

public class TestClient {

	private static final String REMOTE_TEST_NAME = "jboss-test-ear/jboss-test-ejb//RemoteTestBean!org.strangeforest.test.jboss.ejb.RemoteTest";
	private static final String REMOTE_CALCULATOR_NAME = "jboss-test-ear/jboss-test-ejb//RemoteCalculatorBean!org.strangeforest.test.jboss.ejb.RemoteCalculator";

	public static void main(String[] args) throws NamingException {
		String port = System.getProperty("jboss-as.port", "4447");

		// EJB Client
		InitialContext ctx = getEJBClientInitialContext();
		testNode((RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx.lookup("ejb:" + REMOTE_CALCULATOR_NAME + "?stateful"));

		// Remote Naming
		InitialContext ctx2 = getRemoteNamingInitialContext(port);
		traverse(ctx2, "java:");
		testNode((RemoteTest)ctx2.lookup("java:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx2.lookup("java:" + REMOTE_CALCULATOR_NAME));

		// Remote Naming + EJB Client
		InitialContext ctx3 = getRemoteNamingEJBClientInitialContext(port);
		traverse(ctx3, "java:");
		testNode((RemoteTest)ctx3.lookup("ejb:" + REMOTE_TEST_NAME));
		testCalculator((RemoteCalculator)ctx3.lookup("ejb:" + REMOTE_CALCULATOR_NAME + "?stateful"));
	}
}
