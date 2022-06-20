package com.license.license;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleOAuth2User implements OAuth2User{
    private OAuth2User oAuth2User;

    public GoogleOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }//to provide an object, represents users

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }//most important method that returns the name of users

    public String getFullName(){
        return oAuth2User.getAttribute("name");
    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
    
}
