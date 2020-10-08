package com.example.demo.controllers;

import com.example.demo.entities.Faculty;
import com.example.demo.services.UniversityService;
import com.example.demo.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    private FacultyService service;
    private UniversityService universityService;

    @Autowired
    public FacultyController(FacultyService service, UniversityService universityService) {
        this.service = service;
        this.universityService = universityService;
    }

    @GetMapping
    public String showAll(Model model){
        model.addAttribute("faculties", service.getAll());
        return "showAllFaculties";
    }

    @GetMapping("/{id}")
    public String showLocations(Model model, @PathVariable Long id){
        model.addAttribute("faculties",
                service.getAllByUniversity(universityService.getById(id)));
        model.addAttribute("universityID", id);
        return "showAllFaculties";
    }

    @GetMapping("{id}/add")
    public String addCountry(@PathVariable Long id, Model model) {
        model.addAttribute("universityID", id);
        return "addFaculty";
    }

    @PostMapping("{id}/add")
    public String saveOne(@ModelAttribute(name = "facultyName") String facultyName,
                          @PathVariable Long id) {

        Faculty faculty = new Faculty(-1L, facultyName, universityService.getById(id));
        service.saveOne(faculty);
        return "redirect:/faculty/" + id;
    }

    @GetMapping("/{regionId}/delete/{id}")
    public String deleteRegion(@PathVariable Long id,
                               @PathVariable Long regionId){
        service.deleteOne(service.getById(id));
        return "redirect:/faculty/" + regionId;
    }
}
