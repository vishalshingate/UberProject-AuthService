package com.example.uberprojectauthservice.configurations;

import com.example.uberprojectauthservice.filters.JwtAuthFilter;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import com.example.uberprojectauthservice.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {

    private PassengerRepository passengerRepository;
    public SpringSecurity(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService()
    {
        return new UserDetailsServiceImpl(passengerRepository);
    }
    // cors and csrf

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        return http.csrf(csrf->csrf.disable())
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/api/v1/auth/signup/*").permitAll()
                    .requestMatchers("/api/v1/auth/signin/*").permitAll()
            )
            .authorizeHttpRequests(auth ->auth.requestMatchers("/api/v1/auth/validate").authenticated())
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     *This is strategy interface that allows us for various type of authentication scheme will pageable in your application
     *
     * In this method you can provide the logic how you authentication actually going to happen
     * based on email or based email pass, username pass or any other combination
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    // above two function we gave written because we wanted to use the internal matching by spring
    // if you don't want you can use custom mechanism too

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // this is not provide default bean because there are lot possible constructor and spring dont want take decision
    // on the behalf of you
}
