package com.demo.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PyramidData {
    public StringBuilder directions;
    public StringBuilder calculatedDirections;
    public int target;
    public int calculatedTargetProduct;
    public int fileIndex = 0;
    public int index = 0;
    public boolean switcher = true;
    public List<Integer> targetProductList = new ArrayList();
    public List<int[][]> listOfFiles = new ArrayList<>();
    public List<Path> files;
    public int[][] pyramidArr;
}
