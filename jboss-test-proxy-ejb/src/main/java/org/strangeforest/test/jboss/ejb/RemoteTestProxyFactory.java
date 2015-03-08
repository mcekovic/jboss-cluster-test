package org.strangeforest.test.jboss.ejb;

import java.util.*;
import javax.ejb.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import javax.naming.*;

public class RemoteTestProxyFactory {

	public static final String REMOTE_TEST_DELEGATE = "RemoteTestDelegate";
	public static final String REMOTE_TEST_NAME = "jboss-test-ear/jboss-test-ejb//RemoteTestBean!org.strangeforest.test.jboss.ejb.RemoteTest";

	@Produces @Named(REMOTE_TEST_DELEGATE)
	public RemoteTest createRemoteTestDelegate() {
		return (RemoteTest)lookup("ejb:" + REMOTE_TEST_NAME);
	}

	private Object lookup(String jndiName) {
		try {
			Properties props = new Properties();
			props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

			Context context = new InitialContext(props);
			return context.lookup(jndiName);
		}
		catch (NamingException ex) {
			throw new EJBException(ex);
		}
	}
}
