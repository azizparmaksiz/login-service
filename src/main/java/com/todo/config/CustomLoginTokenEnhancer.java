package com.todo.config;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;


public class CustomLoginTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    	// TODO to cast to your desired class that class must be extended from org.springframework.security.core.userdetails.User
        // and you may return that class object on CustomerLoginUserDetailsService.loadUser() method
        User user = (User) authentication.getPrincipal();


        Map<String, Object> additionalInfo = new HashMap<>();
//        additionalInfo.put("customerType", user.getCustomerType());
//        additionalInfo.put("platform", user.getPlatform());
//        additionalInfo.put("userId", user.getCustomerId());
//        additionalInfo.put("uiLangCode", user.getUiLangCode());
//        additionalInfo.put("currencyCode", user.getCurrencyCode());
        additionalInfo.put("role", user.getAuthorities());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
    

}
