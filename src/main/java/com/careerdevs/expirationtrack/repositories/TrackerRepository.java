package com.careerdevs.expirationtrack.repositories;

import com.careerdevs.expirationtrack.models.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackerRepository extends JpaRepository<Tracker,Long> {
    
    List <Tracker> findAllByName(String name);


}
