package com.example.uberprojectauthservice.controllers;

import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignupRequestDto;

import com.example.uberprojectauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto requestDto) {
        PassengerDto passengerDto= authService.signupPassenger(requestDto);
        return new ResponseEntity<>(passengerDto,HttpStatus.CREATED);

    }

    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signIn() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
