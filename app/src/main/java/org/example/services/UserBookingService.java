package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;

public class UserBookingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private User user;
    private List<User> userList;
    private static final String USERS_RESOURCE_PATH = "C:\\Users\\anubhavbhardwaj\\IRCTC\\app\\src\\main\\java\\org\\example\\localDb\\users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = null;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = objectMapper.readValue(new File(USERS_RESOURCE_PATH), new TypeReference<List<User>>() {
        });
    }

    public List<User> getUserList() {
        return userList;
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile(); // Save changes to file
            return Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File usersFile = new File(USERS_RESOURCE_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public Boolean loginUser(String userName, String password) {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(userName)
                    && UserServiceUtil.checkPassword(password, user1.getHashPassword());
        }).findFirst();

        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            return true;
        }
        this.user = null;
        return false;
    }

    public void fetchTickets() {
        if (this.user == null) {
            System.out.println("You need to login First");
            return;
        }
        // Check is user is Assigned or not
        if (this.user.getTicketsBooked()).size() > 0) {
            this.user.printTickets();
        } else {
            System.out.println("The user has not booked any ticket");
        }

    }

    public List<Train> findTrains(String source, String destination) {

        // At first we need to call
        try {
            TrainService trainService = new TrainService();
            return trainService.getTrains(source, destination);

        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public boolean bookTicket(Train selectedTrain, String source, String destination, int row, int column) {

        if (user.getTicketsBooked().size() >= 2) {
            System.out.println("Maximum ticket booking limit reached (2 tickets per user)");
            return false;
        }

        List<List<Integer>> seats = selectedTrain.getSeats();
        if (!isValidSeatPosition(seats, row, column)) {
            System.out.println("Invalid seat position. Please enter valid row and column numbers.");
            return false;
        }

        if (seats.get(row).get(column) == 1) {
            System.out.println("Seat is already booked. Please select another seat.");
            return false;
        }

        seats.get(row).set(column, 1);
        selectedTrain.setSeats(seats);

        // 5. Create and add ticket to user
        Ticket ticket = createTicket(selectedTrain, source, destination, row, column);
        user.addTicket(ticket);

        // 6. Persist changes
        try {
            saveUserListToFile();
            TrainService trainService = new TrainService();
            trainService.updateTrain(selectedTrain);

            System.out.println("Ticket booked successfully!");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            return true;

        } catch (IOException ex) {

            seats.get(row).set(column, 0);
            user.getTicketsBooked().remove(ticket);
            System.out.println("Booking failed due to system error. Please try again.");
            ex.printStackTrace();
            return false;
        }
    }

    private boolean isValidSeatPosition(List<List<Integer>> seats, int row, int column) {
        return row >= 0 && row < seats.size()
                && column >= 0 && column < seats.get(row).size();
    }

    private Ticket createTicket(Train train, String source, String destination, int row, int column) {
        String ticketId = "TKT" + System.currentTimeMillis();
        return new Ticket(
                ticketId,
                user.getUserId(),
                source,
                destination,
                new Date(),
                train);
    }

}
