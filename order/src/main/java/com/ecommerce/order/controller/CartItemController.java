package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<?> createCartItem(@RequestBody CartItemRequest cartItem, @RequestHeader("X-User-ID")  String userId) {

        if (!cartItemService.addToCart(cartItem, Long.valueOf(userId))){
            return ResponseEntity.badRequest().body("Product out of stock or User does not exist or Product not found");

        };

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void>  deleteCartItem(@PathVariable Long productId,
                                                @RequestHeader("X-User-ID") String userId) {
        boolean deleted  = cartItemService.deleteItemFromCart(productId, Long.valueOf(userId));

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestHeader("X-User-ID") String userId) {
        List<CartItemResponse> cartItems = cartItemService.findAll(Long.valueOf(userId));
        return ResponseEntity.ok(cartItems);
    }

}
