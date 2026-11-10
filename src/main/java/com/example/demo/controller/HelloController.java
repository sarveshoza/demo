package com.example.demo.controller;

import com.example.demo.entity.UserHelloEntity;
import com.example.demo.service.UserHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private static final int MIB = 1024 * 1024;
    private static final int MAX_SPIKE_MIB = 512;
    private static final List<byte[]> RETAINED_HEAP = new ArrayList<>();

    private final UserHelloService userHelloService;

    @Value("${message}")
    private String message;

    @Value("${demo.heap.enabled:false}")
    private boolean heapDemoEnabled;

    @Autowired
    public HelloController(UserHelloService userHelloService) {
        this.userHelloService = userHelloService;
    }

    @GetMapping("/hello")
    public String hello(String name) {
            return userHelloService.findByUserName(name).stream().findFirst()
                    .map(UserHelloEntity::getUserHello)
                    .orElse(message);
        }

    @GetMapping("/demo/heap/leak")
    public String leakHeap() {
        requireHeapDemoEnabled();
        synchronized (RETAINED_HEAP) {
            RETAINED_HEAP.add(new byte[MIB]);
            return "Retained " + RETAINED_HEAP.size() + " MiB in this JVM";
        }
    }

    @GetMapping("/demo/heap/spike")
    public String allocateHeapSpike(@RequestParam(defaultValue = "256") int megabytes) {
        requireHeapDemoEnabled();
        if (megabytes < 1 || megabytes > MAX_SPIKE_MIB) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "megabytes must be between 1 and " + MAX_SPIKE_MIB);
        }

        List<byte[]> allocations = new ArrayList<>(megabytes);
        for (int i = 0; i < megabytes; i++) {
            allocations.add(new byte[MIB]);
        }
        return "Allocated " + allocations.size() + " MiB for this request";
    }

    private void requireHeapDemoEnabled() {
        if (!heapDemoEnabled) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Heap demo endpoints are disabled");
        }
    }
}
