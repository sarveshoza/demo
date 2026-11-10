package com.example.demo;// ...existing code...
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// ...existing code...

import com.example.demo.SpringBootDemoApplication;
import org.junit.jupiter.api.Test;

public class SpringBootDemoApplicationTest {
    @Test
    void mainMethodRunsWithoutException() {
        assertDoesNotThrow(() -> SpringBootDemoApplication.main(new String[]{}));
    }
}
// ...existing code...
