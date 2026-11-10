package com.example.demo.controller;

import com.example.demo.service.UserHelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class HelloControllerHeapTest {

    private HelloController controller;

    @BeforeEach
    void setUp() {
        controller = new HelloController(mock(UserHelloService.class));
        ReflectionTestUtils.setField(controller, "heapDemoEnabled", true);
    }

    @Test
    void leakHeapRetainsAllocationsAcrossCalls() {
        int retainedBefore = retainedMiB(controller.leakHeap());
        int retainedAfter = retainedMiB(controller.leakHeap());

        assertEquals(retainedBefore + 1, retainedAfter);
    }

    @Test
    void allocateHeapSpikeAcceptsSmallAllocation() {
        assertEquals("Allocated 1 MiB for this request", controller.allocateHeapSpike(1));
    }

    @Test
    void allocateHeapSpikeRejectsOutOfRangeAllocation() {
        assertThrows(ResponseStatusException.class, () -> controller.allocateHeapSpike(513));
    }

    @Test
    void heapDemoEndpointsAreDisabledByDefault() {
        ReflectionTestUtils.setField(controller, "heapDemoEnabled", false);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, controller::leakHeap);

        assertEquals(404, exception.getStatusCode().value());
    }

    private int retainedMiB(String result) {
        return Integer.parseInt(result.substring("Retained ".length(), result.indexOf(" MiB")));
    }
}
