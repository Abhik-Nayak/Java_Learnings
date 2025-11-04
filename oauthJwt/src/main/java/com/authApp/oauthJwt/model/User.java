package com.authApp.oauthJwt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false, unique=true)
    private String username;

    @Email
    @Column(nullable=false, unique=true)
    private String email;

    @NotBlank
    @Column(nullable=false)
    private String password; // will hash later (Day 2)

    @Column(nullable=false)
    private String role = "USER"; // "USER" or "ADMIN"

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    // constructors, getters and setters
    public User() {}
    // getters/setters omitted for brevity — generate via IDE or Lombok
}
