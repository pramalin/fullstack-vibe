package com.contactapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
