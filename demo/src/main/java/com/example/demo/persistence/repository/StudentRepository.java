package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}