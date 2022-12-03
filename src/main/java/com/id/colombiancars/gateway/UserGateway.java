package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.User;
import com.id.colombiancars.request.UserRequest;

import java.util.List;

public interface UserGateway {


    List<User> findAllUsers();

    User findUserById(Long userId);
    User saveUser(UserRequest userRequest);
    User updateUser(Long userId, UserRequest userRequest);
    void deleteUser(Long userId);
}
