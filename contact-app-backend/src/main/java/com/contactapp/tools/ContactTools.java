package com.contactapp.tools;

import com.contactapp.dto.ContactDTO;
import com.contactapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ContactTools {
    private static final Logger log = LoggerFactory.getLogger(ContactTools.class);
    private final ContactService contactService;

    public ContactTools(ContactService contactService) {
        this.contactService = contactService;
    }

    @Tool(description = "Get a contact by their unique ID")
    public ContactDTO getContact(Long id) {
        log.info("Getting contact: {}", id);
        ContactDTO contact = contactService.getContact(id);
        log.info("Contact: {}", contact);
        return contact;
    }

    @Tool(description = "Get all contacts with pagination. Returns page results with specified page and size")
    public Page<ContactDTO> getAllContacts(int page, int size) {
        log.info("Getting all contacts - page: {}, size: {}", page, size);
        Page<ContactDTO> contacts = contactService.getAllContacts(page, size);
        log.info("Retrieved {} contacts", contacts.getNumberOfElements());
        return contacts;
    }

    @Tool(description = "Search contacts by name, email, phone, company, or other fields")
    public Page<ContactDTO> searchContacts(String searchTerm, int page, int size) {
        log.info("Searching contacts with term: '{}' - page: {}, size: {}", searchTerm, page, size);
        Page<ContactDTO> results = contactService.searchContacts(searchTerm, page, size);
        log.info("Found {} matching contacts", results.getNumberOfElements());
        return results;
    }

    @Tool(description = "Delete a contact by their unique ID")
    public void deleteContact(Long id) {
        log.info("Deleting contact: {}", id);
        try {
            contactService.deleteContact(id);
            log.info("Contact deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting contact: {}", id, e);
            throw new RuntimeException("Failed to delete contact: " + e.getMessage(), e);
        }
    }
}
