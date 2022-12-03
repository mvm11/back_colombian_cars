package com.id.colombiancars.service;


import com.id.colombiancars.common.NotFoundException;
import com.id.colombiancars.common.ParkingException;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.Invoice;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.gateway.UserGateway;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.repository.InvoiceRepository;
import com.id.colombiancars.repository.UserRepository;
import com.id.colombiancars.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements UserGateway {


    @Autowired
    UserRepository userRepository;

    @Autowired
    CellRepository cellRepository;


    @Autowired
    InvoiceRepository invoiceRepository;
    Random random = new SecureRandom();


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        return getUser(userId);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "Id", userId));
    }

    @Override
    public User saveUser(UserRequest userRequest) {

        validateUserFields(userRequest);
        List<Cell> availableCells = getCells();
        validateAvailableCells(availableCells);
        Cell cell = availableCells.get(random.nextInt(availableCells.size()));
        cell.setOccupied(true);


        User user = buildUser(userRequest, cell);

        Invoice invoice = buildInvoice(user);

        invoiceRepository.save(invoice);

        userRepository.save(user);

        return user;
    }

    private static Invoice buildInvoice(User user) {
        return Invoice.builder()
                .amount(100000.0)
                .state("pending")
                .startDate(LocalDate.now())
                .cutOfDate(LocalDate.now().plusDays(30))
                .user(user)
                .build();
    }

    private static User buildUser(UserRequest userRequest, Cell cell) {
        return User.builder()
                .name(userRequest.getName())
                .lastname(userRequest.getLastname())
                .dni(userRequest.getDni())
                .cell(cell)
                .isSubscribed(true)
                .build();
    }

    private static void validateAvailableCells(List<Cell> availableCells) {
        if(availableCells.isEmpty()){
            throw new ParkingException(HttpStatus.INTERNAL_SERVER_ERROR, "There are not available cells");
        }
    }

    private List<Cell> getCells() {
        return cellRepository.findAll()
                .stream()
                .filter(cell -> !cell.isOccupied())
                .collect(Collectors.toList());

    }



    private final Map<String, Function<UserRequest, ?>> userRequestValidations = Map.of(
            "name", UserRequest::getName,
            "lastName", UserRequest::getLastname,
            "dni", UserRequest::getDni
    );

    private void validateUserFields(UserRequest userRequest) {
        userRequestValidations.entrySet().stream()
                .filter(entry -> entry.getValue().apply(userRequest) == null)
                .map(Map.Entry::getKey)
                .map(field -> String.format("The user's %s cannot be null", field))
                .map(s -> new ParkingException(HttpStatus.BAD_REQUEST, "Null values are not accepted"))
                .findFirst()
                .ifPresent(e -> {
                    throw e;
                });
    }

    @Override
    public User updateUser(Long userId, UserRequest userRequest) {
        validateUserFields(userRequest);
        User foundUser = getUser(userId);
        foundUser.setName(userRequest.getName());
        foundUser.setLastname(userRequest.getLastname());
        foundUser.setDni(userRequest.getDni());
        userRepository.save(foundUser);
        return foundUser;
    }

    @Override
    public void deleteUser(Long userId) {
        User foundUser = getUser(userId);
        foundUser.getCell().setOccupied(false);
        foundUser.getCell().setHasVehicle(false);
        cellRepository.save(foundUser.getCell());
        userRepository.delete(getUser(userId));
    }
}
