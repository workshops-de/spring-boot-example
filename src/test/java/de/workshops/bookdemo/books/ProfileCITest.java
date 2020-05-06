package de.workshops.bookdemo.books;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("ci")
public class ProfileCITest {

    @Autowired
    private Environment env;
    
    @Value("${server.port}")
    private int port;
    
    
    @Test
    void testPort() throws Exception {
        assertEquals(8081, port);
        assertEquals(8081, Integer.valueOf(env.getProperty("server.port")));
    }
    
}
