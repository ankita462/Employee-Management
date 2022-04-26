package com.java.employee.service;


import com.java.employee.entity.Address;
import com.java.employee.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService{
    public ResponseEntity<?> addAddress(String userName,String jwtToken, Address address) throws EmployeeNotFoundException;

   public ResponseEntity<?> updateAddress(String userName, String jwtToken, Address addressRequest) throws EmployeeNotFoundException;

   public List<Address> getAddresses(String userName, String jwtToken) throws EmployeeNotFoundException;
}
