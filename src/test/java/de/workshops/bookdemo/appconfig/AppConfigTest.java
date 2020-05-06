package de.workshops.bookdemo.appconfig;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationConfig appConfig;
    
    @Test
    void testAppConfig() throws Exception {
        assertEquals("a value with spaces", appConfig.getStringProp());
        assertEquals(42, appConfig.getNumProp());
        assertEquals("23", appConfig.getNested().getConfigParam());
    }
    
}
