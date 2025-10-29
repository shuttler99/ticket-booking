package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private User user;
    private List<User> userList;
    private static final String USERS_RESOURCE_PATH = "src/main/resources/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
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

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName())
                    && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
        }).findFirst();

        return foundUser.isPresent();

    }

}
