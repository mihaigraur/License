package com.license.license;

import org.springframework.stereotype.Service;

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
