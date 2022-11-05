package com.id.colombiancars.service;

import com.id.colombiancars.common.VehicleNotFoundException;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.gateway.VehicleGateway;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.repository.TicketRepository;
import com.id.colombiancars.repository.VehicleRepository;
import com.id.colombiancars.request.EntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VehicleService implements VehicleGateway {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Vehicle findVehicleById(Long vehicleId) {
        return getVehicleById(vehicleId);
    }

    private Vehicle getVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(VehicleNotFoundException::new);
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        return getVehicleByLicensePlate(licensePlate);
    }

    private Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(licensePlate))
                .findFirst()
                .orElseThrow(VehicleNotFoundException::new);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle registerEntry(EntryRequest entryRequest) {

        Cell cell = new Cell();
        Ticket ticket = new Ticket();

        cell.setOccupied(true);
        ticket.setEntryHour(new Date());

        cell = cellRepository.save(cell);
        ticketRepository.save(ticket);

        Vehicle vehicle = new Vehicle(entryRequest);
        vehicle.setCell(cell);
        vehicle.setTicket(ticket);

        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle registerDeparture(String licensePlate) {

        Vehicle vehicle = getVehicleByLicensePlate(licensePlate);
        vehicle.getCell().setOccupied(false);
        vehicle.getTicket().setDepartureHour(new Date());
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicleRepository.delete(vehicle);
    }
}
