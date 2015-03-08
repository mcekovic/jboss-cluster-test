package org.strangeforest.test.jboss.ejb;

import javax.ejb.*;

@Remote
public interface RemoteCalculator extends NodeAware {

	int get();
	void set(int i);
	int add(int i);
	int subtract(int i);
}
