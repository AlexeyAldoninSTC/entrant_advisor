package com.example.demo.controllers;

import com.example.demo.entities.City;
import com.example.demo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/city")
public class CityController {
    private CityService service;

    @Autowired
    public void setService(CityService service) {
        this.service = service;
    }

    @GetMapping
    public String getRegion(Model model) {
        model.addAttribute("cities", service.getAll());
        return "showAllCity";
    }

    @GetMapping("/add")
    public String addRegion() {
        return "addCity";
    }


    @GetMapping("/delete/{id}")
    public String deleteRegion(@PathVariable Long id){
        service.deleteCity(service.getById(id));
        return "redirect:/city";
    }

    @PostMapping("/add")
    public String saveOne(@ModelAttribute(name = "cityName") String cityName) {
        service.saveOne(new City(-1L, cityName));
        return "redirect:/city";
    }
}
