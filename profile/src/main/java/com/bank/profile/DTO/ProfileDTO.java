package com.bank.profile.DTO;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ProfileDTO {
    private Long id;
    private Long phoneNumber;
    @Email
//    @NotBlank
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private Long actualRegistrationProfile;
    private Long passportProfile;
}