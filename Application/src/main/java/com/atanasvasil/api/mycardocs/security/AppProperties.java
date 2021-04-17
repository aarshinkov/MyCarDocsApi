package com.atanasvasil.api.mycardocs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Component
public class AppProperties {

    @Autowired
    private Environment env;

    public String getMessage(String key) {
        return env.getProperty(key);
    }
}
