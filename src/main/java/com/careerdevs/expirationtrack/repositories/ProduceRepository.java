package com.careerdevs.expirationtrack.repositories;

import com.careerdevs.expirationtrack.models.Produce;
import com.careerdevs.expirationtrack.models.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
public interface ProduceRepository extends JpaRepository<Produce,Long>{
    List<Produce> findAllByTracker_id(long id);
    List<Produce> findByName(String name);
    List<Produce> findByQuantity(String quantity);
    List<Produce> findByType(String type);

    List<Produce> findByExpirationDate(Date expirationDate);


    List<Produce> deleteAllByTracker_id(long id);
    List<Produce> deleteByName(String name);
    List<Produce> deleteByQuantity(String quantity);
    List<Produce> deleteByType(String Type);

}
