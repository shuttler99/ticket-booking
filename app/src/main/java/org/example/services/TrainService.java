package org.example.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.example.entities.Train;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrainService {

    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAINS_RESOURCE_PATH = "C:\\Users\\anubhavbhardwaj\\IRCTC\\app\\src\\main\\java\\org\\example\\localDb\\trains.json";

    public TrainService() throws IOException {
        File trains = new File(TRAINS_RESOURCE_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {
        });
    }

    public List<Train> getTrains(String source, String destination) {
        return trainList.stream().filter(e -> isValidTrain(source, destination, e)).collect(Collectors.toList());
    }

    public boolean isValidTrain(String source, String destination, Train train) {
        int startIndex = train.getStations().indexOf(source);
        int endIndex = train.getStations().indexOf(destination);
        return startIndex != -1 && endIndex != -1 && startIndex < endIndex;
    }

    // We can use the same method to update the details of the train
    public void addTrain(Train train) {

        Optional<Train> isTrainPresent = trainList.stream()
                .filter(e -> e.getTrainId().equalsIgnoreCase(train.getTrainId())).findFirst();

        if (!isTrainPresent.isPresent()) {
            trainList.add(train);
            saveTrainListToFile();
        } else {
            updateTrain(train);
        }

    }

    // Question is what do you wanna update
    // We will compare the two data streams and then will update the changed values
    public void updateTrain(Train train) {

        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equals(train.getTrainId())).findFirst();

        if (index.isPresent()) {
            trainList.set(index.getAsInt(), train);
            saveTrainListToFile();
        } else {
            addTrain(train);
        }

    }

    private void saveTrainListToFile() {
        try {
            objectMapper.writeValue(new File(TRAINS_RESOURCE_PATH), trainList);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }

}

