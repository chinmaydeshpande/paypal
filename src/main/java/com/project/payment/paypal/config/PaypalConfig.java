package com.project.payment.paypal.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {
    @Value("${paypal.mode}")
    private String mode;
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Bean
    public Map<String,String> paypalSdkConfig(){
        Map<String,String> paypalConfigMap = new HashMap<>();
        paypalConfigMap.put("mode",mode);
        return paypalConfigMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext(){
        APIContext apiContext = new APIContext();
        try {
            apiContext = new APIContext(oAuthTokenCredential().getAccessToken());
            apiContext.setConfigurationMap(paypalSdkConfig());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return apiContext;
    }
}
