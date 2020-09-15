package com.basaki.rules.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * {@code ServiceConfiguration} is the main configuration of Rules Service.
 *
 * @author Indra Basak
 * @since 09/15/20
 */
@Configuration
public class ServiceConfiguration implements WebMvcConfigurer {

    @Primary
    @Bean(name = "customObjectMapper")
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter standardConverter =
                new MappingJackson2HttpMessageConverter();
        standardConverter.setPrefixJson(false);
        standardConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.TEXT_PLAIN));
        standardConverter.setObjectMapper(createObjectMapper());

        return standardConverter;
    }
}
