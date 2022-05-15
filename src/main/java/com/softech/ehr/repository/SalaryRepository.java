package com.softech.ehr.repository;
import com.softech.ehr.domain.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

}
