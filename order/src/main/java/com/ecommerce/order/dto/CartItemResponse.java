package com.ecommerce.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemResponse(
        Long id,
        Long productId,
         Integer quantity,
         BigDecimal price
) {
}
