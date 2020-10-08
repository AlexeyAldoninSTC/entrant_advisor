package com.example.demo.controllers;

import com.example.demo.entities.University;
import com.example.demo.services.UniversityService;
import com.example.demo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/university")
public class UniversityController {
    private UniversityService service;
    private CityService cityService;

    @Autowired
    public void setRegionService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setService(UniversityService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("universities", service.getAll());
        return "showAllUniversity";
    }

    @GetMapping("{id}/add")
    public String addCountry(@PathVariable Long id, Model model) {
        model.addAttribute("cityID", id);
        return "addUniversity";
    }

    @PostMapping("{id}/add")
    public String saveOne(@ModelAttribute(name = "universityName") String universityName,
                          @PathVariable Long id) {

        University university = new University(-1L, universityName, cityService.getById(id));
        service.saveOne(university);
        return "redirect:/university/" + id;
    }

    @GetMapping("/{id}")
    public String countriesBy(Model model,
                              @PathVariable Long id) {
        model.addAttribute("universities", service.getAllByCity(cityService.getById(id)));
        model.addAttribute("universityID", id);
        return "showAllUniversity";
    }

    @GetMapping("/{regionId}/delete/{id}")
    public String deleteRegion(@PathVariable Long id,
                               @PathVariable Long regionId){
        service.delete(id);
        return "redirect:/university/" + regionId;
    }
}
