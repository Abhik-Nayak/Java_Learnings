package com.authApp.oauthJwt.model;

import jakarta.persistence.*; //ORM
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private boolean completed = false;

    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY) //Keep relationship owner as LAZY to avoid loading the user unless needed.
    @JoinColumn(name = "owner_id")
    private User owner;

    // constructors, getters and setters
    public Task() {}
    // getters/setters omitted for brevity
}
