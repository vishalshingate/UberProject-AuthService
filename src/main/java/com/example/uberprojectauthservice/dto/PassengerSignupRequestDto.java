package com.example.uberprojectauthservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
 public class PassengerSignupRequestDto {
    private String email;

    private String password; // encrypted

    private String phoneNumber;

    private String name;

    private long id;

}

// why we need getter setter for dto, when dto object come up then it come with json our libraries has to convert
// java object for that we need to have getters and setters on the dto
