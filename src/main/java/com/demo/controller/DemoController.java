package com.demo.controller;

import com.demo.service.PyramidDescentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    PyramidDescentService pyramidDescentService;

    @GetMapping ("/directions")
    public List<String> directions(){

    return pyramidDescentService.descentStart();

    }

}
