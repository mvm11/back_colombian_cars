package com.id.colombiancars.repository;

import com.id.colombiancars.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
