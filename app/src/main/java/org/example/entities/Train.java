package org.example.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Train {
    private String trainId;
    private String trainNumber;
    private List<List<Integer>> seats;
    private HashMap<String, Date> destinationsTime;
    private List<String> stations;

    // Constructors
    public Train() {
        this.seats = new ArrayList<>();
        this.destinationsTime = new HashMap<>();
        this.stations = new ArrayList<>();
    }

    public Train(String trainId, String trainNumber) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.seats = new ArrayList<>();
        this.destinationsTime = new HashMap<>();
        this.stations = new ArrayList<>();
    }

    // Getters and Setters
    public String getTrainId() { return trainId; }
    public void setTrainId(String trainId) { this.trainId = trainId; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public List<List<Integer>> getSeats() { return seats; }
    public void setSeats(List<List<Integer>> seats) { this.seats = seats; }

    public HashMap<String, Date> getDestinationsTime() { return destinationsTime; }
    public void setDestinationsTime(HashMap<String, Date> destinationsTime) { this.destinationsTime = destinationsTime; }

    public List<String> getStations() { return stations; }
    public void setStations(List<String> stations) { this.stations = stations; }
}