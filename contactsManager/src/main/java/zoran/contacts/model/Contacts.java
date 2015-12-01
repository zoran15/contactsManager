package zoran.contacts.model;

import java.util.ArrayList;
import java.util.List;

public class Contacts {
	private List<Contact> contacts;
	private int contactsCount;

	public Contacts(List<Contact> contacts) {
		super();
		this.contacts = contacts != null ? contacts : new ArrayList<Contact>();
		this.contactsCount = contacts.size();
	}

	public Contacts(Contact contact) {
		this.contacts = new ArrayList<Contact>();
		this.contacts.add(contact);
		this.contactsCount = 1;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public int getContactsCount() {
		return contactsCount;
	}

	public void setContactsCount(int contactsCount) {
		this.contactsCount = contactsCount;
	}

}
