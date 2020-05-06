package de.workshops.bookdemo.appconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bookdemo")
public class ApplicationConfig {

    private String stringProp;
    private Integer numProp;
    
    private NestedProps nested;
    
    @Data
    public static class NestedProps {
        
        private String configParam;
    }
}
