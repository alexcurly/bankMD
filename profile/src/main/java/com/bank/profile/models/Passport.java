package com.bank.profile.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="passport" , schema="profile")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="series")
    private int series;

    @Column(name="number")
    private Long number;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="gender")
    private String gender;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="birth_place")
    private String birthPlace;

    @Column(name="issued_by")
    private String issuedBy;

    @Column(name="date_of_issue")
    private Date dateOfIssue;

    @Column(name="division_code")
    private int divisionCode;

    @Column(name="expiration_date")
    private Date expirationDate;

    @OneToOne(mappedBy = "passport",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_id",
            insertable = false,
            updatable = false,
            nullable = false)
    private Registration registration;

    @Column(name="registration_id")
    private Long registrationPassport;
}
