package com.example.uberprojectauthservice.services;

import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignupRequestDto;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import com.example.uberprojectentity.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSignupRequestDto dto) {
        Passenger passenger = Passenger.builder()
            .email(dto.getEmail())
            .name(dto.getName())
            .phoneNumber(dto.getPhoneNumber())
            .password(bCryptPasswordEncoder.encode(dto.getPassword()))
            .build();
        Passenger newPassenger = passengerRepository.save(passenger);
        PassengerDto Passengerdto = PassengerDto.of(newPassenger);
        return Passengerdto;
    }
}
