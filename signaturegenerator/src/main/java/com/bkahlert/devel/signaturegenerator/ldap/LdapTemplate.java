package com.bkahlert.devel.signaturegenerator.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.ContextSource;

import com.bkahlert.devel.signaturegenerator.model.IConfig;

public class LdapTemplate extends org.springframework.ldap.core.LdapTemplate {

	public LdapTemplate(IConfig config) {
		super(new ContextSource() {

			public DirContext getReadWriteContext() throws NamingException {
				// TODO Auto-generated method stub
				return null;
			}

			public DirContext getReadOnlyContext() throws NamingException {
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY,
						"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.PROVIDER_URL,
						"ldap://localhost:389/dc=example,dc=com");

				DirContext ctx;
				try {
					ctx = new InitialDirContext(env);
				} catch (javax.naming.NamingException e) {
					throw new RuntimeException(e);
				}
				return ctx;
			}

			public DirContext getContext(String arg0, String arg1)
					throws NamingException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
