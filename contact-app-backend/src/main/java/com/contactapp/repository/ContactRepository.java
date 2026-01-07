package com.contactapp.repository;

import com.contactapp.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

    @Query("SELECT c FROM Contact c WHERE LOWER(CONCAT(c.firstName, ' ', c.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.company) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Contact> searchContacts(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT c FROM Contact c ORDER BY c.firstName ASC, c.lastName ASC")
    Page<Contact> findAllOrderByName(Pageable pageable);

    List<Contact> findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String firstName, String lastName);
}
