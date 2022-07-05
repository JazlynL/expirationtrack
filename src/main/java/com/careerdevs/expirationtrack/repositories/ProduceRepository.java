package com.careerdevs.expirationtrack.repositories;

import com.careerdevs.expirationtrack.models.Produce;
import com.careerdevs.expirationtrack.models.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduceRepository extends JpaRepository<Produce,Long>{
    List<Produce> findAllByTracker_id(long id);
}
