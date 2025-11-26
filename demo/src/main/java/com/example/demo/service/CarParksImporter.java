package com.example.demo.service;

import com.example.demo.persistence.CarPark;
import com.example.demo.persistence.repository.CarParkRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CarParksImporter {

    private final CarParkRepository repository;

    public CarParksImporter(CarParkRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void importData() {
        try {
            var inputStream = getClass().getResourceAsStream("/carparks.csv");
            if (inputStream == null) return;

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                List<String[]> rows = reader.readAll();
                for (String[] row : rows) {
                    String name = row[0];
                    if (repository.findByName(name) == null) {
                        repository.save(new CarPark(row[0], row[1], Integer.parseInt(row[2])));
                    }
                }
                System.out.println("--- Стоянки імпортовано ---");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}