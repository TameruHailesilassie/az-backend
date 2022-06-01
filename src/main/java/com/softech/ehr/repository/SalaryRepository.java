package com.softech.ehr.repository;

import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByUser(User user);

}
