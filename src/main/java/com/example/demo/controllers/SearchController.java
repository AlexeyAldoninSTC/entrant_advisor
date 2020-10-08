package com.example.demo.controllers;

import com.example.demo.entities.Way;
import com.example.demo.services.WayService;
import com.example.demo.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {
    private WayService wayService;

    @Autowired
    public void setWayService(WayService wayService) {
        this.wayService = wayService;
    }

    @GetMapping
    public String searchFilter() {
        return "searchFilter";
    }


    @GetMapping("/filter")
    public String showSearched(@RequestParam Map<String, String> requestParams,
                               @RequestParam(name = "cityId", required = false) List<Long> citiesId,
                               Model model) {
        SearchFilter searchFilter = new SearchFilter(requestParams, citiesId);
        Specification<Way> spec = searchFilter.getSpecification();
        model.addAttribute("universities", wayService.getAll(spec));

        return "searchByFilter";
    }
}
