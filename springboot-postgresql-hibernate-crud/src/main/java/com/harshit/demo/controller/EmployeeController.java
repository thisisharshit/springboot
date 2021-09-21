package com.harshit.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.demo.model.Employee;
import com.harshit.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	//get employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable(value = "id") int employeeId) {
		Optional<Employee> result = employeeRepository.findById(employeeId);
		Employee employee=null;
		if(result.isPresent()) {
			employee=result.get();
		}else {
			throw new RuntimeException("Did not find Employee with Id: "+employeeId);
		}
		return employee;
	}
	
	
	//save employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		employee.setId(0);
		employeeRepository.save(employee);
		return employee;
	}
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		Optional<Employee> result = employeeRepository.findById(employeeId);
		Employee employee=null;
		if(result.isPresent()) {
			employee=result.get();
		}else {
			throw new RuntimeException("Did not find Employee with Id: "+employeeId);
		}
		
		employeeRepository.deleteById(employeeId);
		return "Deleted Employee Id: "+employeeId;
	}
	
		
	
	
	
	
}
