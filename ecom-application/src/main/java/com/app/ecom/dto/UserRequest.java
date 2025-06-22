package com.app.ecom.dto;

import com.app.ecom.model.Address;
import com.app.ecom.model.UserRole;
import lombok.Builder;

@Builder
public record UserRequest(String email, String firstName, String lastName, String phoneNumber,
                          String id, Address address) {
}
