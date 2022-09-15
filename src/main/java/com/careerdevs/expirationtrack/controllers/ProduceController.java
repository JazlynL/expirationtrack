package com.careerdevs.expirationtrack.controllers;

import com.careerdevs.expirationtrack.models.Produce;
import com.careerdevs.expirationtrack.models.Tracker;
import com.careerdevs.expirationtrack.repositories.ProduceRepository;
import com.careerdevs.expirationtrack.repositories.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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
        try {
            // check if the tracker Id is available.
            Optional<Tracker> trackerId = trackerRepository.findById(id);
            // If the tracker Id is empty or not found
            if (trackerId.isEmpty()) {
                // we want to throw an HTTP error exception
                return new ResponseEntity<>("This is  not found", HttpStatus.NOT_FOUND);
            }
            // with request body we set the produce to a tracker , we use the trackerId get Method from The Tracker model
            produce.setTracker(trackerId.get());
            // then we set the newProduce to  the repo using the save method
            Produce newProduce = produceRepository.save(produce);
            // then we will return it.
            return new ResponseEntity<>(newProduce, HttpStatus.CREATED);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // getting produce by the id itself wether its tracker or produce id
    @GetMapping("/{produceid}")
    public ResponseEntity<?> getbyProduceId(@PathVariable("produceid") String id) {
        try {

            Optional<Produce> findProduce = produceRepository.findById(Long.parseLong(id));
            if (findProduce.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(findProduce, HttpStatus.OK);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tracker/{trackerid}")
    public ResponseEntity<List<Produce>> getbyTrackerId(@PathVariable("trackerid") Long id) {
        try {
            List<Produce> foundByTrackerID = produceRepository.findAllByTracker_id(id);
            if (foundByTrackerID.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(foundByTrackerID, HttpStatus.OK);


        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{field}/{value}")
    public ResponseEntity<?> getProduceByField(@PathVariable() String field, @PathVariable() String value) {
        try {
            List<Produce> foundProduce = null;
            field = field.toLowerCase();

            switch (field) {
                case "name" -> foundProduce = produceRepository.findByName(value);
                case "quantity" -> foundProduce = produceRepository.findByQuantity(value);
                case "type" -> foundProduce = produceRepository.findByType(value);
            }
            if (foundProduce.isEmpty() || foundProduce == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, " Nothing was found at this endpoint.");
            }
            return new ResponseEntity<>(foundProduce, HttpStatus.OK);


        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // create an end point the will fetch is expired = false , and the name of the produce.


    // getting all produce.
    @GetMapping("/all")
    public ResponseEntity<?> getAllProduce(){
        List<Produce> foundProduce = produceRepository.findAll();
        // maybe use this line of code because this cannot be empty
        if(foundProduce.isEmpty()){
            throw  new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundProduce,HttpStatus.OK);
    }

    // deleting by fields.
    @DeleteMapping("/{field}/{value}")
    public ResponseEntity<?> deleteProduceByField(@PathVariable String field, @PathVariable String value) {
        try {
            List<Produce> deletedProduce = null;
            field = field.toLowerCase();

            switch (field) {
                case "name" -> deletedProduce = produceRepository.deleteByName(value);
                case "quantity" -> deletedProduce = produceRepository.deleteByQuantity(value);

            }

            if (deletedProduce.isEmpty() || deletedProduce == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok("Successfully deleted the produce " + deletedProduce);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //deleting by  ids
    @DeleteMapping("/produceid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            Optional<Produce> deleteProduceById = produceRepository.findById(Long.parseLong(id));
            if (deleteProduceById.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            produceRepository.deleteById(Long.parseLong(id));

            return new ResponseEntity<>(deleteProduceById, HttpStatus.OK);


        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // creating an All endpoint

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllProduce() {
        Long foundProduce = produceRepository.count();
        if (foundProduce == 0) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        produceRepository.deleteAll();
        return ResponseEntity.ok("All the produce was successfully deleted, This is how much that was deleted " + foundProduce);

    }

    //deleteMethod For Expired Items..
    // get expirationdate by query , lets see if it works
    @GetMapping("/getisExpired")
    public List<Produce> getByExpirationDate(LocalDate localDate){

        return produceRepository.findAllByExpirationDate(localDate);

    }

    // check fridge/expiration , request
    // this route will find all the pr
    // saving unchanged items as well.




    @PostMapping("/updateExpiration")
    public ResponseEntity<String> fridgeUpdated(){

      // we want to find all the none expired items which will be everything in my database
        List<Produce> foundItems = produceRepository.findAllByIsExpired(false);
        System.out.println(foundItems);




        for ( Produce item : foundItems) {

                item.setExpired();
                produceRepository.save(item);



        }
        return new ResponseEntity<>("Produce Updated", HttpStatus.OK);
    }

    /// create a delete endpoint.


    // kill a server port    kill -9 $(lsof -ti:3000)



    // always use request body for updating information
    // updating produce might have to refaxctor.
    @PostMapping("/produce/{id}")
    public ResponseEntity<?> updatequantityProduce(@RequestBody Produce produce,@PathVariable Long id
    ) {
        try {
            // we create this line of code to avoid using the Optional class
           Produce findId =   produceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

         //
           if(produce.getName()!= null){
               findId.setName(produce.getName());
           }
           if(produce.getQuantity()!= null){
               findId.setQuantity(produce.getQuantity());
           }
           if(produce.getType()!= null){
               findId.setType(produce.getType());
           }
           if(produce.getClass()!= null){
               findId.setExpirationDate(produce.getExpirationDate());
           }

           produceRepository.save(findId);


           return ResponseEntity.ok(findId);


        } catch (HttpClientErrorException e) {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
