package org.strangeforest.test.jboss.client;

import javax.naming.*;

import org.strangeforest.test.jboss.ejb.*;

import static org.strangeforest.test.jboss.client.JNDIUtil.*;
import static org.strangeforest.test.jboss.client.Tests.*;

public class TestProxyClient {

	public static final String REMOTE_TEST_PROXY_NAME = "jboss-test-proxy-ear/jboss-test-proxy-ejb//RemoteTestProxyBean!org.strangeforest.test.jboss.ejb.RemoteTest";

	public static void main(String[] args) throws NamingException {
		String port = System.getProperty("jboss-as.port", "4447");

		// JBoss Remoting
		InitialContext ctx = getInitialContext();
		testNode((RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_PROXY_NAME));

		// Remote Naming
		InitialContext ctx2 = getRemoteInitialContext(port);
		testNode((RemoteTest)ctx2.lookup("java:" + REMOTE_TEST_PROXY_NAME));

		// Remote Naming Alt
		InitialContext ctx3 = getRemoteInitialContextAlt(port);
		testNode((RemoteTest)ctx3.lookup("java:" + REMOTE_TEST_PROXY_NAME));
	}
}
