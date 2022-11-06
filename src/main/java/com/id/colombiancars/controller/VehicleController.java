package com.id.colombiancars.controller;


import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.request.EntryRequest;
import com.id.colombiancars.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Find All
    @GetMapping(value = "/findAllVehicles")
    public List<Vehicle> findAllBooks(){
        return vehicleService.findAllVehicles();
    }

    @PostMapping(value = "/registerEntry")
    public ResponseEntity<Vehicle> registerEntry (@RequestBody EntryRequest entryRequest) {
        return new ResponseEntity<>(vehicleService.registerEntry(entryRequest), HttpStatus.CREATED);

    }

    @PutMapping(value = "/registerDeparture/{vehicleLicensePlate}")
    ResponseEntity<Vehicle> registerDeparture(@PathVariable String vehicleLicensePlate){

        return new ResponseEntity<>(vehicleService.registerDeparture(vehicleLicensePlate), HttpStatus.OK);
    }
}
