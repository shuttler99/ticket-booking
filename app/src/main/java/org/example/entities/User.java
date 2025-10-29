package org.example.entities;

import java.util.List;
import java.util.ArrayList;

public class User {

    private String name;
    private String password;
    private String hashPassword;
    private String userId;
    // When it will be a new user then this will be a non optional field
    private List<Ticket> ticketBooked;

    // Constructors
    public User() {
        this.ticketBooked = new ArrayList<>();
    }

    public User(String userId, String name, String password, String hashPassword) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        // Here it is Automatically handling this
        this.ticketBooked = new ArrayList<>();
    }

    public void printTickets() {
        for (int i = 0; i < ticketBooked.size(); i++) {
            System.out.println(ticketBooked.get(i).getTicketInfo());
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Ticket> getTicketBooked() {
        return ticketBooked;
    }

    public void setTicketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }

    public void addTicket(Ticket ticket) {
        if (this.ticketBooked.size() < 2) {
            this.ticketBooked.add(ticket);
        }
    }
}
