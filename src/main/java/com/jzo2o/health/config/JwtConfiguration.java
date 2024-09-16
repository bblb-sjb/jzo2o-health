package com.jzo2o.health.config;

import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.health.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author itcast
 */
@Configuration
public class JwtConfiguration {

    @Resource
    private ApplicationProperties applicationProperties;

    @Bean
    public JwtTool jwtTool() {
        return new JwtTool(applicationProperties.getJwtKey());
    }
}
