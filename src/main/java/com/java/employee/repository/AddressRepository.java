package com.java.employee.repository;

import com.java.employee.entity.Address;
import com.java.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    //Optional<Address> findByIdAndPostId(Long id, Long employeeId);
    List<Address> findByEmployee(Employee employee);
}
