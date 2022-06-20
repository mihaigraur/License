package com.license.license;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Bean
    public RestTemplate restTemplateMethod(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

    // @InitBinder
    // public void initializationBinder(WebDataBinder webDataBinder){
    //     StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    //     webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    // } //asta ca daca nu completez nimic sa se ia ca null fieldu

    @GetMapping("")
    public String viewHomePage(){
        //prin asta luam practic sesiunea de logare sa detectam cum ca userul este logat
        //si verificam ca daca sesiunea nu exista, ne lasa sa intram la index
        //org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if(authentication instanceof AnonymousAuthenticationToken || authentication == null){
        //     return "index";
        // }
        // return "redirect:/dashboard";

        if(permanentLogin()){
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/register")
    public String viewRegisterPage(User user, Model model){
        model.addAttribute( "user", new User() );

        // org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if(authentication instanceof AnonymousAuthenticationToken || authentication == null){
        //     return "register";
        // }
        // return "redirect:/";

        if(permanentLogin()){
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegistration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException{
        
        String gReCaptchaResponse = request.getParameter("g-recaptcha-response");

        if(!verifingReCaptcha(gReCaptchaResponse)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        BCryptPasswordEncoder confirmPasswordEncoder = new BCryptPasswordEncoder();

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        String confirmPasswordEncoded = confirmPasswordEncoder.encode(user.getConfirmPassword());

        user.setPassword(passwordEncoded);
        user.setConfirmPassword(confirmPasswordEncoded);
        
        userService.registerUser(user);
        String urlSite = URLHelper.getUrlSite(request);
        userService.sendEmailToEnableAccount(user, urlSite);


        HashTable hashTable = new HashTable(100);
        hashTable.insert(user.getId(), user.getEmail());
        Boolean statusEmail = hashTable.search(user.getId(), user.getEmail());
        
        if(statusEmail){
            userRepository.save(user);
            return "login";
        }else{
            return "register";
        }
        
    }

    @GetMapping("/login")
    public String login(){
        // org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if(authentication instanceof AnonymousAuthenticationToken || authentication == null){
        //     return "login";
        // }
        // return "redirect:/dashboard";

        if(permanentLogin()){
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String userList(Model model){
        return "dashboard";
    }

    @GetMapping("/legit")
    public String legitActivateAccount(@Param("code") String code, Model model){
        boolean activated = userService.legit(code);

        String titlePage;
        if(activated == true){
            titlePage = "Activation Succeeded!";
        }else{
            titlePage = "Activation Failed";
        }

        model.addAttribute("titlePage", titlePage);

        if(activated == true){
            return "success_activation";
        }else{
            return "fail_activation";
        }
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

        System.out.println(gReCaptchaResponse);

        return recaptchaResponse.isSuccess();
    }

    public boolean permanentLogin(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken || authentication == null){
            return false;
        }
        return authentication.isAuthenticated();
    }
}