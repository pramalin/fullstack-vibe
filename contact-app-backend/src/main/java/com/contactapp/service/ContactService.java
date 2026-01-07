package com.contactapp.service;

import com.contactapp.dto.ContactDTO;
import com.contactapp.model.Contact;
import com.contactapp.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private static final String UPLOAD_DIR = "uploads/photos";

    public ContactDTO createContact(ContactDTO contactDTO, MultipartFile photoFile) throws IOException {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactDTO, contact, "id", "createdAt", "updatedAt", "photoPath", "photoFileName");

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoInfo = savePhotoFile(photoFile);
            String[] parts = photoInfo.split("\\|");
            contact.setPhotoFileName(parts[0]);
            contact.setPhotoPath(parts[1]);
        }

        Contact savedContact = contactRepository.save(contact);
        return convertToDTO(savedContact);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO, MultipartFile photoFile) throws IOException {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        BeanUtils.copyProperties(contactDTO, contact, "id", "createdAt", "updatedAt", "photoPath", "photoFileName");

        if (photoFile != null && !photoFile.isEmpty()) {
            // Delete old photo if exists
            if (contact.getPhotoPath() != null) {
                deletePhotoFile(contact.getPhotoPath());
            }
            String photoInfo = savePhotoFile(photoFile);
            String[] parts = photoInfo.split("\\|");
            contact.setPhotoFileName(parts[0]);
            contact.setPhotoPath(parts[1]);
        }

        Contact updatedContact = contactRepository.save(contact);
        return convertToDTO(updatedContact);
    }

    public ContactDTO getContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return convertToDTO(contact);
    }

    public Page<ContactDTO> getAllContacts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepository.findAllOrderByName(pageable)
                .map(this::convertToDTO);
    }

    public Page<ContactDTO> searchContacts(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllContacts(page, size);
        }
        return contactRepository.searchContacts(searchTerm.trim(), pageable)
                .map(this::convertToDTO);
    }

    public void deleteContact(Long id) throws IOException {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        // Delete photo file if exists
        if (contact.getPhotoPath() != null) {
            deletePhotoFile(contact.getPhotoPath());
        }

        contactRepository.deleteById(id);
    }

    private String savePhotoFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        return fileName + "|" + filePath.toString();
    }

    private void deletePhotoFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    private ContactDTO convertToDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .company(contact.getCompany())
                .jobTitle(contact.getJobTitle())
                .address(contact.getAddress())
                .city(contact.getCity())
                .state(contact.getState())
                .zipCode(contact.getZipCode())
                .country(contact.getCountry())
                .notes(contact.getNotes())
                .photoFileName(contact.getPhotoFileName())
                .photoPath(contact.getPhotoPath())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }
}
