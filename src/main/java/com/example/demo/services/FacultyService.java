package com.example.demo.services;

import com.example.demo.entities.Faculty;
import com.example.demo.entities.University;
import com.example.demo.repositories.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private FacultyRepo facultyRepo;

    @Autowired
    public void setLocationRepo(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    public List<Faculty> getAll() {
        return facultyRepo.findAll();
    }

    public Faculty getById(Long id){
        return facultyRepo.findById(id).orElseThrow(()->new RuntimeException("not fount faculty by id : " + id));
    }

    public void deleteOne(Faculty faculty){
        facultyRepo.delete(faculty);
    }

    public List<Faculty> getAllByUniversity(University id){
        return facultyRepo.findAllByUniversity(id);
    }

    public Faculty saveOne(Faculty faculty){
        return facultyRepo.save(faculty);
    }
}
