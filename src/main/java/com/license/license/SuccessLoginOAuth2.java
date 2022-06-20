package com.license.license;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component //aici avem eroare daca nu punem asta
public class SuccessLoginOAuth2 extends SimpleUrlAuthenticationSuccessHandler{
    // @Autowired
    // private UserService userService;

    // @Autowired
    // private CreateUserDetailsService createUserDetailsService;

    // @Bean
    // public CreateUserDetailsService createUserDetailsService2(){
    //     return new CreateUserDetailsService();
    // }

    // @Override
    // public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    //     GoogleOAuth2User googleOAuth2User = (GoogleOAuth2User) authentication.getPrincipal();
    //     String email = googleOAuth2User.getEmail();
    //     String firstName = googleOAuth2User.getName();

    //     User user = createUserDetailsService.getUserByEmail(email);
    //     if(user == null){
    //         userService.processDatabaseAfterOAuth2(email, firstName, AuthenticationProducer.GOOGLE);
    //     }else{

    //     }
    //     super.onAuthenticationSuccess(request, response, authentication);
    // }
    
}
