package com.example.demo.controllers;

import com.example.demo.entities.Way;
import com.example.demo.services.FacultyService;
import com.example.demo.services.WayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/way")
public class WayController {
    private WayService wayService;
    private FacultyService facultyService;

    @Autowired
    public WayController(WayService wayService, FacultyService facultyService) {
        this.wayService = wayService;
        this.facultyService = facultyService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("ways", wayService.getAll());
        return "showAllWays";
    }

    @GetMapping("/{id}")
    public String showLocations(Model model, @PathVariable Long id) {
        model.addAttribute("ways",
                wayService.getAllByFaculty(facultyService.getById(id)));
        model.addAttribute("facultyID", id);
        return "showAllWays";
    }

    @GetMapping("{id}/add")
    public String addWay(@PathVariable Long id, Model model) {
        model.addAttribute("facultyId", id);
        return "addWay";
    }

    @PostMapping("{id}/add")
    public String saveOne(@ModelAttribute Way way,
                          @PathVariable Long id) {
        way.setId(-1L);
        way.setFaculty(facultyService.getById(id));
        wayService.saveOne(way);

        return "redirect:/way/" + way.getFaculty().getId();
    }

    @GetMapping("/{id}/edit")
    public String editWay(@PathVariable Long id, Model model) {
        model.addAttribute("way", wayService.getById(id));
        model.addAttribute("facultyID", id);
        return "editWay";
    }

    @PostMapping("{id}/edit")
    public String editWay(@ModelAttribute Way way) {
        wayService.saveOne(way);
        return "redirect:/way/" + way.getFaculty().getId();
    }

    @GetMapping("/{regionId}/delete/{id}")
    public String deleteRegion(@PathVariable Long id,
                               @PathVariable Long regionId) {
        wayService.deleteOne(wayService.getById(id));
        return "redirect:/way/" + regionId;
    }
}
