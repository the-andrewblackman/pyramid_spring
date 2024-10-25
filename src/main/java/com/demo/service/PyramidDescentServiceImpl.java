package com.demo.service;

import com.demo.exception.NoPathException;

import java.util.List;

public interface PyramidDescentServiceImpl {

    List<String> descentStart() throws NoPathException;
    boolean findTargetProductPath(int[][] pyramid, int targetProduct, int row, int col, long currentProduct, List<Integer> path, StringBuilder directions);

}
