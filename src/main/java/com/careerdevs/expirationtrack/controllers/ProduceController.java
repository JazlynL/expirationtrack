package com.careerdevs.expirationtrack.controllers;

import com.careerdevs.expirationtrack.models.Produce;
import com.careerdevs.expirationtrack.models.Tracker;
import com.careerdevs.expirationtrack.repositories.ProduceRepository;
import com.careerdevs.expirationtrack.repositories.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/produce")
public class ProduceController {

    @Autowired
    private TrackerRepository trackerRepository;


    @Autowired
    private ProduceRepository produceRepository;



// uploading the produce to the id
    @PostMapping("/{trackerid}")
    public ResponseEntity<?> uploadProduce(@PathVariable("trackerid") Long id, @RequestBody Produce produce){
        try{
            Optional<Tracker> trackerId = trackerRepository.findById(id);
            if(trackerId.isEmpty()){
                return  new ResponseEntity<>("This is  not found", HttpStatus.NOT_FOUND);
            }
            produce.setTracker(trackerId.get());
            Produce newProduce = produceRepository.save(produce);
            return  new ResponseEntity<>(newProduce,HttpStatus.CREATED);

        }catch(HttpClientErrorException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // getting produce by the id itself wether its tracker or produce id

    @GetMapping("/{produceid}")
    public  ResponseEntity<?> getbyProduceId(@PathVariable ("produceid") String id){
        try{

           Optional<Produce> findProduce = produceRepository.findById(Long.parseLong(id));
            if(findProduce.isEmpty()){
                throw  new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            return  new ResponseEntity<>( findProduce, HttpStatus.OK);

        }catch(HttpClientErrorException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tracker/{trackerid}")
    public  ResponseEntity<List<Produce>> getbyTrackerId(@PathVariable ("trackerid") String id){
        try{
             List <Produce> foundByTrackerID = produceRepository.findAllByTracker_id(Long.parseLong(id));
              if(foundByTrackerID.isEmpty()){
                  throw  new HttpClientErrorException(HttpStatus.NOT_FOUND);
              }
              return  new ResponseEntity<>(foundByTrackerID,HttpStatus.OK);


        }catch(HttpClientErrorException e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
