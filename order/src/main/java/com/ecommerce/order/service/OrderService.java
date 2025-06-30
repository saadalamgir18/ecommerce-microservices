package com.ecommerce.order.service;


import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.dto.OrderItemResponse;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;

    @Transactional
    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItemResponse> cartItems = cartItemService.findAll(userId);
        if (cartItems.isEmpty()) return Optional.empty();
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()) return Optional.empty();
//
//        User user = userOpt.get();
        BigDecimal totalPrice = calculateTotalPrice(cartItems);
        Order order = buildOrder(userId, totalPrice, cartItems);

        Order savedOrder = orderRepository.save(order);
        cartItemService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private BigDecimal calculateTotalPrice(List<CartItemResponse> cartItems) {
        return cartItems.stream()
                .map(CartItemResponse::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order buildOrder(String userId, BigDecimal totalPrice, List<CartItemResponse> cartItems) {
        Order order = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .totalAmount(totalPrice)
                .userId(userId)
                .build();

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> OrderItem.builder()
                        .productId(item.productId())
                        .quantity(item.quantity())
                        .price(item.price())
                        .order(order)
                        .build())
                .toList();

        order.setOrderItems(orderItems);
        return order;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .productId(item.getProductId())
                        .subTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(String.valueOf(order.getId()))
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .items(itemResponses)
                .build();
    }
}
