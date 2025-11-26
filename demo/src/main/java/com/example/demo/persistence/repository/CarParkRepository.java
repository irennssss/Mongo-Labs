package com.example.demo.persistence.repository;

import com.example.demo.persistence.CarPark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkRepository extends MongoRepository<CarPark, String> {
    CarPark findByName(String name);
}