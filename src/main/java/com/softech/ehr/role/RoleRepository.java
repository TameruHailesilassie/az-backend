package com.softech.ehr.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
    boolean existsByName(String name);
}
