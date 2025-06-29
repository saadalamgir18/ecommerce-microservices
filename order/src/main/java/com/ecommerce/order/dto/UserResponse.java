package com.ecommerce.order.dto;

import lombok.Builder;

@Builder
public record UserResponse(String email, String firstName, String lastName,
                           String phoneNumber, String id
//                           UserRole role,
//                           UserAddress userAddress
) {

}
