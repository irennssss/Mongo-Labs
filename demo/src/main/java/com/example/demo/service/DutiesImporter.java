package com.example.demo.service;

import com.example.demo.persistence.CarPark;
import com.example.demo.persistence.Duty;
import com.example.demo.persistence.Employee;
import com.example.demo.persistence.repository.CarParkRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.util.List;

@Service
@DependsOn("employeesImporter")
public class DutiesImporter {

    private final CarParkRepository repository;
    private final MongoTemplate mongoTemplate;

    public DutiesImporter(CarParkRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void importData() {
        try {
            var inputStream = getClass().getResourceAsStream("/duties.csv");
            if (inputStream == null) return;

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                List<String[]> rows = reader.readAll();

                for (String[] row : rows) {
                    String code = row[0];
                    String desc = row[1];
                    String employeeName = row[2];

                    Query query = Query.query(Criteria.where("employees.fullName").is(employeeName));
                    CarPark carPark = mongoTemplate.findOne(query, CarPark.class);

                    if (carPark != null) {
                        for (Employee emp : carPark.getEmployees()) {
                            if (emp.getFullName().equals(employeeName)) {
                                // 3. Додаємо йому обов'язок
                                emp.getDuties().add(new Duty(code, desc));
                            }
                        }
                        repository.save(carPark);
                    }
                }
                System.out.println("--- Обов'язки імпортовані ---");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}