package org.strangeforest.test.jboss.ejb.it;

import java.time.*;
import javax.naming.*;

import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.spec.*;
import org.junit.*;
import org.junit.runner.*;
import org.strangeforest.test.jboss.ejb.*;

import static org.assertj.core.api.Assertions.*;
import static org.strangeforest.test.jboss.client.JNDIUtil.*;

@RunWith(Arquillian.class)
public class RemoteTestAsClientIT {

	public static final String REMOTE_TEST_NAME = "jboss-test-ear/jboss-test-ejb//RemoteTestBean!org.strangeforest.test.jboss.ejb.RemoteTest";

	@Deployment
	public static Archive<?> createDeployment() {
		JavaArchive ejbArchive = ShrinkWrap.create(JavaArchive.class, "jboss-test-ejb.jar")
			.addPackage(RemoteTest.class.getPackage());
		return ShrinkWrap.create(EnterpriseArchive.class, "jboss-test-ear.ear")
			.addAsModule(ejbArchive);
	}

	@Test @RunAsClient
	public void remoteTestIsQueried() throws NamingException {
		InitialContext ctx = getEJBClientInitialContext();
		RemoteTest remoteTest = (RemoteTest)ctx.lookup("ejb:" + REMOTE_TEST_NAME);
		assertThat(remoteTest.getCurrentTime()).isLessThanOrEqualTo(Instant.now());
		assertThat(remoteTest.getNodeName()).isNotEmpty();
	}

	@Test @RunAsClient
	public void jndiTreeIsTraversed() throws NamingException {
		InitialContext ctx2 = getRemoteNamingInitialContext("4447");
		traverse(ctx2, "java:");
	}
}
