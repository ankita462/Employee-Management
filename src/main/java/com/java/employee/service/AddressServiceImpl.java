package com.java.employee.service;

import com.java.employee.entity.Address;
import com.java.employee.entity.Employee;
import com.java.employee.exception.EmployeeNotFoundException;
import com.java.employee.payload.response.MessageResponse;
import com.java.employee.repository.AddressRepository;
import com.java.employee.repository.EmployeeRepository;
import com.java.employee.service.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AddressService")
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> addAddress(String userName,String jwtToken, Address addressRequest) throws EmployeeNotFoundException {
        String username = jwtUtils.parseJwt(jwtToken);
        if(userName.equals(username)) {
            Employee employee= employeeRepository.findByUsername(userName).orElseThrow(() -> new EmployeeNotFoundException("Error: Username is not found."));
            addressRequest.setEmployee(employee);
            addressRepository.save(addressRequest);
            return ResponseEntity.ok(new MessageResponse("Address added Successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User is not authorized with valid username"));
    }

    @Override
    public ResponseEntity<?> updateAddress(String userName, String jwtToken, Address addressRequest) throws EmployeeNotFoundException {
        String username=jwtUtils.parseJwt(jwtToken);
        if(userName.equals(username)) {
            Employee employee= employeeRepository.findByUsername(userName).orElseThrow(() -> new EmployeeNotFoundException("Error: Username is not found."));
            List<Address> address=addressRepository.findByEmployee(employee);
            address.get(0).setAddress(addressRequest.getAddress());
            addressRepository.save(address.get(0));
            return ResponseEntity.ok(new MessageResponse("Address updated Successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User is not authorized with valid username"));
    }

    @Override
    public List<Address> getAddresses(String userName, String jwtToken) throws EmployeeNotFoundException {
        String username=jwtUtils.parseJwt(jwtToken);
        if(userName.equals(username)) {
            Employee employee=employeeRepository.findByUsername(userName).orElseThrow(() -> new EmployeeNotFoundException("Error: Username is not found."));
            return addressRepository.findByEmployee(employee);
        }
        return null;
    }


}
