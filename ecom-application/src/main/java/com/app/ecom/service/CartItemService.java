package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(CartItemRequest cartItem, String userId) {
        Product product = productRepository.findById(cartItem.productId())
                .filter(p -> p.getStockQuantity() >= cartItem.quantity())
                .orElse(null);

        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);

        if (product == null || user == null) return false;

        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);

        int quantity = cartItem.quantity();
        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        if (existingItem != null) {
            int newQty = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQty);
            existingItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQty)));
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .price(price)
                    .user(user)
                    .build();
            cartItemRepository.save(newItem);
        }

        return true;
    }

    @Transactional
    public boolean deleteItemFromCart(Long productId, String userId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

       if (productOpt.isPresent() && userOpt.isPresent()) {

           cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
           return true;
       }

       return false;

    }


    public List<CartItemResponse> findAll(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user == null) return null;
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        return  cartItems.stream().map(cartItems1-> CartItemResponse.builder()
                .id(cartItems1.getId())
                .price(cartItems1.getPrice())
                .quantity(cartItems1.getQuantity())
                .product(cartItems1.getProduct())
                .build()).collect(Collectors.toList());

    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
