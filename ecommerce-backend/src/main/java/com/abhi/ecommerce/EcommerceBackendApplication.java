package com.abhi.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
	}

}


// This is the entry point of your Spring Boot application. When you run this class, Spring Boot:
// Starts the embedded server (Tomcat),
// Scans components (@Component, @Service, @Repository),
// Configures your application automatically.
// Run the app and you should see Started EcommerceBackendApplication in the console.