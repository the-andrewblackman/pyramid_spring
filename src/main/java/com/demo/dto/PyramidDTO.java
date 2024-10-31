package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PyramidDTO {

    public List<Integer> pyramidNums;
    public int target;
    public String calculatedDirections;

}
