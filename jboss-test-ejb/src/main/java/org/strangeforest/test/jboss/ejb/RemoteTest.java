package org.strangeforest.test.jboss.ejb;

import java.time.*;
import javax.ejb.*;

@Remote
public interface RemoteTest extends NodeAware {

	Instant getCurrentTime();
	String getServerName();
	long getServerHash();
	String getAppName();
}
