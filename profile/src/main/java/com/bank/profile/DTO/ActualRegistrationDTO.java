package com.bank.profile.DTO;

import lombok.Data;

@Data
public class ActualRegistrationDTO  {
    private Long id;
    private String country;
    private String region;
    private String city;
    private String district;
    private String locality;
    private String street;
    private String houseNumber;
    private String houseBlock;
    private String flatNumber;
    private int index;
}
