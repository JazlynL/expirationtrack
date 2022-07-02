package com.careerdevs.expirationtrack.controllers;

import com.careerdevs.expirationtrack.models.Tracker;
import com.careerdevs.expirationtrack.repositories.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {
 @Autowired
    private TrackerRepository trackerRepository;

 // we want to have them in a order of create read update delete


    @PostMapping("/")

    public ResponseEntity<?> createTracker(@RequestBody Tracker newTracker){
        try {
            Tracker trackerPerson = trackerRepository.save(newTracker);
            return  new ResponseEntity<>(trackerPerson,HttpStatus.CREATED);

        }catch(HttpClientErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

  // getting all users
    @GetMapping("/")
    public ResponseEntity<List<Tracker>> getAllTrackers(){
       try {
           List<Tracker> allTrackers = trackerRepository.findAll();
           return new ResponseEntity<>(allTrackers,HttpStatus.OK);

       }catch(HttpClientErrorException e){
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }
    //getting user by id.
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrackerById(@PathVariable ("id") String trackerId){
        try {
            Long numOfTracker = Long.parseLong(trackerId);
            Optional<Tracker> foundId = trackerRepository.findById(numOfTracker);
            if(foundId.isEmpty()){
                throw  new HttpClientErrorException(HttpStatus.NOT_FOUND," Id is not found");
            }

            return  new ResponseEntity<>(foundId,HttpStatus.OK);

        }catch(HttpClientErrorException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //deleting users by id

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTrackerById(@PathVariable("id") String id){
     try {
         long deleteTrackerById = Long.parseLong(id);
         Optional<Tracker> foundTracker = trackerRepository.findById(deleteTrackerById);
         if (foundTracker.isEmpty()) {
             throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Id is not found");
         }
         trackerRepository.deleteById(deleteTrackerById);

         return ResponseEntity.ok("This Tracker is deleted: " + deleteTrackerById);
     }catch(HttpClientErrorException e ){
         return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
     }}

    // deleting all users
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllTrackers(){
        try{
            // remember the count line.
            long counted = trackerRepository.count();
            if(counted == 0 ){
                return   ResponseEntity.ok("Nothing  found, nothing to be deleted.");
            }
            trackerRepository.deleteAll();
            return   ResponseEntity.ok("This is how many trackers that got deleted out the database"+ counted);


        }catch(HttpClientErrorException e ){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // updating the users information

    @PutMapping
    public  ResponseEntity<?> updateTrackerInfo(@RequestBody Tracker updatedTracker){
        try {
            Tracker updateTracker = trackerRepository.save(updatedTracker);
            return new ResponseEntity<>(updateTracker,HttpStatus.CREATED);
        }catch( HttpClientErrorException e ){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
