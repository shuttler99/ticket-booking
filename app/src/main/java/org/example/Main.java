package org.example;

import java.io.IOException;

import org.example.services.UserBookingService;

public class Main {

    public static void main(String[] args) {
        UserBookingService user;
        try {
            user = new UserBookingService(null);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}