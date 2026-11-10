package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest(HelloController.class) // Uncomment this line if you want to test only
// the HelloController since we have authorize in hello controller then using spring boot test is
// recommended for full context loading
@TestPropertySource(properties = {"message=TestMessage"})
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello_withName() throws Exception {
        mockMvc.perform(get("/hello").param("name", "sarvesh"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello from sarvesh"));
    }

    @Test
    public void testHello_withoutName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("TestMessage"));
    }

}
