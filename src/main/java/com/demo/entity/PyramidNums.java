package com.demo.entity;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PyramidNums {
    private List<Integer> pyramidNums;

    public PyramidNums(){
        this.pyramidNums = new ArrayList<>();
    }
    public PyramidNums addPyramidNums(int nums){
        this.pyramidNums.add(nums);
        return this;
    }
}
