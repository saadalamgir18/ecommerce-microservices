package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;

import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartItemService;
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

        if (!cartItemService.addToCart(cartItem, userId)){
            return ResponseEntity.badRequest().body("Product out of stock or User does not exist or Product not found");

        };

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void>  deleteCartItem(@PathVariable Long productId,
                                                @RequestHeader("X-User-ID") String userId) {
        boolean deleted  = cartItemService.deleteItemFromCart(productId, userId);

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestHeader("X-User-ID") String userId) {
        List<CartItemResponse> cartItems = cartItemService.findAll(userId);
        return ResponseEntity.ok(cartItems);
    }

}
