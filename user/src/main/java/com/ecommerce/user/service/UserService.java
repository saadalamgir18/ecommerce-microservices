package com.ecommerce.user.service;


import com.ecommerce.user.dto.UserAddress;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());

    }

    public UserResponse saveUser(UserRequest user) {
        User user1 = mapToUser(user);
        return  mapToUserResponse(userRepository.save(user1));
    }

    public UserResponse updateUser(UserRequest user, String  id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.firstName());
            existingUser.setLastName(user.lastName());
            existingUser.setEmail(user.email());
            existingUser.setAddress(user.address());
            existingUser.setPhoneNumber(user.phoneNumber());

            return mapToUserResponse(userRepository.save(existingUser));


        }
        return null;

    }

    public UserResponse findUserById(String id) {
         Optional<User> user = userRepository.findById(id);
        System.out.println("user: " + user.isPresent() );
        return user.map(this::mapToUserResponse).orElse(null);
    }

    User mapToUser(UserRequest user){
        Address address =null;
        if ((user.address() != null)){
             address = user.address();

        }
        return User.builder()
                .address(address)
                .email(user.email())
                .phoneNumber(user.phoneNumber())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .phoneNumber(user.phoneNumber())
                .build();

    }


    public UserResponse mapToUserResponse(User user) {
        System.out.println("user roles: " +user.getRole());
        UserAddress userAddress = null;
        if (user.getAddress() != null) {
            userAddress = UserAddress.builder()
                    .city(user.getAddress().getCity())
                    .country(user.getAddress().getCountry())
                    .state(user.getAddress().getState())
                    .zipCode(user.getAddress().getZipCode())
                    .street(user.getAddress().getStreet())
                    .build();
        }

        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userAddress(userAddress)
                .build();

    }
}
