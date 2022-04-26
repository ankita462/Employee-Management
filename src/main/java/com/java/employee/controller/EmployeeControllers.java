package com.java.employee.controller;

import com.java.employee.entity.Employee;
import com.java.employee.payload.request.SignupRequest;
import com.java.employee.payload.response.MessageResponse;
import com.java.employee.repository.EmployeeRepository;
import com.java.employee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class EmployeeControllers {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuthController authController;

    //Admin
    @GetMapping("/viewAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getAllData() {
        return employeeRepository.findAll();
    }

    @PostMapping("/addAdminUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody SignupRequest signupRequest,@RequestHeader("Authorization") String jwtToken) {
            return authController.registerUser(signupRequest,jwtToken);
    }

    //Moderator
    @PostMapping("/addUser")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> addEmployee(@RequestBody SignupRequest signupRequest,@RequestHeader("Authorization") String jwtToken) {
        Set<String> strRoles = signupRequest.getRole();
        if(strRoles.contains("user")) {
            return authController.registerUser(signupRequest,jwtToken);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Moderator adds only users role"));
    }

    // Both
    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id,@RequestBody SignupRequest updateRequest) {
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User id is not found."));;
        emp.setUsername(updateRequest.getUsername());
        emp.setEmail(updateRequest.getEmail());
        emp.setPassword(updateRequest.getPassword());
        employeeRepository.save(emp);
        return ResponseEntity.ok(new MessageResponse("Employee Updated Successfully"));
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable("id") Long id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User id is not found."));;
        employee.setIsActive(false);
        employeeRepository.save(employee);
        return ResponseEntity.ok(new MessageResponse("User Deactivated"));
    }
}
