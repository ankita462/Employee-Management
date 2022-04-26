package com.java.employee.repository;

import com.java.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    @Query("select e from employees e where e.id in(select distinct user_id from user_roles where role_id=3)")
//    List<Employee> findAllUserRoles();

}
