package com.ecommerce.user.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseModel {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}
