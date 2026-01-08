package com.contactapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class ContactDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$|^$", message = "Phone number should be valid (10-15 digits)")
    private String phone;

    private String company;

    private String jobTitle;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String country;

    private String notes;

    private String photoFileName;

    private String photoPath;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public ContactDTO() {}

    public ContactDTO(Long id, String firstName, String lastName, String email, String phone,
                      String company, String jobTitle, String address, String city, String state,
                      String zipCode, String country, String notes, String photoFileName,
                      String photoPath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.jobTitle = jobTitle;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.notes = notes;
        this.photoFileName = photoFileName;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static builder pattern
    public static ContactDTOBuilder builder() {
        return new ContactDTOBuilder();
    }

    public static class ContactDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String company;
        private String jobTitle;
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private String country;
        private String notes;
        private String photoFileName;
        private String photoPath;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ContactDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ContactDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ContactDTOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ContactDTOBuilder company(String company) {
            this.company = company;
            return this;
        }

        public ContactDTOBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public ContactDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ContactDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ContactDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ContactDTOBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public ContactDTOBuilder country(String country) {
            this.country = country;
            return this;
        }

        public ContactDTOBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public ContactDTOBuilder photoFileName(String photoFileName) {
            this.photoFileName = photoFileName;
            return this;
        }

        public ContactDTOBuilder photoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public ContactDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ContactDTOBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ContactDTO build() {
            return new ContactDTO(id, firstName, lastName, email, phone, company, jobTitle,
                                address, city, state, zipCode, country, notes, photoFileName,
                                photoPath, createdAt, updatedAt);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
