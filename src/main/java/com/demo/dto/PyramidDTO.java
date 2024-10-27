package com.demo.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PyramidDTO {

    public List<Integer> pyramidNums;
    public int target;
    public String calculatedDirections;

}
