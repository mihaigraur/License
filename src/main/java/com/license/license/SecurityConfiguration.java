package com.license.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{ 
    //mostenim clasa asta ca sa avem la dispozitie comportamentul deafult al spring security

    @Bean  //folosind Bean ne folosim de crearea automata a instanteiprin autowired ca sa ajungem la clasa CreateUserDetailsService
    public UserDetailsService userDetailsService(){
        return new CreateUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder cryptoPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(cryptoPasswordEncoder());

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/oauth2/**").permitAll()
            .antMatchers("/dashboard").authenticated()  //se pune asta ca sa protejeze /dashboard ca numai atunci cand utilizatorul este autentifica sa poata accesa pagina respectiva
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            .loginPage("/login")    //practic am suprascris form ul de logn predefinit in unul custom 
            .permitAll()
            .and()
            .oauth2Login().loginPage("/login").userInfoEndpoint().userService(oAuth2UserService).and()
            // .successHandler(successLoginOAuth2)
            .and()
            .logout().logoutSuccessUrl("/").permitAll();   //ala "/" te duce pe pagina index, principala
    }

    @Autowired
    private GoogleOAuth2UserService oAuth2UserService;

}