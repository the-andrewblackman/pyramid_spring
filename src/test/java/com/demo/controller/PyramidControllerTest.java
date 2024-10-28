package com.demo.controller;

import com.demo.dto.PyramidDTO;
import com.demo.service.PyramidDescentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PyramidControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PyramidDescentService pyramidDescentService;

    @Test
    void max() throws Exception {
            given(pyramidDescentService.maxNumberFiles()).willReturn(5);

            mockMvc.perform(get("/max")
                    .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk())
                    .andExpect(content().string("5"));
    }

    @Test
    void data() throws Exception {

            given(pyramidDescentService.dataToList()).willReturn(
                    new PyramidDTO(Arrays.asList(1,2,3), 1200,"LLLR"));

            mockMvc.perform(get("/data")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pyramidNums",hasItems(1,2,3)))
                    .andExpect(jsonPath("$.target",is(1200)))
                    .andExpect(jsonPath("$.calculatedDirections",is("LLLR")));

    }
}