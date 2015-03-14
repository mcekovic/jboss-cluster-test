package org.strangeforest.test.jboss.ejb.it;

import java.time.*;
import javax.ejb.*;

import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.asset.*;
import org.jboss.shrinkwrap.api.spec.*;
import org.junit.*;
import org.junit.runner.*;
import org.strangeforest.test.jboss.ejb.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(Arquillian.class)
public class RemoteTestIT {

	@EJB private RemoteTest remoteTest;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
			.addPackage(RemoteTest.class.getPackage())
			.addClass(RemoteTestIT.class)
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
			.addAsManifestResource("jboss-ejb-client.xml");
	}

	@Test
	public void remoteTestIsQueried() {
		assertThat(remoteTest.getCurrentTime()).isLessThanOrEqualTo(Instant.now());
		assertThat(remoteTest.getNodeName()).isNotEmpty();
	}
}
