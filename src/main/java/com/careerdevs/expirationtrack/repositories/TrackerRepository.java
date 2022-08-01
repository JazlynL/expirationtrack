package com.careerdevs.expirationtrack.repositories;

import com.careerdevs.expirationtrack.models.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackerRepository extends JpaRepository<Tracker,Long> {
    
    List <Tracker> findAllByName(String name);
    List<Tracker> findByName (String name);
    List<Tracker> findByEmail(String email);
    List<Tracker> findByAge(Long age);


    Optional<Tracker> findById(Long id);


}
