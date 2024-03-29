package com.dnd.dndcharactercreator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/login", "/signup", "/favicon.ico").permitAll() // Allow public access to certain pages
                            .anyRequest().authenticated() // Require authentication for other pages
            )
            .formLogin(formLogin ->
                    formLogin
                            .loginPage("/login") // Specify your custom login page
                            .defaultSuccessUrl("/", true) // Redirect to home page after successful login
                            .permitAll()

            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout") // Redirect to login page with 'logout' query parameter
                    .deleteCookies("JSESSIONID") // Optionally delete cookies
                    .invalidateHttpSession(true) // Invalidate session
                    .clearAuthentication(true) // Clear authentication
                    .permitAll()).csrf(csrf -> csrf
                    .ignoringRequestMatchers("/logout") // Disable CSRF protection for /logout
            ); // Allow anyone to logout
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
