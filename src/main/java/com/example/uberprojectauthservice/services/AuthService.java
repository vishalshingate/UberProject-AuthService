package com.example.uberprojectauthservice.services;

import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignupRequestDto;
import com.example.uberprojectauthservice.model.Passenger;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private PassengerRepository passengerRepository;

    @Autowired
    public AuthService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerDto signupPassenger(PassengerSignupRequestDto dto) {
        Passenger passenger = Passenger.builder()
            .email(dto.getEmail())
            .name(dto.getName())
            .phoneNumber(dto.getPhoneNumber())
            .password(dto.getPassword())
            .build();
        Passenger newPassenger = passengerRepository.save(passenger);
        PassengerDto Passengerdto = PassengerDto.of(newPassenger);
        return Passengerdto;
    }
}
