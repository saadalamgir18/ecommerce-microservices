package com.ecommerce.order.dto;

public record CartItemRequest(Long productId, Integer quantity) {
}
