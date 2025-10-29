package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;
    private List<User> userList;
    private static final String USERS_RESOURCE_PATH = "users.json";

    public UserBookingService(User user1) {
        this.user = user1;

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USERS_RESOURCE_PATH)) {
            if (inputStream == null) {
                throw new IOException(
                        "Resource not found: " + USERS_RESOURCE_PATH + ". Ensure it's in src/main/resources/");
            }
            File usersFile = new File("app/src/main/resources/users.json");
            System.out.println(usersFile);

            // Deserialize JSON into List<User>
            userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        File usersFile = new File("app/src/main/resources/users.json");
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
