package org.strangeforest.test.jboss.ejb;

import java.time.*;
import javax.annotation.security.*;
import javax.ejb.*;
import javax.inject.*;
import javax.naming.*;

@Stateless @Remote(RemoteTest.class)
public class RemoteTestProxyBean implements RemoteTest {

	@Inject @Named(RemoteTestProxyFactory.REMOTE_TEST_DELEGATE) private RemoteTest test;

	@RolesAllowed("Tester")
	@Override public Instant getCurrentTime() {
		return test.getCurrentTime();
	}

	@RolesAllowed("Tester")
	@Override public String getServerName() {
		return test.getServerName();
	}

	@RolesAllowed("Tester")
	@Override public long getServerHash() {
		return test.getServerHash();
	}

	@RolesAllowed("Tester")
	@Override public String getAppName() {
		return String.format("%1$s[%2$s]", appName(), test.getAppName());
	}

	private String appName() {
		try {
			return (String)(new InitialContext()).lookup("java:app/AppName");
		}
		catch (NamingException ex) {
			throw new EJBException(ex);
		}
	}

	@RolesAllowed("Tester")
	@Override public String getNodeName() {
		return String.format("%1$s[%2$s]", System.getProperty("jboss.node.name"), test.getNodeName());
	}
}
