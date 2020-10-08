package com.example.demo.services;

import com.example.demo.entities.City;
import com.example.demo.entities.University;
import com.example.demo.repositories.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
    private UniversityRepo universityRepo;

    @Autowired
    public void setCountryRepo(UniversityRepo universityRepo) {
        this.universityRepo = universityRepo;
    }

    public List<University> getAll() {
        return universityRepo.findAll();
    }

    public void delete(Long id){
        universityRepo.deleteById(id);
    }

    public void saveOne(University university) {
        universityRepo.save(university);
    }

    public University getById(Long id) {
        return universityRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found university with id : " + id));
    }

    public List<University> getAllByCity(City city){
        return universityRepo.findAllByCity(city);
    }
}
