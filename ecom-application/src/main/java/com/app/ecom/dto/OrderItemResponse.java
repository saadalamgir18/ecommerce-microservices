package com.app.ecom.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal price,
        BigDecimal subTotal
) {
}
