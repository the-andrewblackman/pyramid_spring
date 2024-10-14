package com.demo.controller;

import com.demo.service.PyramidDescentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DemoController {

    @Autowired
    PyramidDescentService pyramidDescentService;

    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping (value = "/directions", produces="text/plain")
    @ResponseBody
    public String directions(){
    return pyramidDescentService.descentStart().get(1);
    }
    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping ("/targets")
    public Integer target(){
        return Integer.parseInt(pyramidDescentService.descentStart().get(0));
    }
    @CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
    @GetMapping ("/data")
    public List<Integer> data(){
        return pyramidDescentService.dataToList();
    }

}
