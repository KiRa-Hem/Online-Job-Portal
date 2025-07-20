package com.workfolio.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import com.workfolio.repository.AdminRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for stateless APIs
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/applicantDetails/applyjob").permitAll() // Public access
                .antMatchers("/admin/**").hasRole("ADMIN") // Admin-only access
                .anyRequest().authenticated()
            .and()
            .httpBasic(); // Use basic authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var admin = adminRepository.findByCompanyId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + username));
            return org.springframework.security.core.userdetails.User
                    .withUsername(admin.getCompanyId())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        };
    }
}