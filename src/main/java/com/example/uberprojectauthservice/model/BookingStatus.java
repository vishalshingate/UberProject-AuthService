package com.example.uberprojectauthservice.model;

public enum BookingStatus {
    SCHEDULED,

    CANCELLED,

    CAB_ARRIVED,

    ASSIGNING_DRIVER,

    IN_RIDE,

    COMPLETED

    // there are two ways to handle enum you can store enum
    //1. in the database as a string
    // 2. we can handle as the number.
}
