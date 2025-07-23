package com.example.uberprojectauthservice.controller;

import com.example.uberprojectauthservice.dto.PassengerSignupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto requestDto) {

       return ResponseEntity.ok().body("hello");
    }
}
