package com.ecommerce.order.dto;

import com.ecommerce.order.model.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponse(
        String id,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponse> items

) {
}
