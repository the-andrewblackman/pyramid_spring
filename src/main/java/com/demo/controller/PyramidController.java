package com.demo.controller;

import com.demo.dto.PyramidDTO;
import com.demo.service.PyramidDescentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PyramidController {

    private final PyramidDescentService pyramidDescentService;
    @Autowired
    public PyramidController(PyramidDescentService pyramidDescentService){
        this.pyramidDescentService = pyramidDescentService;
    }

    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping ("/max")
    public int max(){
        return pyramidDescentService.maxNumberFiles();
    }
    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping ("/data")
    public PyramidDTO data(){
        return pyramidDescentService.dataToList();
    }

}
