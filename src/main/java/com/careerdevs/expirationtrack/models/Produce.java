package com.careerdevs.expirationtrack.models;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

// category system
// use name field
// use Date class for expiration date
@Entity
public class Produce {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Long quantity;
    private String type;
    // add annotation for date field
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate expirationDate;




    @ManyToOne
    @JoinColumn(name = "tracker_id", referencedColumnName = "id")
    private Tracker tracker;

    public Produce() {
    }

    public Produce(Tracker tracker, Long id, String name, Long quantity, String type, LocalDate expirationDate) {
        this.tracker = tracker;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.expirationDate = expirationDate;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

}
