// src/test/java/com/example/demo/SecurityConfigTest.java
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetRepos_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/repos"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenGetHello_thenOk() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}