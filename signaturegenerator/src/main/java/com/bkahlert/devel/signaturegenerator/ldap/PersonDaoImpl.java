package com.bkahlert.devel.signaturegenerator.ldap;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class PersonDaoImpl implements PersonDao {
	private LdapTemplate ldapTemplate;

	public PersonDaoImpl(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	private class PersonAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
				throws NamingException {
			Person person = new Person();
			person.setFullName((String) attrs.get("cn").get());
			person.setLastName((String) attrs.get("sn").get());
			person.setDescription((String) attrs.get("description").get());
			return person;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Person> getAllPersons() {
		return (List<Person>) this.ldapTemplate.search("",
				"(objectclass=person)", new PersonAttributesMapper());
	}
}