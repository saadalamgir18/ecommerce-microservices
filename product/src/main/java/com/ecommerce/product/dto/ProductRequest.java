package com.ecommerce.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(

         String name,
         String description,
         BigDecimal price,
         String category,
         Integer stockQuantity,

         String imageUrl
) {
}
