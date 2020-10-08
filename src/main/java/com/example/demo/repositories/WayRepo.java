package com.example.demo.repositories;

import com.example.demo.entities.Faculty;
import com.example.demo.entities.University;
import com.example.demo.entities.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WayRepo extends JpaRepository<Way, Long>,
        JpaSpecificationExecutor<Way> {

    List<Way> findAllByFaculty(Faculty faculty);
}
