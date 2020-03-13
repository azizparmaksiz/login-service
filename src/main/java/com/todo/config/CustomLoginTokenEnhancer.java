package com.todo.config;


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;


public class CustomLoginTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//    	User user = (User) authentication.getPrincipal();


        Map<String, Object> additionalInfo = new HashMap<>();
//        additionalInfo.put("customerType", user.getCustomerType());
//        additionalInfo.put("platform", user.getPlatform());
//        additionalInfo.put("userId", user.getCustomerId());
//        additionalInfo.put("uiLangCode", user.getUiLangCode());
//        additionalInfo.put("currencyCode", user.getCurrencyCode());
//        additionalInfo.put("userStatus", user.getUserStatus());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
    

}