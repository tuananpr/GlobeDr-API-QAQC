package com.apis.globedr.model.general;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Address {
    private String address;
    private String country;
    private String city;
    private String district;
    private String ward;

    public Address(String address){
        this.address = address;
    }
}
