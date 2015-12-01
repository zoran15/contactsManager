package zoran.contacts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import zoran.contacts.model.Contact;
import zoran.contacts.model.ContactNotFoundException;

@Component
public class ContactsRepository {

	private static final int INITIAL_ID = 1;
	private ConcurrentHashMap<Long, Contact> contacts = new ConcurrentHashMap<>();
	private AtomicLong contactIdCounter = new AtomicLong(INITIAL_ID);

	public Contact createAndAddContact(String name, String email, String phoneNumber) {
		Contact contact = new Contact(contactIdCounter.getAndIncrement(), name, email, phoneNumber);
		contacts.put(contact.getId(), contact);
		return contact;
	}

	public Contact getContact(long id) {
		if (!contacts.containsKey(id)) {
			throw new ContactNotFoundException();
		}

		return contacts.get(id);
	}

	public List<Contact> getAllContacts() {
		return contacts.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
	}

	public void deleteContact(long id) {
		if (!contacts.containsKey(id)) {
			throw new ContactNotFoundException();
		}

		contacts.remove(id);
	}

	public Contact updateContact(long id, String name, String email, String phoneNumber) {
		if (!contacts.containsKey(id)) {
			throw new ContactNotFoundException();
		}

		Contact contact = contacts.get(id);
		contact.setName(name);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);

		return contact;
	}

}
