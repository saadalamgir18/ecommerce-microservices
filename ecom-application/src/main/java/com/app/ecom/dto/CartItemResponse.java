package com.app.ecom.dto;

import com.app.ecom.model.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemResponse(
        Long id,
         Product product,
         Integer quantity,
         BigDecimal price
) {
}
