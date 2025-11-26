package com.example.demo.service;

import com.example.demo.persistence.CarPark;
import com.example.demo.persistence.Employee;
import com.example.demo.persistence.repository.CarParkRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DependsOn("carParksImporter")
public class EmployeesImporter {

    private final CarParkRepository repository;

    public EmployeesImporter(CarParkRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void importData() {
        try {
            var inputStream = getClass().getResourceAsStream("/employees.csv");
            if (inputStream == null) return;

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                List<String[]> rows = reader.readAll();

                Map<String, List<String[]>> grouped = rows.stream()
                        .collect(Collectors.groupingBy(row -> row[2]));

                grouped.forEach((carParkName, employeeRows) -> {
                    CarPark carPark = repository.findByName(carParkName);
                    if (carPark != null) {
                        for (String[] row : employeeRows) {
                            carPark.getEmployees().add(new Employee(row[0], row[1]));
                        }
                        repository.save(carPark); // Оновлюємо стоянку в базі
                    }
                });
                System.out.println("--- Працівники імпортовані ---");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}