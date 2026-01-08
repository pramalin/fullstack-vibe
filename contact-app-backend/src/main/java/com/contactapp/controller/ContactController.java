package com.contactapp.controller;

import com.contactapp.dto.ContactDTO;
import com.contactapp.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, allowCredentials = "true")
public class ContactController {

    private final ContactService contactService;

    // Constructor injection (replacing Lombok @RequiredArgsConstructor)
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(
            @Valid @RequestPart("contact") ContactDTO contactDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) throws IOException {
        ContactDTO createdContact = contactService.createContact(contactDTO, photoFile);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ContactDTO>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        Page<ContactDTO> contacts;
        if (search != null && !search.isEmpty()) {
            contacts = contactService.searchContacts(search, page, size);
        } else {
            contacts = contactService.getAllContacts(page, size);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {
        ContactDTO contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(
            @PathVariable Long id,
            @Valid @RequestPart("contact") ContactDTO contactDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) throws IOException {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO, photoFile);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) throws IOException {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContactDTO>> searchContacts(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ContactDTO> contacts = contactService.searchContacts(searchTerm, page, size);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
