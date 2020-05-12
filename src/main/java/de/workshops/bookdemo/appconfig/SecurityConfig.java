package de.workshops.bookdemo.appconfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       auth.inMemoryAuthentication()
//           .withUser("user").password("password").roles("USER").and()
//           .withUser("admin").password("password").roles("USER", "ADMIN");
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            private final List<User> users = Arrays.asList(
                    new User("alice", "$2a$10$jUkm.4VL3Iz7d3fJSQdPtOVi55BrSChHtQn5ZhYsf4krs7aHq/Ikq", Arrays.asList(new SimpleGrantedAuthority("ADMIN"))),
                    new User("bob", "$2a$10$EgCeWCCt.GuatXOX8yUchOSI1u.dazAOdshg/hJzcKKNskfyeL6Ta", Arrays.asList(new SimpleGrantedAuthority("USER"))));
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                        if (list.size() ==  0) {
                            throw new UsernameNotFoundException(username);
                        }
                        // deep copy notwendig
                        User result = list.get(0);
                        return new User(result.getUsername(), result.getPassword(), result.getAuthorities());
                    }));
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .and()
            .formLogin()
                .usernameParameter("user")
                .passwordParameter("pass")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, 
                                HttpServletResponse response, 
                                Authentication authentication) 
                                        throws IOException, ServletException {
                        response.setStatus(200);
                    }
                })
                .and()
            .oauth2Login()
                .and()
            .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
            .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/**");
    }
    
    

    
}
