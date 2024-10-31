package com.demo.service;

import com.demo.dto.PyramidDTO;
import com.demo.entity.PyramidData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PyramidDescentServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PyramidDescentService pyramidDescentService;

    @MockBean
    private PyramidData pyramidData;

    @BeforeEach
    void setup() {
        assertNotNull(pyramidDescentService, "PyramidDescentService is not correctly initialized.");
        assertNotNull(pyramidData, "PyramidData is not correctly mocked.");
    }

    @Test
    void testProduceDataWithNoFiles() {
        // Simulate scenario with no files
        when(pyramidData.getFiles()).thenReturn(Collections.emptyList());
        // Assert that an exception is thrown
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            pyramidDescentService.produceData();
        });

        assertEquals("No files found in directory.", exception.getMessage());
    }

    @Test
    void testProduceDataWithFiles() {

            // Prepare the test environment with mock files
            Path filePath1 = Paths.get("file1.txt");
            Path filePath2 = Paths.get("file2.txt");

            // Set up pyramidData to avoid directory listing
            when(pyramidData.getFiles()).thenReturn(Arrays.asList(filePath1, filePath2));
            when(pyramidData.isSwitcher()).thenReturn(true);

            // Mock the list of files directly for produceData call
            when(pyramidData.getFileIndex()).thenReturn(0, 1, 2);
            doReturn(new int[][]{{1}, {2, 3}}).when(pyramidDescentService).readPyramidFromFile("file1.txt");
            doReturn(new int[][]{{4}, {5, 6}}).when(pyramidDescentService).readPyramidFromFile("file2.txt");

            // Call dataToList(), which should in turn call produceData()
            PyramidDTO result = pyramidDescentService.dataToList();

            // Verify produceData was called to populate listOfFiles
            assertEquals(2, pyramidData.getListOfFiles().size(), "List of files should contain two items.");

            // Validate other aspects of PyramidDTO if necessary
            assertNotNull(result, "Resulting PyramidDTO should not be null.");
            assertEquals(pyramidData.getTarget(), result.getTarget(), "Target should match the expected value.");
    }


    @Test
    void dataToList() {
    }

    @Test
    void descentStart() {
    }

    @Test
    void findTargetProductPath() {
    }

    @Test
    void readPyramidFromFile() {
    }

    @Test
    void maxNumberFiles() {
    }
}