package com.demo.service;

import com.demo.exception.NoPathException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PyramidDescentService {
    static int targetProduct;
    static StringBuilder directions;
    static int[][] data;
    private static List<Path> files;
    private static int fileIndex = 0;
    static int index = 0;

    static int descentIndex = 0;

    static List<int[][]> listOfFiles = new ArrayList<>();

    static boolean switcher = true;


    static {
        try {
            // Initialize the list of files from the directory, and stores them.
            files = Files.list(Paths.get("/Users/{username}/Desktop/pyramidDescentData"))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Grabs a new file with every run
    public List<int[][]> produceData() {
        int i = 1;
        Path currentFile = files.get(fileIndex);
        int[][] pyramid = readPyramidFromFile(currentFile.toString());
        List<int[][]> list = new ArrayList<>();
        list.add(pyramid);

        while(fileIndex < files.size()) {
            if (files == null || files.isEmpty()) {
                throw new IllegalStateException("No files found in directory.");
            }
            // Get the current file path
            currentFile = files.get(fileIndex);

            // Read pyramid from file
            pyramid = readPyramidFromFile(currentFile.toString());
            list.add(pyramid);
            // Update index for next call
            fileIndex++;
        }
        return list;
    }
    public List<Integer> dataToList(){

        if(this.switcher) {
            this.listOfFiles = produceData();
            this.switcher = false;
        }

        List<Integer> dataList = new ArrayList<>();

        int[][] arr = listOfFiles.get(this.index);
        this.index = this.descentIndex;
        this.index++;

        for(int[] singleArray:arr){
            for(int num:singleArray){
                dataList.add(num);
            }
        }


        return dataList;
    }
    public List<String> descentStart() throws NoPathException {

        int[][] pyramid = listOfFiles.get(this.descentIndex);

        if (pyramid == null) {
            throw new NoPathException("Error reading the pyramid file");
        }

        List<Integer> path = new ArrayList<>();
        directions = new StringBuilder();

        if (!findTargetProductPath(pyramid, targetProduct, 0, 0, 1, path, directions)) {
            throw new NoPathException("No path found for target product: " + targetProduct);
        }
        // add answer to list
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(this.targetProduct));
        list.add(this.directions.toString());

        this.index++;

        return list;
    }
    public boolean findTargetProductPath(int[][] pyramid, int targetProduct, int row, int col, long currentProduct, List<Integer> path, StringBuilder directions) {

        currentProduct *= pyramid[row][col];
        path.add(pyramid[row][col]);

        // If at bottom row, check if the product equals the target
        if (row == pyramid.length - 1) {
            if (currentProduct == targetProduct) {
                this.directions = directions;
                this.targetProduct = targetProduct;
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
     private int[][] readPyramidFromFile(String fileName) throws NoPathException{
        System.out.println(fileName);
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            line = br.readLine();
            if (line != null && line.trim().startsWith("Target:")) {
                targetProduct = Integer.parseInt(line.split(":")[1].trim());
            } else {
                throw new NoPathException("First line does not start with 'Target:'.");
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
            throw new NoPathException("File error. Please reformat and try again.");
        }

        if (rows.isEmpty()) {
            throw new NoPathException("Pyramid is empty.");
        }

        // Convert to int[][]
        return rows.toArray(new int[0][]);
    }

}
