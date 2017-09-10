package com.money.api.config.token;

import com.money.api.model.AppUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        AppUser appUser = (AppUser) oAuth2Authentication.getPrincipal();

        Map<String, Object> addfInfo = new HashMap<>();
        addfInfo.put("name", appUser.getUser().getName());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(addfInfo);
        return oAuth2AccessToken;
    }
}
