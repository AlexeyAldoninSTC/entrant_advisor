package com.example.demo.services;

import com.example.demo.entities.Faculty;
import com.example.demo.entities.Way;
import com.example.demo.repositories.WayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WayService {

    private WayRepo wayRepo;

    @Autowired
    public void setLocationRepo(WayRepo facultyRepo) {
        this.wayRepo = facultyRepo;
    }

    public List<Way> getAll() {
        return wayRepo.findAll();
    }

    public List<Way> getAll(Specification<Way> specification){
        return wayRepo.findAll(specification);
    }

    public Way getById(Long id){
        return wayRepo.findById(id).orElseThrow(()->new RuntimeException("not fount way by id : " + id));
    }

    public void deleteOne(Way way){
        wayRepo.delete(way);
    }

    public List<Way> getAllByFaculty(Faculty faculty){
        return wayRepo.findAllByFaculty(faculty);
    }

    public Way saveOne(Way way){
        return wayRepo.save(way);
    }
}
