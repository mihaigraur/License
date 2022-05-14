package com.license.license;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model){
        model.addAttribute( "user", new User() );

        return "register";
    }

    @PostMapping("/process_register")
    public String doRegistration(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);

        return "success_register";
    }

    @GetMapping("/list_of_users")
    public String userList(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        
        return "userList";
    }

}
