package com.careerdevs.expirationtrack.controllers;

import com.careerdevs.expirationtrack.repositories.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {
 @Autowired
    private TrackerRepository trackerRepository;

 @GetMapping()
}
