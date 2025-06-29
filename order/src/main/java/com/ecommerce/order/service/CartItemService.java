package com.ecommerce.order.service;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    private final RestClient productRestClient;
    private final RestClient userRestClient;

    public boolean addToCart(CartItemRequest cartItem, Long userId) {
        ProductResponse product = productRestClient.get()
                .uri("/api/products/{productId}", cartItem.productId())
                .retrieve()
                .body(ProductResponse.class);
        if (product == null ) return false;
        if (product.stockQuantity() < cartItem.quantity()) return false;

        UserResponse userResponse = userRestClient.get()
                .uri("/api/users/{userID}", userId)
                .retrieve()
                .body(UserResponse.class);

        if (userResponse == null) return false;

        CartItem existingItem = cartItemRepository.findByUserIdAndProductId(userId, cartItem.productId());

        int quantity = cartItem.quantity();
        BigDecimal price = product.price().multiply(BigDecimal.valueOf(quantity));

        if (existingItem != null) {
            int newQty = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQty);
            existingItem.setPrice(product.price().multiply(BigDecimal.valueOf(newQty)));

//            existingItem.setPrice(BigDecimal.valueOf(100000));

            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .productId(1L)
                    .quantity(quantity)
                    .price(price)
                    .price(BigDecimal.valueOf(100000))
                    .userId(userId)
                    .build();
            cartItemRepository.save(newItem);
        }

        return true;
    }

    @Transactional
    public boolean deleteItemFromCart(Long productId, Long userId) {

        CartItem cartItem =  cartItemRepository.findByUserIdAndProductId(userId, productId);

       if (cartItem != null) {

           cartItemRepository.deleteByUserIdAndProductId(userId, productId);
           return true;
       }

       return false;

    }


    public List<CartItemResponse> findAll(Long userId) {

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        return  cartItems.stream().map(cartItems1-> CartItemResponse.builder()
                .id(cartItems1.getId())
                .price(cartItems1.getPrice())
                .quantity(cartItems1.getQuantity())
                .productId(cartItems1.getProductId())
                .build()).collect(Collectors.toList());

    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
