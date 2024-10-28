package com.demo.service;

import com.demo.dto.PyramidDTO;
import com.demo.entity.PyramidData;
import com.demo.entity.PyramidNums;
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
public class PyramidDescentService implements PyramidDescentServiceImpl {
    private PyramidNums pyramidNums = new PyramidNums();
    private PyramidData pyramidData = new PyramidData(null,null, 0,0,
            0,0,true,new ArrayList<>(),new ArrayList<>(),null,null);

    // Loads files
    public List<int[][]> produceData() {

        List<int[][]> list = new ArrayList<>();

        while(pyramidData.getFileIndex() < pyramidData.getFiles().size()) {
            if (pyramidData.getFiles() == null || pyramidData.getFiles().isEmpty()) {
                throw new IllegalStateException("No files found in directory.");
            }
            // Get the current file path
            Path currentFile = pyramidData.getFiles().get(pyramidData.getFileIndex());

            // Read pyramid from file
            int[][] pyramid = readPyramidFromFile(currentFile.toString());
            list.add(pyramid);
            // Update index for next call
            pyramidData.setFileIndex(pyramidData.getFileIndex()+1);
        }

        return list;
    }
    public PyramidDTO dataToList(){

        if(pyramidData.isSwitcher()) {
            try {
                // Initialize the list of files from the directory, and stores them.
                pyramidData.setFiles(Files.list(Paths.get("/Users/user/Desktop/pyramidDescentData"))
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pyramidData.setListOfFiles(produceData());
            pyramidData.setSwitcher(false);
        }

        pyramidData.setPyramidArr(pyramidData.getListOfFiles().get(pyramidData.getIndex()));
        pyramidData.setTarget(pyramidData.getTargetProductList().get(pyramidData.getIndex()));

        if(pyramidData.getIndex() < pyramidData.getListOfFiles().size()-1) {
            pyramidData.setIndex(pyramidData.getIndex()+1);
        }else{
            pyramidData.setIndex(0);
        }

        if(!pyramidNums.getPyramidNums().isEmpty()){
            pyramidNums.clearPyramidNums();
        }

        for(int[] singleArray:pyramidData.getPyramidArr()){
            for(int num:singleArray){
                pyramidNums.addPyramidNums(num);
            }
        }

        descentStart();

        return new PyramidDTO(pyramidNums.getPyramidNums(), pyramidData.getTarget(), pyramidData.getCalculatedDirections().toString());
    }
    public List<String> descentStart() throws NoPathException {
        if (pyramidData.getPyramidArr() == null) {
            throw new NoPathException("Error reading the pyramid file");
        }

        if (!findTargetProductPath(pyramidData.getPyramidArr(), pyramidData.getTarget(), 0, 0, 1, new ArrayList<>(), new StringBuilder())) {
            throw new NoPathException("No path found for target product: " + pyramidData.getTargetProductList().get(pyramidData.getIndex()-1));
        }

        // add answer to list
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(pyramidData.getCalculatedTargetProduct()));
        list.add(pyramidData.getCalculatedDirections().toString());

        return list;
    }
    public boolean findTargetProductPath(int[][] pyramid, int targetProduct, int row, int col, long currentProduct, List<Integer> path, StringBuilder directions) {

        currentProduct *= pyramid[row][col];
        path.add(pyramid[row][col]);

        // If at bottom row, check if the product equals the target
        if (row == pyramid.length - 1) {
            if (currentProduct == targetProduct) {
                pyramidData.setCalculatedDirections(directions);
                pyramidData.setCalculatedTargetProduct(targetProduct);
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
    public int[][] readPyramidFromFile(String fileName) throws NoPathException{

        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            line = br.readLine();
            if (line != null && line.trim().startsWith("Target:")) {
                pyramidData.getTargetProductList().add(Integer.parseInt(line.split(":")[1].trim()));
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

    public int maxNumberFiles(){
        return pyramidData.getListOfFiles().size();
    }

}
