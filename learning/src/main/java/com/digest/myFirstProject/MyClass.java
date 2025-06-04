package com.digest.myFirstProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClass {

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private TimeService timeService;

    @GetMapping("/abc")
    public String SayHello() {
        return greetingService.getGreeting();
    }

    @GetMapping("/time")
    public String showTime() {
        return timeService.getCurrentTime();
    }
}
