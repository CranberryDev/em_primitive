package com.example.schedulingtasks.domain.repository;

import com.example.schedulingtasks.domain.entity.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, String> {
}
