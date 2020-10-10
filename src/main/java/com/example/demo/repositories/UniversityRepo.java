package com.example.demo.repositories;

import com.example.demo.entities.City;
import com.example.demo.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityRepo extends JpaRepository<University, Long> {
    List<University> findAllByCity(City city);
}
