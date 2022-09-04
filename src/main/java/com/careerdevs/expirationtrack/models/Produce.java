package com.careerdevs.expirationtrack.models;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    private boolean isExpired;




    @ManyToOne

    @JoinColumn(name = "tracker_id", referencedColumnName = "id")
    // include a json property
    private Tracker tracker;

    public Produce() {
    }

    public Produce(Tracker tracker, Long id, String name, Long quantity, String type, LocalDate expirationDate, boolean isExpired) {
        this.tracker = tracker;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.expirationDate = expirationDate;
        this.isExpired = isExpired;
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

    public boolean getIsExpired() {
        return isExpired;
    }


    // creating my boolean value for my expiration
    public void setExpired() {
      if(expirationDate ==null){
          this.isExpired = true;
          return;
      }
        LocalDate currentDate = LocalDate.now();

        System.out.println(currentDate);
        // refactor
          if(expirationDate.isBefore(currentDate)) {
               this.isExpired= true;
//            return;
         }
    }


    public Tracker getTracker() {
        return tracker;
    }
    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

}
