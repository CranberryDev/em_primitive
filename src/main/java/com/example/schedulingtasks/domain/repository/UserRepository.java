package com.example.schedulingtasks.domain.repository;

import com.example.schedulingtasks.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
