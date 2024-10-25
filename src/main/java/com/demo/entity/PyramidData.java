package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PyramidData {
    private StringBuilder directions;
    private StringBuilder calculatedDirections;
    private int target;
    private int calculatedTargetProduct;
    private int fileIndex;
    private int index;
    private boolean switcher;
    private List<Integer> targetProductList;
    private List<int[][]> listOfFiles;
    private List<Path> files;
    private int[][] pyramidArr;
}
