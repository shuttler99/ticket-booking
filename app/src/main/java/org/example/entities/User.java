package org.example.entities;

import java.util.List;
import java.util.ArrayList;

public class User {

    private String name;
    private String password;
    private String hashedPassword;
    private String userId;
    // When it will be a new user then this will be a non optional field
    private List<Ticket> ticketsBooked;

    // Constructors
    public User() {
        this.ticketsBooked = new ArrayList<>();
    }

    public User(String userId, String name, String password, String hashPassword) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.hashedPassword = hashPassword;
        // Here it is Automatically handling this
        this.ticketsBooked = new ArrayList<>();
    }

    public void printTickets() {
        for (int i = 0; i < ticketsBooked.size(); i++) {
            System.out.println(ticketsBooked.get(i).getTicketInfo());
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
        return hashedPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashedPassword = hashPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setticketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void addTicket(Ticket ticket) {
        if (this.ticketsBooked.size() < 2) {
            this.ticketsBooked.add(ticket);
        }
    }
}
