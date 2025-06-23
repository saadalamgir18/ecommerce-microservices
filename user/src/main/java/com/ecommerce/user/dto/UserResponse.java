package com.ecommerce.user.dto;

import com.ecommerce.user.model.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(String email, String firstName, String lastName,
                           String phoneNumber, String id, UserRole role, UserAddress userAddress) {

}
