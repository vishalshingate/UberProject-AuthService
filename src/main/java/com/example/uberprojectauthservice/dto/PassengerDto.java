package com.example.uberprojectauthservice.dto;

import com.example.uberprojectentity.models.Passenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDto {
    private Long id;
    private String name;

    private String email;

    private String phoneNumber;


    private String password;

    private Date createdAt;

    public static PassengerDto of(Passenger passenger) {
        PassengerDto passengerDto = PassengerDto.builder()
            .id(passenger.getId())
            .email(passenger.getEmail())
            .name(passenger.getName())
            .phoneNumber(passenger.getPhoneNumber())
            .password(passenger.getPassword())
            .createdAt(passenger.getCreatedAt())
            .build();
        return passengerDto;
    }


}
