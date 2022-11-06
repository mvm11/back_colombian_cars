package com.id.colombiancars.service;

import com.id.colombiancars.common.*;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.gateway.VehicleGateway;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.repository.TicketRepository;
import com.id.colombiancars.repository.VehicleRepository;
import com.id.colombiancars.request.EntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VehicleService implements VehicleGateway {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CellService cellService;


    @Override
    public Vehicle findVehicleById(Long vehicleId) {
        return getVehicleById(vehicleId);
    }

    private Vehicle getVehicleById(Long vehicleId) {
        return getVehicle(vehicleId);
    }

    private Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle", "id", vehicleId));
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        return getVehicleByLicensePlate(licensePlate);
    }

    private Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(licensePlate))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Vehicle", "licensePlate", licensePlate));
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle registerEntry(EntryRequest entryRequest) {
        Random random = new SecureRandom();

        List<Cell> cells = cellRepository.findAll();
        List<Cell> availableCells = getAvailableCells(cells);
        List<Vehicle> vehicles = vehicleRepository.findAll();

        Vehicle vehicleValidated = getVehicleValidated(entryRequest, vehicles);

        if(isParking(vehicleValidated)){
            throwParkingException();
        }

        if(validateOldVehicle(vehicleValidated)){
            return setValuesForOldVehicle(entryRequest, random, availableCells, vehicleValidated);
        }

        if (validateAvailableCells(availableCells)) {

            return setValuesForNewVehicle(entryRequest, random, availableCells);
        }

        throw new ParkingException((HttpStatus.INTERNAL_SERVER_ERROR), "There aren't cells available");
    }

    private boolean validateAvailableCells(List<Cell> availableCells) {
        return !availableCells.isEmpty();
    }

    private boolean validateOldVehicle(Vehicle vehicleValidated) {
        return vehicleValidated.getLicensePlate() != null;
    }

    private void throwParkingException() {
        throw new ParkingException(HttpStatus.BAD_REQUEST, "Actually that car is parking");
    }

    private boolean isParking(Vehicle vehicleValidated) {
        return vehicleValidated.isParking();
    }

    private Vehicle setValuesForNewVehicle(EntryRequest entryRequest, Random random, List<Cell> availableCells) {
        Vehicle vehicle = new Vehicle();
        Ticket ticket = new Ticket();
        ticket.setEntryHour(new Date());
        vehicle.setOwnerName(entryRequest.getOwnerName());
        vehicle.setOwnerLastname(entryRequest.getOwnerLastname());
        vehicle.setOwnerDni(entryRequest.getOwnerDni());
        vehicle.setLicensePlate(entryRequest.getLicensePlate());
        vehicle.setType(entryRequest.getType());
        vehicle.setParking(true);
        vehicle.setCell(getRandomCell(random, availableCells));
        vehicle.getCell().setOccupied(true);
        ticket.setAssignedCell(vehicle.getCell().getCellName());
        ticket.setVehicle(vehicle);
        ticketRepository.save(ticket);
        return vehicleRepository.save(vehicle);
    }

    private Vehicle setValuesForOldVehicle(EntryRequest entryRequest, Random random, List<Cell> availableCells, Vehicle vehicleValidated) {
        Ticket ticket = new Ticket();
        ticket.setEntryHour(new Date());
        vehicleValidated.setOwnerName(entryRequest.getOwnerName());
        vehicleValidated.setOwnerLastname(entryRequest.getOwnerLastname());
        vehicleValidated.setOwnerDni(entryRequest.getOwnerDni());
        vehicleValidated.setLicensePlate(entryRequest.getLicensePlate());
        vehicleValidated.setType(entryRequest.getType());
        vehicleValidated.setParking(true);
        vehicleValidated.setCell(getRandomCell(random, availableCells));
        vehicleValidated.getCell().setOccupied(true);
        ticket.setAssignedCell(vehicleValidated.getCell().getCellName());
        ticket.setVehicle(vehicleValidated);
        ticketRepository.save(ticket);
        return vehicleRepository.save(vehicleValidated);
    }

    private Vehicle getVehicleValidated(EntryRequest entryRequest, List<Vehicle> vehicles) {
        return vehicles.stream().filter(vehicle -> vehicle.getLicensePlate()
                        .equalsIgnoreCase(entryRequest.getLicensePlate()))
                .findFirst()
                .orElse(new Vehicle());
    }

    private Cell getRandomCell(Random random, List<Cell> availableCells) {
        return availableCells.get(random.nextInt(availableCells.size()));
    }




    private List<Cell> getAvailableCells(List<Cell> cells) {
        return cells.stream()
                .filter(cell -> !cell.isOccupied()).
                collect(Collectors.toList());
    }

    @Override
    public Vehicle registerDeparture(String licensePlate) {

        Vehicle vehicle = getVehicleByLicensePlate(licensePlate);

        if(validateCarParking(vehicle)){
            throwNotParkingException();
        }

        vehicle.getCell().setOccupied(false);
        cellRepository.save(vehicle.getCell());
        vehicle.setParking(false);
        vehicle.setCell(null);
        Ticket newTicket = getTicket(vehicle);

        newTicket.setDepartureHour(new Date());
        vehicle.getTickets().removeIf(ticket -> ticket.getDepartureHour()==null);
        vehicle.getTickets().add(newTicket);

        return vehicleRepository.save(vehicle);
    }

    private static Ticket getTicket(Vehicle vehicle) {
        return vehicle.getTickets().stream().filter(ticket -> ticket.getDepartureHour() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ticket", "", ""));
    }

    private static void throwNotParkingException() {
        throw new ParkingException(HttpStatus.BAD_REQUEST, "Actually that car is not parking");
    }

    private static boolean validateCarParking(Vehicle vehicle) {
        return !vehicle.isParking();
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicleRepository.delete(vehicle);
    }
}
