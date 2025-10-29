package org.example;

import java.io.IOException;
import java.util.Scanner;

import org.example.entities.User;
import org.example.services.UserBookingService;
import org.example.util.UserServiceUtil;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running the Train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService = null;
        try {
            // here the empty constructor will be initiliazed
            userBookingService = new UserBookingService();

        } catch (IOException ex) {
            System.out.println("Error initializing UserBookingService: " + ex.getMessage());
            return; // Exit the program if initialization fails
        }

        while (option != 7) {
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Fetch My Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel My Booking");
            System.out.println("7. Exit App");
            System.out.print("Enter your choice: ");
            option = sc.nextInt();
            sc.nextLine(); // Consume the newline

            switch (option) {
                case 1:
                    System.out.println("Enter the username ");
                    String userName = sc.nextLine();
                    System.out.println("Enter the password");
                    String password = sc.nextLine();
                    User currentUser = new User(UUID.randomUUID().toString(), userName, password,
                            UserServiceUtil.hashPassword(password));
                    userBookingService.signUp(currentUser);
                    break;
                case 2:
                    System.out.println("Enter the username ");
                    String nameToLogin = sc.nextLine();
                    System.out.println("Enter the password");
                    String passwordToLogin = sc.nextLine();

                    User loginUser = new User(UUID.randomUUID().toString(), nameToLogin, passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin));

                    try {
                        userBookingService = new UserBookingService(loginUser);
                    } catch (IOException ex) {
                        return;
                    }

                    break;

                case 3:
                    System.out.println("Search Bookings");
                    // By
                case 4:
                    System.out.println("Enter the station");

            }

        }

    }
}

// When a user wanted to print tickets which we had
// We first need to check that if a user exists in the system or not
//