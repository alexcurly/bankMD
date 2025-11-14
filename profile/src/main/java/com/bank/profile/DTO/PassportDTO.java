package com.bank.profile.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class PassportDTO {
    private Long id;
    private int series;
    private Long number;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private Date birthDate;
    private String birthPlace;
    private String issuedBy;
    private Date dateOfIssue;
    private int divisionCode;
    private Date expirationDate;
    private Long registrationPassport;
}