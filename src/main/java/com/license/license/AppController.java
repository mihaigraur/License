package com.license.license;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplateMethod(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

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
    public String doRegistration(User user, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String gReCaptchaResponse = request.getParameter("g-recaptcha-response");

        if(!verifingReCaptcha(gReCaptchaResponse)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);

        return "success_register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String userList(Model model){
        return "dashboard";
    }

    private Boolean verifingReCaptcha(String gReCaptchaResponse) {
        String urlReCaptcha = "https://www.google.com/recaptcha/api/siteverify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("secret", "6LcS4-sfAAAAACFS69Uu0gMTPBN0qvYZquheBbdM");
        multiValueMap.add("response", gReCaptchaResponse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);

        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(urlReCaptcha, request, RecaptchaResponse.class);

        System.out.println("Success " + recaptchaResponse.isSuccess());
        System.out.println("Hostname " + recaptchaResponse.getHostname());
        System.out.println("ChallangeTS " + recaptchaResponse.getChallenge_ts());

        return recaptchaResponse.isSuccess();
    }
}