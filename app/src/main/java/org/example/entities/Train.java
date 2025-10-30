package org.example.entities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private HashMap<String, String> destinationsTime;
    private List<String> stations;

    // Constructors
    public Train() {
        // Why this is a 1d Array List
        this.seats = new ArrayList<>();
        this.destinationsTime = new HashMap<>();
        this.stations = new ArrayList<>();
    }

    public Train(String trainId, String trainNo) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = new ArrayList<>();
        this.destinationsTime = new HashMap<>();
        this.stations = new ArrayList<>();
    }

    // Getters and Setters
    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String gettrainNo() {
        return trainNo;
    }

    public void settrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public HashMap<String, String> getDestinationsTime() {
        return destinationsTime;
    }

    public void setDestinationsTime(HashMap<String, String> destinationsTime) {
        this.destinationsTime = destinationsTime;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

}