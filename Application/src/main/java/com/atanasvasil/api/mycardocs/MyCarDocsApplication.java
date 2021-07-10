package com.atanasvasil.api.mycardocs;

import com.atanasvasil.api.mycardocs.beans.ConfigurationBean;
import com.atanasvasil.api.mycardocs.beans.RandomBuilder;
import com.atanasvasil.api.mycardocs.security.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MyCarDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCarDocsApplication.class, args);
    }

    /**
     * @return password encoder object with the desired strength
     * @author Atanas Yordanov Arshinkov
     * @see org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public RandomBuilder randomBuilder() {
        return new RandomBuilder();
    }

    @Bean
    public ConfigurationBean configurationBean() {
        return new ConfigurationBean();
    }

    @Bean
    public SecurityExpressions securityExpressions() {
        return new SecurityExpressions();
    }
}
