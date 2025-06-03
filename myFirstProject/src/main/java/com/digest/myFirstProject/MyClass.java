package com.digest.myFirstProject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MyClass {

    @GetMapping("/abc") // annotation
    public String SayHello() { // method
        return "Hello, World!";
    }
}
