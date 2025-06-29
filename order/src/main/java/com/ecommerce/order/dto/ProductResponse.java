package com.ecommerce.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Integer stockQuantity,

        String imageUrl,
        Boolean active
) {
}
