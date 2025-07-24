package com.example.uberprojectauthservice.controllers;

import com.example.uberprojectauthservice.dto.AuthRequestDto;
import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignupRequestDto;

import com.example.uberprojectauthservice.services.AuthService;
import com.example.uberprojectauthservice.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto requestDto) {
        PassengerDto passengerDto= authService.signupPassenger(requestDto);
        return new ResponseEntity<>(passengerDto,HttpStatus.CREATED);

    }

    /**
     * Here we are getting email and password and return the access token if user is authentic
     *
     * @return
     */
    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),
                authRequestDto.getPassword()));
        if(authentication.isAuthenticated()) {

            Map<String,Object> payload = new HashMap<>();
            payload.put("email",authRequestDto.getEmail());

            String token = jwtService.createToken(authRequestDto.getEmail());

            ResponseCookie responseCookie = ResponseCookie.from("JwtToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpiry)
                .build();
            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        Cookie cookie = Arrays.stream(request.getCookies())
            .filter(cookie1 -> cookie1.getName().equals("JwtToken")).findFirst().get();
        String token = cookie.getValue();
        System.out.println("Value"+ cookie.getValue());
        String email = jwtService.extractEmail(token);
        boolean authenticated = jwtService.validateToken(token, email);
        if(authenticated) {
            return ResponseEntity.ok().body("Authentication Successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
