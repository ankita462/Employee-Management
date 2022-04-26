package com.java.employee.controller;

import com.java.employee.entity.Address;
import com.java.employee.exception.EmployeeNotFoundException;
import com.java.employee.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/get")
    public String get(@RequestHeader("Authorization") String jwt) {
        return jwt;
    }

    @PostMapping("employee/{userName}/addAddress")
    public ResponseEntity<?> addAddress(@PathVariable(value = "userName") String userName,
                                     @RequestHeader("Authorization") String jwtToken,
                                     @RequestBody Address addressRequest) throws EmployeeNotFoundException {
        return addressService.addAddress(userName,jwtToken,addressRequest);
    }

    @PutMapping("employee/{userName}/updateAddress")
    public ResponseEntity<?> updateAddress(@PathVariable(value = "userName") String userName,
                                        @RequestHeader("Authorization") String jwtToken,
                                        @RequestBody Address addressRequest) throws EmployeeNotFoundException {
        return addressService.updateAddress(userName,jwtToken,addressRequest);
    }

    @GetMapping("employee/{userName}/getAddress")
    public List<Address> getAddresses(@PathVariable(value = "userName") String userName,
                                      @RequestHeader("Authorization") String jwtToken) throws EmployeeNotFoundException {
        return addressService.getAddresses(userName,jwtToken);
    }

}
