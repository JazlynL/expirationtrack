package com.careerdevs.expirationtrack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Tracker {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Long age;


    @OneToMany(mappedBy = "tracker",fetch = FetchType.LAZY)
    @JsonIncludeProperties({"name","id"})
    private Set<Produce> produce;


    public Tracker() {
    }

    public Tracker(Long id, String name, String email, Long age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Set<Produce> getProduce() {
        return produce;
    }
}
