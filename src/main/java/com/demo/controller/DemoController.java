package com.demo.controller;

import com.demo.entity.PyramidNums;
import com.demo.service.PyramidDescentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {


    private final PyramidDescentService pyramidDescentService;
    @Autowired
    public DemoController(PyramidDescentService pyramidDescentService){
        this.pyramidDescentService = pyramidDescentService;
    }

    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping (value = "/directions")
    @ResponseBody
    public List<String> directions(){
    return pyramidDescentService.descentStart();
    }
    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping ("/data")
    public PyramidNums data(){
        return pyramidDescentService.dataToList();
    }

}
