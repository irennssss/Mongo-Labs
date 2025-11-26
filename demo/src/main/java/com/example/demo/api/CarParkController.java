package com.example.demo.api;

import com.example.demo.persistence.CarPark;
import com.example.demo.service.CarParkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carparks")
public class CarParkController {

    private final CarParkService carParkService;

    public CarParkController(CarParkService carParkService) {
        this.carParkService = carParkService;
    }

    @GetMapping
    public List<CarPark> getAllCarParks() {
        return carParkService.findAll();
    }

    @GetMapping("/import")
    public String triggerImport() {
        carParkService.importFromCsv();
        return "Імпорт успішно запущено! Перевірте консоль або оновіть список.";
    }
}