package com.demo.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class PyramidNums {
    private List<Integer> pyramidNums;

    public PyramidNums(){
        this.pyramidNums = new ArrayList<>();
    }
    public PyramidNums addPyramidNums(int nums){
        this.pyramidNums.add(nums);
        return this;
    }
    public void clearPyramidNums(){
        this.pyramidNums.clear();
    }
    public List<Integer> getPyramidNums(){
        return pyramidNums;
    }
}
