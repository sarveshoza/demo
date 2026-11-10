package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainDemoTest {
    @Test
    void testSumPositiveNumbers() {
        assertEquals(30, MainDemo.sum(10, 20));
    }

    @Test
    void testSumNegativeNumbers() {
        assertEquals(-30, MainDemo.sum(-10, -20));
    }

    @Test
    void testSumWithZero() {
        assertEquals(10, MainDemo.sum(10, 0));
        assertEquals(0, MainDemo.sum(0, 0));
    }

    @Test
    void testMainMethodRunsWithoutException() {
        assertDoesNotThrow(() -> MainDemo.main(new String[]{}));
    }
}

