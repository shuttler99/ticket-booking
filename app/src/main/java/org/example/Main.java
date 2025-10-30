package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.entities.Train;
import org.example.entities.User;
import org.example.services.UserBookingService;
import org.example.util.UserServiceUtil;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running the Train Booking System");
        String source = "";
        String destination = "";
        Scanner sc = new Scanner(System.in);
        int option = 0;
        boolean isLoggedIn = false;
        Train selectedTrain = new Train();
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
                    int attempts = 0;
                    int maxRetry = 3;

                    while (!isLoggedIn && attempts < maxRetry) {
                        System.out.println("Enter the username: ");
                        String nameToLogin = sc.nextLine();
                        System.out.println("Enter the password: ");
                        String passwordToLogin = sc.nextLine();

                        isLoggedIn = userBookingService.loginUser(nameToLogin, passwordToLogin);

                        if (isLoggedIn) {
                            System.out.println("You have logged in successfully!");
                        } else {
                            attempts++;
                            if (attempts < maxRetry) {
                                System.out.println("Invalid credentials. " + (maxRetry - attempts) + " attempts left.");
                                System.out.println("Please try again.");
                            } else {
                                System.out.println("Too many failed attempts. Returning to main menu.");
                            }
                        }
                    }
                    break;

                case 3:
                    if (!isLoggedIn) {
                        System.out.println("You need to login First");
                        break;
                    }
                    userBookingService.fetchTickets();
                    break;

                case 4:
                    System.out.println("Enter the start city");
                    source = sc.nextLine();
                    System.out.println("Enter the destination city");
                    destination = sc.nextLine();

                    List<Train> trains = userBookingService.findTrains(source, destination);
                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + " Train id : " + t.getTrainId());
                        for (Map.Entry<String, String> entry : t.getDestinationsTime().entrySet()) {
                            System.out.println("station " + entry.getKey() + " time: " + entry.getValue());
                        }
                    }

                    System.out.println("Please select a train by pressing 1 2 3 or ..");
                    int selectedTrainIndex = sc.nextInt();
                    // here user will have the selected train
                    selectedTrain = trains.get(selectedTrainIndex);

                case 5:
                    // Show the available seats in the train
                    List<List<Integer>> availableSeats = selectedTrain.getSeats();
                    // We need to map over the data to show the seats

                    System.out.println("Available Seats (0 = Available, 1 = Occupied):");
                    System.out.println("Columns: 0  1  2  3");
                    for (int i = 0; i < availableSeats.size(); i++) {
                        System.out.print("Row " + i + ":   ");
                        for (int j = 0; j < availableSeats.get(i).size(); j++) {
                            System.out.print(availableSeats.get(i).get(j) + "  ");
                        }
                        System.out.println();
                    }

                    System.out.println("Enter the column please..");
                    int column = sc.nextInt();

                    System.out.println("Enter the row please..");
                    int row = sc.nextInt();

                    System.out.println("Booking your seat thanks for pateience.....");
                    boolean isTicketBooked = userBookingService.bookTicket(selectedTrain, source, destination, row,
                            column);

                    if (isTicketBooked) {
                        System.out.println("Your tickets have been booked");
                    } else {
                        System.out.println("You cannot book this seat");
                    }
                    break;

                default:
                    break;

            }

            sc.close();

        }

    }
}
