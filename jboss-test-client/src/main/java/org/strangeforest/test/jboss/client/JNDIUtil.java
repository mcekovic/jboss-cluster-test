package org.strangeforest.test.jboss.client;

import java.util.*;
import javax.naming.*;

import static com.google.common.base.Strings.*;

public class JNDIUtil {

	public static InitialContext getEJBClientInitialContext() throws NamingException {
		Properties props = new Properties();
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		return new InitialContext(props);
	}

	public static InitialContext getRemoteNamingInitialContext(String port) throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL,"remote://localhost:" + port);
		props.put(Context.SECURITY_PRINCIPAL, "test");
		props.put(Context.SECURITY_CREDENTIALS, "test123.");
		return new InitialContext(props);
	}

	public static InitialContext getRemoteNamingEJBClientInitialContext(String port) throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL,"remote://localhost:" + port);
		props.put(Context.SECURITY_PRINCIPAL, "test");
		props.put(Context.SECURITY_CREDENTIALS, "test123.");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		return new InitialContext(props);
	}

	public static void traverse(Context ctx, String path) throws NamingException {
		System.out.println();
		if (isNullOrEmpty(path))
			traverse(ctx, null, 0);
		else {
			System.out.println(path);
			traverse(ctx, path, 1);
		}
	}

	private static void traverse(Context ctx, String path, int depth) throws NamingException {
		for (NamingEnumeration<Binding> e = ctx.listBindings(path); e.hasMore() && depth < 10; ) {
			Binding next = e.next();
			String name = next.getName();
			System.out.print(repeat(" ", depth * 2));
			System.out.printf("%1$s[%2$s]%n", name, next.getClassName());
			Object obj = next.getObject();
			if (obj instanceof Context)
				traverse((Context)obj, isNullOrEmpty(path) ? name : path + "/" + name, depth+1);
		}
	}
}
