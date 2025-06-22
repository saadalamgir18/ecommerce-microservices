package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(String email, String firstName, String lastName,
                           String phoneNumber, String id, UserRole role, UserAddress userAddress) {

}
