package com.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PyramidDescentService {
    static int targetProduct;

    public static void descentStart(String[] args) {

        String fileName = "/Users/{username}/Desktop/pyramid_sample_input.txt";

        int[][] pyramid = readPyramidFromFile(fileName);

        if (pyramid == null) {
            System.out.println("Error reading the pyramid file.");
            return;
        }

        List<Integer> path = new ArrayList<>();
        StringBuilder directions = new StringBuilder();

        if (!findTargetProductPath(pyramid, targetProduct, 0, 0, 1, path, directions)) {
            System.out.println("No path found for target product: " + targetProduct);
        }
    }
    public static boolean findTargetProductPath(int[][] pyramid, int targetProduct, int row, int col, long currentProduct, List<Integer> path, StringBuilder directions) {

        currentProduct *= pyramid[row][col];
        path.add(pyramid[row][col]);

        // If at bottom row, check if the product equals the target
        if (row == pyramid.length - 1) {
            if (currentProduct == targetProduct) {
                System.out.println(directions);
                return true;
            } else {
                //Backtrack
                path.remove(path.size() - 1);
                return false;
            }
        }

        // Try left child
        directions.append("L");
        if (findTargetProductPath(pyramid, targetProduct, row + 1, col, currentProduct, new ArrayList<>(path), new StringBuilder(directions))) {
            return true;
        }
        // Backtrack
        directions.deleteCharAt(directions.length() - 1);

        // Try right child
        directions.append("R");
        if (findTargetProductPath(pyramid, targetProduct, row + 1, col + 1, currentProduct, new ArrayList<>(path), new StringBuilder(directions))) {
            return true;
        }
        // Backtrack
        directions.deleteCharAt(directions.length() - 1);

        return false;
    }
    private static int[][] readPyramidFromFile(String fileName) {
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            line = br.readLine();
            if (line != null && line.trim().startsWith("Target:")) {
                targetProduct = Integer.parseInt(line.split(":")[1].trim());
            } else {
                System.out.println("First line does not start with 'Target:'.");
                return null;
            }

            // Convert pyramid to int[]
            while ((line = br.readLine()) != null) {
                // Split line into array of strings, then convert to int[]
                String[] numberStrings = line.split(",");
                int[] numberInts = Arrays.stream(numberStrings)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                rows.add(numberInts);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (rows.isEmpty()) {
            System.out.println("Pyramid is empty.");
            return null;
        }

        // Convert to int[][]
        return rows.toArray(new int[0][]);
    }

}
