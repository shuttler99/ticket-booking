package org.example;

import org.example.services.UserBookingService;

public class Main {

    public static void main(String[] args) {
        UserBookingService user = new UserBookingService(null);
        System.out.println(user);
    }
}