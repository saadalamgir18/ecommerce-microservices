package com.app.ecom.service;

import com.app.ecom.dto.UserAddress;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
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

    public UserResponse updateUser(UserRequest user, Long id) {
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

    public UserResponse findUserById(Long id) {
         Optional<User> user = userRepository.findById(id);
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
