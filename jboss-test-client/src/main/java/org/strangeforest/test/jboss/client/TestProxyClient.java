package org.strangeforest.test.jboss.client;

import javax.naming.*;

import org.strangeforest.test.jboss.ejb.*;

import static org.strangeforest.test.jboss.client.JNDIUtil.*;
import static org.strangeforest.test.jboss.client.Tests.*;

public class TestProxyClient {

	private static final String REMOTE_TEST_PROXY_NAME = "jboss-test-proxy-ear/jboss-test-proxy-ejb//RemoteTestProxyBean!org.strangeforest.test.jboss.ejb.RemoteTest";

	public static void main(String[] args) throws NamingException {
		String port = System.getProperty("jboss-as.port", "4447");

		// EJB Client
		InitialContext ctx = getEJBClientInitialContext();
		testNode((RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_PROXY_NAME));

		// Remote Naming
		InitialContext ctx2 = getRemoteNamingInitialContext(port);
		testNode((RemoteTest)ctx2.lookup("java:" + REMOTE_TEST_PROXY_NAME));

		// Remote Naming + EJB Client
		InitialContext ctx3 = getRemoteNamingEJBClientInitialContext(port);
		testNode((RemoteTest)ctx3.lookup("ejb:" + REMOTE_TEST_PROXY_NAME));
	}
}
