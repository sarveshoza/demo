package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_hello")
public class UserHelloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "user_hello", length = 1000)
    private String userHello;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserHello() { return userHello; }
    public void setUserHello(String userHello) { this.userHello = userHello; }
}