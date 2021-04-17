package com.atanasvasil.api.mycardocs.security;

import com.atanasvasil.api.mycardocs.SpringApplicationContext;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 60 * 60 * 24 * 365; //365 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_TYPE = "JWT";
    public static final String SIGN_UP_URL = "/api/users";
    public static final String LOGIN_URL = "/login";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final String JWT_AUTH_KEY = "rol";
    public static final String TOKEN_SECRET = "r0oe0NyaeK9bJpw8ujI0QZQCKdSxJ";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getMessage("token.secret");
    }
}
