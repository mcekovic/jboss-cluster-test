package org.strangeforest.test.jboss.ejb;

import javax.annotation.security.*;
import javax.ejb.*;

import org.jboss.ejb3.annotation.*;

@Stateful @Clustered @Remote(RemoteCalculator.class)
public class RemoteCalculatorBean implements RemoteCalculator {

	private int m;

	@RolesAllowed("Tester")
	@Override public int get() {
		return m;
	}

	@RolesAllowed("Calculator")
	@Override public void set(int i) {
		this.m = i;
	}

	@RolesAllowed("Calculator")
	@Override public int add(int i) {
		return m += i;
	}

	@RolesAllowed("Calculator")
	@Override public int subtract(int i) {
		return m -= i;
	}

	@RolesAllowed("Tester")
	@Override public String getNodeName() {
		return System.getProperty("jboss.node.name");
	}
}
