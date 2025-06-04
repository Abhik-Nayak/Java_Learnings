package com.digest.myFirstProject;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class TimeService {
    public String getCurrentTime() {
        return "Current time: " + LocalTime.now().toString();
    }
}