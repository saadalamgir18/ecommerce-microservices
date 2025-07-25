package com.ecommerce.user.dto;

import lombok.Builder;

@Builder
public record UserAddress( String street, String city, String state, String country,
                           String zipCode) {
}
