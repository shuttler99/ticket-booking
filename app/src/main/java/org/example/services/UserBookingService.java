package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.type.ReferenceType;

public class UserBookingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private User user;
    private List<User> userList;
    private static final String USERS_RESOURCE_PATH = "C:\\Users\\anubhavbhardwaj\\IRCTC\\app\\src\\main\\java\\org\\example\\localDb\\users.json";
    private static final String TRAINS_RESOURCE_PATH = "C:\\Users\\anubhavbhardwaj\\IRCTC\\app\\src\\main\\java\\org\\example\\localDb\\trains.json";

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
        if (this.user.getTicketBooked().size() > 0) {
            this.user.printTickets();
        } else {
            System.out.println("The user has not booked any ticket");
        }

    }

}
