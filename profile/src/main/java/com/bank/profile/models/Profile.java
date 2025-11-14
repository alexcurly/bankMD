package com.bank.profile.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="profile" , schema="profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="phone_number")
    private Long phoneNumber;

    @Column(name="email")
    @Nullable
    private String email;

    @Column(name="name_on_card")
    @Nullable
    private String nameOnCard;

    @Column(name="inn", length = 8, unique = true)
    @Nullable
    private int inn;

    @Column(name="snils", unique = true)
    @Nullable
    private int snils;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "actual_registration_id",
            insertable = false,
            updatable = false,
            nullable = true)
    private Actual_registration actual_registration;
    @Column(name="actual_registration_id")
    private Long actualRegistrationProfile;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id",
            insertable = false,
            updatable = false,
            nullable = false)
    private Passport passport;
    @Column(name="passport_id")
    private Long passportProfile;

    @OneToMany(mappedBy = "profile",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Account_details_id> account_details_idList;
}
