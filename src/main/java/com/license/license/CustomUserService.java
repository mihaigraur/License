package com.license.license;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserService {
    private final UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Transactional
    // public Optional<User> findUserByEmail(String email) {
    //     return userRepository.findByEmail(email);
    // }
}
