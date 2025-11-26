package com.example.demo.service;

import com.example.demo.persistence.CarPark;

import com.example.demo.persistence.repository.CarParkRepository;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CarParkService {

    private final CarParkRepository carParkRepository;

    public CarParkService(CarParkRepository carParkRepository) {
        this.carParkRepository = carParkRepository;
    }

    public List<CarPark> findAll() {
        return carParkRepository.findAll();
    }

    public CarPark save(CarPark carPark) {
        return carParkRepository.save(carPark);
    }

    public void importFromCsv() {
        try {
            java.io.InputStream inputStream = getClass().getResourceAsStream("/carparks.csv");

            if (inputStream == null) {
                System.out.println("ФАЙЛ НЕ ЗНАЙДЕНО! Перевірте назву carparks.csv у папці resources");
                return;
            }

            CSVReader reader = new CSVReader(new java.io.InputStreamReader(inputStream));
            List<String[]> r = reader.readAll();

            for (String[] row : r) {
                String name = row[0];
                String address = row[1];
                int capacity = Integer.parseInt(row[2]);

                CarPark carPark = new CarPark(name, address, capacity);
                carParkRepository.save(carPark);
            }
            reader.close();
            System.out.println("Дані успішно імпортовано!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}