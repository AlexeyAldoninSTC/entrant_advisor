package com.example.demo.services;

import com.example.demo.entities.City;
import com.example.demo.repositories.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CityService {

    private CityRepo cityRepo;

    @Autowired
    public void setRegionRepo(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    public List<City> getAll(){
        return cityRepo.findAll();

    }

    @Transactional
    public void deleteCity(City city){
        cityRepo.delete(city);
    }

    @Transactional
    public City saveOne(City city){
        return cityRepo.save(city);
    }

    public City getById(Long id){
        return cityRepo.getOne(id);
    }
}
