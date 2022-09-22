package com.careerdevs.expirationtrack.repositories;

import com.careerdevs.expirationtrack.models.Produce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
@Transactional
public interface ProduceRepository extends JpaRepository<Produce,Long>{
    List<Produce> findAllByTracker_id(long id);

    List<Produce> findByName(String name);
    List<Produce> findByQuantity(String quantity);
    List<Produce> findByType(String type);




    // create a query to find expiration date .....
    //@Query("select p from Produce p where p.isExpired = ?1")

    // when we search through our data we want to find the items that are not expired
    // and the items  that are found are going to be set to expired
    // expiredDate < currentDate
    // getting another query
    List <Produce> findAllByExpirationDate(LocalDate expirationDate);
    
    List<Produce> findAllByIsExpired(boolean isExpired);

    @Override
    void delete(Produce entity);

    List<Produce> deleteAllByTracker_id(long id);
    List<Produce> deleteByName(String name);
    List<Produce> deleteByQuantity(String quantity);
    List<Produce> deleteByType(String Type);

}
