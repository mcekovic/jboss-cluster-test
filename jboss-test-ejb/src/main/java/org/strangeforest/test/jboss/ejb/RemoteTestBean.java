package org.strangeforest.test.jboss.ejb;

import java.time.*;
import javax.annotation.security.*;
import javax.ejb.*;
import javax.naming.*;

import com.google.common.base.*;
import com.google.common.hash.*;

@Stateless @Remote(RemoteTest.class)
public class RemoteTestBean implements RemoteTest {

	@RolesAllowed("Tester")
	@Override public Instant getCurrentTime() {
		return Instant.now();
	}

	@RolesAllowed("Tester")
	@Override public String getServerName() {
		return System.getProperty("jboss.server.name");
	}

	@RolesAllowed("Tester")
	@Override public long getServerHash() {
		HashFunction hf = Hashing.md5();
		return hf.newHasher()
			.putString(getServerName(), Charsets.UTF_8)
			.putString(getNodeName(), Charsets.UTF_8)
			.hash().padToLong();
	}

	@RolesAllowed("Tester")
	@Override public String getAppName() {
		try {
			return (String)(new InitialContext()).lookup("java:app/AppName");
		}
		catch (NamingException ex) {
			throw new EJBException(ex);
		}
	}

	@RolesAllowed("Tester")
	@Override public String getNodeName() {
		return System.getProperty("jboss.node.name");
	}
}
