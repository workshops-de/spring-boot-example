package de.workshops.bookdemo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Import(TestSecurityConfigurationWithGivenUser.class)
public class LoginTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserDetailsService testUserDetailsService() throws Exception {
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withDefaultPasswordEncoder().username("test").password("test").roles("ROLE").build());
            return manager;
        }

    }
    
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void checkLogin() throws Exception{
        ResultActions action = mvc.perform(formLogin("/login")
                .user("user", "test")
                .password("pass", "test"));
        int status = action.andReturn().getResponse().getStatus();
        assertTrue(status == 200);
    }

    @Test
    public void checkLoginWithWrongPassword() throws Exception{
        ResultActions action = mvc.perform(formLogin("/login")
                .user("user", "alice")
                .password("pass", "wrongPass"));
        int status = action.andReturn().getResponse().getStatus();
        assertTrue(status == 302);
        String redirectURL = action.andReturn().getResponse().getHeader("Location");
        assertNotNull(redirectURL);
    }

}
