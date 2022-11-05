package com.id.colombiancars.controller;


import com.id.colombiancars.common.VehicleNotFoundException;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.repository.TicketRepository;
import com.id.colombiancars.repository.VehicleRepository;
import com.id.colombiancars.request.EntryRequest;
import com.id.colombiancars.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(value = "/registerEntry")
    public ResponseEntity<Vehicle> registerEntry (@RequestBody EntryRequest entryRequest) {
        return new ResponseEntity<>(vehicleService.registerEntry(entryRequest), HttpStatus.CREATED);

    }

    @PutMapping(value = "/registerDeparture/{vehicleLicensePlate}")
    ResponseEntity<Vehicle> registerDeparture(@PathVariable String vehicleLicensePlate){

        return new ResponseEntity<>(vehicleService.registerDeparture(vehicleLicensePlate), HttpStatus.OK);
    }
}
