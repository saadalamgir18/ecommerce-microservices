package com.ecommerce.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderCreate(
        String userId,
        String orderId,
        String OrderStatus,
        List<OrderItemResponse> orderItems,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
