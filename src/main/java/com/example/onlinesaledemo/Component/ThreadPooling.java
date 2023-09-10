package com.example.onlinesaledemo.Component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Getter


public class ThreadPooling {

    private final ExecutorService es = Executors.newFixedThreadPool(500);
}
