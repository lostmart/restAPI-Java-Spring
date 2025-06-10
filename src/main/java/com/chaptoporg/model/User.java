package com.chaptoporg.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
