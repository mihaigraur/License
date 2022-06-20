package com.license.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CreateUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        return new CreateUserDetails(user);
    } 

    // public User getUserByEmail(String email){
    //     User user = repository.findByEmail(email);
    //     if(user == null){
    //         throw new UsernameNotFoundException("User not found!");
    //     }
    //     return new User();
    // }

    // public CreateUserDetailsService() {
    // }

    // public CreateUserDetailsService(UserRepository repository) {
    //     this.repository = repository;
    // }
}
