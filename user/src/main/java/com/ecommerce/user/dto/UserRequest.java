package com.ecommerce.user.dto;

import com.ecommerce.user.model.Address;
import lombok.Builder;

@Builder
public record UserRequest(String email, String firstName, String lastName, String phoneNumber,
                          String id, Address address) {
}
