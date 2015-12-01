package zoran.contacts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zoran.contacts.model.Contact;
import zoran.contacts.model.Contacts;

@Controller
@RequestMapping("/api/contacts")
public class ContactsController {

	@Autowired
	private ContactsRepository contactsRepository;

	@PostConstruct
	public void setUpDummyData() {
		contactsRepository.createAndAddContact("Zoran Lovric", "zoran.lovric@gmail.com", "858-231-1719");
		contactsRepository.createAndAddContact("Andrew Hendrickson", "Andrew_Hendrickson@intuit.com", " 650-944-3758");
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Contacts getAllContacts() {
		return buildAllContacts();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Contact getContact(@PathVariable(value = "id") long id) {
		return contactsRepository.getContact(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Contacts addContact(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {

		contactsRepository.createAndAddContact(name, email, phoneNumber);
		return buildAllContacts();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public @ResponseBody Contact updateContact(@PathVariable(value = "id") long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber) {

		return contactsRepository.updateContact(id, name, email, phoneNumber);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody Contacts deleteContact(@PathVariable(value = "id") long id) {
		contactsRepository.deleteContact(id);
		return buildAllContacts();
	}

	private Contacts buildAllContacts() {
		return new Contacts(contactsRepository.getAllContacts());
	}
}