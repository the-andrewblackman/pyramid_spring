package com.demo.service;

import com.demo.dto.PyramidDTO;
import com.demo.exception.NoPathException;

import java.util.List;

public interface PyramidDescentServiceImpl {


    List<int[][]> produceData();
    PyramidDTO dataToList();
    List<String> descentStart() throws NoPathException;
    boolean findTargetProductPath(int[][] pyramid, int targetProduct, int row, int col, long currentProduct, List<Integer> path, StringBuilder directions);
    int[][] readPyramidFromFile(String fileName) throws NoPathException;
    int maxNumberFiles();

    }
