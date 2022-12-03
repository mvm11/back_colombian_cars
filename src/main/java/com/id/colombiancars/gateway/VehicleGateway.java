package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.request.VehicleRequest;

import java.util.List;

public interface VehicleGateway {

    Vehicle findVehicleById(Long vehicleId);
    Vehicle findVehicleByLicensePlate(String licensePlate);
    List<Vehicle> findAllVehicles();
    Vehicle registerEntry(VehicleRequest vehicleRequest);
    Vehicle registerDeparture(String licensePlate);
    void deleteVehicle(Long vehicleId);

}
