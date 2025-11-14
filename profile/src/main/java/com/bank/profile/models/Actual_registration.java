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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="actual_registration" , schema="profile")
public class Actual_registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="country")
    private String country;

    @Column(name="region")
    @Nullable
    private String region;

    @Column(name="city")
    @Nullable
    private String city;

    @Column(name="district")
    @Nullable
    private String district;

    @Column(name="locality")
    @Nullable
    private String locality;

    @Column(name="street")
    @Nullable
    private String street;

    @Column(name="house_number")
    @Nullable
    private String houseNumber;

    @Column(name="house_block")
    @Nullable
    private String houseBlock;

    @Column(name="flat_number")
    @Nullable
    private String flatNumber;

    @Column(name="index")
    private int index;

    @OneToOne(mappedBy = "actual_registration",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Profile profileList;
}
