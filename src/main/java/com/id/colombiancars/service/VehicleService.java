package com.id.colombiancars.service;

import com.id.colombiancars.common.*;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.entity.Vehicle;
import com.id.colombiancars.gateway.VehicleGateway;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.repository.TicketRepository;
import com.id.colombiancars.repository.UserRepository;
import com.id.colombiancars.repository.VehicleRepository;
import com.id.colombiancars.request.UserRequest;
import com.id.colombiancars.request.VehicleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

@Service
public class VehicleService implements VehicleGateway {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CellService cellService;

    Random random = new SecureRandom();


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
    public Vehicle registerEntry(VehicleRequest vehicleRequest) {

        validateUserFields(vehicleRequest);

        User foundUser = getUserByDni(vehicleRequest);

        if(foundUser.getCell().isHasVehicle()){
            throwParkingException();
        }

        foundUser.getCell().setHasVehicle(true);

        Vehicle vehicle = buildVehicle(vehicleRequest, foundUser);

        Ticket ticket = Ticket.builder()
                .assignedCell(foundUser.getCell().getCellName())
                .entryHour(new Date())
                .vehicle(vehicle)
                .build();

        cellRepository.save(foundUser.getCell());
        ticketRepository.save(ticket);
        vehicleRepository.save(vehicle);

        return vehicle;
    }

    private Vehicle buildVehicle(VehicleRequest vehicleRequest, User foundUser) {
        return vehicleRepository.findAll()
                .stream()
                .filter(vehicle1 -> vehicle1.getLicensePlate().equalsIgnoreCase(vehicleRequest.getLicensePlate()))
                .findFirst()
                .orElse(Vehicle.builder()
                        .licensePlate(vehicleRequest.getLicensePlate())
                        .user(foundUser)
                        .type(vehicleRequest.getType())
                        .build());
    }

    private final Map<String, Function<VehicleRequest, ?>> vehicleRequestValidations = Map.of(
            "ownerDni", VehicleRequest::getOwnerDni,
            "licensePlate", VehicleRequest::getLicensePlate,
            "type", VehicleRequest::getType


    );

    private void validateUserFields(VehicleRequest vehicleRequest) {
        vehicleRequestValidations.entrySet().stream()
                .filter(entry -> entry.getValue().apply(vehicleRequest) == null)
                .map(Map.Entry::getKey)
                .map(field -> String.format("The vehicle's %s cannot be null", field))
                .map(s -> new ParkingException(HttpStatus.BAD_REQUEST, "Null values are not accepted"))
                .findFirst()
                .ifPresent(e -> {
                    throw e;
                });
    }

    private User getUserByDni(VehicleRequest vehicleRequest) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getDni().equalsIgnoreCase(vehicleRequest.getOwnerDni()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User", "dni", vehicleRequest.getOwnerDni()));
    }
    private void throwParkingException() {
        throw new ParkingException(HttpStatus.BAD_REQUEST, "This cell already has a vehicle parked in it");
    }
    @Override
    public Vehicle registerDeparture(String licensePlate) {

        Vehicle vehicle = getVehicleByLicensePlate(licensePlate);
        validateVehicleInCell(vehicle);
        vehicle.getUser().getCell().setHasVehicle(false);
        Ticket foundTicket = getTicket(vehicle);

        foundTicket.setDepartureHour(new Date());
        vehicle.getTickets().removeIf(ticket -> ticket.getDepartureHour()==null);
        vehicle.getTickets().add(foundTicket);

        cellRepository.save(vehicle.getUser().getCell());

        return vehicleRepository.save(vehicle);
    }

    private static void validateVehicleInCell(Vehicle vehicle) {
        if(!vehicle.getUser().getCell().isHasVehicle()){
            throw new ParkingException(HttpStatus.BAD_REQUEST, "This cell doesn't have any vehicle parking in it");
        }
    }

    private static Ticket getTicket(Vehicle vehicle) {
        return vehicle.getTickets().stream().filter(ticket -> ticket.getDepartureHour() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ticket", "", ""));
    }

    private static void throwNotParkingException() {
        throw new ParkingException(HttpStatus.BAD_REQUEST, "Actually that car is not parking");
    }



    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicleRepository.delete(vehicle);
    }
}
