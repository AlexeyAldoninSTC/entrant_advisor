package com.example.demo.repositories;

import com.example.demo.entities.Faculty;
import com.example.demo.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepo extends JpaRepository<Faculty, Long> {

    List<Faculty> findAllByUniversity(University university);
}
